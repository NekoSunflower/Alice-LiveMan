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

package site.alice.liveman.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.config.SettingConfig;
import site.alice.liveman.jenum.ExternalServiceType;
import site.alice.liveman.model.LiveManSetting;
import site.alice.liveman.model.ServerInfo;
import site.alice.liveman.model.BroadcastConfig;
import site.alice.liveman.service.broadcast.BroadcastTask;
import site.alice.liveman.service.external.DynamicServerService;
import site.alice.liveman.utils.JschSshUtil;

import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BroadcastServerService {

    private static final String[]             STATIC_SYNC_FILES = new String[]{"/etc/resolv.conf", "/etc/hosts"};
    @Autowired
    private              LiveManSetting       liveManSetting;
    @Autowired
    private              SettingConfig        settingConfig;
    @Autowired
    private              DynamicServerService dynamicServerService;

    public ServerInfo getAvailableServer(BroadcastTask broadcastTask) {
        CopyOnWriteArraySet<ServerInfo> servers = liveManSetting.getServers();
        try {
            // 获取服务器之前先释放掉所有被占用的服务器
            releaseServer(broadcastTask);
            BroadcastConfig broadcastConfig = broadcastTask.getVideoInfo().getBroadcastConfig();
            // 查找已经可用的服务器
            List<ServerInfo> availableServers = servers.stream().filter(server -> server.getBroadcastTask() == null && server.getPerformance() == broadcastConfig.getBroadcastResolution().getPerformance() && server.isAvailable()).collect(Collectors.toList());
            while (!availableServers.isEmpty()) {
                ServerInfo serverInfo = availableServers.get((int) (Math.random() * availableServers.size()));
                if (serverInfo.setCurrentVideo(broadcastTask)) {
                    log.info("转播服务器调度成功[" + serverInfo.getRemark() + "@" + serverInfo.getAddress() + ":" + serverInfo.getPort() + "] => " + broadcastTask);
                    return serverInfo;
                } else {
                    availableServers.remove(serverInfo);
                }
            }
            // 查找尚未初始化完毕的服务器
            List<ServerInfo> unavailableServers = servers.stream().filter(server -> server.getBroadcastTask() == null && server.getPerformance() == broadcastConfig.getBroadcastResolution().getPerformance() && !server.isAvailable()).collect(Collectors.toList());
            if (unavailableServers.isEmpty()) {
                // 没有找到可用的服务器，动态扩容
                ServerInfo serverInfo = dynamicServerService.create(broadcastConfig.getBroadcastResolution().getPerformance());
                if (serverInfo != null) {
                    addServer(serverInfo);
                    settingConfig.saveSetting(liveManSetting);
                    if (serverInfo.setCurrentVideo(broadcastTask)) {
                        if (testServer(serverInfo) && installServer(serverInfo)) {
                            serverInfo.setAvailable(true);
                            settingConfig.saveSetting(liveManSetting);
                            log.info("转播服务器调度成功[" + serverInfo.getRemark() + "@" + serverInfo.getAddress() + ":" + serverInfo.getPort() + "] => " + broadcastTask);
                            return serverInfo;
                        } else {
                            serverInfo.removeCurrentVideo(broadcastTask);
                        }
                    }
                }
            }
            while (!unavailableServers.isEmpty()) {
                ServerInfo serverInfo = unavailableServers.get((int) (Math.random() * unavailableServers.size()));
                if (serverInfo.setCurrentVideo(broadcastTask)) {
                    if (serverInfo.getExternalServiceType() == ExternalServiceType.VULTR_API) {
                        if (dynamicServerService.update(serverInfo) == null) {
                            log.warn("server " + serverInfo.getRemark() + " was not found, remove it.");
                            servers.remove(serverInfo);
                            unavailableServers.remove(serverInfo);
                            serverInfo.removeCurrentVideo(broadcastTask);
                            settingConfig.saveSetting(liveManSetting);
                            continue;
                        }
                    }
                    if (testServer(serverInfo) && installServer(serverInfo)) {
                        serverInfo.setAvailable(true);
                        settingConfig.saveSetting(liveManSetting);
                        log.info("转播服务器调度成功[" + serverInfo.getRemark() + "@" + serverInfo.getAddress() + ":" + serverInfo.getPort() + "] => " + broadcastTask);
                        return serverInfo;
                    } else {
                        log.info("转播服务器[" + serverInfo.getRemark() + "]尚未初始化完毕，当前无法连接。");
                        unavailableServers.remove(serverInfo);
                        serverInfo.removeCurrentVideo(broadcastTask);
                    }
                } else {
                    unavailableServers.remove(serverInfo);
                }
            }
            log.info("没有找到空闲的转播服务器![broadcastTask=" + broadcastTask + "]");
        } catch (Throwable e) {
            releaseServer(broadcastTask);
            throw e;
        }
        return null;
    }


    public boolean testServer(ServerInfo serverInfo) {
        if (StringUtils.isBlank(serverInfo.getAddress()) || "0.0.0.0".equals(serverInfo.getAddress())) {
            log.info("Connect to server" + serverInfo.getRemark() + " server failed: invalid host [" + serverInfo.getAddress() + "]");
            return false;
        }
        try (JschSshUtil jschSshUtil = new JschSshUtil(serverInfo)) {
            jschSshUtil.openSession();
            log.info("Connect to server " + serverInfo.getAddress() + "[" + serverInfo.getRemark() + "] success");
            return true;
        } catch (Throwable e) {
            log.info("Connect to server " + serverInfo.getAddress() + "[" + serverInfo.getRemark() + "] failed:" + e.getMessage());
        }
        return false;
    }

    public boolean addAndInstallServer(ServerInfo serverInfo) {
        if (installServer(serverInfo)) {
            addServer(serverInfo);
            return true;
        } else {
            return false;
        }
    }

    public void addServer(ServerInfo serverInfo) {
        log.info("addServer " + serverInfo);
        if (liveManSetting.getServers().add(serverInfo)) {
            settingConfig.saveSetting(liveManSetting);
        } else {
            throw new RuntimeException("服务器[" + serverInfo.getRemark() + "]添加失败，请检查是否已存在！");
        }
    }

    public boolean installServer(ServerInfo serverInfo) {
        log.info("installServer " + serverInfo);
        if ("127.0.0.1".equals(serverInfo.getAddress())) {
            log.info("检测到需要初始化的服务器为本机[127.0.0.1]，跳过初始化流程！");
            return true;
        }
        try (JschSshUtil jschSshUtil = new JschSshUtil(serverInfo)) {
            jschSshUtil.transferFile(liveManSetting.getFfmpegPath(), liveManSetting.getFfmpegPath());
            for (String syncFile : STATIC_SYNC_FILES) {
                jschSshUtil.transferFile(syncFile, syncFile);
            }
            return true;
        } catch (Throwable e) {
            log.error("Install server " + serverInfo.getAddress() + "[" + serverInfo.getRemark() + "] failed:" + e);
            return false;
        }
    }

    public void releaseServer(BroadcastTask videoInfo) {
        CopyOnWriteArraySet<ServerInfo> servers = liveManSetting.getServers();
        for (ServerInfo server : servers) {
            server.removeCurrentVideo(videoInfo);
        }
    }

}
