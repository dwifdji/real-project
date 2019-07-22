
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.account.TFranchiseeCondition;
import com.winback.core.model._case.TCase;
import com.winback.core.model.account.*;
import com.winback.arch.core.abs.BaseDao;

import java.util.Map;

/**
 * <p>TFranchisee的基础操作Dao</p>
 * @ClassName: TFranchiseeDao
 * @Description: TFranchisee基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
public interface TFranchiseeDao extends BaseDao<TFranchisee,TFranchiseeCondition>{
    TFranchisee findByAccountId(Integer accountId);

    TFranchisee createFromApply(TFranchiseeApplyRecord applyRecord);

    PageInfo<TFranchisee> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TAccount> getInviteCustomerList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TCase> getInviteCaseList(int page, int perPage, Map<String, Object> params, String orderBy);
}
