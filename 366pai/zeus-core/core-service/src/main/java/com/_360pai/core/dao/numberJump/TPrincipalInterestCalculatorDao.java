
package com._360pai.core.dao.numberJump;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorCondition;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TPrincipalInterestCalculator的基础操作Dao</p>
 * @ClassName: TPrincipalInterestCalculatorDao
 * @Description: TPrincipalInterestCalculator基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TPrincipalInterestCalculatorDao extends BaseDao<TPrincipalInterestCalculator,TPrincipalInterestCalculatorCondition>{

    PageInfo<TPrincipalInterestCalculator> getNeedToBroadcastList(int page, int perPage, Map<String, Object> params);

    PageInfo<CalculatorHistory> getHistoryListV2(int page, int perPage, Map<String, Object> params);

    PageInfo<CalculatorBroadcast> getMyBroadcastList(int page, int perPage, Map<String, Object> stringObjectMap);
}
