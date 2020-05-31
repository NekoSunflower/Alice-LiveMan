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
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import site.alice.liveman.customlayout.CustomLayout;
import site.alice.liveman.customlayout.impl.ImageSegmentBlurLayout;
import site.alice.liveman.jenum.VideoBannedTypeEnum;
import site.alice.liveman.jenum.VideoResolutionEnum;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.mediaproxy.proxytask.MediaProxyTask;
import site.alice.liveman.model.*;
import site.alice.liveman.service.external.consumer.impl.ImageSegmentConsumerImpl;
import site.alice.liveman.service.external.consumer.impl.TextLocationConsumerImpl;
import site.alice.liveman.utils.FfmpegUtil;
import site.alice.liveman.utils.ProcessUtil;
import site.alice.liveman.utils.ThreadPoolUtil;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class BroadcastTask implements Runnable {

    private BroadcastServiceManager broadcastServiceManager;
    private Pattern                 logSpeedPattern = Pattern.compile("speed=([0-9\\.\\s]+)x");
    private VideoInfo               videoInfo;
    private long                    pid;
    private AccountInfo             broadcastAccount;
    private boolean                 terminate;
    private boolean                 singleTask;
    private long                    lastLogTime;
    private float                   health;
    private VideoInfo               lowVideoInfo;

    public BroadcastTask(BroadcastServiceManager broadcastServiceManager, VideoInfo videoInfo, AccountInfo broadcastAccount) {
        this.broadcastServiceManager = broadcastServiceManager;
        this.videoInfo = videoInfo;
        this.broadcastAccount = broadcastAccount;
        // 从频道中拷贝默认配置信息
        BroadcastConfig defaultBroadcastConfig = videoInfo.getChannelInfo().getDefaultBroadcastConfig();
        if (videoInfo.getBroadcastConfig() == null && defaultBroadcastConfig != null) {
            BroadcastConfig broadcastConfig = new BroadcastConfig();
            BeanUtils.copyProperties(defaultBroadcastConfig, broadcastConfig);
            videoInfo.setBroadcastConfig(broadcastConfig);
        }
        if (videoInfo.getBroadcastConfig() == null) {
            BroadcastConfig broadcastConfig = new BroadcastConfig();
            videoInfo.setBroadcastConfig(broadcastConfig);
        }
    }

    public VideoInfo getVideoInfo() {
        return videoInfo;
    }

    public long getPid() {
        return pid;
    }

    public AccountInfo getBroadcastAccount() {
        return broadcastAccount;
    }

    public boolean isSingleTask() {
        return singleTask;
    }

    public void setSingleTask(boolean singleTask) {
        this.singleTask = singleTask;
    }

    @Override
    public synchronized void run() {
        try {
            // 任务第一次启动时尝试用默认的转播账号进行一次转播
            if (!singleTask) {
                broadcastAccount.setDisable(false);
            }
            ThreadPoolUtil.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (broadcastAccount != null && videoInfo.getBroadcastConfig().getVideoBannedType() == VideoBannedTypeEnum.CUSTOM_SCREEN && videoInfo.getBroadcastConfig().isAutoBlur()) {
                            MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoInfo.getVideoUnionId());
                            if (mediaProxyTask != null) {
                                broadcastServiceManager.getTextLocationService().requireTextLocation(mediaProxyTask.getKeyFrame().getFrameImage(), new TextLocationConsumerImpl(BroadcastTask.this));
                            }
                        }
                    } catch (Throwable e) {
                        log.error("requireTextLocation failed", e);
                    }
                    if (!terminate) {
                        ThreadPoolUtil.schedule(this, 30, TimeUnit.SECONDS);
                    }
                }
            }, 30, TimeUnit.SECONDS);
            ThreadPoolUtil.schedule(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (broadcastAccount != null && videoInfo.getBroadcastConfig().getVideoBannedType() == VideoBannedTypeEnum.CUSTOM_SCREEN && videoInfo.getBroadcastConfig().isAutoImageSegment()) {
                            MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoInfo.getVideoUnionId());
                            if (mediaProxyTask != null) {
                                broadcastServiceManager.getImageSegmentService().imageSegment(mediaProxyTask.getKeyFrame().getFrameImage(), new ImageSegmentConsumerImpl(BroadcastTask.this));
                            }
                        } else {
                            CopyOnWriteArrayList<CustomLayout> layouts = videoInfo.getBroadcastConfig().getLayouts();
                            if (layouts != null) {
                                layouts.removeIf(layout -> layout instanceof ImageSegmentBlurLayout);
                            }
                        }
                    } catch (Throwable e) {
                        log.error("requireImageSegment failed", e);
                    }
                    if (!terminate) {
                        ThreadPoolUtil.schedule(this, 10, TimeUnit.SECONDS);
                    }
                }
            }, 10, TimeUnit.SECONDS);
            Map<String, MediaProxyTask> executedProxyTaskMap = MediaProxyManager.getExecutedProxyTaskMap();
            while (executedProxyTaskMap.containsKey(videoInfo.getVideoUnionId()) && !terminate) {
                try {
                    if (!singleTask) {
                        if (broadcastServiceManager.getVideoFilterService().doFilter(videoInfo)) {
                            MediaHistory mediaHistory = broadcastServiceManager.getMediaHistoryService().getMediaHistory(videoInfo.getVideoUnionId());
                            if (mediaHistory == null || !mediaHistory.isPostDynamic()) {
                                broadcastServiceManager.getDynamicPostManager().postDynamic(this, broadcastAccount);
                                if (mediaHistory != null) {
                                    mediaHistory.setPostDynamic(true);
                                }
                            }
                        } else {
                            terminateTask();
                        }
                    }
                    while (executedProxyTaskMap.containsKey(videoInfo.getVideoUnionId()) && !terminate && broadcastAccount != null && broadcastAccount.getCurrentVideo() == videoInfo && !broadcastAccount.isDisable()) {
                        try {
                            BroadcastService broadcastService = broadcastServiceManager.getBroadcastService(broadcastAccount.getAccountSite());
                            if (broadcastAccount.readCookies() == null) {
                                broadcastAccount.setDisable(true);
                                throw new RuntimeException("转播启动失败，账户Cookies为空！提示：如果是自动转播请检查【我的账号】中【自动保存Cookies】选项是否已开启，如果是手动认领请尝试重新登录或联系管理员。");
                            }
                            String broadcastAddress = broadcastService.getBroadcastAddress(broadcastAccount);
                            if (broadcastAccount.isAutoRoomTitle()) {
                                broadcastService.setBroadcastSetting(broadcastAccount, videoInfo.getTitle(), null);
                            }
                            String ffmpegCmdLine;
                            // 如果是区域打码或自定义的，创建低分辨率媒体代理服务
                            pid = 0;
                            ServerInfo availableServer = null;
                            BroadcastConfig broadcastConfig = videoInfo.getBroadcastConfig();
                            switch (broadcastConfig.getVideoBannedType()) {
                                case CUSTOM_SCREEN: {
                                    health = -1;
                                    VideoResolutionEnum broadcastResolution = broadcastConfig.getBroadcastResolution();
                                    if (broadcastResolution == null) {
                                        broadcastConfig.setBroadcastResolution(VideoResolutionEnum.R720F30);
                                    }
                                    broadcastResolution = broadcastConfig.getBroadcastResolution();
                                    int performance = broadcastResolution.getPerformance();
                                    int serverPoint = broadcastServiceManager.getLiveManSetting().getServerPoints()[performance];
                                    if (broadcastAccount.getPoint() < serverPoint && broadcastAccount.getBillTimeMap().get(performance) == null) {
                                        terminateTask();
                                        throw new RuntimeException("账户积分不足[roomId=" + broadcastAccount.getRoomId() + ", point=" + broadcastAccount.getPoint() + ", need=" + serverPoint + "]");
                                    }
                                    VideoInfo _lowVideoInfo = broadcastServiceManager.getLiveServiceFactory().getLiveService(videoInfo.getVideoInfoUrl().toString()).getLiveVideoInfo(videoInfo.getVideoInfoUrl(), videoInfo.getChannelInfo(), broadcastAccount, broadcastResolution.getResolution() + "");
                                    if (_lowVideoInfo == null) {
                                        throw new RuntimeException("获取低清晰度视频源信息失败");
                                    }
                                    _lowVideoInfo.setLowVideoInfo(true);
                                    _lowVideoInfo.setAccountInfo(videoInfo.getAccountInfo());
                                    if (!_lowVideoInfo.getVideoUnionId().equals(videoInfo.getVideoUnionId())) {
                                        MediaProxyTask mediaProxyTask = executedProxyTaskMap.get(_lowVideoInfo.getVideoUnionId());
                                        if (mediaProxyTask != null) {
                                            lowVideoInfo = mediaProxyTask.getVideoInfo();
                                        } else {
                                            lowVideoInfo = _lowVideoInfo;
                                            MediaProxyManager.createProxy(lowVideoInfo);
                                        }
                                        lowVideoInfo.setBroadcastTask(this);
                                        lowVideoInfo.setBroadcastConfig(broadcastConfig);
                                        ffmpegCmdLine = FfmpegUtil.buildFfmpegCmdLine(lowVideoInfo, broadcastConfig, broadcastAddress);
                                    } else {
                                        deleteLowVideoTask();
                                        ffmpegCmdLine = FfmpegUtil.buildFfmpegCmdLine(videoInfo, broadcastConfig, broadcastAddress);
                                    }
                                    // pid = ProcessUtil.createProcess(ffmpegCmdLine, videoInfo.getVideoUnionId());
                                    availableServer = broadcastServiceManager.getBroadcastServerService().getAvailableServer(this);
                                    if (availableServer != null) {
                                        pid = ProcessUtil.createRemoteProcess(ffmpegCmdLine, availableServer, true, videoInfo.getVideoUnionId());
                                    } else {
                                        continue;
                                    }
                                    break;
                                }
                                default: {
                                    // 如果不是区域打码了自动终止创建的低清晰度媒体代理任务
                                    deleteLowVideoTask();
                                    ffmpegCmdLine = FfmpegUtil.buildFfmpegCmdLine(videoInfo, broadcastConfig, broadcastAddress);
                                    pid = ProcessUtil.createProcess(ffmpegCmdLine, videoInfo.getVideoUnionId());
                                }
                            }
                            log.info("[" + broadcastAccount.getRoomId() + "@" + broadcastAccount.getAccountSite() + ", videoId=" + videoInfo.getVideoUnionId() + "]推流进程已启动[PID:" + pid + "]");
                            health = 0;
                            long lastHitTime = 0;
                            int lowHealthCount = 0;
                            long lastLogLength = 0;
                            // 等待进程退出或者任务结束
                            while (broadcastAccount.getCurrentVideo() == videoInfo && !ProcessUtil.waitProcess(pid, 1000)) {
                                ProcessUtil.AliceProcess aliceProcess = ProcessUtil.getAliceProcess(pid);
                                if (aliceProcess == null) {
                                    continue;
                                }
                                if (availableServer != null && availableServer.getBroadcastTask() != this) {
                                    log.warn("推流服务器已被释放，终止推流进程[videoId=" + videoInfo.getVideoUnionId() + "]...");
                                    break;
                                }
                                File logFile = aliceProcess.getProcessBuilder().redirectOutput().file();
                                if (logFile != null && logFile.length() > 1024) {
                                    if (lastLogLength != logFile.length()) {
                                        lastLogLength = logFile.length();
                                        lastLogTime = System.nanoTime();
                                    }
                                    long dt = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - lastLogTime);
                                    if (dt > 10000) {
                                        log.warn("持续" + dt + "毫秒没有推流日志输出，终止推流进程...[pid:" + pid + ", logFile:\"" + logFile + "\"]");
                                        ProcessUtil.killProcess(pid);
                                        continue;
                                    }
                                    try (FileInputStream fis = new FileInputStream(logFile)) {
                                        fis.skip(logFile.length() - 1024);
                                        List<String> logLines = IOUtils.readLines(fis, StandardCharsets.UTF_8);
                                        // 最多向上读取10行日志
                                        for (int i = logLines.size() - 1; i >= Math.max(0, logLines.size() - 10); i--) {
                                            if (logLines.get(i).contains("segments ahead, expired from playlists")) {
                                                log.warn("发现m3u8序列过期日志，终止推流进程[pid:" + pid + ", logFile:\"" + logFile + "\"]...");
                                                ProcessUtil.killProcess(pid);
                                                break;
                                            }
                                            Matcher matcher = logSpeedPattern.matcher(logLines.get(i));
                                            if (matcher.find()) {
                                                health = Float.parseFloat(matcher.group(1).trim()) * 100;
                                                lastHitTime = System.nanoTime();
                                                break;
                                            }
                                        }
                                        if (lastHitTime > 0 && TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - lastHitTime) > 10000) {
                                            log.warn("超过10秒无法获取当前推流健康度，终止推流进程[pid:" + pid + ", lastHitTime:" + lastHitTime + ", logFile:\"" + logFile + "\"]...");
                                            ProcessUtil.killProcess(pid);
                                        } else if (health > 0 && health < 94) {
                                            if (lowHealthCount++ < 15) {
                                                log.warn("当前推流健康度过低，该情况已经持续" + lowHealthCount + "次！[pid:" + pid + ", health:" + health + ", logFile:\"" + logFile + "\"]");
                                            } else {
                                                log.warn("当前推流健康度过低，该情况已经持续" + lowHealthCount + "次，终止推流进程...[pid:" + pid + ", health:" + health + ", logFile:\"" + logFile + "\"]");
                                                ProcessUtil.killProcess(pid);
                                            }
                                        } else if (health > 102) {
                                            log.warn("当前推流健康度异常，终止推流进程[pid:" + pid + ", health:" + health + ", logFile:\"" + logFile + "\"]...");
                                            ProcessUtil.killProcess(pid);
                                        }
                                    } catch (Exception e) {
                                        log.error("读取推流进程日志文件时出错[pid:" + pid + ", logFile:\"" + logFile + "\"]", e);
                                    }
                                }
                            }
                        } catch (Throwable e) {
                            log.error("startBroadcast failed", e);
                            if (broadcastAccount != null && (broadcastAccount.isDisable() || terminate)) {
                                broadcastAccount.setBroadcastError(new BroadcastError(e.getMessage()));
                            }
                        } finally {
                            broadcastServiceManager.getBroadcastServerService().releaseServer(this);
                            // 杀死进程
                            if (pid != 0) {
                                ProcessUtil.killProcess(pid);
                                log.info("[" + broadcastAccount.getRoomId() + "@" + broadcastAccount.getAccountSite() + ", videoId=" + videoInfo.getVideoUnionId() + "]推流进程已终止PID:" + pid);
                            }
                        }
                        if (!terminate) {
                            Thread.sleep(1000);
                        }
                    }
                    // 终止推流时自动终止创建的低清晰度媒体代理任务
                    deleteLowVideoTask();
                    if (broadcastAccount.isDisable()) {
                        log.warn("直播账号[" + broadcastAccount.getAccountId() + "]不可用，已终止推流任务[videoId=" + videoInfo.getVideoUnionId() + "]。");
                        terminate = true;
                        break;
                    }
                } catch (Throwable e) {
                    log.error("broadcastTask failed", e);
                    if (broadcastAccount != null && terminate) {
                        broadcastAccount.setBroadcastError(new BroadcastError(e.getMessage()));
                    }
                } finally {
                    if (broadcastAccount != null) {
                        broadcastAccount.removeCurrentVideo(videoInfo);
                    }
                }
                if (!terminate) {
                    Thread.sleep(1000);
                }
            }
        } catch (InterruptedException ignore) {
        } finally {
            log.info("节目[" + videoInfo.getTitle() + "][videoId=" + videoInfo.getVideoUnionId() + "]的推流任务[roomId=" + (broadcastAccount.getRoomId() != null ? broadcastAccount.getRoomId() : "(无)") + "]已停止");
            if (videoInfo.getBroadcastTask() != null && !videoInfo.removeBroadcastTask(this)) {
                log.warn("警告：无法移除[videoId=" + videoInfo.getVideoUnionId() + "]的推流任务，CAS操作失败");
            }
            terminate = true;
            MediaHistory mediaHistory = broadcastServiceManager.getMediaHistoryService().getMediaHistory(videoInfo.getVideoUnionId());
            // 不需要录像，终止媒体代理服务
            if (mediaHistory != null && mediaHistory.isNeedRecord()) {
                log.info("节目[" + videoInfo.getTitle() + "][videoId=" + videoInfo.getVideoUnionId() + "]的推流任务已终止，但需要录像，保留媒体代理服务！");
            } else {
                log.info("节目[" + videoInfo.getTitle() + "][videoId=" + videoInfo.getVideoUnionId() + "]的推流任务已终止，且不需要录像，终止媒体代理服务！");
                MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoInfo.getVideoUnionId());
                if (mediaProxyTask != null) {
                    mediaProxyTask.terminate();
                }
            }
        }
    }

    /**
     * 终止创建的低清晰度媒体代理任务
     */
    private void deleteLowVideoTask() {
        if (lowVideoInfo != null) {
            lowVideoInfo.removeBroadcastTask(this);
            Map<String, MediaProxyTask> executedProxyTaskMap = MediaProxyManager.getExecutedProxyTaskMap();
            MediaProxyTask mediaProxyTask = executedProxyTaskMap.get(lowVideoInfo.getVideoUnionId());
            if (mediaProxyTask != null) {
                mediaProxyTask.terminate();
                // 这里需要等待任务停止
                mediaProxyTask.waitForTerminate();
                FileUtils.deleteQuietly(new File(mediaProxyTask.getTempPath()));
            }
            lowVideoInfo = null;
        }
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isTerminate() {
        return terminate;
    }

    public boolean terminateTask() {
        log.info("强制终止节目[" + videoInfo.getTitle() + "][videoId=" + videoInfo.getVideoUnionId() + "]的推流任务[roomId=" + (broadcastAccount != null ? broadcastAccount.getRoomId() : "(无)") + "]...");
        if (broadcastAccount != null) {
            ThreadPoolUtil.schedule(() -> {
                if (broadcastAccount.getCurrentVideo() == null) {
                    broadcastServiceManager.getBroadcastService(broadcastAccount.getAccountSite()).stopBroadcast(broadcastAccount, true);
                }
            }, 2, TimeUnit.MINUTES);
            if (!broadcastAccount.removeCurrentVideo(videoInfo)) {
                log.error("无法移除账号[" + broadcastAccount.getAccountId() + "]正在转播的节目[" + broadcastAccount.getCurrentVideo().getVideoUnionId() + "]，目标节目与预期节目[" + videoInfo.getVideoUnionId() + "]不符");
                return false;
            }
        }
        terminate = true;
        videoInfo.removeBroadcastTask(this);
        ProcessUtil.killProcess(pid);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BroadcastTask that = (BroadcastTask) o;
        return Objects.equals(broadcastAccount, that.broadcastAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(broadcastAccount);
    }

    public VideoInfo getLowVideoInfo() {
        return lowVideoInfo;
    }

    public void setLowVideoInfo(VideoInfo lowVideoInfo) {
        this.lowVideoInfo = lowVideoInfo;
    }

    public synchronized void waitForTerminate() {
    }

    @Override
    public String toString() {
        return "BroadcastTask{" +
                "videoId=" + videoInfo.getVideoUnionId() +
                ", accountId=" + broadcastAccount.getAccountId() +
                '}';
    }
}
