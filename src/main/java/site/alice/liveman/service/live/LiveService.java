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
package site.alice.liveman.service.live;

import site.alice.liveman.jenum.VideoBannedTypeEnum;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.model.VideoInfo;
import site.alice.liveman.mediaproxy.proxytask.MediaProxyTask;

import java.net.URI;

public abstract class LiveService {

    public abstract URI getLiveVideoInfoUrl(ChannelInfo channelInfo) throws Exception;

    protected abstract VideoInfo getLiveVideoInfo0(URI videoInfoUrl, ChannelInfo channelInfo, AccountInfo accountInfo, String resolution) throws Exception;

    public VideoInfo getLiveVideoInfo(URI videoInfoUrl, ChannelInfo channelInfo, AccountInfo accountInfo, String resolution) throws Exception {
        VideoInfo videoInfo = getLiveVideoInfo0(videoInfoUrl, channelInfo, accountInfo, resolution);
        if (videoInfo != null) {
            videoInfo.setRequiredResolution(resolution);
        }
        return videoInfo;
    }

    protected abstract boolean isMatch(URI channelUrl);
}
