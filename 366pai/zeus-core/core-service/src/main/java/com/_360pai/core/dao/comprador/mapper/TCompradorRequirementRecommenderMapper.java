
package com._360pai.core.dao.comprador.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.comprador.TCompradorRequirementRecommenderCondition;
import com._360pai.core.model.comprador.TCompradorRequirementRecommender;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TCompradorRequirementRecommender数据层的基础操作</p>
 * @ClassName: TCompradorRequirementRecommenderMapper
 * @Description: TCompradorRequirementRecommender数据层的基础操作
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
@Mapper
public interface TCompradorRequirementRecommenderMapper extends BaseMapper<TCompradorRequirementRecommender, TCompradorRequirementRecommenderCondition>{

    int getCountByRequirementId(Integer requirementId);
}
