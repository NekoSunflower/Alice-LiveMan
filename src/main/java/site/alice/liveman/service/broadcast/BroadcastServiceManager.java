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
package site.alice.liveman.service.broadcast;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import site.alice.liveman.event.MediaProxyEvent;
import site.alice.liveman.event.MediaProxyEventListener;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.mediaproxy.proxytask.MediaProxyTask;
import site.alice.liveman.model.*;
import site.alice.liveman.service.BroadcastServerService;
import site.alice.liveman.service.MediaHistoryService;
import site.alice.liveman.service.VideoFilterService;
import site.alice.liveman.service.dynamic.post.impl.DynamicPostManager;
import site.alice.liveman.service.external.ImageSegmentService;
import site.alice.liveman.service.external.TextLocationService;
import site.alice.liveman.service.live.LiveServiceFactory;
import site.alice.liveman.utils.ThreadPoolUtil;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class BroadcastServiceManager implements ApplicationContextAware {
    private Map<String, BroadcastService> broadcastServiceMap;
    @Autowired
    private LiveManSetting                liveManSetting;
    @Autowired
    private DynamicPostManager            dynamicPostManager;
    @Autowired
    private MediaHistoryService           mediaHistoryService;
    @Autowired
    private LiveServiceFactory            liveServiceFactory;
    @Autowired
    private BroadcastServerService        broadcastServerService;
    @Autowired
    private TextLocationService           textLocationService;
    @Autowired
    private ImageSegmentService           imageSegmentService;
    @Autowired
    private VideoFilterService            videoFilterService;

    @PostConstruct
    public void init() {
        MediaProxyManager.addListener(new MediaProxyEventListener() {
            @Override
            public void onProxyStart(MediaProxyEvent e) {
                MediaProxyTask mediaProxyTask = e.getMediaProxyTask();
                VideoInfo videoInfo = mediaProxyTask.getVideoInfo();
                if (videoInfo != null) {
                    if (mediaProxyTask.isShadowProxyTask()) {
                        return;
                    }
                    List<AccountInfo> broadcastAccounts = getBroadcastAccount(videoInfo);
                    for (AccountInfo broadcastAccount : broadcastAccounts) {
                        // 添加默认推流账号的推流任务
                        try {
                            createBroadcastTask(videoInfo, broadcastAccount, false);
                        } catch (Throwable throwable) {
                            log.error("启动推流任务时发生异常", throwable);
                        }
                    }
                }
            }

            @Override
            public void onProxyStop(MediaProxyEvent e) {
                final VideoInfo videoInfo = e.getMediaProxyTask().getVideoInfo();
                if (videoInfo != null) {
                    if (videoInfo.getChannelInfo() == null) {
                        return;
                    }
                    final Set<BroadcastTask> broadcastTasks = videoInfo.getBroadcastTasks();
                    for (BroadcastTask broadcastTask : broadcastTasks) {
                        AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
                        if (broadcastAccount != null) {
                            broadcastAccount.removeCurrentVideo(videoInfo);
                        }
                        videoInfo.removeBroadcastTask(broadcastTask);
                        if (!broadcastTask.isTerminate()) {
                            broadcastTask.terminateTask();
                        }
                    }
                    ThreadPoolUtil.schedule(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                log.info("检查节目[" + videoInfo.getVideoInfoUrl() + "]是否仍然在直播中...");
                                VideoInfo liveVideoInfo;
                                AccountInfo privateAccount = videoInfo.getPrivateAccount();
                                if (privateAccount == null) {
                                    liveVideoInfo = liveServiceFactory.getLiveService(videoInfo.getVideoInfoUrl().toString()).getLiveVideoInfo(videoInfo.getVideoInfoUrl(), videoInfo.getChannelInfo(), null, liveManSetting.getDefaultResolution());
                                } else {
                                    BroadcastConfig broadcastConfig = videoInfo.getBroadcastConfig(privateAccount);
                                    liveVideoInfo = liveServiceFactory.getLiveService(videoInfo.getVideoInfoUrl().toString()).getLiveVideoInfo(videoInfo.getVideoInfoUrl(), videoInfo.getChannelInfo(), broadcastConfig.getCookies(), liveManSetting.getDefaultResolution());
                                }
                                if (liveVideoInfo == null) {
                                    log.info("节目[" + videoInfo.getVideoInfoUrl() + "]当前已停止直播！");
                                } else {
                                    log.info("节目[" + videoInfo.getVideoInfoUrl() + "]当前依然在直播中！");
                                    liveVideoInfo.setPrivateAccount(privateAccount);
                                    liveVideoInfo.setTextLocations(videoInfo.getTextLocations());
                                    liveVideoInfo.setBroadcastConfigs(videoInfo.getBroadcastConfigs());
                                    for (BroadcastTask broadcastTask : broadcastTasks) {
                                        AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
                                        try {
                                            if (broadcastAccount != null) {
                                                log.info("节目[" + videoInfo.getVideoInfoUrl() + "]推流账号[" + broadcastAccount.getAccountId() + "]中断自动恢复...");
                                                createSingleBroadcastTask(liveVideoInfo, broadcastAccount);
                                            }
                                        } catch (Exception e) {
                                            log.error("节目[" + videoInfo.getVideoInfoUrl() + "]推流账号[" + broadcastAccount.getAccountId() + "]中断自动恢复操作失败", e);
                                        }
                                    }

                                }
                            } catch (Throwable e) {
                                log.info("节目[" + videoInfo.getVideoInfoUrl() + "]中断自动恢复操作失败", e);
                            }
                        }
                    }, 2, TimeUnit.SECONDS);
                }
            }
        });
    }

    public BroadcastTask createSingleBroadcastTask(VideoInfo videoInfo, AccountInfo broadcastAccount) throws Exception {
        return createBroadcastTask(videoInfo, broadcastAccount, true);
    }

    public BroadcastTask createBroadcastTask(VideoInfo videoInfo, AccountInfo broadcastAccount, boolean singleTask) throws Exception {
        if (broadcastAccount.setCurrentVideo(videoInfo)) {
            try {
                Map<String, MediaProxyTask> executedProxyTaskMap = MediaProxyManager.getExecutedProxyTaskMap();
                MediaProxyTask mediaProxyTask = executedProxyTaskMap.get(videoInfo.getVideoUnionId());
                BroadcastTask broadcastTask = new BroadcastTask(this, videoInfo, broadcastAccount);
                broadcastTask.setSingleTask(singleTask);
                // 创建媒体流代理任务
                if (!videoInfo.addBroadcastTask(broadcastTask)) {
                    throw new RuntimeException("试图创建推流任务的媒体资源已存在相同推流任务[accountId=" + broadcastAccount.getAccountId() + ", videoId=" + videoInfo.getVideoUnionId() + "]");
                }
                // 如果要推流的媒体不存在，则创建媒体流代理任务
                if (mediaProxyTask == null) {
                    mediaProxyTask = MediaProxyManager.createProxy(videoInfo);
                    if (mediaProxyTask == null) {
                        throw new RuntimeException("MediaProxyTask创建失败");
                    }
                }
                ThreadPoolUtil.execute(broadcastTask);
                return broadcastTask;
            } catch (Exception e) {
                // 操作失败，释放刚才获得的直播间资源
                broadcastAccount.removeCurrentVideo(videoInfo);
                throw e;
            }
        } else {
            VideoInfo currentVideo = broadcastAccount.getCurrentVideo();
            if (currentVideo != null) {
                BroadcastTask broadcastTask = currentVideo.getBroadcastTask(broadcastAccount);
                if (broadcastTask != null && !broadcastTask.isTerminate()) {
                    throw new RuntimeException("无法创建转播任务，直播间已被节目[" + currentVideo.getTitle() + "]占用！");
                }
                log.info("直播间[roomId=" + broadcastAccount.getRoomId() + "]当前正在被占用的节目[" + currentVideo.getVideoUnionId() + "]已不存在，清除CurrentVideo标记！");
                broadcastAccount.removeCurrentVideo(currentVideo);
            }
            return createSingleBroadcastTask(videoInfo, broadcastAccount);
        }
    }

    public List<AccountInfo> getBroadcastAccount(VideoInfo videoInfo) {
        ChannelInfo channelInfo = videoInfo.getChannelInfo();
        List<AccountInfo> accountInfoList = new ArrayList<>();
        CopyOnWriteArraySet<BroadcastConfig> defaultBroadcastConfigs = channelInfo.getDefaultBroadcastConfigs();
        for (BroadcastConfig defaultBroadcastConfig : defaultBroadcastConfigs) {
            if (defaultBroadcastConfig.isAutoBroadcast()) {
                AccountInfo accountInfo = defaultBroadcastConfig.getAccountInfo();
                String logInfo = "频道[" + channelInfo.getChannelName() + "], videoId=" + videoInfo.getVideoUnionId() + "的默认直播间[" + accountInfo.getAccountId() + "]";
                if (accountInfo.isDisable()) {
                    log.info(logInfo + "的账号信息不可用");
                } else if (!accountInfo.setCurrentVideo(videoInfo)) {
                    log.info(logInfo + "已被占用[videoInfo=" + accountInfo.getCurrentVideo().getVideoUnionId() + "]");
                } else {
                    log.info(logInfo + "已添加到推流账户列表中");
                    accountInfoList.add(defaultBroadcastConfig.getAccountInfo());
                }
            }
        }
        if (accountInfoList.isEmpty()) {
            log.info("频道[" + channelInfo.getChannelName() + "], videoId=" + videoInfo.getVideoUnionId() + "没有找到可以推流的直播间");
        }
        return accountInfoList;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        broadcastServiceMap = applicationContext.getBeansOfType(BroadcastService.class);
    }

    public BroadcastService getBroadcastService(String accountSite) {
        for (BroadcastService broadcastService : broadcastServiceMap.values()) {
            if (broadcastService.isMatch(accountSite)) {
                return broadcastService;
            }
        }
        throw new BeanDefinitionStoreException("没有找到可以推流到[" + accountSite + "]的BroadcastService");
    }

    public LiveManSetting getLiveManSetting() {
        return liveManSetting;
    }

    public void setLiveManSetting(LiveManSetting liveManSetting) {
        this.liveManSetting = liveManSetting;
    }

    public DynamicPostManager getDynamicPostManager() {
        return dynamicPostManager;
    }

    public MediaHistoryService getMediaHistoryService() {
        return mediaHistoryService;
    }

    public LiveServiceFactory getLiveServiceFactory() {
        return liveServiceFactory;
    }


    public BroadcastServerService getBroadcastServerService() {
        return broadcastServerService;
    }


    public TextLocationService getTextLocationService() {
        return textLocationService;
    }


    public ImageSegmentService getImageSegmentService() {
        return imageSegmentService;
    }


    public VideoFilterService getVideoFilterService() {
        return videoFilterService;
    }

}
