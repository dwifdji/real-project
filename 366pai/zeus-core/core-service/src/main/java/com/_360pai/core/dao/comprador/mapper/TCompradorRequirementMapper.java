
package com._360pai.core.dao.comprador.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.comprador.TCompradorRequirementCondition;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TCompradorRequirement数据层的基础操作</p>
 * @ClassName: TCompradorRequirementMapper
 * @Description: TCompradorRequirement数据层的基础操作
 * @author Generator
 * @date 2018年09月03日 12时40分06秒
 */
@Mapper
public interface TCompradorRequirementMapper extends BaseMapper<TCompradorRequirement, TCompradorRequirementCondition>{
    /**
     * 描述 未支付的获取 根据id获取需求单
     *
     * @author : whisky_vip
     * @date : 2018/9/18 13:59
     */
    TCompradorRequirement selectByIdWithoutPay(Integer id);

    List<TCompradorRequirement> selectListForPlatform(TCompradorRequirementCondition condition);

    List<TCompradorRequirement> myRequirementList(TCompradorRequirementCondition condition);
}
