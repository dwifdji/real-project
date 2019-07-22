
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.AccountCondition;
import com._360pai.core.model.account.Account;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Account的基础操作Dao</p>
 * @ClassName: AccountDao
 * @Description: Account基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AccountDao extends BaseDao<Account,AccountCondition>{
    Account getByMobile(String mobile);

}
