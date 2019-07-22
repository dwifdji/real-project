
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TBankCondition;
import com._360pai.core.model.account.TBank;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TBank的基础操作Dao</p>
 * @ClassName: TBankDao
 * @Description: TBank基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
public interface TBankDao extends BaseDao<TBank,TBankCondition>{
    TBank getByCode(String code);
}
