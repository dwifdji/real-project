
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TFundProviderApplyCondition;
import com._360pai.core.model.account.TFundProviderApply;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TFundProviderApply的基础操作Dao</p>
 * @ClassName: TFundProviderApplyDao
 * @Description: TFundProviderApply基础操作的Dao
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
public interface TFundProviderApplyDao extends BaseDao<TFundProviderApply,TFundProviderApplyCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
    boolean hasPendingApply(Integer partyId);
}
