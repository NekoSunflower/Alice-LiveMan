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

package site.alice.liveman.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BaseDOMapper<D, E> {
    long countByExample(E example);

    int deleteByExample(E example);

    int deleteByPrimaryKey(Long id);

    int insert(D record);

    int insertSelective(D record);

    List<D> selectByExample(E example);

    D selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") D record, @Param("example") E example);

    int updateByExample(@Param("record") D record, @Param("example") E example);

    int updateByPrimaryKeySelective(D record);

    int updateByPrimaryKey(D record);
}
