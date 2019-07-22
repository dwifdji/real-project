
package com._360pai.core.dao.numberJump.mapper;

import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TDebtCalculator;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorCondition;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TPrincipalInterestCalculator数据层的基础操作</p>
 * @ClassName: TPrincipalInterestCalculatorMapper
 * @Description: TPrincipalInterestCalculator数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Mapper
public interface TPrincipalInterestCalculatorMapper extends BaseMapper<TPrincipalInterestCalculator, TPrincipalInterestCalculatorCondition>{
    List<TPrincipalInterestCalculator> getNeedToBroadcastList(Map<String, Object> params);

    List<CalculatorHistory> getHistoryList(Map<String,Object> params);

    List<CalculatorBroadcast> getMyBroadcastList(Map<String,Object> stringObjectMap);
}
