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

public class ChannelDO {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Date gmtCreated;

    /**
     * 
     */
    private Date gmtModified;

    /**
     * 
     */
    private String modifier;

    /**
     * 
     */
    private String creator;

    /**
     * 默认推流账户ID
     */
    private String defaultAccountId;

    /**
     * 默认动态推送账户
     */
    private String dynamicPostAccountid;

    /**
     * 频道地址
     */
    private String channelUrl;

    /**
     * 频道名称
     */
    private String channelName;

    /**
     * 转播Cookies
     */
    private String cookies;

    /**
     * 默认推流区域
     */
    private String defaultArea;

    /**
     * 需要录像
     */
    private Boolean needRecord;

    /**
     * 租户ID
     */
    private Long tenantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Date gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public String getDefaultAccountId() {
        return defaultAccountId;
    }

    public void setDefaultAccountId(String defaultAccountId) {
        this.defaultAccountId = defaultAccountId == null ? null : defaultAccountId.trim();
    }

    public String getDynamicPostAccountid() {
        return dynamicPostAccountid;
    }

    public void setDynamicPostAccountid(String dynamicPostAccountid) {
        this.dynamicPostAccountid = dynamicPostAccountid == null ? null : dynamicPostAccountid.trim();
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl == null ? null : channelUrl.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies == null ? null : cookies.trim();
    }

    public String getDefaultArea() {
        return defaultArea;
    }

    public void setDefaultArea(String defaultArea) {
        this.defaultArea = defaultArea == null ? null : defaultArea.trim();
    }

    public Boolean getNeedRecord() {
        return needRecord;
    }

    public void setNeedRecord(Boolean needRecord) {
        this.needRecord = needRecord;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}