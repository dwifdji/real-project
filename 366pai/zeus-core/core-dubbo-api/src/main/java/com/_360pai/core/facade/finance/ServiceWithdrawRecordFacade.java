package com._360pai.core.facade.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.finance.req.WithdrawRecordReq;

/**
 * @author xiaolei
 * @create 2018-10-07 20:23
 */
public interface ServiceWithdrawRecordFacade {

    /**
     * 提现
     * @param req
     * @return
     */
    int addWithdrawRecord(WithdrawRecordReq.AddWithdrawRecord req);

    /**
     * 获取提现记录
     * @param req
     * @return
     */
    PageInfoResp getWithdrawRecordPage(WithdrawRecordReq.GetWithdrawRecord req);

    /**
     * 再次提交
     * @param req
     * @return
     */
    int resubmitWithdrawRecord(WithdrawRecordReq.AddWithdrawRecord req);

    /**
     * admin获取提现记录
     * @param req
     * @return
     */
    PageInfoResp getAdminWithdrawRecordPage(WithdrawRecordReq.GetAdminWithdrawRecord req);
}
