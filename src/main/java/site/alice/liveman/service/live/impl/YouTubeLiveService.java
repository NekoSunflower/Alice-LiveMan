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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.model.VideoInfo;
import site.alice.liveman.service.live.LiveService;
import site.alice.liveman.utils.HttpRequestUtil;
import site.alice.liveman.utils.M3u8Util;
import site.alice.liveman.utils.M3u8Util.StreamInfo;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class YouTubeLiveService extends LiveService {

    private static final String  GET_VIDEO_INFO_URL       = "https://www.youtube.com/watch?v=";
    private static final Pattern hlsvpPattern             = Pattern.compile("(\\\\\\\"hlsManifestUrl\\\\\\\":\\\\\\\"(.+?)\\\\\\\")|(\"hlsManifestUrl\":\"(.+?)\")");
    private static final Pattern videoTitlePattern        = Pattern.compile(",\"title\":\"(.+?)\"");
    private static final Pattern videoIdPattern           = Pattern.compile("/id/(.+?)/");
    private static final Pattern browseIdPattern          = Pattern.compile("RICH_METADATA_RENDERER_STYLE_BOX_ART.+?\\{\"browseId\":\"(.+?)\"}");
    private static final Pattern playabilityStatusPattern = Pattern.compile("\"playerErrorMessageRenderer\":\\{\"subreason\":\\{\"runs\":\\[\\{\"text\":\"(.+?)\"}]}");

    @Override
    public URI getLiveVideoInfoUrl(ChannelInfo channelInfo) throws Exception {
        return new URI(channelInfo.getChannelUrl() + "/").resolve("live");
    }

    @Override
    public VideoInfo getLiveVideoInfo(URI videoInfoUrl, ChannelInfo channelInfo, String resolution) throws Exception {
        if (videoInfoUrl == null) {
            return null;
        }
        String videoInfoRes = HttpRequestUtil.downloadUrl(videoInfoUrl, channelInfo != null ? channelInfo.getCookies() : null, Collections.emptyMap(), StandardCharsets.UTF_8);
        Matcher hlsvpMatcher = hlsvpPattern.matcher(videoInfoRes);
        Matcher videoTitleMatcher = videoTitlePattern.matcher(videoInfoRes);
        Matcher browseIdMatcher = browseIdPattern.matcher(videoInfoRes);
        String videoTitle = "";
        String description = "";
        String videoId = "";
        if (hlsvpMatcher.find()) {
            if (!videoInfoRes.contains("\\\"isLive\\\":true") && !videoInfoRes.contains("\"isLive\":true")) {
                log.info("节目[" + videoInfoUrl + "]已停止直播(isLive=false)");
                return null;
            }
            String hlsvpMatchResult = hlsvpMatcher.group(2) == null ? hlsvpMatcher.group(4) : hlsvpMatcher.group(2);
            String hlsvpUrl = URLDecoder.decode(StringEscapeUtils.unescapeJava(hlsvpMatchResult), StandardCharsets.UTF_8.name());
            Matcher videoIdMatcher = videoIdPattern.matcher(hlsvpUrl);
            if (videoIdMatcher.find()) {
                videoId = videoIdMatcher.group(1);
            }
            if (StringUtils.isEmpty(videoId)) {
                throw new RuntimeException("获取视频VideoId失败！");
            }
            if (videoTitleMatcher.find()) {
                videoTitle = videoTitleMatcher.group(1).replace("\\/", "/");
            }
            if (browseIdMatcher.find()) {
                description = browseIdMatcher.group(1);
            }
            String[] m3u8List = HttpRequestUtil.downloadUrl(new URI(hlsvpUrl), StandardCharsets.UTF_8).split("\n");
            String mediaUrl = null;
            StreamInfo streamInfo = null;
            for (int i = 0; i < m3u8List.length; i++) {
                if (m3u8List[i].startsWith("#EXT-X-STREAM-INF") && m3u8List[i].contains(resolution)) {
                    streamInfo = M3u8Util.getStreamInfo(m3u8List[i]);
                    mediaUrl = m3u8List[i + 1];
                    break;
                }
            }
            if (mediaUrl == null) {
                streamInfo = M3u8Util.getStreamInfo(m3u8List[m3u8List.length - 2]);
                mediaUrl = m3u8List[m3u8List.length - 1];
            }
            String[] videoParts = videoId.split("\\.");
            VideoInfo videoInfo = new VideoInfo(channelInfo, videoParts[0], videoTitle, videoInfoUrl, new URI(mediaUrl), "m3u8");
            if (videoParts.length > 1) {
                videoInfo.setPart(videoParts[1]);
            }
            videoInfo.setDescription(description);
            videoInfo.setResolution(streamInfo.getResolution());
            videoInfo.setFrameRate(streamInfo.getFrameRate());
            return videoInfo;
        } else if (!videoInfoRes.contains("ytInitialData")) {
            throw new RuntimeException("没有找到InitData[" + videoInfoUrl + "]");
        } else if (videoInfoRes.contains("LOGIN_REQUIRED")) {
            Matcher matcher = playabilityStatusPattern.matcher(videoInfoRes);
            throw new RuntimeException("请求的直播节目需要登陆，请提供登陆Cookies" + (matcher.find() ? "，错误信息：" + matcher.group(1) : ""));
        }
        return null;
    }

    @Override
    public boolean isMatch(URI channelUrl) {
        return channelUrl.getHost().contains("youtube.com");
    }
}
