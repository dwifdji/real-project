
package com._360pai.core.dao.numberJump.mapper;

import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorBroadcastCondition;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorBroadcast;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TPrincipalInterestCalculatorBroadcast数据层的基础操作</p>
 * @ClassName: TPrincipalInterestCalculatorBroadcastMapper
 * @Description: TPrincipalInterestCalculatorBroadcast数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Mapper
public interface TPrincipalInterestCalculatorBroadcastMapper extends BaseMapper<TPrincipalInterestCalculatorBroadcast, TPrincipalInterestCalculatorBroadcastCondition>{
    TPrincipalInterestCalculatorBroadcast findLatestByCalculatorId(@Param("calculatorId") Integer calculatorId);

    List<TPrincipalInterestCalculatorBroadcast> findByCalculatorId(@Param("calculatorId") Integer calculatorId);

    Integer getUnreadBroadcastCount(@Param("extBindId") Integer extBindId);
}
