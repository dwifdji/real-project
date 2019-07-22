
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.AccountCompanyMapCondition;
import com._360pai.core.model.account.AccountCompanyMap;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>AccountCompanyMap的基础操作Dao</p>
 * @ClassName: AccountCompanyMapDao
 * @Description: AccountCompanyMap基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AccountCompanyMapDao extends BaseDao<AccountCompanyMap,AccountCompanyMapCondition>{
    List<AccountCompanyMap> getByAccountId(Integer accountId);

    List<AccountCompanyMap> getByCompanyId(Integer accountId);

    AccountCompanyMap getByAccountIdCompanyId(Integer accountId, Integer companyId);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
}
