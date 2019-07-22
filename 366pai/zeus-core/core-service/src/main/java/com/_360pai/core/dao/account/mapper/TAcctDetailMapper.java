
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAcctDetailCondition;
import com._360pai.core.facade.account.vo.CommissionVo;
import com._360pai.core.facade.account.vo.InviteCommissionVo;
import com._360pai.core.facade.account.vo.WithdrawAcctDetailVo;
import com._360pai.core.model.account.TAcctDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAcctDetail数据层的基础操作</p>
 * @ClassName: TAcctDetailMapper
 * @Description: TAcctDetail数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Mapper
public interface TAcctDetailMapper extends BaseMapper<TAcctDetail, TAcctDetailCondition>{
    List<InviteCommissionVo> getInviteCommissionList(Map<String, Object> params);

    InviteCommissionVo getInviteCommission(@Param("id") Long id);

    List<TAcctDetail> getMyCommissionList(@Param("acctId") Integer acctId);

    List<TAcctDetail> getFrontList(Map<String, Object> params);

    List<TAcctDetail> getList(Map<String, Object> params);

    List<TAcctDetail> getWithdrawList(Integer acctId);

    List<WithdrawAcctDetailVo> getFirstVerifyWithdrawList(Map<String, Object> params);

    Map<String, Object> getFirstVerifyWithdrawSummaryInfo(Map<String, Object> params);

    List<WithdrawAcctDetailVo> getInvoiceVerifyWithdrawList(Map<String, Object> params);

    Map<String, Object> getInvoiceVerifyWithdrawSummaryInfo(Map<String, Object> params);

    List<TAcctDetail> getNoBatchDetail();

    int countUncompletedWithdrawRecords(@Param("bankAccountId") Integer bankAccountId);

    int countRelatedWithdrawRecords(@Param("payBankAccountId") Integer payBankAccountId);
}
