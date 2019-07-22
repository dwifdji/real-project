
package com._360pai.core.dao.fastway.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayDisposeApply数据层的基础操作</p>
 * @ClassName: TFastwayDisposeApplyMapper
 * @Description: TFastwayDisposeApply数据层的基础操作
 * @author Generator
 * @date 2018年11月26日 10时48分58秒
 */
@Mapper
public interface TFastwayDisposeApplyMapper extends BaseMapper<TFastwayDisposeApply, TFastwayDisposeApplyCondition>{

    List<TFastwayDisposeApply> findByParam(Map<String, Object> query);
}
