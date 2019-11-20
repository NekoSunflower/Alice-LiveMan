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
import java.util.concurrent.CopyOnWriteArraySet;

public class ChannelInfo implements Serializable {
    private String                               channelUrl;
    private String                               channelName;
    private CopyOnWriteArraySet<BroadcastConfig> defaultBroadcastConfigs;
    @JSONField(serialize = false)
    private Long                                 startAt;
    @JSONField(serialize = false)
    private Long                                 endAt;

    public ChannelInfo() {
        this.defaultBroadcastConfigs = new CopyOnWriteArraySet<>();
    }

    public ChannelInfo(String channelName, String channelUrl) {
        this.channelName = channelName;
        this.channelUrl = channelUrl;
        this.defaultBroadcastConfigs = new CopyOnWriteArraySet<>();
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

    public CopyOnWriteArraySet<BroadcastConfig> getDefaultBroadcastConfigs() {
        return defaultBroadcastConfigs;
    }

    public void setDefaultBroadcastConfigs(CopyOnWriteArraySet<BroadcastConfig> defaultBroadcastConfigs) {
        this.defaultBroadcastConfigs = defaultBroadcastConfigs;
    }

    public BroadcastConfig getDefaultBroadcastConfig(AccountInfo accountInfo) {
        for (BroadcastConfig defaultBroadcastConfig : defaultBroadcastConfigs) {
            if (accountInfo.getAccountId().equals(defaultBroadcastConfig.getAccountId())) {
                return defaultBroadcastConfig;
            }
        }
        return null;
    }

    public void addDefaultBroadcastConfig(BroadcastConfig broadcastConfig) {
        if (!this.defaultBroadcastConfigs.add(broadcastConfig)) {
            this.defaultBroadcastConfigs.remove(broadcastConfig);
            this.defaultBroadcastConfigs.add(broadcastConfig);
        }
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
