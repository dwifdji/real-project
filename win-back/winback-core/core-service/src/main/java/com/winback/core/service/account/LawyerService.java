package com.winback.core.service.account;

import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.vo.LawFirm;
import com.winback.core.facade.account.vo.Lawyer;
import com.winback.core.facade.account.vo.LawyerApplyRecord;
import com.winback.core.model.account.TLawyer;

/**
 * @author xdrodger
 * @Title: LawyerService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 14:33
 */
public interface LawyerService {
    AppAccountResp.ApplyResp lawyerApply(AppAccountReq.LawyerApplyReq req);

    TLawyer findById(Integer id);

    TLawyer findByAccountId(Integer accountId);

    String getApplyStatus(Integer accountId);

    PageInfoResp<LawyerApplyRecord> getLawyerApplyRecordListByPage(AdminAccountReq.LawyerQueryReq req);

    Integer applyApprove(AdminAccountReq.LawyerVerifyReq req);

    void applyReject(AdminAccountReq.LawyerVerifyReq req);

    PageInfoResp<Lawyer> getLawyerListByPage(AdminAccountReq.LawyerQueryReq req);

    Integer lawyerUpdate(AdminAccountReq.LawyerUpdateReq req);

    PageInfoResp<LawFirm> getLawFirmListByPage(AdminAccountReq.LawFirmQueryReq req);

    Integer lawFirmAdd(AdminAccountReq.LawFirmAddReq req);

    Integer lawFirmUpdate(AdminAccountReq.LawFirmUpdateReq req);
}
