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

public class IspConfigDTO extends BaseDO {
    /**
     * 主机提供商编号
     */
    private String ispCode;

    /**
     * api授权参数
     */
    private String ispToken;

    /**
     * 主机提供商类型
     */
    private String ispType;

    /**
     * 优先级
     */
    private Integer order;

    public String getIspCode() {
        return ispCode;
    }

    public void setIspCode(String ispCode) {
        this.ispCode = ispCode == null ? null : ispCode.trim();
    }

    public String getIspToken() {
        return ispToken;
    }

    public void setIspToken(String ispToken) {
        this.ispToken = ispToken == null ? null : ispToken.trim();
    }

    public String getIspType() {
        return ispType;
    }

    public void setIspType(String ispType) {
        this.ispType = ispType == null ? null : ispType.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}