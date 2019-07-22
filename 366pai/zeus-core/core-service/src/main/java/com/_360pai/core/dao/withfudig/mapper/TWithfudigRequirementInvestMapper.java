
package com._360pai.core.dao.withfudig.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.withfudig.TWithfudigRequirementInvestCondition;
import com._360pai.core.model.withfudig.TWithfudigRequirementInvest;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TWithfudigRequirementInvest数据层的基础操作</p>
 * @ClassName: TWithfudigRequirementInvestMapper
 * @Description: TWithfudigRequirementInvest数据层的基础操作
 * @author Generator
 * @date 2018年09月06日 15时50分14秒
 */
@Mapper
public interface TWithfudigRequirementInvestMapper extends BaseMapper<TWithfudigRequirementInvest, TWithfudigRequirementInvestCondition>{

    Integer getCountByRequirementId(Integer requirementId);
}
