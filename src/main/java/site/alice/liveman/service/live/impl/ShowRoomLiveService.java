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
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.dataobject.dto.ChannelDTO;
import site.alice.liveman.dataobject.dto.VideoTaskDTO;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.dataobject.dto.SystemSettingDTO;
import site.alice.liveman.dataobject.dto.VideoTaskDTO;
import site.alice.liveman.service.live.LiveService;
import site.alice.liveman.utils.HttpRequestUtil;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShowRoomLiveService extends LiveService {

    @Autowired
    private              LiveManSetting liveManSetting;
    private static final Pattern        initDataPattern = Pattern.compile("<script id=\"js-initial-data\" data-json=\"(.+?)\"></script>");

    @Override
    public URI getLiveVideoInfoUrl(ChannelDTO channelDTO) throws Exception {
        return new URI(channelDTO.getChannelUrl());
    }

    @Override
    public VideoTaskDTO getLiveVideoInfo(URI videoInfoUrl, ChannelDTO channelDTO, String resolution) throws Exception {
        if (videoInfoUrl == null) {
            return null;
        }
        String channelHtml = HttpRequestUtil.downloadUrl(videoInfoUrl, channelDTO != null ? channelDTO.getCookies() : null, Collections.emptyMap(), StandardCharsets.UTF_8);
        Matcher matcher = initDataPattern.matcher(channelHtml);
        if (matcher.find()) {
            JSONObject liveDataObj = JSON.parseObject(StringEscapeUtils.unescapeHtml(matcher.group(1)));
            if (liveDataObj.getBoolean("isLive")) {
                String videoId = liveDataObj.getString("liveId");
                String videoTitle = liveDataObj.getString("roomName");
                URI m3u8ListUrl = new URI(liveDataObj.getString("streamingUrlHls"));
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
                return new VideoInfo(channelDTO, videoId, videoTitle, videoInfoUrl, m3u8ListUrl.resolve(mediaUrl), "m3u8");
            }
        }
        return null;
    }

    @Override
    protected boolean isMatch(URI channelUrl) {
        return channelUrl.getHost().contains("showroom-live.com");
    }
}
