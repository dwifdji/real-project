
package com._360pai.core.dao.numberJump.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.numberJump.TDebtCalculatorCondition;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TDebtCalculator;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TDebtCalculator数据层的基础操作</p>
 * @ClassName: TDebtCalculatorMapper
 * @Description: TDebtCalculator数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Mapper
public interface TDebtCalculatorMapper extends BaseMapper<TDebtCalculator, TDebtCalculatorCondition>{
    List<CalculatorHistory> getHistoryListV2(Map<String, Object> params);

    List<TDebtCalculator> getNeedToBroadcastList(Map<String, Object> params);

    List<CalculatorBroadcast> getMyBroadcastListV2(Map<String, Object> params);

    int getUnreadBroadcastCount(@Param("extBindId") Integer extBindId);

    List<CalculatorHistory> getHistoryList(Map<String, Object> params);

    List<CalculatorBroadcast> getMyBroadcastList(Map<String, Object> params);
}
