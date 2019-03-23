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

package site.alice.liveman.bo;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.alice.liveman.dataobject.BaseDO;
import site.alice.liveman.mapper.BaseDOMapper;
import site.alice.liveman.model.UserContext;

import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Throwable.class)
public abstract class AbstractBaseBO<D extends BaseDO, E, M extends BaseDOMapper<D, E>> {

    @Autowired
    private UserContext userContext;

    private M mapper;

    public M getMapper() {
        return mapper;
    }

    protected abstract Class<D> getDOClass();

    @Autowired
    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    long countByExample(E example) {
        return mapper.countByExample(example);
    }

    int deleteByExample(E example) {
        D record = null;
        try {
            record = getDOClass().newInstance();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        record.setGmtModified(new Date());
        record.setIsDeleted(true);
        record.setModifier(userContext.getAccountId() + "");
        return mapper.updateByExample(record, example);
    }

    int deleteByPrimaryKey(Long id) {
        D record = null;
        try {
            record = getDOClass().newInstance();
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
        record.setGmtModified(new Date());
        record.setModifier(userContext.getAccountId() + "");
        record.setIsDeleted(true);
        record.setId(id);
        return mapper.updateByPrimaryKeySelective(record);
    }

    int insert(D record) {
        record.setGmtCreated(new Date());
        record.setGmtModified(new Date());
        record.setCreator(userContext.getAccountId() + "");
        record.setModifier(userContext.getAccountId() + "");
        return mapper.insert(record);
    }

    int insertSelective(D record) {
        record.setGmtCreated(new Date());
        record.setGmtModified(new Date());
        return mapper.insertSelective(record);
    }

    List<D> selectByExample(E example) {
        return mapper.selectByExample(example);
    }

    D selectByPrimaryKey(Long id) {
        return mapper.selectByPrimaryKey(id);
    }

    int updateByExampleSelective(@Param("record") D record, @Param("example") E example) {
        record.setGmtModified(new Date());
        record.setModifier(userContext.getAccountId() + "");
        return mapper.updateByExampleSelective(record, example);
    }

    int updateByExample(@Param("record") D record, @Param("example") E example) {
        record.setGmtModified(new Date());
        record.setModifier(userContext.getAccountId() + "");
        return mapper.updateByExample(record, example);
    }

    int updateByPrimaryKeySelective(D record) {
        record.setGmtModified(new Date());
        record.setModifier(userContext.getAccountId() + "");
        return mapper.updateByPrimaryKeySelective(record);
    }

    int updateByPrimaryKey(D record) {
        record.setGmtModified(new Date());
        record.setModifier(userContext.getAccountId() + "");
        return mapper.updateByPrimaryKey(record);
    }
}
