
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.CompanyCondition;
import com._360pai.core.model.account.Company;

import java.util.List;

/**
 * <p>Company的基础操作Dao</p>
 * @ClassName: CompanyDao
 * @Description: Company基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface CompanyDao extends BaseDao<Company,CompanyCondition>{
    List<Company> getByAdminId(Integer accountId);

    Company getByLicense(String license);
}
