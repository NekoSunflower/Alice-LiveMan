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
package site.alice.liveman.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.mediaproxy.proxytask.MediaProxyTask;
import site.alice.liveman.model.*;
import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.service.live.LiveService;
import site.alice.liveman.service.live.LiveServiceFactory;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Component
public class AutoLiveManJob {
    private static final Logger             LOGGER = LoggerFactory.getLogger(AutoLiveManJob.class);
    @Autowired
    private              LiveServiceFactory liveServiceFactory;
    @Autowired
    private              LiveManSetting     liveManSetting;

    @Scheduled(cron = "0/5 * * * * ?")
    public void aliceLiveJob() {
        /* 获取频道状态信息 */
        CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
        accounts.parallelStream().forEach(accountInfo -> {
            CopyOnWriteArraySet<ChannelInfo> channels = accountInfo.getChannels();
            if (channels == null) {
                return;
            }
            for (ChannelInfo channelInfo : channels) {
                try {
                    LiveService liveService = liveServiceFactory.getLiveService(channelInfo.getChannelUrl());
                    VideoInfo videoInfo = liveService.getLiveVideoInfo(liveService.getLiveVideoInfoUrl(channelInfo), channelInfo, accountInfo, liveManSetting.getDefaultResolution());
                    VideoInfo currentVideoInfo = channelInfo.getVideoInfo();
                    if (currentVideoInfo == null) {
                        channelInfo.setVideoInfo(videoInfo);
                    } else {
                        BroadcastTask broadcastTask = currentVideoInfo.getBroadcastTask();
                        if (broadcastTask == null || broadcastTask.isTerminate()) {
                            if (currentVideoInfo.getVideoUnionId().equals(videoInfo.getVideoUnionId())) {
                                videoInfo.setBroadcastConfig(currentVideoInfo.getBroadcastConfig());
                                videoInfo.setTextLocations(currentVideoInfo.getTextLocations());
                            }
                            channelInfo.setVideoInfo(videoInfo);
                        }
                    }
                    if (videoInfo != null) {
                        LOGGER.info(accountInfo.getAccountId() + "@" + channelInfo.getChannelName() + "[" + channelInfo.getChannelUrl() + "]正在直播，媒体地址:" + videoInfo.getVideoInfoUrl());
                        BroadcastConfig defaultBroadcastConfig = channelInfo.getDefaultBroadcastConfig();
                        // 开始直播时，判断是否需要启动媒体代理服务器
                        if ((currentVideoInfo == null || !currentVideoInfo.getVideoUnionId().equals(videoInfo.getVideoUnionId())) && defaultBroadcastConfig != null && (defaultBroadcastConfig.isAutoBroadcast() || defaultBroadcastConfig.isNeedRecord())) {
                            LOGGER.info(accountInfo.getAccountId() + "@" + channelInfo.getChannelName() + "[" + channelInfo.getChannelUrl() + "]自动启动媒体代理服务，defaultBroadcastConfig=" + defaultBroadcastConfig);
                            MediaProxyManager.createProxy(videoInfo);
                        }
                    } else {
                        LOGGER.info(accountInfo.getAccountId() + "@" + channelInfo.getChannelName() + "[" + channelInfo.getChannelUrl() + "]没有正在直播的节目");
                    }
                    Thread.sleep(1000);
                } catch (Throwable e) {
                    LOGGER.error(accountInfo.getAccountId() + "@" + "获取 " + channelInfo.getChannelName() + "[" + channelInfo.getChannelUrl() + "] 频道信息失败", e);
                }
            }
        });
    }
}

