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

package site.alice.liveman.service.dynamic.post.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import site.alice.liveman.model.AccountInfo;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.model.VideoInfo;
import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.service.dynamic.post.DynamicPostService;
import site.alice.liveman.web.dataobject.DynamicPostSetting;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
public class DynamicPostManager implements ApplicationContextAware {

    @Autowired
    private LiveManSetting                 liveManSetting;
    private Collection<DynamicPostService> dynamicPostServices;

    public static final String CONTENT_FORMAT = "#Vtuber##%s# 正在直播：%s %s";

    public void postDynamic(BroadcastTask broadcastTask, AccountInfo accountInfo) {
        VideoInfo videoInfo = broadcastTask.getVideoInfo();
        String content = String.format(CONTENT_FORMAT, videoInfo.getChannelInfo().getChannelName(), videoInfo.getTitle(), accountInfo.getRoomUrl());
        // 判断是否需要发送动态
        CopyOnWriteArraySet<AccountInfo> accounts = liveManSetting.getAccounts();
        for (AccountInfo account : accounts) {
            try {
                if (checkPostSetting(broadcastTask, account)) {
                    for (DynamicPostService dynamicPostService : dynamicPostServices) {
                        if (dynamicPostService.match(accountInfo)) {
                            dynamicPostService.postDynamic(accountInfo, content);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("账户[" + account.getAccountId() + "]发送推流任务[" + broadcastTask + "]动态失败", e);
            }
        }
    }

    private boolean checkPostSetting(BroadcastTask broadcastTask, AccountInfo account) {
        VideoInfo videoInfo = broadcastTask.getVideoInfo();
        AccountInfo accountInfo = broadcastTask.getBroadcastAccount();
        List<DynamicPostSetting> dynamicPostSettings = account.getDynamicPostSettings();
        for (DynamicPostSetting dynamicPostSetting : dynamicPostSettings) {
            if ("channel".equals(dynamicPostSetting.getType())) {
                if (videoInfo.getChannelInfo().getChannelUrl().contains(dynamicPostSetting.getSetting())) {
                    return true;
                }
            } else if ("account".equals(dynamicPostSetting.getType())) {
                if (accountInfo.getAccountId().equals(dynamicPostSetting.getSetting())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DynamicPostService> dynamicPostServiceMap = applicationContext.getBeansOfType(DynamicPostService.class);
        dynamicPostServices = dynamicPostServiceMap.values();
    }
}
