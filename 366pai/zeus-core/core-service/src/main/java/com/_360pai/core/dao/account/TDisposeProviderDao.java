
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TDisposeProviderCondition;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TDisposeProvider的基础操作Dao</p>
 * @ClassName: TDisposeProviderDao
 * @Description: TDisposeProvider基础操作的Dao
 * @author Generator
 * @date 2018年08月27日 15时03分04秒
 */
public interface TDisposeProviderDao extends BaseDao<TDisposeProvider,TDisposeProviderCondition>{
    TDisposeProvider getByAccountId(Integer accountId);

    TDisposeProvider getByPartyId(Integer partyId);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TDisposeProvider> getRecommendList(int page, int perPage, Map<String, Object> params, String orderBy);

    TDisposeProvider findByName(String name);
}
