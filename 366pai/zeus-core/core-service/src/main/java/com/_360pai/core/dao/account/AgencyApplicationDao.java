
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.AgencyApplicationCondition;
import com._360pai.core.model.account.AgencyApplication;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AgencyApplication的基础操作Dao</p>
 * @ClassName: AgencyApplicationDao
 * @Description: AgencyApplication基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AgencyApplicationDao extends BaseDao<AgencyApplication,AgencyApplicationCondition>{

    AgencyApplication getApprovedByLicense(String license);

}
