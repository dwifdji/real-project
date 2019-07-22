
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TAcctDetailCondition;
import com._360pai.core.facade.account.vo.InviteCommissionVo;
import com._360pai.core.model.account.TAcctDetail;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TAcctDetail的基础操作Dao</p>
 * @ClassName: TAcctDetailDao
 * @Description: TAcctDetail基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
public interface TAcctDetailDao extends BaseDao<TAcctDetail,TAcctDetailCondition>{
    PageInfo getInviteCommissionList(int page, int perPage, Map<String, Object> params, String orderBy);

    InviteCommissionVo getInviteCommission(Long id);

    PageInfo getMyCommissionList(int page, int perPage, Integer acctId, String orderBy);
    PageInfo getWithdrawList(int page, int perPage, Integer acctId);

    PageInfo getFrontListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getFirstVerifyWithdrawListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    Map<String, Object> getFirstVerifyWithdrawSummaryInfo(Map<String, Object> params);

    PageInfo getInvoiceVerifyWithdrawListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    Map<String, Object> getInvoiceVerifyWithdrawSummaryInfo(Map<String, Object> params);

    List<TAcctDetail> getNoBatchDetail();

    boolean hasUncompletedWithdrawRecords(Integer bankAccountId);

    boolean hasRelatedWithdrawRecords(Integer bankAccountId);
}
