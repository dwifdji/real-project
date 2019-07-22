package com.winback.core.service._case;

import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.dto._case.UpdateCaseReq;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.*;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.HomePageCaseVO;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseStatusDesc;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;

/**
 * @author RuQ
 * @Title: CaseService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/18 15:43
 */
public interface CaseService {
    public boolean publishCaseAsset(CaseAssetReq req);
    int applyCaseCount(Integer accountId);
    PageInfoResp<Case> getListByAccountId(AdminCaseReq.QueryReq req);
    public boolean saveCase(CaseCommReq caseCommReq);
    public PageInfo<TCase> searchCase(CaseCommReq req);
    public PageInfo<TCase> searchCaseByName(CaseCommReq req);
    public PageInfo<TCase> searchSelfCaseByName(CaseCommReq req);
    public PageInfo<TCase> getHasApplyedCaseList(CaseCommReq req);
    public List<TCaseStatusDesc> getCaseStatusDescList();
    public boolean updateCaseStatusDesc(Integer caseStatusDescId,String statusDesc);
    public CaseVo getCaseDetailByCaseId(String caseId,Integer accountId);
    public CaseAdminVo getCaseDetailByCaseIdAdmin(String caseId);
    public AdminCaseInfoVo getAdminCaseCheckInfo(String caseId);
    public AdminCaseInfoVo getAdminCaseRiskInfo(String caseId);
    public AdminCaseInfoVo getAdminCaseSignInfo(String caseId);
    public boolean deleteCaseAttachment(Integer attachmentId);
    public boolean acceptCase(Integer accountId,String caseId);
    public boolean updateCaseStatus(Integer accountId,String caseId,String subStatus,String statusDesc);
    public boolean updateCaseInfo(CaseCommReq req);
    public boolean verifyCase(CaseCommReq req);
    public boolean endLawsuit(CaseCommReq req);
    public boolean endExcute(CaseCommReq req);
    public boolean endSuccess(CaseCommReq req);
    public boolean setLawyerForCase(CaseCommReq req);
    public PageInfo<LawyerVo> getApplyAcceptLawyers(CaseCommReq req);
    public boolean updateCaseSubStatus(Integer operatorId,String caseId,String subStatusId,String statusDesc);
    public PageInfo<CaseLawyerOrderVo> searchLawyerOrder(CaseLawyerOrderReq req);
    PageInfo<CaseAssetVo> searchCaseAsset(CaseAssetReq req);
    List<CaseAssetVo> exportCaseList(CaseAssetReq req);
    public PageInfo<CasePublishVo> getPublishedCaseList(CaseCommReq req);
    public PageInfo<LawyerVo> searchLawyer(CaseCommReq req);

    /**
     * @operatorId 操作人账户id
     * @caseId    案件id
     * @operate  具体操作
     * 添加放款申请 CaseEnum.CaseStep.ADD_LOAN_APPLY
     * 添加回款申请 CaseEnum.CaseStep.ADD_RECEIVED_APPLY
     * 添加开票申请 CaseEnum.CaseStep.ADD_INVOICE_APPLY
     * 财务审核收款 CaseEnum.CaseStep.HAS_RECEIVED_PAY
     * 财务审核开票 CaseEnum.CaseStep.HAS_INVOICED
     * 财务审核放款 CaseEnum.CaseStep.HAS_LOAN
     *
     * 诉讼或执行更新子状态 CaseEnum.CaseStep.LAWSUIT_EXECUTE
     */

    public boolean updateCaseOperate(UpdateCaseReq req);

    public boolean uploadFile(AttachmentReq req);

    List<HomePageCaseVO> getRecommendedCaseList(Integer caseSize);

    List<WorkBenchVO> getStatusCase();

    public List<String> transStatus2Step(String caseId,String status);

    PageInfoResp<Case> getProjectManagerAllocatedCaseList(CaseCommReq req);

    Integer allocatedCaseToProjectManager(CaseCommReq req);

    Integer cancelAllocatedCaseToProjectManager(CaseCommReq req);

    PageInfoResp<TCase> getMyManageCaseList(CaseCommReq req);

    Integer getProjectManagerByCaseId(String caseId);
}
