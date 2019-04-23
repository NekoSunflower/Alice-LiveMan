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

package site.alice.liveman.dataobject.dto;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class AccountInfo implements Comparable<AccountInfo> {

    private String                     accountId;
    private String                     accountSite;
    private String                     cookies;
    private String                     nickname;
    private String                     description;
    private String                     roomId;
    private String                     uid;
    private boolean                    joinAutoBalance;
    private boolean                    admin;
    private boolean                    vip;
    private boolean                    disable;
    private boolean                    postBiliDynamic;
    private boolean                    autoRoomTitle;
    @JSONField(serialize = false)
    private AtomicReference<VideoInfo> currentVideo = new AtomicReference<>();

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

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
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

    public boolean isJoinAutoBalance() {
        return joinAutoBalance;
    }

    public void setJoinAutoBalance(boolean joinAutoBalance) {
        this.joinAutoBalance = joinAutoBalance;
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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountInfo that = (AccountInfo) o;
        return Objects.equals(accountSite, that.accountSite) &&
                Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountSite, roomId);
    }

    @Override
    public int compareTo(AccountInfo o) {
        return this.getAccountId().compareTo(o.getAccountId());
    }
}
