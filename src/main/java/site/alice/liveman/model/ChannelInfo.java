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
package site.alice.liveman.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Objects;

public class ChannelInfo implements Serializable {
    private String          channelUrl;
    private String          channelName;
    private String          cookies;
    private BroadcastConfig defaultBroadcastConfig;
    @JSONField(serialize = false)
    private VideoInfo       videoInfo;
    @JSONField(serialize = false)
    private Long            startAt;
    @JSONField(serialize = false)
    private Long            endAt;

    public ChannelInfo() {
        this.defaultBroadcastConfig = new BroadcastConfig();
    }

    public ChannelInfo(String channelName, String channelUrl) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.defaultBroadcastConfig = new BroadcastConfig();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        if (channelUrl != null) {
            this.channelUrl = channelUrl.trim();
        } else {
            this.channelUrl = null;
        }
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoInfo videoInfo) {
        this.videoInfo = videoInfo;
    }

    public Long getStartAt() {
        return startAt;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public BroadcastConfig getDefaultBroadcastConfig() {
        return defaultBroadcastConfig;
    }

    public void setDefaultBroadcastConfig(BroadcastConfig defaultBroadcastConfig) {
        this.defaultBroadcastConfig = defaultBroadcastConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelInfo that = (ChannelInfo) o;
        return Objects.equals(channelUrl, that.channelUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(channelUrl);
    }

}
