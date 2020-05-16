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
import site.alice.liveman.jenum.VideoResolutionEnum;
import site.alice.liveman.web.dataobject.DynamicPostSetting;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class AccountInfo implements Comparable<AccountInfo> {

    private String                           parentAccountId;
    private String                           accountId;
    private String                           accountSite;
    private String                           cookies;
    private String                           nickname;
    private String                           description;
    private String                           roomId;
    private String                           uid;
    private String                           roomUrl;
    private String                           shareCode;
    private boolean                          admin;
    private boolean                          disable;
    private boolean                          postBiliDynamic;
    private boolean                          autoRoomTitle;
    private boolean                          saveCookies;
    private AtomicLong                       point;
    private ConcurrentHashMap<Integer, Long> billTimeMap;
    private CopyOnWriteArrayList<BillRecord> billRecords;
    private List<DynamicPostSetting>         dynamicPostSettings;
    private CopyOnWriteArraySet<ChannelInfo> channels;
    private VideoResolutionEnum              broadcastResolution;
    private BroadcastError                   broadcastError;
    @JSONField(serialize = false)
    private AtomicReference<VideoInfo>       currentVideo = new AtomicReference<>();
    @JSONField(serialize = false)
    private Long                             rtmpUrlRefreshTime;
    @JSONField(serialize = false)
    private String                           rtmpUrl;
    @JSONField(serialize = false)
    private String                           rtmpHost;
    @JSONField(serialize = false)
    private String                           rtmpPassword;
    @JSONField(serialize = false)
    private AccountInfo                      parentAccountInfo;

    public AccountInfo() {
        point = new AtomicLong();
        billTimeMap = new ConcurrentHashMap<>();
        billRecords = new CopyOnWriteArrayList<>();
        dynamicPostSettings = new CopyOnWriteArrayList<>();
        channels = new CopyOnWriteArraySet<>();
    }

    public String getParentAccountId() {
        return parentAccountId;
    }

    public void setParentAccountId(String parentAccountId) {
        this.parentAccountId = parentAccountId;
    }

    public AccountInfo getParentAccountInfo() {
        return parentAccountInfo;
    }

    public void setParentAccountInfo(AccountInfo parentAccountInfo) {
        this.parentAccountInfo = parentAccountInfo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountSite() {
        return accountSite;
    }

    public void setAccountSite(String accountSite) {
        this.accountSite = accountSite;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    /**
     * 配置信息持久化使用
     *
     * @return
     */
    public String getCookies() {
        if (saveCookies) {
            return cookies;
        } else {
            return null;
        }
    }

    public String readCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public CopyOnWriteArraySet<ChannelInfo> getChannels() {
        return channels;
    }

    public void setChannels(CopyOnWriteArraySet<ChannelInfo> channels) {
        this.channels = channels;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRoomUrl() {
        return roomUrl;
    }

    public void setRoomUrl(String roomUrl) {
        this.roomUrl = roomUrl;
    }

    public boolean isSaveCookies() {
        return saveCookies;
    }

    public void setSaveCookies(boolean saveCookies) {
        this.saveCookies = saveCookies;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public VideoInfo getCurrentVideo() {
        return currentVideo.get();
    }

    public boolean setCurrentVideo(VideoInfo currentVideo) {
        return this.currentVideo.compareAndSet(null, currentVideo);
    }

    public boolean removeCurrentVideo(VideoInfo currentVideo) {
        return this.currentVideo.compareAndSet(currentVideo, null) || this.currentVideo.compareAndSet(null, null);
    }

    public List<DynamicPostSetting> getDynamicPostSettings() {
        return dynamicPostSettings;
    }

    public void setDynamicPostSettings(List<DynamicPostSetting> dynamicPostSettings) {
        this.dynamicPostSettings = dynamicPostSettings;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public long getPoint() {
        if (parentAccountInfo != null) {
            return parentAccountInfo.getPoint();
        } else {
            return point.get();
        }
    }

    /**
     * 反序列化时候要用
     *
     * @param point
     */
    public void setPoint(AtomicLong point) {
        this.point = point;
    }

    public long changePoint(long delta, String remark) {
        BillRecord billRecord;
        if (parentAccountInfo != null) {
            billRecord = new BillRecord(delta, remark + "(父账号:" + parentAccountInfo.getNickname() + ")");
        } else {
            billRecord = new BillRecord(delta, remark);
        }
        billRecords.add(0, billRecord);
        if (billRecords.size() > 100) {
            billRecords = new CopyOnWriteArrayList<>(billRecords.subList(0, 100));
        }
        if (parentAccountInfo != null) {
            return parentAccountInfo.changePoint(delta, remark + "(子账号:" + getNickname() + ")");
        } else {
            return this.point.addAndGet(delta);
        }
    }

    public BroadcastError getBroadcastError() {
        return broadcastError;
    }

    public void setBroadcastError(BroadcastError broadcastError) {
        this.broadcastError = broadcastError;
    }

    public ConcurrentHashMap<Integer, Long> getBillTimeMap() {
        return billTimeMap;
    }

    public void setBillTimeMap(ConcurrentHashMap<Integer, Long> billTimeMap) {
        this.billTimeMap = billTimeMap;
    }

    public CopyOnWriteArrayList<BillRecord> getBillRecords() {
        return billRecords;
    }

    public void setBillRecords(CopyOnWriteArrayList<BillRecord> billRecords) {
        this.billRecords = billRecords;
    }

    public boolean isPostBiliDynamic() {
        return postBiliDynamic;
    }

    public void setPostBiliDynamic(boolean postBiliDynamic) {
        this.postBiliDynamic = postBiliDynamic;
    }

    public boolean isAutoRoomTitle() {
        return autoRoomTitle;
    }

    public void setAutoRoomTitle(boolean autoRoomTitle) {
        this.autoRoomTitle = autoRoomTitle;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public VideoResolutionEnum getBroadcastResolution() {
        return broadcastResolution;
    }

    public void setBroadcastResolution(VideoResolutionEnum broadcastResolution) {
        this.broadcastResolution = broadcastResolution;
    }

    public Long getRtmpUrlRefreshTime() {
        return rtmpUrlRefreshTime;
    }

    public void setRtmpUrlRefreshTime(Long rtmpUrlRefreshTime) {
        this.rtmpUrlRefreshTime = rtmpUrlRefreshTime;
    }

    public String getRtmpUrl() {
        return rtmpUrl;
    }

    public void setRtmpUrl(String rtmpUrl) {
        this.rtmpUrl = rtmpUrl;
    }

    public String getRtmpHost() {
        return rtmpHost;
    }

    public void setRtmpHost(String rtmpHost) {
        this.rtmpHost = rtmpHost;
    }

    public String getRtmpPassword() {
        return rtmpPassword;
    }

    public void setRtmpPassword(String rtmpPassword) {
        this.rtmpPassword = rtmpPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInfo that = (AccountInfo) o;
        return Objects.equals(accountId, that.accountId) &&
                Objects.equals(accountSite, that.accountSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountSite);
    }

    @Override
    public int compareTo(AccountInfo o) {
        return this.getAccountId().compareTo(o.getAccountId());
    }
}
