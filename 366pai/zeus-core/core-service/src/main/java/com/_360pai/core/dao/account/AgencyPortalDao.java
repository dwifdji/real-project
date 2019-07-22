
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.AgencyPortalCondition;
import com._360pai.core.model.account.AgencyPortal;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AgencyPortal的基础操作Dao</p>
 * @ClassName: AgencyPortalDao
 * @Description: AgencyPortal基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AgencyPortalDao extends BaseDao<AgencyPortal,AgencyPortalCondition>{
    AgencyPortal getByAgencyId(Integer agencyId);
}
