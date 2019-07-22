
package com._360pai.core.dao.withfudig.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.withfudig.TWithfudigConfigCondition;
import com._360pai.core.model.withfudig.TWithfudigConfig;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TWithfudigConfig数据层的基础操作</p>
 * @ClassName: TWithfudigConfigMapper
 * @Description: TWithfudigConfig数据层的基础操作
 * @author Generator
 * @date 2018年09月06日 18时32分31秒
 */
@Mapper
public interface TWithfudigConfigMapper extends BaseMapper<TWithfudigConfig, TWithfudigConfigCondition>{

    Integer selectMaxRank();
}
