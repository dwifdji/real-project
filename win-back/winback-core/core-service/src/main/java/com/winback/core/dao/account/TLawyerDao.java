
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.account.TLawyerCondition;
import com.winback.core.model.account.TLawyer;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model.account.TLawyerApplyRecord;

import java.util.Map;

/**
 * <p>TLawyer的基础操作Dao</p>
 * @ClassName: TLawyerDao
 * @Description: TLawyer基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
public interface TLawyerDao extends BaseDao<TLawyer,TLawyerCondition>{
    TLawyer findByAccountId(Integer accountId);

    TLawyer createFromApply(TLawyerApplyRecord applyRecord);

    PageInfo<TLawyer> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isExistLawyerLicenseNumber(String lawyerLicenseNumber);

}
