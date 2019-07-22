
package com._360pai.core.dao.numberJump.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.numberJump.TDebtCalculatorBroadcastCondition;
import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>TDebtCalculatorBroadcast数据层的基础操作</p>
 * @ClassName: TDebtCalculatorBroadcastMapper
 * @Description: TDebtCalculatorBroadcast数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Mapper
public interface TDebtCalculatorBroadcastMapper extends BaseMapper<TDebtCalculatorBroadcast, TDebtCalculatorBroadcastCondition>{
    TDebtCalculatorBroadcast findLatestByCalculatorId(@Param("calculatorId") Integer calculatorId);

    List<TDebtCalculatorBroadcast> findByCalculatorId(@Param("calculatorId") Integer calculatorId);

    TDebtCalculatorBroadcast findLatestByCalculatorIdAndDate(@Param("calculatorId") Integer calculatorId, @Param("date") Date date);

    Integer getUnreadBroadcastCount(@Param("extBindId")Integer extBindId);
}
