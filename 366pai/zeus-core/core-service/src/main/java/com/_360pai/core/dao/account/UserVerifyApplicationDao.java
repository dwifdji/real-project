
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.UserVerifyApplicationCondition;
import com._360pai.core.model.account.TUserApplyRecord;
import com._360pai.core.model.account.UserVerifyApplication;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>UserVerifyApplication的基础操作Dao</p>
 * @ClassName: UserVerifyApplicationDao
 * @Description: UserVerifyApplication基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface UserVerifyApplicationDao extends BaseDao<UserVerifyApplication,UserVerifyApplicationCondition>{
    UserVerifyApplication getApprovedByCertificateNumber(String certificateNumber);
}
