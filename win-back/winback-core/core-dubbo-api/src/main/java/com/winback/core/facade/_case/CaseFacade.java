package com.winback.core.facade._case;


import com.winback.arch.common.AppReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.CaseAssetVo;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.CaseBigBrief;
import com.winback.core.facade._case.vo.CaseBrief;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.operate.req.OperationReq;

import java.util.List;

/**
 * @author RuQ
 * @Title: CaseFacade
 * @ProjectName winback
 * @Description:
 * @date 2019/1/18 15:47
 */
public interface CaseFacade {


    /** 发布债权收购 **/
    public ResponseModel publishCaseAsset(CaseAssetReq req);

    /** 发布案件 **/

    public ResponseModel  publishCase(CaseCommReq req);


    /** 我发布的案件列表 **/
    public ResponseModel getMyPublishCaseList(CaseCommReq req);

    /** 案件库列表接口 **/
    public ResponseModel getCaseList(CaseCommReq req);

    /** 已完结案件列表接口 **/
    public ResponseModel getEndCaseList(CaseCommReq req);

    /** 根据案由名称搜索案件 **/
    public ResponseModel searchCaseByName(CaseCommReq req);


    /** 根据案由名称搜索自己案件 **/
    public ResponseModel searchSelfCaseByName(CaseCommReq req);

    /** 我承接的案件列表 **/
    public ResponseModel getMyAcceptCaseList(CaseCommReq req);

    /** 案件详情 **/
    public ResponseModel getCaseDetail(CaseCommReq req);

    /** 承接案件 **/
    public ResponseModel acceptCase(CaseCommReq req);

    /** 更新案件进展 **/
    public ResponseModel updateCaseStatus(CaseCommReq req);


    /** 新发布的案件列表 **/
    public ResponseModel getPublishedCaseList(CaseCommReq req);

    /** 后台上传文件 **/
    public ResponseModel uploadFile(AttachmentReq req);
    /** 后台案件搜索 **/
    public ResponseModel searchCase(CaseCommReq req);

    /** 后台编辑基本信息 **/
    public ResponseModel updateCaseBaseInfo(CaseCommReq req);

    /** 后台预检审核 **/
    public ResponseModel preCheck(CaseCommReq req);

    /** 后台风控审核 **/
    public ResponseModel riskCheck(CaseCommReq req);

    /** 后台智能匹配站内律所 **/
    public ResponseModel aiSearchFirm(CaseCommReq req);

    /** 后台获取案件已承接律师 **/
    public ResponseModel getAcceptedLawyers(CaseCommReq req);

    /** 后台搜索律师 **/
    public ResponseModel searchLawyer(CaseCommReq req);

    /** 后台安排案件承接律师 **/
    public ResponseModel setCaseLawyer(CaseCommReq req);

    /** 后台合同线下签约 **/
    public ResponseModel signContract(CaseCommReq req);

    /** 后台审核放款 **/
    public ResponseModel verifyPayMoney(CaseCommReq req);

    /** 后台完成诉讼 **/
    public ResponseModel endLawsuit(CaseCommReq req);

    /** 后台完成执行 **/
    public ResponseModel endExcute(CaseCommReq req);

    /** 后台完成结案 **/
    public ResponseModel endSuccess(CaseCommReq req);

    /** 后台获取案件基本信息 **/
    public ResponseModel getAdminCaseBaseInfo(CaseCommReq req);

    /** 后台获取案件 预检信息 **/
    public ResponseModel getAdminCaseCheckInfo(CaseCommReq req);

    /** 后台获取案件 风控审核信息 **/
    public ResponseModel getAdminCaseRiskInfo(CaseCommReq req);

    /** 后台获取案件 合同签约信息 **/
    public ResponseModel getAdminCaseSignInfo(CaseCommReq req);

    /** 后台删除附件 **/
    public ResponseModel deleteCaseAttachment(CaseCommReq req);

    /** 后台获取案件状态列表 **/
    public ResponseModel getCaseStatusList();

    /** 后台获取案件状态描述列表 **/
    public ResponseModel getCaseStatusDescList();

    /** 修改案件状态描述 **/
    public ResponseModel updateCaseStatusDesc(CaseCommReq req);

    /** 后台律师接单管理 **/
    public ResponseModel searchLawyerOrder(CaseLawyerOrderReq req);

    /** 后台债权收购列表 **/
    public ResponseModel searchCaseAsset(CaseAssetReq req);

    /** 后台债权收购列表 **/
    public List<CaseAssetVo> exportCaseList(CaseAssetReq req);

    PageInfoResp<Case> getPublishCaseList(AdminCaseReq.QueryReq req);

    ListResp<CaseBrief> getCaseBriefList(AdminCaseReq.QueryReq req);

    PageInfoResp<CaseBrief> getCaseBriefListByPage(AdminCaseReq.QueryReq req);

    PageInfoResp<CaseBrief> getCaseBriefList(AppReq req);

    Integer addCaseBrief(AdminCaseReq.AddCaseBriefReq req);

    Integer editCaseBrief(AdminCaseReq.EditCaseBriefReq req);

    Integer deleteCaseBrief(AdminCaseReq.DeleteCaseBriefReq req);

    ResponseModel getRecommendedCaseList(OperationReq.CaseSizeReq caseSizeReq);

    ListResp<CaseBigBrief> getCaseBigBriefList();

    ListResp<CaseBigBrief> getBackgroundCaseBigBriefList(Boolean all);

    PageInfoResp<Case> getProjectManagerAllocatedCaseList(CaseCommReq req);

    Integer allocatedCaseToProjectManager(CaseCommReq req);

    Integer cancelAllocatedCaseToProjectManager(CaseCommReq req);

    /** 我管理的案件列表 **/
    ResponseModel getMyManageCaseList(CaseCommReq req);

    Integer getProjectManagerByCaseId(String caseId);
}
