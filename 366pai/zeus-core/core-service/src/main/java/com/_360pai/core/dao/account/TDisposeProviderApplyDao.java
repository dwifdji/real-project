
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TDisposeProviderApplyCondition;
import com._360pai.core.model.account.TDisposeProviderApply;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TDisposeProviderApply的基础操作Dao</p>
 * @ClassName: TDisposeProviderApplyDao
 * @Description: TDisposeProviderApply基础操作的Dao
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
public interface TDisposeProviderApplyDao extends BaseDao<TDisposeProviderApply,TDisposeProviderApplyCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean hasPendingApply(Integer partyId);


}
