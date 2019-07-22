
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.CompanyVerifyApplicationCondition;
import com._360pai.core.model.account.CompanyVerifyApplication;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>CompanyVerifyApplication的基础操作Dao</p>
 * @ClassName: CompanyVerifyApplicationDao
 * @Description: CompanyVerifyApplication基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface CompanyVerifyApplicationDao extends BaseDao<CompanyVerifyApplication,CompanyVerifyApplicationCondition>{
    CompanyVerifyApplication getApprovedByLicense(String license);
}
