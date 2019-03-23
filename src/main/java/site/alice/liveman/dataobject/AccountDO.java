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

package site.alice.liveman.dataobject;

import java.util.Date;

public class AccountDO extends BaseDO {
    /**
     * 账户所属平台
     */
    private String accountSite;

    /**
     * 平台cookies
     */
    private String cookies;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 描述
     */
    private String description;

    /**
     * 平台房间编号
     */
    private String siteRoomId;

    /**
     * 平台用户id
     */
    private String siteUid;

    /**
     * 是否为管理员
     */
    private Boolean admin;

    /**
     * 是否不可用
     */
    private Boolean disable;

    /**
     * 是否发送动态
     */
    private Boolean postDynamic;

    /**
     * 自动修改房间标题
     */
    private Boolean autoRoomTitle;

    /**
     * 推流地址
     */
    private String streamUrl;

    /**
     * 当前正在转播的视频id
     */
    private Long currentVideoId;

    public String getAccountSite() {
        return accountSite;
    }

    public void setAccountSite(String accountSite) {
        this.accountSite = accountSite == null ? null : accountSite.trim();
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies == null ? null : cookies.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getSiteRoomId() {
        return siteRoomId;
    }

    public void setSiteRoomId(String siteRoomId) {
        this.siteRoomId = siteRoomId == null ? null : siteRoomId.trim();
    }

    public String getSiteUid() {
        return siteUid;
    }

    public void setSiteUid(String siteUid) {
        this.siteUid = siteUid == null ? null : siteUid.trim();
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getDisable() {
        return disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }

    public Boolean getPostDynamic() {
        return postDynamic;
    }

    public void setPostDynamic(Boolean postDynamic) {
        this.postDynamic = postDynamic;
    }

    public Boolean getAutoRoomTitle() {
        return autoRoomTitle;
    }

    public void setAutoRoomTitle(Boolean autoRoomTitle) {
        this.autoRoomTitle = autoRoomTitle;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl == null ? null : streamUrl.trim();
    }

    public Long getCurrentVideoId() {
        return currentVideoId;
    }

    public void setCurrentVideoId(Long currentVideoId) {
        this.currentVideoId = currentVideoId;
    }

}