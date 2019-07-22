
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.TownCondition;
import com._360pai.core.model.assistant.Town;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Town数据层的基础操作</p>
 * @ClassName: TownMapper
 * @Description: Town数据层的基础操作
 * @author Generator
 * @date 2019年04月25日 14时53分26秒
 */
@Mapper
public interface TownMapper extends BaseMapper<Town, TownCondition>{

    String getAreaDetail(@Param("townId") String townId);

    String getAreaDetailByTownId(@Param("townId")String townId);

    String getAreaDetailByAreaId(@Param("areaId")String areaId);
}
