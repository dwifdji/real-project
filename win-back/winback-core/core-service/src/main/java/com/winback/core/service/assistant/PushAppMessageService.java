package com.winback.core.service.assistant;

import com.winback.core.dto.assistant.PushMsgDto;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AppPushMessageService
 * @ProjectName winback
 * @Description:
 * @date 2019/3/1 14:42
 */
public interface PushAppMessageService {

    /**
     * 发送合同上新消息
     */
    void pushContractNewArrivalMsg(String typeName);

    /**
     * 发送合同订单支付成功消息
     */
    void pushContractOrderPaySuccessMsg(Integer accountId);

    /**
     * 发送合同下载成功消息
     */
    void pushContractDownloadSuccessMsg(Integer accountId);

    /**
     * 发送合同电子发票申请成功消息
     */
    void pushContractInvoiceApplySuccessMsg(Integer accountId);

    /**
     * 发送加盟商申请成功消息
     */
    void pushFranchiseeApplySuccessMsg(Integer accountId);

    /**
     * 发送加盟商申请审核消息
     */
    void pushFranchiseeApplyNewArrivalMsg();

    /**
     * 发送加盟商审核通过消息
     */
    void pushFranchiseeApplyApproveMsg(Integer accountId);

    /**
     * 发送加盟商客户发布案件风控审核通过消息
     */
    void pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsgIfNeed(String caseId);

    /**
     * 发送加盟商客户发布案件风控审核通过消息
     */
    void pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsg(Integer accountId, String subordinateName);

    /**
     * 发送加盟商客户发布案件完成消息
     */
    void pushFranchiseeSubordinatePublishedCaseSuccessMsgIfNeed(String caseId);

    /**
     * 发送加盟商客户发布案件完成消息
     */
    void pushFranchiseeSubordinatePublishedCaseSuccessMsg(Integer accountId, String subordinateName);

    /**
     * 发送发布风险投案件成功消息
     */
    void pushRiskCasePublishSuccessMsg(Integer accountId,String caseId);

    /**
     * 发送用户发布风险投案件消息（运营）
     */
    void pushRiskCaseNewArrivalMsg();

    /**
     * 发送风险投案件审核通过消息
     */
    void pushRiskCaseApplyApproveMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送风险投案件审核拒绝消息
     */
    void pushRiskCaseApplyRejectMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送发布联合执行案件成功消息
     */
    void pushUniteExecuteCasePublishSuccessMsg(Integer accountId,String caseId);

    /**
     * 发送用户发布联合执行案件消息（运营）
     */
    void pushUniteExecuteCaseNewArrivalMsg();

    /**
     * 发送联合执行案件审核通过消息
     */
    void pushUniteExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送联合执行案件审核拒绝消息
     */
    void pushUniteExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送发布委托执行案件成功消息
     */
    void pushEntrustExecuteCasePublishSuccessMsg(Integer accountId, String caseId);

    /**
     * 发送用户发布委托执行案件消息（运营）
     */
    void pushEntrustExecuteCaseNewArrivalMsg();

    /**
     * 发送委托执行案件审核通过消息
     */
    void pushEntrustExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送诉委托行案件审核拒绝消息
     */
    void pushEntrustExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送发布诉讼执行案件成功消息
     */
    void pushLawsuitExecuteCasePublishSuccessMsg(Integer accountId,String caseId);

    /**
     * 发送用户发布诉讼执行案件消息（运营）
     */
    void pushLawsuitExecuteCaseNewArrivalMsg();

    /**
     * 发送诉讼执行案件审核通过消息
     */
    void pushLawsuitExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送诉讼执行案件审核拒绝消息
     */
    void pushLawsuitExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送发布债权收购案件成功消息
     */
    void pushObligationPurchaseCasePublishSuccessMsg(Integer accountId);

    /**
     * 发送用户发布债权收购案件消息（运营）
     */
    void pushObligationPurchaseCaseNewArrivalMsg();

    /**
     * 发送发布案件成功消息
     */
    void pushCasePublishSuccessMsg(Integer accountId,String caseId);

    /**
     * 发送用户发布案件消息（运营）
     */
    void pushCaseNewArrivalMsg();

    /**
     * 发送案件审核通过消息
     */
    void pushCaseApplyApproveMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送案件审核拒绝消息
     */
    void pushCaseApplyRejectMsg(Integer accountId, String caseId, String createTime);

    /**
     * 发送律师承接案件成功消息
     */
    void pushLawyerAcceptCaseSuccessMsg(Integer accountId, Integer id, String caseId);

    /**
     * 发送律师承接案件消息（运营）
     */
    void pushLawyerAcceptCaseNewArrivalMsg(Integer id, String caseId);

    /**
     * 发送律师承接案件审核通过消息
     */
    void pushLawyerAcceptCaseApplyApproveMsg(Integer accountId, String caseId);

    /**
     * 发送律师承接案件审核拒绝消息
     */
    void pushLawyerAcceptCaseApplyRejectMsg(Integer accountId, String caseId);

    /**
     * 发送律师申请成功消息
     */
    void pushLawyerApplySuccessMsg(Integer accountId);

    /**
     * 发送律师申请审核消息
     */
    void pushLawyerApplyNewArrivalMsg();

    /**
     * 发送律师审核通过消息
     */
    void pushLawyerApplyApproveMsg(Integer accountId);

    /**
     * 发送律师审核通过消息
     */
    void pushLawyerApplyRejectMsg(Integer accountId);



    /**
     * 发送律师审核通过消息
     */
    void newCasePushLawyer(String caseId);



    /**
     * 匹配律师 发送 给委托人
     */
    void setCaseLawyerPushUser(Integer accountId,String caseId);



    /**
     * 线下签约 发给律师
     */
    void pushLawyerAcceptCaseApplyPassMsg(Integer accountId, String caseId);

    /**
     * 用户发布案件 发给项目经理
     */
    void pushCasePublishToProjectManagerMsg(Integer accountId, String caseId);

    /**
     * 推送客服
     */
    void pushWatersAppList(String partyName, String defaultSystemWaiterUrl);
}
