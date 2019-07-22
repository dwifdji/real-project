
package com.winback.core.dao.account;

import com.winback.core.condition.account.TAccountContactsCondition;
import com.winback.core.model.account.TAccountContacts;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAccountContacts的基础操作Dao</p>
 * @ClassName: TAccountContactsDao
 * @Description: TAccountContacts基础操作的Dao
 * @author Generator
 * @date 2019年04月03日 14时37分27秒
 */
public interface TAccountContactsDao extends BaseDao<TAccountContacts,TAccountContactsCondition>{
    List<TAccountContacts> find(Integer accountId);

    int batchSave(List<TAccountContacts> list);
}
