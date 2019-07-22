
package com._360pai.core.dao.numberJump;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.numberJump.TDebtCalculatorCondition;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TDebtCalculator;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TDebtCalculator的基础操作Dao</p>
 * @ClassName: TDebtCalculatorDao
 * @Description: TDebtCalculator基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TDebtCalculatorDao extends BaseDao<TDebtCalculator,TDebtCalculatorCondition>{

    PageInfo<CalculatorHistory> getHistoryListV2(int page, int perPage, Map<String, Object> params);

    PageInfo<TDebtCalculator> getNeedToBroadcastList(int page, int perPage, Map<String, Object> params);

    PageInfo<CalculatorBroadcast> getMyBroadcastListV2(int page, int perPage, Map<String, Object> params);

    int getUnreadBroadcastCount(Integer extBindId);

    PageInfo<CalculatorHistory> getHistoryList(int page, int perPage, Map<String, Object> params);

    PageInfo<CalculatorBroadcast> getMyBroadcastList(int page, int perPage, Map<String,Object> stringObjectMap);
}
