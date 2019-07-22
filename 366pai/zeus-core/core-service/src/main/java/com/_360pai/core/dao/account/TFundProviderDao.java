
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TFundProviderCondition;
import com._360pai.core.model.account.TFundProvider;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TFundProvider的基础操作Dao</p>
 * @ClassName: TFundProviderDao
 * @Description: TFundProvider基础操作的Dao
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
public interface TFundProviderDao extends BaseDao<TFundProvider,TFundProviderCondition>{
    TFundProvider getByAccountId(Integer accountId);
    TFundProvider getByPartyId(Integer partyId);
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
}
