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

import site.alice.liveman.dataobject.BaseDO;

import java.util.Date;

public class ExternalAppSecretDTO extends BaseDO {

    /**
     * 外部服务类型
     */
    private String type;

    /**
     * 应用编号
     */
    private String appId;

    /**
     * 应用秘钥
     */
    private String appKey;

    /**
     * 安全秘钥
     */
    private String secretKey;

    /**
     * 剩余次数
     */
    private Integer limit;

    /**
     * 总次数
     */
    private Integer totalLimit;

    /**
     * 下一次恢复计数的时间
     */
    private Date nextResumeTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey == null ? null : secretKey.trim();
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Integer totalLimit) {
        this.totalLimit = totalLimit;
    }

    public Date getNextResumeTime() {
        return nextResumeTime;
    }

    public void setNextResumeTime(Date nextResumeTime) {
        this.nextResumeTime = nextResumeTime;
    }
}