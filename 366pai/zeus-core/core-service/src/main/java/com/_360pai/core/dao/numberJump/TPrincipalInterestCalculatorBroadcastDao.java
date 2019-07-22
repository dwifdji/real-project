
package com._360pai.core.dao.numberJump;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorBroadcastCondition;
import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculatorBroadcast;
import com.github.pagehelper.PageInfo;

/**
 * <p>TPrincipalInterestCalculatorBroadcast的基础操作Dao</p>
 * @ClassName: TPrincipalInterestCalculatorBroadcastDao
 * @Description: TPrincipalInterestCalculatorBroadcast基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TPrincipalInterestCalculatorBroadcastDao extends BaseDao<TPrincipalInterestCalculatorBroadcast,TPrincipalInterestCalculatorBroadcastCondition>{
    TPrincipalInterestCalculatorBroadcast findLatestByCalculatorId(Integer calculatorId);

    PageInfo<TPrincipalInterestCalculatorBroadcast> findByCalculatorId(int page, int perPage, Integer calculatorId);

    Integer getUnreadBroadcastCount(Integer extBindId);
}
