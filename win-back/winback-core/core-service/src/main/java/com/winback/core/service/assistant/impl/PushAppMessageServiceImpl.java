package com.winback.core.service.assistant.impl;

import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.core.condition.account.TLawyerCondition;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dao.account.TAccountDao;
import com.winback.core.dao.account.TFranchiseeDao;
import com.winback.core.dao.account.TLawyerDao;
import com.winback.core.dto.assistant.PushMsgDto;
import com.winback.core.facade.account.vo.Lawyer;
import com.winback.core.model._case.TCase;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TFranchisee;
import com.winback.core.model.account.TLawyer;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.assistant.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AppPushMessageServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/3/1 14:43
 */
@Slf4j
@Service
public class PushAppMessageServiceImpl implements PushAppMessageService {

    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TFranchiseeDao franchiseeDao;

    @Autowired
    private TLawyerDao lawyerDao;

    @Override
    public void pushContractNewArrivalMsg(String typeName) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_1);
        JSONObject param = new JSONObject();
        param.put("classifyName", typeName);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushContractOrderPaySuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_2);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushContractDownloadSuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_3);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushContractInvoiceApplySuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_4);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushFranchiseeApplySuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_5);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
        pushFranchiseeApplyNewArrivalMsg();
    }

    @Override
    public void pushFranchiseeApplyNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_6);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushFranchiseeApplyApproveMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_7);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsgIfNeed(String caseId) {
        TCase _case = caseDao.getCaseByCaseId(caseId);
        TAccount account = accountDao.selectById(_case.getAccountId());
        if (account == null) {
            return;
        }
        if (StringUtils.isEmpty(account.getInviteCode())) {
            return;
        }
        TAccount pAccount = accountDao.findByMobile(account.getInviteCode());
        TFranchisee franchisee = franchiseeDao.findByAccountId(pAccount.getId());
        if (franchisee == null || franchisee.getCreateTime().compareTo(account.getCreateTime()) > 0) {
            return;
        }
        pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsg(pAccount.getId(), account.getNickName());
    }

    @Override
    public void pushFranchiseeSubordinatePublishedCaseRiskCheckSuccessMsg(Integer accountId, String subordinateName) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_8);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("userName", subordinateName);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushFranchiseeSubordinatePublishedCaseSuccessMsgIfNeed(String caseId) {
        TCase _case = caseDao.getCaseByCaseId(caseId);
        TAccount account = accountDao.selectById(_case.getAccountId());
        if (StringUtils.isEmpty(account.getInviteCode())) {
            return;
        }
        TAccount pAccount = accountDao.findByMobile(account.getInviteCode());
        TFranchisee franchisee = franchiseeDao.findByAccountId(pAccount.getId());
        if (franchisee == null || franchisee.getCreateTime().compareTo(account.getCreateTime()) > 0) {
            return;
        }
        pushFranchiseeSubordinatePublishedCaseSuccessMsg(pAccount.getId(), account.getNickName());
    }

    @Override
    public void pushFranchiseeSubordinatePublishedCaseSuccessMsg(Integer accountId, String subordinateName) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_9);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("userName", subordinateName);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushRiskCasePublishSuccessMsg(Integer accountId,String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_10);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
        pushRiskCaseNewArrivalMsg();
    }

    @Override
    public void pushRiskCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_11);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushRiskCaseApplyApproveMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_12);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushRiskCaseApplyRejectMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_13);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushUniteExecuteCasePublishSuccessMsg(Integer accountId,String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_14);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
        pushUniteExecuteCaseNewArrivalMsg();
    }

    @Override
    public void pushUniteExecuteCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_15);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushUniteExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_16);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushUniteExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_17);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushEntrustExecuteCasePublishSuccessMsg(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_18);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
        pushEntrustExecuteCaseNewArrivalMsg();
    }

    @Override
    public void pushEntrustExecuteCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_19);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushEntrustExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_20);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushEntrustExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_21);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawsuitExecuteCasePublishSuccessMsg(Integer accountId,String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_22);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
        pushLawsuitExecuteCaseNewArrivalMsg();
    }

    @Override
    public void pushLawsuitExecuteCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_23);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawsuitExecuteCaseApplyApproveMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_24);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawsuitExecuteCaseApplyRejectMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_25);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushObligationPurchaseCasePublishSuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_26);
        msgDto.setTargetList(Arrays.asList(accountId + ""));

        pushMessageService.pushMsg(msgDto);
        pushObligationPurchaseCaseNewArrivalMsg();
    }

    @Override
    public void pushObligationPurchaseCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_27);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushCasePublishSuccessMsg(Integer accountId,String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_28);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);


        pushMessageService.pushMsg(msgDto);
        pushCaseNewArrivalMsg();
    }

    @Override
    public void pushCaseNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_29);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushCaseApplyApproveMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_30);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushCaseApplyRejectMsg(Integer accountId, String caseId, String createTime) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_31);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("createTime", createTime);
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerAcceptCaseSuccessMsg(Integer accountId, Integer id, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_32);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        param.put("id", id);
        msgDto.setParam(param);

        pushMessageService.pushMsg(msgDto);
        pushLawyerAcceptCaseNewArrivalMsg(id, caseId);
    }

    @Override
    public void pushLawyerAcceptCaseNewArrivalMsg(Integer id, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_33);
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        param.put("id", id);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerAcceptCaseApplyApproveMsg(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_34);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerAcceptCaseApplyRejectMsg(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_35);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerApplySuccessMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_36);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
        pushLawyerApplyNewArrivalMsg();
    }

    @Override
    public void pushLawyerApplyNewArrivalMsg() {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_37);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerApplyApproveMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_38);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushLawyerApplyRejectMsg(Integer accountId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_39);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        pushMessageService.pushMsg(msgDto);
    }


    @Override
    public void setCaseLawyerPushUser(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_42);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    /**
     *
     *案件上新推送
     */
    @Override
    public void newCasePushLawyer(String caseId) {

        new Thread(()->newCasePushLawyerThread(caseId)).start();


    }

    private void newCasePushLawyerThread(String caseId) {

        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_41);
        JSONObject param = new JSONObject();
        param.put("caseId",caseId);
        msgDto.setParam(param);

        TLawyerCondition condition = new TLawyerCondition();
        condition.setDeleteFlag(false);

        List<TLawyer> lawyerList = lawyerDao.selectList(condition);

        List<String> accountList = new ArrayList<>();

        for(TLawyer lawyer:lawyerList){

            accountList.add(lawyer.getAccountId()+"");
        }

        if(accountList.size()>0){
            msgDto.setTargetList(accountList);
            pushMessageService.pushMsg(msgDto);
        }

    }




    @Override
    public void pushLawyerAcceptCaseApplyPassMsg(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_43);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushCasePublishToProjectManagerMsg(Integer accountId, String caseId) {
        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_45);
        msgDto.setTargetList(Arrays.asList(accountId + ""));
        JSONObject param = new JSONObject();
        param.put("caseId", caseId);
        msgDto.setParam(param);
        pushMessageService.pushMsg(msgDto);
    }

    @Override
    public void pushWatersAppList(String partyName, String defaultSystemWaiterUrl) {

        PushMsgDto msgDto = new PushMsgDto();
        msgDto.setType(MessageTemplateEnum.TYPE.TYPE_46);

        JSONObject param = new JSONObject();
        param.put("partyName", partyName);
        param.put("defaultSystemWaiterUrl", defaultSystemWaiterUrl);
        msgDto.setParam(param);

        pushMessageService.pushMsg(msgDto);
    }
}
