
package com._360pai.core.dao.numberJump;

import com._360pai.core.condition.numberJump.TDebtCalculatorBroadcastCondition;
import com._360pai.core.model.numberJump.TDebtCalculatorBroadcast;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Date;

/**
 * <p>TDebtCalculatorBroadcast的基础操作Dao</p>
 * @ClassName: TDebtCalculatorBroadcastDao
 * @Description: TDebtCalculatorBroadcast基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TDebtCalculatorBroadcastDao extends BaseDao<TDebtCalculatorBroadcast,TDebtCalculatorBroadcastCondition>{
    TDebtCalculatorBroadcast findLatestByCalculatorId(Integer calculatorId);

    PageInfo<TDebtCalculatorBroadcast> findByCalculatorId(int page, int perPage, Integer calculatorId);

    TDebtCalculatorBroadcast findLatestByCalculatorIdAndDate(Integer id, Date date);

    Integer getUnreadBroadcastCount(Integer extBindId);
}
