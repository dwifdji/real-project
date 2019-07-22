
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.UserCondition;
import com._360pai.core.model.account.User;

/**
 * <p>User的基础操作Dao</p>
 * @ClassName: UserDao
 * @Description: User基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface UserDao extends BaseDao<User,UserCondition>{
    User getByAccountId(Integer accountId);

    User getByCertificateNumber(String certificateNumber);
}
