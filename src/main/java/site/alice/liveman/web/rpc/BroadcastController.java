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

package site.alice.liveman.web.rpc;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.customlayout.CustomLayout;
import site.alice.liveman.customlayout.impl.RectangleBlurLayout;
import site.alice.liveman.jenum.VideoBannedTypeEnum;
import site.alice.liveman.mediaproxy.MediaProxyManager;
import site.alice.liveman.mediaproxy.proxytask.MediaProxyTask;
import site.alice.liveman.model.*;
import site.alice.liveman.service.MediaHistoryService;
import site.alice.liveman.service.broadcast.BroadcastServiceManager;
import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.service.live.LiveServiceFactory;
import site.alice.liveman.utils.HttpRequestUtil;
import site.alice.liveman.utils.ProcessUtil;
import site.alice.liveman.web.dataobject.ActionResult;
import site.alice.liveman.web.dataobject.vo.BroadcastTaskVO;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@RestController
@RequestMapping("/api/broadcast")
public class BroadcastController {

    @Autowired
    private HttpSession             session;
    @Autowired
    private LiveServiceFactory      liveServiceFactory;
    @Autowired
    private BroadcastServiceManager broadcastServiceManager;
    @Autowired
    private MediaHistoryService     mediaHistoryService;
    @Autowired
    private SettingConfig           settingConfig;
    @Autowired
    private LiveManSetting          liveManSetting;

    @RequestMapping("/taskList.json")
    public ActionResult<List<BroadcastTaskVO>> taskList() {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        List<BroadcastTaskVO> broadcastTaskVOList = new ArrayList<>();
        Map<String, MediaProxyTask> executedProxyTaskMap = MediaProxyManager.getExecutedProxyTaskMap();
        for (MediaProxyTask mediaProxyTask : executedProxyTaskMap.values()) {
            VideoInfo videoInfo = mediaProxyTask.getVideoInfo();
            if (videoInfo != null && videoInfo.getChannelInfo() != null) {
                if (videoInfo.getVideoUnionId().endsWith("_low")) {
                    continue;
                }
                if (videoInfo.getPrivateAccount() != null && !videoInfo.getPrivateAccount().getAccountId().equals(account.getAccountId())) {
                    continue;
                }
                BroadcastTaskVO broadcastTaskVO = new BroadcastTaskVO();
                BroadcastTask broadcastTask = videoInfo.getBroadcastTask(account);
                if (broadcastTask != null) {
                    broadcastTaskVO.setHealth(broadcastTask.getHealth());
                    AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
                    if (broadcastAccount != null) {
                        broadcastTaskVO.setAccountSite(broadcastAccount.getAccountSite());
                        broadcastTaskVO.setNickname(broadcastAccount.getNickname());
                        broadcastTaskVO.setRoomId(broadcastAccount.getRoomId());
                    }
                }
                ChannelInfo channelInfo = videoInfo.getChannelInfo();
                if (channelInfo != null) {
                    broadcastTaskVO.setChannelName(channelInfo.getChannelName());
                }
                BroadcastConfig broadcastConfig = videoInfo.getBroadcastConfig(account);
                if (broadcastConfig == null) {
                    // 从频道中拷贝默认配置信息
                    BroadcastConfig defaultBroadcastConfig = videoInfo.getChannelInfo().getDefaultBroadcastConfig(account);
                    if (defaultBroadcastConfig != null) {
                        broadcastConfig = new BroadcastConfig();
                        BeanUtils.copyProperties(defaultBroadcastConfig, broadcastConfig);
                        broadcastConfig.setAccountId(account.getAccountId());
                        videoInfo.addBroadcastConfig(broadcastConfig);
                    }
                }
                if (broadcastConfig != null) {
                    broadcastTaskVO.setArea(broadcastConfig.getArea());
                    broadcastTaskVO.setAudioBanned(broadcastConfig.isAudioBanned());
                    broadcastTaskVO.setNeedRecord(broadcastConfig.isNeedRecord());
                    broadcastTaskVO.setVertical(broadcastConfig.isVertical());
                }
                broadcastTaskVO.setVideoId(videoInfo.getVideoUnionId());
                broadcastTaskVO.setVideoTitle(videoInfo.getTitle());
                broadcastTaskVO.setMediaUrl(mediaProxyTask.getTargetUrl().getPath());
                broadcastTaskVOList.add(broadcastTaskVO);
            }
        }
        // 自己认领的或者是自己添加的任务排在前面
        broadcastTaskVOList.sort((o1, o2) -> {
            if (o1.getRoomId() != null) {
                return o2.getRoomId() == null ? 1 : 0;
            } else if (o1.getChannelName().equals("手动推流")) {
                return o2.getChannelName().equals("手动推流") ? 0 : 1;
            }
            return -1;
        });
        return ActionResult.getSuccessResult(broadcastTaskVOList);
    }

    @RequestMapping("/adoptTask.json")
    public ActionResult adoptTask(String videoId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        account.setDisable(false);
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        VideoInfo videoInfo = mediaProxyTask.getVideoInfo();
        if (account.getAccountSite().equals("17live") && videoInfo.getChannelInfo().getChannelName().equals("手动推流")) {
            return ActionResult.getErrorResult("17Live账号不允许手动推流，请联系管理员添加相应频道！");
        }
        log.info(account.getAccountId() + "认领了转播任务[videoId=" + videoId + ", title=" + videoInfo.getTitle() + "]");
        BroadcastTask broadcastTask = videoInfo.getBroadcastTask(account);
        if (broadcastTask != null) {
            AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
            if (broadcastAccount != null) {
                log.info("此转播任务已经被认领了，请刷新页面后重试");
                return ActionResult.getErrorResult("此转播任务已经被认领了，请刷新页面后重试");
            } else {
                broadcastTask.terminateTask();
            }
        }
        try {
            BroadcastConfig cropConf = videoInfo.getBroadcastConfig(account);
            if (cropConf != null) {
                if (cropConf.getBroadcastResolution() != null && cropConf.getVideoBannedType() == VideoBannedTypeEnum.CUSTOM_SCREEN) {
                    if (account.getAccountSite().equals("17live")) {
                        return ActionResult.getErrorResult("17Live账号不支持自定义推流，请点击[管理] -> [视频内容规则]选择[取消内容规制]选项！");
                    }
                    int performance = cropConf.getBroadcastResolution().getPerformance();
                    int serverPoint = liveManSetting.getServerPoints()[performance];
                    if (account.getPoint() < serverPoint && account.getBillTimeMap().get(performance) == null) {
                        return ActionResult.getErrorResult("账户积分不足[当前可用余额：" + account.getPoint() + ", 需要积分(" + cropConf.getBroadcastResolution() + ")：" + serverPoint + "]");
                    }
                }
            }
            broadcastTask = broadcastServiceManager.createSingleBroadcastTask(videoInfo, account);
            if (broadcastTask == null) {
                return ActionResult.getErrorResult("操作失败：BroadcastTask创建失败");
            }
            return ActionResult.getSuccessResult(null);
        } catch (Exception e) {
            log.error("adoptTask() failed, videoId=" + videoId, e);
            return ActionResult.getErrorResult("操作失败：" + e.getMessage());
        }
    }

    @RequestMapping("/getCropConf.json")
    public ActionResult<BroadcastConfig> getCropConf(String videoId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        BroadcastConfig cropConf = mediaProxyTask.getVideoInfo().getBroadcastConfig(account);
        if (cropConf == null) {

        }
        return ActionResult.getSuccessResult(cropConf);
    }


    @RequestMapping("/cropConfSave.json")
    public ActionResult cropConfSave(String videoId, @RequestBody BroadcastConfig cropConf) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        log.info("cropConfSave()[videoId=" + videoId + "][accountRoomId=" + account.getRoomId() + "]");
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        VideoInfo videoInfo = mediaProxyTask.getVideoInfo();
        BroadcastTask broadcastTask = videoInfo.getBroadcastTask(account);
        AccountInfo broadcastAccount = null;
        if (broadcastTask != null) {
            broadcastAccount = broadcastTask.getBroadcastAccount();
            if (broadcastAccount != null) {
                if (!broadcastAccount.getRoomId().equals(account.getRoomId()) && !account.isAdmin()) {
                    log.info("您没有权限编辑他人直播间的推流任务[videoId=" + videoId + "][broadcastRoomId=" + broadcastAccount.getRoomId() + "]");
                    return ActionResult.getErrorResult("你没有权限编辑他人直播间的推流任务");
                }
            }
        }
        BroadcastConfig _cropConf = videoInfo.getBroadcastConfig(account);
        if (cropConf != null) {
            boolean needRestart = false;
            if (cropConf.getVideoBannedType() == VideoBannedTypeEnum.CUSTOM_SCREEN) {
                if (account.getAccountSite().equals("17live")) {
                    return ActionResult.getErrorResult("17Live账号不支持自定义推流，请点击[管理] -> [视频内容规则]选择[取消内容规制]选项！");
                }
                if (cropConf.getBroadcastResolution() != _cropConf.getBroadcastResolution() && broadcastTask != null) {
                    needRestart = true;
                }
                int performance = cropConf.getBroadcastResolution().getPerformance();
                int serverPoint = liveManSetting.getServerPoints()[performance];
                if (broadcastAccount != null && broadcastAccount.getPoint() < serverPoint && broadcastAccount.getBillTimeMap().get(performance) == null) {
                    return ActionResult.getErrorResult("账户积分不足[当前可用余额：" + broadcastAccount.getPoint() + ", 需要积分(" + cropConf.getBroadcastResolution() + ")：" + serverPoint + "]");
                }
                int blurLayoutCount = 0;
                Collections.sort(cropConf.getLayouts());
                for (CustomLayout layout : cropConf.getLayouts()) {
                    layout.setVideoInfo(videoInfo);
                    if (layout instanceof RectangleBlurLayout) {
                        blurLayoutCount++;
                    }
                }
                // 如果没有高斯迷糊滤镜层则设置模糊强度为0，减少不必要的性能损耗
                if (blurLayoutCount == 0) {
                    cropConf.setBlurSize(0);
                }
            } else if (CollectionUtils.isNotEmpty(cropConf.getLayouts())) {
                cropConf.getLayouts().clear();
            }
            videoInfo.addBroadcastConfig(cropConf);
            MediaProxyTask lowMediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId + "_low");
            if (lowMediaProxyTask != null) {
                lowMediaProxyTask.getVideoInfo().addBroadcastConfig(cropConf);
            }
            try {
                settingConfig.saveSetting(liveManSetting);
            } catch (Exception e) {
                log.error("保存系统配置信息失败", e);
                return ActionResult.getErrorResult("系统内部错误，请联系管理员");
            }
            if (needRestart) {
                stopTask(videoId);
                adoptTask(videoId);
            } else if ((_cropConf == null || _cropConf.getVideoBannedType() != cropConf.getVideoBannedType() || _cropConf.getBlurSize() != cropConf.getBlurSize()) && broadcastTask != null) {
                ProcessUtil.killProcess(broadcastTask.getPid());
            }
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/stopTask.json")
    public ActionResult stopTask(String videoId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        log.info("stopTask()[videoId=" + videoId + "][accountRoomId=" + account.getRoomId() + "]");
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        BroadcastTask broadcastTask = mediaProxyTask.getVideoInfo().getBroadcastTask(account);
        if (broadcastTask == null) {
            log.info("此转播任务尚未运行，或已停止[BroadcastTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
        if (broadcastAccount == null) {
            log.info("此转播任务尚未运行，或已停止[BroadcastAccount不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        if (!broadcastAccount.getRoomId().equals(account.getRoomId()) && !account.isAdmin()) {
            log.info("您没有权限停止他人直播间的推流任务[videoId=" + videoId + "][broadcastRoomId=" + broadcastAccount.getRoomId() + "]");
            return ActionResult.getErrorResult("你没有权限停止他人直播间的推流任务");
        }
        if (broadcastTask.terminateTask()) {
            broadcastTask.waitForTerminate();
            return ActionResult.getSuccessResult(null);
        } else {
            log.info("终止转播任务失败：CAS操作失败，请刷新页面后重试[videoId=" + videoId + "]");
            return ActionResult.getErrorResult("终止转播任务失败：CAS操作失败，请刷新页面后重试");
        }
    }

    @RequestMapping("/terminateTask.json")
    public ActionResult terminateTask(String videoId) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        log.info("terminateTask()[videoId=" + videoId + "][accountRoomId=" + account.getRoomId() + "]");
        if (!account.isAdmin()) {
            return ActionResult.getErrorResult("没有权限！");
        }
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        Set<BroadcastTask> broadcastTasks = mediaProxyTask.getVideoInfo().getBroadcastTasks();
        for (BroadcastTask broadcastTask : broadcastTasks) {
            broadcastTask.terminateTask();
            broadcastTask.waitForTerminate();
        }
        mediaProxyTask.terminate();
        mediaProxyTask.waitForTerminate();
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/createTask.json")
    public ActionResult createTask(String videoUrl, String cookies) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        account.setDisable(false);
        try {
            if (account.getAccountSite().equals("17live")) {
                return ActionResult.getErrorResult("17Live账号不允许手动推流，请联系管理员添加相应频道！");
            }
            ChannelInfo channelInfo = new ChannelInfo();
            channelInfo.setChannelName("手动推流");
            VideoInfo liveVideoInfo = liveServiceFactory.getLiveService(videoUrl).getLiveVideoInfo(new URI(videoUrl), channelInfo, cookies, liveManSetting.getDefaultResolution());
            if (liveVideoInfo == null) {
                return ActionResult.getErrorResult("当前节目尚未开播");
            }
            BroadcastTask broadcastTask;
            Map<String, MediaProxyTask> executedProxyTaskMap = MediaProxyManager.getExecutedProxyTaskMap();
            MediaProxyTask mediaProxyTask = executedProxyTaskMap.get(liveVideoInfo.getVideoUnionId());
            if (mediaProxyTask != null) {
                // 如果媒体流已存在则直接认领
                broadcastTask = broadcastServiceManager.createSingleBroadcastTask(mediaProxyTask.getVideoInfo(), account);
            } else {
                // 如果公共的推流任务没有，则创建私有的推流任务
                liveVideoInfo.setPrivateAccount(account);
                broadcastTask = broadcastServiceManager.createSingleBroadcastTask(liveVideoInfo, account);
            }
            if (broadcastTask == null) {
                return ActionResult.getErrorResult("操作失败：BroadcastTask创建失败");
            }
            return ActionResult.getSuccessResult(null);
        } catch (Exception e) {
            log.error("createTask() failed, videoUrl=" + videoUrl, e);
            if (e instanceof URISyntaxException) {
                return ActionResult.getErrorResult("输入的媒体地址不正确");
            } else {
                return ActionResult.getErrorResult("操作失败：" + e.getMessage());
            }
        }
    }

    @RequestMapping("/editTask.json")
    public ActionResult editTask(@RequestBody BroadcastTaskVO broadcastTaskVO) {
        String videoId = broadcastTaskVO.getVideoId();
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        log.info("editTask()[videoId=" + videoId + "][accountRoomId=" + account.getRoomId() + "]");
        MediaProxyTask mediaProxyTask = MediaProxyManager.getExecutedProxyTaskMap().get(videoId);
        if (mediaProxyTask == null) {
            log.info("此转播任务尚未运行，或已停止[MediaProxyTask不存在][videoId=" + videoId + "]");
            return ActionResult.getErrorResult("此转播任务尚未运行或已停止");
        }
        VideoInfo videoInfo = mediaProxyTask.getVideoInfo();
        BroadcastTask broadcastTask = videoInfo.getBroadcastTask(account);
        BroadcastConfig broadcastConfig = videoInfo.getBroadcastConfig(account);
        if (broadcastTask != null) {
            AccountInfo broadcastAccount = broadcastTask.getBroadcastAccount();
            if (broadcastAccount != null) {
                if (!broadcastAccount.getRoomId().equals(account.getRoomId()) && !account.isAdmin()) {
                    log.info("您没有权限编辑他人直播间的推流任务[videoId=" + videoId + "][broadcastRoomId=" + broadcastAccount.getRoomId() + "]");
                    return ActionResult.getErrorResult("你没有权限编辑他人直播间的推流任务");
                }
                Integer areaId = null;
                if (broadcastTaskVO.getArea() != null && broadcastTaskVO.getArea().length > 1) {
                    areaId = broadcastTaskVO.getArea()[1];
                }
                if (broadcastTaskVO.isVertical() != broadcastConfig.isVertical()) {
                    broadcastServiceManager.getBroadcastService(account.getAccountSite()).stopBroadcast(broadcastAccount, false);
                }
                broadcastServiceManager.getBroadcastService(account.getAccountSite()).setBroadcastSetting(broadcastAccount, broadcastTaskVO.getRoomTitle(), areaId);
            }
        }
        broadcastConfig.setVertical(broadcastTaskVO.isVertical());
        broadcastConfig.setArea(broadcastTaskVO.getArea());
        broadcastConfig.setNeedRecord(broadcastTaskVO.isNeedRecord());
        MediaHistory mediaHistory = mediaHistoryService.getMediaHistory(videoId);
        if (mediaHistory != null) {
            mediaHistory.setNeedRecord(broadcastTaskVO.isNeedRecord() || checkNeedRecord(videoInfo, account));
        }
        if (broadcastConfig.isAudioBanned() != broadcastTaskVO.isAudioBanned()) {
            broadcastConfig.setAudioBanned(broadcastTaskVO.isAudioBanned());
            if (broadcastTask != null) {
                ProcessUtil.killProcess(broadcastTask.getPid());
            }
        }
        return ActionResult.getSuccessResult(null);
    }

    private boolean checkNeedRecord(VideoInfo videoInfo, AccountInfo currentAccount) {
        // 如果设置为不需要录像，则需要先检查其他用户及默认的频道是否需要录像
        CopyOnWriteArraySet<BroadcastConfig> broadcastConfigs = videoInfo.getBroadcastConfigs();
        for (BroadcastConfig config : broadcastConfigs) {
            if (config.getAccountId().equals(currentAccount.getAccountId())) {
                continue;
            }
            if (config.isNeedRecord()) {
                return true;
            }
        }
        CopyOnWriteArraySet<BroadcastConfig> defaultBroadcastConfigs = videoInfo.getChannelInfo().getDefaultBroadcastConfigs();
        for (BroadcastConfig config : defaultBroadcastConfigs) {
            if (config.getAccountId().equals(currentAccount.getAccountId())) {
                continue;
            }
            if (config.isNeedRecord()) {
                return true;
            }
        }
        return false;
    }

    @RequestMapping("/getAreaList.json")
    public Object getAreaList() throws URISyntaxException, IOException {
        String areaList = HttpRequestUtil.downloadUrl(new URI("https://api.live.bilibili.com/room/v1/Area/getList"), StandardCharsets.UTF_8);
        return JSON.parseObject(areaList);
    }
}

