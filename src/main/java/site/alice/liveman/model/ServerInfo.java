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

package site.alice.liveman.model;

import com.alibaba.fastjson.annotation.JSONField;
import site.alice.liveman.jenum.ExternalServiceType;
import site.alice.liveman.service.broadcast.BroadcastServiceManager.BroadcastTask;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ServerInfo {
    private Integer                        port;
    private String                         address;
    private String                         username;
    private String                         password;
    private Long                           dateCreated;
    private int                            performance;
    private ExternalServiceType            externalServiceType;
    private String                         remark;
    private boolean                        available;
    @JSONField(serialize = false)
    private AtomicReference<BroadcastTask> broadcastTask = new AtomicReference<>();

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public Long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ExternalServiceType getExternalServiceType() {
        return externalServiceType;
    }

    public void setExternalServiceType(ExternalServiceType externalServiceType) {
        this.externalServiceType = externalServiceType;
    }

    public BroadcastTask getBroadcastTask() {
        return broadcastTask.get();
    }

    public boolean setCurrentVideo(BroadcastTask broadcastTask) {
        return this.broadcastTask.compareAndSet(null, broadcastTask);
    }

    public boolean removeCurrentVideo(BroadcastTask broadcastTask) {
        return this.broadcastTask.compareAndSet(broadcastTask, null) || this.broadcastTask.compareAndSet(null, null);
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfo that = (ServerInfo) o;
        return Objects.equals(remark, that.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remark);
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "port=" + port +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dateCreated=" + dateCreated +
                ", performance=" + performance +
                ", externalServiceType=" + externalServiceType +
                ", remark='" + remark + '\'' +
                '}';
    }
}
