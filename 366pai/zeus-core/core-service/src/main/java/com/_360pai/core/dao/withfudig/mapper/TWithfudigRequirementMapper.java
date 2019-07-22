
package com._360pai.core.dao.withfudig.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TWithfudigRequirement数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TWithfudigRequirementMapper
 * @Description: TWithfudigRequirement数据层的基础操作
 * @date 2018年09月06日 15时50分14秒
 */
@Mapper
public interface TWithfudigRequirementMapper extends BaseMapper<TWithfudigRequirement, TWithfudigRequirementCondition> {

    /**
     * 描述 获取未支付的需求单
     *
     * @author : whisky_vip
     * @date : 2018/9/18 13:57
     */
    TWithfudigRequirement selectByIdWithoutPay(Integer id);

    List<TWithfudigRequirement> selectListForPlatform(TWithfudigRequirementCondition condition);

    List<TWithfudigRequirement> selectFollowListForPlatform(TWithfudigRequirementCondition condition);

    List<TWithfudigRequirement> myRequirementList(TWithfudigRequirementCondition condition);
}
