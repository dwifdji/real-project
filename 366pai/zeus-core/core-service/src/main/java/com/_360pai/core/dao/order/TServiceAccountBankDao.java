
package com._360pai.core.dao.order;

import com._360pai.core.condition.order.TServiceAccountBankCondition;
import com._360pai.core.model.order.TServiceAccountBank;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TServiceAccountBank的基础操作Dao</p>
 * @ClassName: TServiceAccountBankDao
 * @Description: TServiceAccountBank基础操作的Dao
 * @author Generator
 * @date 2018年10月06日 23时43分10秒
 */
public interface TServiceAccountBankDao extends BaseDao<TServiceAccountBank,TServiceAccountBankCondition>{

    int updateByPartyId(TServiceAccountBank bank);
}
