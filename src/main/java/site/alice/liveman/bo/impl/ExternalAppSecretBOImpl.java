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

package site.alice.liveman.bo.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.alice.liveman.bo.AbstractBaseBO;
import site.alice.liveman.bo.ExternalAppSecretBO;
import site.alice.liveman.dataobject.ExternalAppSecretDO;
import site.alice.liveman.dataobject.ExternalAppSecretDOExample;
import site.alice.liveman.dataobject.dto.ExternalAppSecretDTO;
import site.alice.liveman.mapper.ExternalAppSecretDOMapper;
import site.alice.liveman.model.UserContext;
import site.alice.liveman.service.external.ExternalServiceType;
import site.alice.liveman.utils.BeanUtils;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ExternalAppSecretBOImpl extends AbstractBaseBO<ExternalAppSecretDO, ExternalAppSecretDOExample, ExternalAppSecretDOMapper> implements ExternalAppSecretBO {

    @Autowired
    private UserContext userContext;

    @Override
    public ExternalAppSecretDO getAppSecret(ExternalServiceType type) {
        ExternalAppSecretDOExample example = new ExternalAppSecretDOExample();
        example.createCriteria().andTenantIdEqualTo(userContext.getTenantId()).andTypeEqualTo(type.getCode());
        example.setOrderByClause("`limit` desc limit 1");
        List<ExternalAppSecretDO> externalAppSecretDOS = mapper.selectByExample(example);
        if (!externalAppSecretDOS.isEmpty()) {
            ExternalAppSecretDO externalAppSecretDO = externalAppSecretDOS.get(0);
            mapper.decrementLimit(externalAppSecretDO.getId());
            log.info("返回OcrAppSecret：" + ToStringBuilder.reflectionToString(externalAppSecretDO));
            return externalAppSecretDO;
        }
        log.info("没有找到可用的AppSecret，请求的Type:" + type.getCode());
        return null;
    }

    @Override
    protected Class<ExternalAppSecretDO> getDOClass() {
        return ExternalAppSecretDO.class;
    }

    @Autowired
    public void setMapper(ExternalAppSecretDOMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public int insert(ExternalAppSecretDTO externalAppSecretDTO) {
        if (externalAppSecretDTO.getNextResumeTime() == null) {
            Date date = new Date();
            externalAppSecretDTO.setNextResumeTime(new Date(date.getYear(), date.getMonth(), date.getDate() + 1));
        }
        if (externalAppSecretDTO.getLimit() == null) {
            externalAppSecretDTO.setLimit(externalAppSecretDTO.getTotalLimit());
        }
        return super.insert(externalAppSecretDTO.trans(ExternalAppSecretDO.class));
    }

    @Override
    public List<ExternalAppSecretDTO> selectForList() {
        ExternalAppSecretDOExample example = new ExternalAppSecretDOExample();
        example.createCriteria().andTenantIdEqualTo(userContext.getTenantId());
        List<ExternalAppSecretDO> externalAppSecretDOS = mapper.selectByExample(example);
        return BeanUtils.transList(externalAppSecretDOS, ExternalAppSecretDTO.class);
    }

    @Override
    public int update(ExternalAppSecretDTO externalAppSecretDTO) {
        return updateByPrimaryKey(externalAppSecretDTO.trans(ExternalAppSecretDO.class));
    }

    @Override
    public int remove(ExternalAppSecretDTO externalAppSecretDTO) {
        return deleteByPrimaryKey(externalAppSecretDTO.getId());
    }
}
