
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TAccountCondition;
import com._360pai.core.model.account.TAccount;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAccount的基础操作Dao</p>
 * @ClassName: TAccountDao
 * @Description: TAccount基础操作的Dao
 * @author Generator
 * @date 2018年08月15日 19时32分10秒
 */
public interface TAccountDao extends BaseDao<TAccount,TAccountCondition>{

    PageInfo getAccountList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getCompanyAccountList(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isExistMobile(String mobile);

    TAccount getByMobile(String mobile);

    TAccount getAgencyAdminAccount(Integer agencyId);

    TAccount selectById(Integer id);

    int updateById(TAccount account);

    int unBindAgency(Integer id);

    PageInfo getApplyRecordList(int page, int perPage, Map<String, Object> params, String orderBy);

    int updateCurrentPartyId(Integer id, Integer currentPartyId);

    String getLatestApplyStatus(Integer accountId);

    TAccount getByShopId(Integer shopId);

    PageInfo getAppletAccountListNeedRepair(int page, int perPage, Map<String, Object> params);

    String getMobile(Integer accountId);
}
