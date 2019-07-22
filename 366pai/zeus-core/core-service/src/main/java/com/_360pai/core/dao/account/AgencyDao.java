
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.AgencyCondition;
import com._360pai.core.model.account.Agency;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Agency的基础操作Dao</p>
 * @ClassName: AgencyDao
 * @Description: Agency基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AgencyDao extends BaseDao<Agency,AgencyCondition>{
    Agency getByLicense(String license);
}
