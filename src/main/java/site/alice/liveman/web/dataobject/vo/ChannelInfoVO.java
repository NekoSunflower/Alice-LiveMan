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

package site.alice.liveman.web.dataobject.vo;

import site.alice.liveman.model.BroadcastConfig;

public class ChannelInfoVO {
    private String          channelUrl;
    private String          channelName;
    private BroadcastConfig defaultBroadcastConfig;

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public BroadcastConfig getDefaultBroadcastConfig() {
        return defaultBroadcastConfig;
    }

    public void setDefaultBroadcastConfig(BroadcastConfig defaultBroadcastConfig) {
        this.defaultBroadcastConfig = defaultBroadcastConfig;
    }
}
