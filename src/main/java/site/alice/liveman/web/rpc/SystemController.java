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

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.model.*;
import site.alice.liveman.utils.SecurityUtils;
import site.alice.liveman.web.dataobject.ActionResult;
import site.alice.liveman.web.dataobject.vo.SettingVO;

import javax.servlet.http.HttpSession;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Controller
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private HttpSession    session;
    @Autowired
    private LiveManSetting liveManSetting;
    @Autowired
    private SettingConfig  settingConfig;

    @ResponseBody
    @RequestMapping("/getSetting.json")
    public ActionResult<SettingVO> getSetting() {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (!account.isAdmin()) {
            return ActionResult.getErrorResult("权限不足");
        }
        SettingVO settingVO = new SettingVO();
        settingVO.setFfmpegPath(liveManSetting.getFfmpegPath());
        settingVO.setBannedKeywords(String.join(",", liveManSetting.getBannedKeywords()));
        settingVO.setBannedYoutubeChannel(String.join(",", liveManSetting.getBannedYoutubeChannel()));
        settingVO.setDefaultResolution(liveManSetting.getDefaultResolution());
        settingVO.setBaseUrl(liveManSetting.getBaseUrl());
        settingVO.setHasOneDriveToken(StringUtils.isNotEmpty(liveManSetting.getOneDriveToken()));
        settingVO.setPreReEncode(liveManSetting.getPreReEncode());
        settingVO.setEncodeKey(liveManSetting.getEncodeKey());
        settingVO.setApShopUrl(liveManSetting.getApShopUrl());
        settingVO.setServerPoints(liveManSetting.getServerPoints());
        return ActionResult.getSuccessResult(settingVO);
    }

    @ResponseBody
    @RequestMapping("/saveSetting.json")
    public ActionResult<SettingVO> saveSetting(@RequestBody SettingVO settingVO) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (!account.isAdmin()) {
            return ActionResult.getErrorResult("权限不足");
        }
        liveManSetting.setFfmpegPath(settingVO.getFfmpegPath());
        liveManSetting.setBannedKeywords(settingVO.getBannedKeywordsArray());
        liveManSetting.setBannedYoutubeChannel(settingVO.getBannedYoutubeChannelArray());
        liveManSetting.setDefaultResolution(settingVO.getDefaultResolution());
        liveManSetting.setBaseUrl(settingVO.getBaseUrl());
        liveManSetting.setPreReEncode(settingVO.getPreReEncode());
        liveManSetting.setEncodeKey(settingVO.getEncodeKey());
        liveManSetting.setApShopUrl(settingVO.getApShopUrl());
        liveManSetting.setServerPoints(settingVO.getServerPoints());
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(settingVO);
    }

    @ResponseBody
    @RequestMapping("/cardGenerator.json")
    public ActionResult<String> cardGenerator(int point, int count) {
        AccountInfo account = (AccountInfo) session.getAttribute("account");
        if (!account.isAdmin()) {
            return ActionResult.getErrorResult("权限不足");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= count; i++) {
            sb.append(SecurityUtils.aesEncrypt(point + "|" + System.currentTimeMillis() + "|" + i)).append("\n");
        }
        return ActionResult.getSuccessResult(sb.toString());
    }

    @ResponseBody
    @RequestMapping("/importOldChannel.json")
    public ActionResult importOldChannel() {
        CopyOnWriteArraySet<OldChannelInfo> channels = liveManSetting.getChannels();
        CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
        if (channels != null) {
            for (OldChannelInfo oldChannel : channels) {
                if (oldChannel.getDefaultAccountId() != null) {
                    for (AccountInfo account : accounts) {
                        if (account.getAccountId().equals(oldChannel.getDefaultAccountId())) {
                            ChannelInfo channelInfo = new ChannelInfo();
                            channelInfo.setCookies(oldChannel.getCookies());
                            channelInfo.setChannelUrl(oldChannel.getChannelUrl());
                            channelInfo.setChannelName(oldChannel.getChannelName());
                            channelInfo.setEndAt(oldChannel.getEndAt());
                            channelInfo.setStartAt(oldChannel.getStartAt());
                            BroadcastConfig broadcastConfig = new BroadcastConfig();
                            broadcastConfig.setAutoBroadcast(true);
                            broadcastConfig.setArea(oldChannel.getDefaultArea());
                            broadcastConfig.setVideoBannedType(oldChannel.getDefaultCropConf().getVideoBannedType());
                            broadcastConfig.setAutoBlur(oldChannel.getDefaultCropConf().isAutoBlur());
                            channelInfo.setDefaultBroadcastConfig(broadcastConfig);
                            account.getChannels().add(channelInfo);
                        }
                    }
                }
            }
        }
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/apShop")
    public String apShop() {
        return "redirect:" + liveManSetting.getApShopUrl();
    }

}
