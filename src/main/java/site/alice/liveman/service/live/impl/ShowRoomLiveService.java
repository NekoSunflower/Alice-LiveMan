/*
 * <Alice LiveMan>
 * Copyright (C) <2018>  <NekoSunflower>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package site.alice.liveman.service.live.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.model.VideoInfo;
import site.alice.liveman.service.live.LiveService;
import site.alice.liveman.utils.HttpRequestUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ShowRoomLiveService extends LiveService {

    @Autowired
    private              LiveManSetting liveManSetting;
    private static final Pattern        initDataPattern = Pattern.compile("<script id=\"js-initial-data\" data-json=\"(.+?)\"></script>");
    private static final String         STREAMING_URL   = "https://www.showroom-live.com/api/live/streaming_url?ignore_low_stream=1&room_id=";

    @Override
    public URI getLiveVideoInfoUrl(ChannelInfo channelInfo) throws Exception {
        return new URI(channelInfo.getChannelUrl());
    }

    @Override
    public VideoInfo getLiveVideoInfo0(URI videoInfoUrl, ChannelInfo channelInfo, AccountInfo accountInfo, String resolution) throws Exception {
        if (videoInfoUrl == null) {
            return null;
        }
        String channelHtml = HttpRequestUtil.downloadUrl(videoInfoUrl, channelInfo.getCookies(), Collections.emptyMap(), StandardCharsets.UTF_8);
        Matcher matcher = initDataPattern.matcher(channelHtml);
        if (matcher.find()) {
            JSONObject liveDataObj = JSON.parseObject(StringEscapeUtils.unescapeHtml(matcher.group(1)));
            if (liveDataObj.getBoolean("isLive")) {
                String videoId = liveDataObj.getString("liveId");
                String videoTitle = liveDataObj.getString("roomName");
                String streamingUrlJSON = HttpRequestUtil.downloadUrl(URI.create(STREAMING_URL + liveDataObj.getString("roomId")), channelInfo.getCookies(), Collections.emptyMap(), StandardCharsets.UTF_8);
                Optional<String> hlsUrlOptional = JSON.parseObject(streamingUrlJSON).getJSONArray("streaming_url_list").stream().filter(url -> "hls".equals(((JSONObject) url).getString("type"))).map(url -> ((JSONObject) url).getString("url")).findFirst();
                if (hlsUrlOptional.isPresent()) {
                    URI m3u8ListUrl = new URI(hlsUrlOptional.get());
                    String[] m3u8List = HttpRequestUtil.downloadUrl(m3u8ListUrl, StandardCharsets.UTF_8).split("\n");
                    String mediaUrl = null;
                    for (int i = 0; i < m3u8List.length; i++) {
                        if (m3u8List[i].contains(resolution)) {
                            mediaUrl = m3u8List[i + 1];
                            break;
                        }
                    }
                    if (mediaUrl == null) {
                        mediaUrl = m3u8List[3];
                    }
                    return new VideoInfo(channelInfo, accountInfo, videoId, videoTitle, videoInfoUrl, m3u8ListUrl.resolve(mediaUrl), "m3u8");
                } else {
                    log.warn("没有找到节目[" + videoInfoUrl + "]的HLS媒体流信息:\n" + streamingUrlJSON);
                }
            }
        }
        return null;
    }

    @Override
    protected boolean isMatch(URI channelUrl) {
        return channelUrl.getHost().contains("showroom-live.com");
    }
}
