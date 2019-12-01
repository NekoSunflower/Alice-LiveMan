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
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.BroadcastConfig;
import site.alice.liveman.model.ChannelInfo;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.web.dataobject.ActionResult;
import site.alice.liveman.web.dataobject.vo.ChannelInfoVO;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/channel")
public class ChannelController {

    @Autowired
    private LiveManSetting liveManSetting;
    @Autowired
    private SettingConfig  settingConfig;
    @Autowired
    private HttpSession    session;

    @RequestMapping("/channelList.json")
    public ActionResult<List<ChannelInfoVO>> channelList() {
        AccountInfo accountInfo = (AccountInfo) session.getAttribute("account");
        Set<ChannelInfo> channels = accountInfo.getChannels();
        List<ChannelInfoVO> channelInfoVOList = new ArrayList<>();
        for (ChannelInfo channel : channels) {
            ChannelInfoVO channelInfoVO = new ChannelInfoVO();
            channelInfoVO.setChannelName(channel.getChannelName());
            channelInfoVO.setChannelUrl(channel.getChannelUrl());
            BroadcastConfig defaultBroadcastConfig = channel.getDefaultBroadcastConfig();
            if (defaultBroadcastConfig == null) {
                defaultBroadcastConfig = new BroadcastConfig();
            }
            channelInfoVO.setDefaultBroadcastConfig(defaultBroadcastConfig);
            channelInfoVOList.add(channelInfoVO);
        }
        return ActionResult.getSuccessResult(channelInfoVOList);
    }


    @RequestMapping("/addChannel.json")
    public ActionResult addChannel(@RequestBody ChannelInfo channelInfo) {
        AccountInfo accountInfo = (AccountInfo) session.getAttribute("account");
        try {
            Assert.hasText(channelInfo.getChannelName(), "频道名称不能为空");
            Assert.hasText(channelInfo.getChannelUrl(), "频道地址不能为空");
        } catch (IllegalArgumentException e) {
            return ActionResult.getErrorResult(e.getMessage());
        }
        channelInfo.setChannelName(channelInfo.getChannelName().trim());
        channelInfo.setChannelUrl(channelInfo.getChannelUrl().trim());
        if (!accountInfo.getChannels().add(channelInfo)) {
            return ActionResult.getErrorResult("尝试添加的频道已存在");
        }
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }

    @RequestMapping("/editChannel.json")
    public ActionResult editChannel(@RequestBody ChannelInfoVO channelInfoVO) {
        AccountInfo accountInfo = (AccountInfo) session.getAttribute("account");
        try {
            Assert.hasText(channelInfoVO.getChannelName(), "频道名称不能为空");
            Assert.hasText(channelInfoVO.getChannelUrl(), "频道地址不能为空");
        } catch (IllegalArgumentException e) {
            return ActionResult.getErrorResult(e.getMessage());
        }
        log.info("accountId=" + accountInfo.getAccountId() + "编辑频道channelInfo=" + JSON.toJSONString(channelInfoVO));
        Set<ChannelInfo> channels = accountInfo.getChannels();
        for (ChannelInfo channel : channels) {
            if (channel.getChannelUrl().equals(channelInfoVO.getChannelUrl())) {
                if (accountInfo.isAdmin()) {
                    channel.setChannelName(channelInfoVO.getChannelName());
                }
                BroadcastConfig defaultBroadcastConfig = channel.getDefaultBroadcastConfig();
                if (defaultBroadcastConfig == null) {
                    defaultBroadcastConfig = new BroadcastConfig();
                    channel.setDefaultBroadcastConfig(defaultBroadcastConfig);
                }
                BeanUtils.copyProperties(channelInfoVO.getDefaultBroadcastConfig(), defaultBroadcastConfig);
                try {
                    settingConfig.saveSetting(liveManSetting);
                } catch (Exception e) {
                    log.error("保存系统配置信息失败", e);
                    return ActionResult.getErrorResult("系统内部错误，请联系管理员");
                }
                return ActionResult.getSuccessResult(null);
            }
        }
        return ActionResult.getErrorResult("没有找到尝试修改的频道记录");
    }

    @RequestMapping("/removeChannel.json")
    public ActionResult removeChannel(@RequestBody ChannelInfo channelInfo) {
        AccountInfo accountInfo = (AccountInfo) session.getAttribute("account");
        try {
            Assert.hasText(channelInfo.getChannelName(), "频道名称不能为空");
            Assert.hasText(channelInfo.getChannelUrl(), "频道地址不能为空");
        } catch (IllegalArgumentException e) {
            return ActionResult.getErrorResult(e.getMessage());
        }
        if (!accountInfo.getChannels().remove(channelInfo)) {
            return ActionResult.getErrorResult("尝试删除的频道不存在，无法删除");
        }
        try {
            settingConfig.saveSetting(liveManSetting);
        } catch (Exception e) {
            log.error("保存系统配置信息失败", e);
            return ActionResult.getErrorResult("系统内部错误，请联系管理员");
        }
        return ActionResult.getSuccessResult(null);
    }
}
