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
import com.fasterxml.jackson.annotation.JsonIgnore;
import site.alice.liveman.dataobject.BaseDO;
import site.alice.liveman.utils.BeanUtils;
import site.alice.liveman.utils.SecurityUtils;

public class BaseDTO {

    @JsonIgnore
    @JSONField(serialize = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecurityId() {
        return SecurityUtils.aesEncrypt(id + "");
    }

    public void setSecurityId(String securityId) {
        if (securityId != null) {
            String aesDecrypt = SecurityUtils.aesDecrypt(securityId);
            if (aesDecrypt != null) {
                this.id = Long.parseLong(aesDecrypt);
            }
        }
        this.id = null;
    }

    public <R extends BaseDO> R trans(Class<R> classz) {
        R transDist = BeanUtils.instantiateClass(classz);
        BeanUtils.copyProperties(this, transDist);
        return transDist;
    }
}
