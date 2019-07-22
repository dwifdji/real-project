
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.BankAccountCondition;
import com._360pai.core.model.account.BankAccount;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>BankAccount的基础操作Dao</p>
 * @ClassName: BankAccountDao
 * @Description: BankAccount基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface BankAccountDao extends BaseDao<BankAccount,BankAccountCondition>{
    int deleteById(Integer id);

    List<BankAccount> getByPartyId(Integer partyId);

    boolean hasBankAccount(Integer partyId);

    BankAccount getLatestByPartyId(Integer partyId);
}
