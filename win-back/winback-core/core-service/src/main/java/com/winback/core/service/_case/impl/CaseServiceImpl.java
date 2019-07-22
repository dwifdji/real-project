package com.winback.core.service._case.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Throwables;
import com.winback.arch.common.HttpUtils;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.exception.ExceptionEnumImpl;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.RandomNumberGenerator;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.core.aspact.GatewayMqSender;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.condition._case.*;
import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.dao._case.*;
import com.winback.core.dao.account.TLawyerDao;
import com.winback.core.dao.account.TSysStaffDao;
import com.winback.core.dao.connect.TConnectRoomPersonDao;
import com.winback.core.dto._case.QNFileInfoDto;
import com.winback.core.dto._case.UpdateCaseReq;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade._case.req.*;
import com.winback.core.facade._case.resp.*;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.HomePageCaseVO;
import com.winback.core.model._case.*;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.account.TSysStaff;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.core.service._case.CaseService;
import com.winback.core.service._case.TCaseStepService;
import com.winback.core.service.account.AccountService;
import com.winback.core.service.account.LawyerService;
import com.winback.core.service.assistant.CityService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.connect.ConnectService;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author RuQ
 * @Title: CaseServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/18 15:44
 */
@Service
public class CaseServiceImpl implements CaseService {

    public static final Logger LOGGER = LoggerFactory.getLogger(CaseServiceImpl.class);

    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private TCaseTypeDao caseTypeDao;
    @Autowired
    private TCaseBriefDao caseBriefDao;

    @Autowired
    private TCaseStatusUpdateRecordDao caseStatusUpdateRecordDao;

    @Autowired
    private TCaseAttachmentDao caseAttachmentDao;

    @Autowired
    private TCaseStatusDescDao caseStatusDescDao;

    @Autowired
    private TSysStaffDao sysStaffDao;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TCaseStepDao caseStepDao;

    @Autowired
    private TCaseStepNoticeTemplateDao caseStepNoticeTemplateDao;

    @Autowired
    private TCaseLawyerOrderDao caseLawyerOrderDao;

    @Autowired
    private LawyerService lawyerService;

    @Autowired
    private CityService cityService;

    @Autowired
    private ConnectService connectService;

    @Autowired
    private PushAppMessageService pushAppMessageService;

    @Autowired
    private TLawyerDao lawyerDao;

    @Autowired
    private TConnectRoomPersonDao connectRoomPersonDao;

    @Autowired
    private TCaseAssetDao caseAssetDao;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TCaseStepService caseStepService;
    @Autowired
    private TCaseProjectManagerMapDao caseProjectManagerMapDao;
    @Autowired
    private SystemProperties systemProperties;


    @Override
    public boolean publishCaseAsset(CaseAssetReq req) {
        TCaseAsset asset = new TCaseAsset();
        BeanUtils.copyProperties(req, asset);
        TAccount account = accountService.findById(req.getAccountId());
        if (account != null) {
            asset.setMobile(account.getMobile());
        }
        boolean flag = caseAssetDao.insert(asset) == 1;
        if(flag){
            try{
                pushAppMessageService.pushObligationPurchaseCasePublishSuccessMsg(req.getAccountId());
            }catch (Exception e){
                LOGGER.info("调用 pushObligationPurchaseCasePublishSuccessMsg 异常,信息：{}",e.getMessage());
            }
        }
        return flag;
    }

    @Override
    public int applyCaseCount(Integer accountId) {
        TCaseCondition condition = new TCaseCondition();
        condition.setAccountId(accountId);
        List<TCase> list = caseDao.selectList(condition);
        return list.size();
    }

    @Override
    public PageInfoResp<Case> getListByAccountId(AdminCaseReq.QueryReq req) {
        PageInfoResp<Case> resp = new PageInfoResp<>();
        PageInfo<TCase> pageInfo = caseDao.getListByAccountId(req.getPage(), req.getPerPage(), req.getAccountId(), "id desc");
        List<Case> resultList = new ArrayList<>();
        for (TCase item : pageInfo.getList()) {
            Case vo = new Case();
            BeanUtils.copyProperties(item, vo);
            vo.setCaseTypeName(caseTypeDao.getName(item.getCaseTypeId()));
            vo.setCaseBriefName(caseBriefDao.getName(item.getCaseBriefId()));
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }


    @Transactional
    @Override
    public boolean saveCase(CaseCommReq caseCommReq) {
        TCase tCase = new TCase();
        BeanUtils.copyProperties(caseCommReq, tCase);
        String caseId = RandomNumberGenerator.generatorOrderNum(4);
        tCase.setCaseId(caseId);
        caseDao.insert(tCase);

        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(caseId);
        record.setMainStatus(CaseEnum.CaseStep.INIT.getKey());
        record.setLawyerAccountId(tCase.getAccountId());
        record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
        caseStatusUpdateRecordDao.insert(record);
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());


        boolean flag = caseStatusUpdateRecordDao.insert(record) == 1;
        if (flag) {
            try {
                connectService.createRoom(tCase.getId());
            } catch (Exception e) {
                LOGGER.error("调用 connectService.createRoom 异常,异常信息:{}", JSON.toJSONString(e));
            }


            if (caseCommReq.getAccountId() != -1) {
                try {
                    if(caseCommReq.getCaseTypeId() == 1){
                        if(caseCommReq.getCaseFlag()!=null&&caseCommReq.getCaseFlag()){
                            pushAppMessageService.pushCasePublishSuccessMsg(caseCommReq.getAccountId(),caseId);
                        }else {
                            pushAppMessageService.pushRiskCasePublishSuccessMsg(caseCommReq.getAccountId(),caseId);
                        }
                    }else if(caseCommReq.getCaseTypeId() == 2){
                        pushAppMessageService.pushUniteExecuteCasePublishSuccessMsg(caseCommReq.getAccountId(),caseId);
                    }else if(caseCommReq.getCaseTypeId() == 3){
                        pushAppMessageService.pushLawsuitExecuteCasePublishSuccessMsg(caseCommReq.getAccountId(),caseId);
                    }else if(caseCommReq.getCaseTypeId() == 4){
                        pushAppMessageService.pushEntrustExecuteCasePublishSuccessMsg(caseCommReq.getAccountId(),caseId);
                    }else if(caseCommReq.getCaseTypeId() == 5){
                        pushAppMessageService.pushObligationPurchaseCasePublishSuccessMsg(caseCommReq.getAccountId());
                    }
                } catch (Exception e) {
                    LOGGER.error("调用 pushAppMessageService.pushRiskCasePublishSuccessMsg 异常,异常信息:{}", JSON.toJSONString(e));
                }
            }
        }
        allocatedCaseToDefaultProjectManager(tCase.getCaseId());
        return flag;
    }

    @Override
    public PageInfo<TCase> searchCase(CaseCommReq req) {
        TCaseCondition condition = new TCaseCondition();
        BeanUtils.copyProperties(req, condition);
        if (!StringUtils.isEmpty(req.getCooperateWay())) {
            condition.setCaseTypeId(Integer.parseInt(req.getCooperateWay()));
        }
        PageInfo<TCase> casePageInfo = caseDao.searchCase(req.getPage(), req.getPerPage(), condition, "c.id desc",req.getPersonType());
        return casePageInfo;
    }

    @Override
    public PageInfo<TCase> searchCaseByName(CaseCommReq req) {
        PageInfo<TCase> casePageInfo = caseDao.searchCaseByName(req.getPage(), req.getPerPage(), req.getCaseBrieName(), "id desc");
        return casePageInfo;
    }

    @Override
    public PageInfo<TCase> searchSelfCaseByName(CaseCommReq req) {
        PageInfo<TCase> casePageInfo = caseDao.searchSelfCaseByName(req.getPage(), req.getPerPage(), req.getCaseBrieName(), req.getAccountId(),"id desc");
        return casePageInfo;
    }

    @Override
    public PageInfo<TCase> getHasApplyedCaseList(CaseCommReq req) {
        return caseDao.getApplyedCase(req);
    }

    @Override
    public List<TCaseStatusDesc> getCaseStatusDescList() {
        return caseStatusDescDao.selectAll();
    }

    @Override
    public boolean updateCaseStatusDesc(Integer caseStatusDescId, String statusDesc) {
        TCaseStatusDesc condition = new TCaseStatusDesc();
        condition.setId(caseStatusDescId);
        condition.setStatusDesc(statusDesc);
        return caseStatusDescDao.updateById(condition) == 1;
    }

    private String bigDecimal2String(BigDecimal amount){
        if(amount != null){
            return amount.setScale(2,BigDecimal.ROUND_HALF_UP)+"";
        }else {
            return null;
        }
    }

    @Override
    public CaseAdminVo getCaseDetailByCaseIdAdmin(String caseId) {
        CaseAdminVo caseVo = new CaseAdminVo();
        TCase tCase = caseDao.getCaseByCaseId(caseId);
        if (tCase == null) {
            return null;
        } else {
            BeanUtils.copyProperties(tCase, caseVo);
            TCase tcase = caseDao.getCaseByCaseId(caseId);
            //caseVo.setCaseAmount(bigDecimal2String(tCase.getCaseAmount()));
            caseVo.setCaseAcceptFee(bigDecimal2String(tCase.getCaseAcceptFee()));
            caseVo.setAssetProtectFee(bigDecimal2String(tCase.getAssetProtectFee()));
            caseVo.setProtectWarrantFee(bigDecimal2String(tCase.getProtectWarrantFee()));
            caseVo.setLawyerFee(bigDecimal2String(tCase.getLawyerFee()));
            caseVo.setNotarialFee(bigDecimal2String(tCase.getNotarialFee()));
            caseVo.setAuditFee(bigDecimal2String(tCase.getAuditFee()));
            caseVo.setAppraiseFee(bigDecimal2String(tCase.getAppraiseFee()));
            caseVo.setNoticeFee(bigDecimal2String(tCase.getNoticeFee()));
            caseVo.setExecuteFee(bigDecimal2String(tCase.getExecuteFee()));
            caseVo.setInvestigateFee(bigDecimal2String(tCase.getInvestigateFee()));
            caseVo.setOtherFee(bigDecimal2String(tCase.getOtherFee()));
            caseVo.setTotalFee(bigDecimal2String(tCase.getTotalFee()));
            caseVo.setSubSourceDesc(CaseEnum.SubSource.getValueByKey(tCase.getSubSource()));
            TCaseBrief caseBrief = caseBriefDao.selectById(tCase.getCaseBriefId());
            if (caseBrief != null) {
                caseVo.setCaseBigBriefId(caseBrief.getBigBriefId());
            }
            TCaseType caseType = caseTypeDao.selectById(tcase.getCaseTypeId());
            if (caseType != null) {
                caseVo.setCaseType(caseType.getName());
                caseVo.setCooperateWay(caseType.getName());
            }
            caseVo.setCaseReason(caseBriefDao.getName(tCase.getCaseBriefId()));
            caseVo.setStatusDesc(CaseEnum.MainStatus.getValueByKey(tcase.getMainStatus()));
            caseVo.setCreateTime(DateUtil.getNormDateStr(tcase.getCreateTime()));
            if (tcase.getSignContractTime() != null) {
                caseVo.setSignContractTime(DateUtil.getNormDateStr(tcase.getSignContractTime()));
            }
            if (tcase.getAccountId() != -1) {
                TAccount account = accountService.findById(tCase.getAccountId());
                if (account != null) {
                    caseVo.setApplyName(accountService.getRealName(account.getId()));
                    caseVo.setApplyMobile(account.getMobile());
                }
            }
            TCaseStatusUpdateRecordCondition recordCondition = new TCaseStatusUpdateRecordCondition();
            recordCondition.setCaseId(caseId);
            recordCondition.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
            List<TCaseStatusUpdateRecord> recordList = caseStatusUpdateRecordDao.selectList(recordCondition);
            if (recordList != null && !recordList.isEmpty()) {

//                recordCondition.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
//                List<TCaseStatusUpdateRecord> excuteList = caseStatusUpdateRecordDao.selectList(recordCondition);
//
//                recordCondition.setMainStatus(CaseEnum.MainStatus.BEING_LAWSUIT.getKey());
//                List<TCaseStatusUpdateRecord> lawsuitList = caseStatusUpdateRecordDao.selectList(recordCondition);

                caseVo.setCaseRecordList(transAdminStaus(tCase, recordList));
            }
            caseVo.setCaseStepList(transStatus2Step(tCase.getCaseId(), tCase.getMainStatus()));
            return caseVo;
        }
    }


    @Override
    public AdminCaseInfoVo getAdminCaseCheckInfo(String caseId) {
        AdminCaseInfoVo vo = new AdminCaseInfoVo();
        List<TCaseStatusUpdateRecord> recordList = caseStatusUpdateRecordDao.getPrecheckRecord(caseId);
        getAdminPreAndRiskOrCheckInfo(vo, caseId, CaseEnum.CaseAttachmentMainStatus.PRE_CHECK.getKey(), recordList);
        return vo;
    }

    @Override
    public AdminCaseInfoVo getAdminCaseRiskInfo(String caseId) {
        AdminCaseInfoVo vo = new AdminCaseInfoVo();
        List<TCaseStatusUpdateRecord> recordList = caseStatusUpdateRecordDao.getRiskcheckRecord(caseId);
        getAdminPreAndRiskOrCheckInfo(vo, caseId, CaseEnum.CaseAttachmentMainStatus.RISK_CHECK.getKey(), recordList);
        return vo;
    }

    @Override
    public AdminCaseInfoVo getAdminCaseSignInfo(String caseId) {
        AdminCaseInfoVo vo = new AdminCaseInfoVo();
        List<TCaseStatusUpdateRecord> recordList = caseStatusUpdateRecordDao.getSignContractRecord(caseId);
        getAdminPreAndRiskOrCheckInfo(vo, caseId, CaseEnum.CaseAttachmentMainStatus.SIGN_CONTRACT.getKey(), recordList);
        return vo;
    }

    @Transactional
    @Override
    public boolean deleteCaseAttachment(Integer attachmentId) {

        TCaseAttachment oldAttachment = caseAttachmentDao.selectById(attachmentId);
        TCase tCase = caseDao.getCaseByCaseId(oldAttachment.getCaseId());
        if(CaseEnum.CaseAttachmentMainStatus.PRE_CHECK.getKey().equals(oldAttachment.getCaseStatus())){
            if(!(CaseEnum.MainStatus.INIT.getKey().equals(tCase.getMainStatus())
                || CaseEnum.MainStatus.PRE_CHECK_FAIL.getKey().equals(tCase.getMainStatus()))){
                throw new BusinessException(ExceptionEnumImpl.CAN_NOT_DELETE);
            }
        }else if(CaseEnum.CaseAttachmentMainStatus.RISK_CHECK.getKey().equals(oldAttachment.getCaseStatus())){
            if(!CaseEnum.MainStatus.PRE_CHECK_SUCCESS.getKey().equals(tCase.getMainStatus())){
                throw new BusinessException(ExceptionEnumImpl.CAN_NOT_DELETE);
            }
        }else{
            if(!CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(tCase.getMainStatus())){
                throw new BusinessException(ExceptionEnumImpl.CAN_NOT_DELETE);
            }
        }

        TCaseAttachment caseAttachment = new TCaseAttachment();
        caseAttachment.setId(attachmentId);
        caseAttachment.setDeleteFlag(1);
        return caseAttachmentDao.updateById(caseAttachment) == 1;
    }

    private void getAdminPreAndRiskOrCheckInfo(AdminCaseInfoVo vo, String caseId, String attchType, List<TCaseStatusUpdateRecord> recordList) {
        List<AdminCaseCheckVo> checkVoList = new ArrayList<AdminCaseCheckVo>();
        List<AdminCaseSignContractVo> signContractVoList = new ArrayList<AdminCaseSignContractVo>();
        List<CaseAttachmentVo> attachmentList = new ArrayList<CaseAttachmentVo>();
        TCaseAttachmentCondition condition = new TCaseAttachmentCondition();
        condition.setDeleteFlag(0);
        condition.setCaseId(caseId);
        condition.setCaseStatus(attchType);
        List<TCaseAttachment> caseAttachmentList = caseAttachmentDao.selectList(condition);
        if (caseAttachmentList != null) {
            for (TCaseAttachment caseAttachment : caseAttachmentList) {
                CaseAttachmentVo attachmentVo = new CaseAttachmentVo();
                attachmentVo.setId(caseAttachment.getId());
                attachmentVo.setAttachName(caseAttachment.getAttachName());
                attachmentVo.setAttachUrl(caseAttachment.getAttachUrl());
                attachmentVo.setAttchType(caseAttachment.getAttachType());
                attachmentList.add(attachmentVo);
            }
        }
        vo.setAttachmentList(attachmentList);

        if (recordList != null) {
            if (attchType.equals(CaseEnum.CaseAttachmentMainStatus.SIGN_CONTRACT.getKey())) {
                for (TCaseStatusUpdateRecord record : recordList) {
                    AdminCaseSignContractVo caseSignContractVo = new AdminCaseSignContractVo();
                    //TCase tCase = caseDao.getCaseByCaseId(caseId);
                    TLawyer lawyer = lawyerService.findByAccountId(record.getLawyerAccountId());
                    if (lawyer != null) {
                        TAccount account = accountService.findById(lawyer.getAccountId());
                        if (account != null) {
                            caseSignContractVo.setLawyerMobile(account.getMobile());
                        }
                        caseSignContractVo.setLawyerId(lawyer.getId());
                        caseSignContractVo.setLawyerName(lawyer.getName());
                        caseSignContractVo.setLawFirmName(lawyer.getLawFirm());
                        caseSignContractVo.setAreaName(cityService.getFullName(lawyer.getBusinessCityCode(), lawyer.getBusinessAreaCode()));
                    }

                    caseSignContractVo.setCreateTime(DateUtil.getNormDateStr(record.getCreateTime()));
                    caseSignContractVo.setOperatorName(getAdminName(record.getOperatorId()));
                    signContractVoList.add(caseSignContractVo);
                }
                vo.setOperateList(signContractVoList);
            } else {
                for (TCaseStatusUpdateRecord record : recordList) {
                    AdminCaseCheckVo checkVo = new AdminCaseCheckVo();
                    checkVo.setId(record.getId());
                    checkVo.setCaseId(record.getCaseId());
                    checkVo.setCheckRemark(record.getRemark());
                    checkVo.setCheckResult(CaseEnum.CaseStep.getValueByKey(record.getMainStatus()));
                    checkVo.setCreateTime(DateUtil.getNormDateStr(record.getCreateTime()));
                    checkVo.setOperatorName(getAdminName(record.getOperatorId()));
                    checkVoList.add(checkVo);
                }
                vo.setOperateList(checkVoList);
            }

        }


    }

    @Override
    public CaseVo getCaseDetailByCaseId(String caseId,Integer accountId) {
        CaseVo caseVo = new CaseVo();
        TCase tCase = caseDao.getCaseByCaseId(caseId);
        if (tCase == null) {
            return null;
        } else {
            BeanUtils.copyProperties(tCase, caseVo);
            caseVo.setCaseReason(caseBriefDao.getName(tCase.getCaseBriefId()));
            caseVo.setStatusDesc(CaseEnum.MainStatus.getValueByKey(tCase.getMainStatus()));
            List<TCaseAttachment> attachmentList = getAttachmentByCaseIdAndStatus(caseId, null);
            if (attachmentList != null && !attachmentList.isEmpty()) {
                caseVo.setAttachmentMap(transListToMapGroupByStatus(attachmentList));
            }

            if(String.valueOf(tCase.getLawyerAccountId()).equals(String.valueOf(accountId))){
                caseVo.setUpdateStepButtonFlag(CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(tCase.getMainStatus())
                        || CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(tCase.getMainStatus()));
            }
            caseVo.setEndFlag(CaseEnum.MainStatus.SUCCESS.getKey().equals(tCase.getMainStatus()));
            TCaseStatusUpdateRecordCondition recordCondition = new TCaseStatusUpdateRecordCondition();
            recordCondition.setCaseId(caseId);
            recordCondition.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            List<TCaseStatusUpdateRecord> recordList = caseStatusUpdateRecordDao.getStepRecordListByCaseId(caseId,CaseEnum.CaseRecordType.STEP.getKey());
            if (recordList != null && !recordList.isEmpty()) {
                recordCondition.setMainStatus(CaseEnum.MainStatus.BEING_LAWSUIT.getKey());
                List<TCaseStatusUpdateRecord> lawsuitList = caseStatusUpdateRecordDao.selectList(recordCondition);

                recordCondition.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
                List<TCaseStatusUpdateRecord> excuteList = caseStatusUpdateRecordDao.selectList(recordCondition);

                caseVo.setCaseRecordList(transStatus(recordList, lawsuitList, excuteList));
            }

            List<CaseRoleVo> caseRoleVoList = new ArrayList<>();
            CaseRoleVo applyRole = new CaseRoleVo();
            if (tCase.getAccountId() != -1) {
                TAccount account = accountService.findById(tCase.getAccountId());
                applyRole.setRoleName(accountService.getRealName(account.getId()));
                applyRole.setCaseRole(PushEnum.PUSH_PERSON_TYPE.PARTY.getTypeDesc());
                applyRole.setHeadImgUrl(account.getHeadImgUrl());
                caseRoleVoList.add(applyRole);
            }else{
                applyRole.setRoleName(tCase.getApplyName());
                applyRole.setCaseRole(PushEnum.PUSH_PERSON_TYPE.PARTY.getTypeDesc());
                applyRole.setHeadImgUrl(SystemConstant.DEFAULT_HEAD_IMG_URL);
                caseRoleVoList.add(applyRole);
            }

            if(tCase.getLawyerAccountId() != null){
                CaseRoleVo lawyerRole = new CaseRoleVo();
                TLawyer lawyer = lawyerService.findByAccountId(tCase.getLawyerAccountId());
                lawyerRole.setRoleName(lawyer.getName());
                lawyerRole.setCaseRole(PushEnum.PUSH_PERSON_TYPE.LAWYER.getTypeDesc());
                lawyerRole.setHeadImgUrl(lawyer.getHeadImgUrl());
                caseRoleVoList.add(lawyerRole);
            }

            TCaseProjectManagerMap projectManagerMap = caseProjectManagerMapDao.findBy(tCase.getCaseId());
            if(projectManagerMap != null){
                CaseRoleVo projectMangerRole = new CaseRoleVo();
                TAccount projectManagerAccount = accountService.findById(projectManagerMap.getAccountId());
                projectMangerRole.setRoleName(projectManagerAccount.getNickName());
                projectMangerRole.setCaseRole(PushEnum.PUSH_PERSON_TYPE.MANAGE.getTypeDesc());
                projectMangerRole.setHeadImgUrl(projectManagerAccount.getHeadImgUrl());
                caseRoleVoList.add(projectMangerRole);
            }

            if (!StringUtils.isEmpty(tCase.getSubStatus())) {
                TCaseStep nextStep = getNextStep(tCase.getSubStatus());
                if (nextStep != null) {
                    caseVo.setNextStatusName(nextStep.getName());
                    caseVo.setNextStatusDesc(nextStep.getNameDesc());
                    caseVo.setNextStatusId(nextStep.getId());
                }
            }
            caseVo.setCaseRoleList(caseRoleVoList);
            caseVo.setUnreadNum(getUnreadNumInfo(tCase.getId(),accountId));
            caseVo.setApplyAcceptFlag(getHasAcceptCaseFlag(accountId,caseId));
            caseVo.setSignContractFlag(CaseEnum.MainStatus.hasSignContract(tCase.getMainStatus()));
            return caseVo;
        }
    }

    private boolean getHasAcceptCaseFlag(Integer accountId,String caseId){
        TCaseLawyerOrderCondition condition = new TCaseLawyerOrderCondition();
        condition.setCaseId(caseId);
        condition.setLawyerAccountId(accountId);
        condition.setDeleteFlag(0);
        return caseLawyerOrderDao.selectFirst(condition) != null;
    }

    /**
     *获取当前案件未读信息
     */
    private String getUnreadNumInfo(Integer caseId, Integer accountId) {

        TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
        condition.setCaseId(caseId);
        condition.setRelevanceId(accountId);
        List<TConnectRoomPerson> roomPersonList = connectRoomPersonDao.selectList(condition);

        if(roomPersonList==null&&roomPersonList.size()<1){
            return "0";
        }

        TConnectRoomPerson roomPerson= new TConnectRoomPerson();

        for(TConnectRoomPerson person:roomPersonList){
            if(!"3".equals(person.getType())){
                roomPerson = person;
            }
        }


       return roomPerson.getUnreadNum()==null?"0":String.valueOf(roomPerson.getUnreadNum());
    }


    @Override
    public boolean acceptCase(Integer accountId, String caseId) {
        //是不是律师
        //案件是否已经安排律师
        //是否已经申请承接
        TCase tCase = caseDao.getCaseByCaseId(caseId);
        if (tCase == null) {
            throw new BusinessException("案件不存在");
        }
        TCaseLawyerOrderCondition condition = new TCaseLawyerOrderCondition();
        condition.setCaseId(caseId);
        condition.setLawyerAccountId(accountId);
        List<TCaseLawyerOrder> list = caseLawyerOrderDao.selectList(condition);
        if (list != null && !list.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.HAS_APPLY_ERROR);
        }
        try{
            pushAppMessageService.pushLawyerAcceptCaseSuccessMsg(accountId, tCase.getId(), caseId);
        }catch (Exception e){
            LOGGER.error("调用 pushAppMessageService.pushLawyerAcceptCaseSuccessMsg 异常,异常信息:{}",JSON.toJSONString(e));
        }
        TCaseLawyerOrder order = new TCaseLawyerOrder();
        order.setCaseId(caseId);
        order.setLawyerAccountId(accountId);
        order.setId(RandomNumberGenerator.generatorOrderNum(5));
        return caseLawyerOrderDao.insert(order) == 1;
    }

    @Transactional
    @Override
    public boolean updateCaseStatus(Integer accountId, String caseId, String subStatus, String statusDesc) {
        //是不是律师
        //是不是案件承接律师

        TCase tCase = caseDao.getCaseByCaseId(caseId);
        if (tCase != null) {

            if (!(CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(tCase.getMainStatus())
                    || CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(tCase.getMainStatus()))) {
                throw new BusinessException(ExceptionEnumImpl.STATUS_ERROR);
            }

            TCaseStep nextStep = getNextStep(tCase.getSubStatus());
            if (nextStep == null || String.valueOf(nextStep.getId()).equals(String.valueOf(tCase.getSubStatus()))) {
                throw new BusinessException(ExceptionEnumImpl.HAS_LAST_STEP_ERROR);
            }
            TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
            record.setCaseId(caseId);
            record.setLawyerAccountId(accountId);
            record.setMainStatus(tCase.getMainStatus());
            record.setSubStaus(getNextStep(tCase.getSubStatus()).getId() + "");
            record.setRemark(statusDesc);
            record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            caseStatusUpdateRecordDao.insert(record);
            record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
            caseStatusUpdateRecordDao.insert(record);

            TCase updateParam = new TCase();
            updateParam.setCaseId(caseId);
            updateParam.setSubStatus(nextStep.getId() + "");
            boolean flag = caseDao.updateById(updateParam) == 1;
            // 更新诉讼或者跟踪记录表
            updateCaseStepRecord(accountId, tCase.getId()+"", nextStep.getId()+"", statusDesc, tCase.getMainStatus());
            return flag;
        }
        return false;
    }


    private void updateCaseStepRecord(Integer accountId, String caseId,
                                      String subStatus, String msgBody, String mainStatus) {

        System.out.println("传入的的数据是" + accountId  + "*" + caseId + "*" + subStatus + "*" + msgBody + "*" + mainStatus);
        CaseStepReq.CaseStepRecordSaveReq req = new CaseStepReq.CaseStepRecordSaveReq();
        req.setAccountId(accountId == null ? null : accountId.toString());
        req.setCaseId(caseId);
        req.setPushMsg(msgBody);
        req.setStepId(subStatus);

        if (CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(mainStatus)){
            req.setType("1");
        }else if(CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(mainStatus)){
            req.setType("2");
        }

        caseStepService.saveCaseStepRecord(req);
    }


    @Override
    public boolean updateCaseSubStatus(Integer operatorId, String caseId, String subStatusId, String statusDesc) {

        TCase tCase = caseDao.getCaseByCaseId(caseId);
        if (tCase != null) {
            TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
            record.setCaseId(caseId);
            record.setOperatorId(operatorId);
            record.setMainStatus(tCase.getMainStatus());
            record.setSubStaus(subStatusId);
            record.setRemark(statusDesc);
            record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            caseStatusUpdateRecordDao.insert(record);
            record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
            caseStatusUpdateRecordDao.insert(record);

            TCase updateParam = new TCase();
            updateParam.setCaseId(caseId);
            updateParam.setSubStatus(subStatusId);
            return caseDao.updateById(updateParam) == 1;
        }
        return false;
    }

    @Override
    public List<CaseAssetVo> exportCaseList(CaseAssetReq req) {
        List<CaseAssetVo> voList = new ArrayList<>();
        List<TCaseAsset> list = caseDao.searchCaseList(req);
        if(list != null){
            for (TCaseAsset asset : list) {
                CaseAssetVo vo = new CaseAssetVo();
                transeCaseAssetVo(vo,asset);
                voList.add(vo);
            }
        }
        return voList;
    }

    @Override
    public PageInfo<CaseAssetVo> searchCaseAsset(CaseAssetReq req) {
        PageInfo<CaseAssetVo> voPageInfo = new PageInfo<>();
        List<CaseAssetVo> voList = new ArrayList<>();
        PageInfo<TCaseAsset> assetPageInfo = caseDao.searchCaseAsset(req.getPage(), req.getPerPage(), "id desc", req);
        if (assetPageInfo != null && assetPageInfo.getList() != null) {
            voPageInfo.setHasNextPage(assetPageInfo.isHasNextPage());
            voPageInfo.setTotal(assetPageInfo.getTotal());
            for (TCaseAsset asset : assetPageInfo.getList()) {
                CaseAssetVo vo = new CaseAssetVo();
                transeCaseAssetVo(vo,asset);
                voList.add(vo);
            }
            voPageInfo.setList(voList);
        }
        return voPageInfo;
    }

    private void transeCaseAssetVo(CaseAssetVo vo,TCaseAsset asset){
        vo.setId(asset.getId());
        vo.setAccountId(asset.getAccountId());
        vo.setMobile(asset.getMobile());
        vo.setAssetAmount(asset.getAssetAmount());
        vo.setAssetDesc(asset.getAssetDesc());
        vo.setCreateTime(DateUtil.getNormDateStr(asset.getCreateTime()));
    }

    @Override
    public PageInfo<CaseLawyerOrderVo> searchLawyerOrder(CaseLawyerOrderReq req) {
        PageInfo<CaseLawyerOrderVo> voPageInfo = new PageInfo<>();
        List<CaseLawyerOrderVo> voList = new ArrayList<>();
        PageInfo<TCaseLawyerOrder> orderPageInfo = caseDao.searchLawyerOrder(req.getPage(), req.getPerPage(), "id desc", req);
        if (orderPageInfo != null && orderPageInfo.getList() != null) {
            voPageInfo.setHasNextPage(orderPageInfo.isHasNextPage());
            voPageInfo.setTotal(orderPageInfo.getTotal());
            for (TCaseLawyerOrder order : orderPageInfo.getList()) {
                CaseLawyerOrderVo vo = new CaseLawyerOrderVo();
                vo.setId(order.getId());
                vo.setCaseId(order.getCaseId());
                vo.setCreateTime(DateUtil.formatNormDate(order.getCreateTime()));
                vo.setOrderStatus(CaseEnum.CaseLawyerOrderStatus.getValueByKey(order.getOrderStatus()));
                TCase tCase = caseDao.getCaseByCaseId(order.getCaseId());
                if (tCase != null) {
                    vo.setCaseAmount(tCase.getCaseAmount());
                    vo.setDefendant(tCase.getDefendant());
                    vo.setPlaintiff(tCase.getPlaintiff());
                }
                TLawyer lawyer = lawyerService.findByAccountId(order.getLawyerAccountId());
                if (lawyer != null) {
                    vo.setLawyerName(lawyer.getName());
                }

                TCaseType caseType = caseTypeDao.selectById(tCase.getCaseTypeId());
                if (caseType != null) {
                    vo.setCooperateWay(caseType.getName());
                }

                voList.add(vo);
            }
            voPageInfo.setList(voList);
        }
        return voPageInfo;
    }

    @Override
    public PageInfo<CasePublishVo> getPublishedCaseList(CaseCommReq req) {
        PageInfo<CasePublishVo> voPageInfo = new PageInfo<>();
        List<CasePublishVo> voList = new ArrayList<>();

        if(req.getCaseTypeId() == 5){
            PageInfo<TCaseAsset> caseAssetPageInfo = caseDao.searchCaseAsset(req.getPage(),req.getPerPage(),"id desc",null);
            if(caseAssetPageInfo != null && caseAssetPageInfo.getList() != null){
                voPageInfo.setHasNextPage(caseAssetPageInfo.isHasNextPage());
                voPageInfo.setTotal(caseAssetPageInfo.getTotal());
                for(TCaseAsset asset : caseAssetPageInfo.getList()){
                    CasePublishVo vo = new CasePublishVo();
                    vo.setCreateTime(DateUtil.formatNormDate(asset.getCreateTime()));
                    vo.setUserName(accountService.getAppRealName(asset.getAccountId()));
                    vo.setCaseTypeDesc("债权收购");
                    voList.add(vo);
                }
                voPageInfo.setList(voList);
            }
        }else{
            PageInfo<TCase> casePageInfo = caseDao.getPublishedCaseList(req.getPage(), req.getPerPage(), "id desc", req.getCaseTypeId());
            if (casePageInfo != null && casePageInfo.getList() != null) {
                voPageInfo.setHasNextPage(casePageInfo.isHasNextPage());
                voPageInfo.setTotal(casePageInfo.getTotal());
                for (TCase tCase : casePageInfo.getList()) {
                    CasePublishVo vo = new CasePublishVo();
                    vo.setCreateTime(DateUtil.formatNormDate(tCase.getCreateTime()));
                    vo.setUserName(accountService.getAppRealName(tCase.getAccountId()));
                    TCaseType caseType = caseTypeDao.selectById(tCase.getCaseTypeId());
                    if (caseType != null) {
                        vo.setCaseTypeDesc(caseType.getName());
                    }
                    TCaseBrief caseBrief = caseBriefDao.selectById(tCase.getCaseBriefId());
                    if (caseBrief != null) {
                        vo.setCaseReason(caseBrief.getName());
                    }
                    voList.add(vo);
                }
                voPageInfo.setList(voList);
            }
        }
        return voPageInfo;
    }

    @Override
    public boolean updateCaseInfo(CaseCommReq req) {
        TCase tCase = new TCase();
        BeanUtils.copyProperties(req, tCase);
        return caseDao.updateById(tCase) == 1;
    }

    @Transactional
    @Override
    public boolean verifyCase(CaseCommReq req) {

        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(req.getCaseId());
        record.setOperatorId(req.getOperatorId());
        record.setMainStatus(req.getMainStatus());
        record.setSubStaus(req.getSubStatus());
        record.setRemark(req.getRemark());
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
        caseStatusUpdateRecordDao.insert(record);

        if (CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey().equals(req.getMainStatus())
                || CaseEnum.MainStatus.PRE_CHECK_SUCCESS.getKey().equals(req.getMainStatus())
                || CaseEnum.MainStatus.PRE_CHECK_FAIL.getKey().equals(req.getMainStatus())) {
            record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            caseStatusUpdateRecordDao.insert(record);
        }


        TCase oldCase = caseDao.getCaseByCaseId(req.getCaseId());
        TCase tCase = new TCase();
        tCase.setCaseId(req.getCaseId());
        if (CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey().equals(req.getMainStatus())) {
            if (oldCase.getLawyerAccountId() == null) {
                throw new BusinessException(ExceptionEnumImpl.MUST_SET_LAWYER);
            }

            if(!CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(oldCase.getMainStatus())){
                throw new BusinessException(ExceptionEnumImpl.MUST_RISK_SUCCESS);
            }

            checkAttachmentHasUpload(tCase.getCaseId(), CaseEnum.CaseAttachmentMainStatus.SIGN_CONTRACT.getKey());
            tCase.setSignContractTime(new Date());
            tCase.setMainStatus(req.getMainStatus());
        } else if (CaseEnum.MainStatus.EXECUTE_RISK_CHECK_SUCCESS.getKey().equals(req.getMainStatus())) {
            checkAttachmentHasUpload(tCase.getCaseId(), CaseEnum.CaseAttachmentMainStatus.RISK_CHECK.getKey());
            if (CaseEnum.MainStatus.LAWSUIT_RISK_CHECK_SUCCESS.getKey().equals(oldCase.getLawsuitStatus())) {
                tCase.setMainStatus(CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey());
            }
            tCase.setExecuteStatus(CaseEnum.MainStatus.EXECUTE_RISK_CHECK_SUCCESS.getKey());
        } else if (CaseEnum.MainStatus.LAWSUIT_RISK_CHECK_SUCCESS.getKey().equals(req.getMainStatus())) {
            checkAttachmentHasUpload(tCase.getCaseId(), CaseEnum.CaseAttachmentMainStatus.RISK_CHECK.getKey());
            if (CaseEnum.MainStatus.EXECUTE_RISK_CHECK_SUCCESS.getKey().equals(oldCase.getExecuteStatus())) {
                tCase.setMainStatus(CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey());
            }
            tCase.setLawsuitStatus(CaseEnum.MainStatus.LAWSUIT_RISK_CHECK_SUCCESS.getKey());
        } else if (CaseEnum.MainStatus.EXECUTE_RISK_CHECK_FAIL.getKey().equals(req.getMainStatus())) {
            tCase.setExecuteStatus(req.getMainStatus());
        } else if (CaseEnum.MainStatus.LAWSUIT_RISK_CHECK_FAIL.getKey().equals(req.getMainStatus())) {
            tCase.setLawsuitStatus(req.getMainStatus());
        } else {
            tCase.setMainStatus(req.getMainStatus());
            if (CaseEnum.MainStatus.PRE_CHECK_SUCCESS.getKey().equals(req.getMainStatus())) {
                checkAttachmentHasUpload(req.getCaseId(), CaseEnum.CaseAttachmentMainStatus.PRE_CHECK.getKey());
                try{
                    if(oldCase.getAccountId() != -1){
                        if(oldCase.getCaseTypeId() == 1){
                            pushAppMessageService.pushRiskCaseApplyApproveMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 2){
                            pushAppMessageService.pushUniteExecuteCaseApplyApproveMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 3){
                            pushAppMessageService.pushLawsuitExecuteCaseApplyApproveMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 4){
                            pushAppMessageService.pushEntrustExecuteCaseApplyApproveMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }
                    }
                }catch (Exception e){
                    LOGGER.error("调用 pushAppMessageService 异常，异常信息:{}",JSON.toJSONString(e));
                }

            }else{
                try{
                    if(oldCase.getAccountId() != -1){
                        if(oldCase.getCaseTypeId() == 1){
                            pushAppMessageService.pushRiskCaseApplyRejectMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 2){
                            pushAppMessageService.pushUniteExecuteCaseApplyRejectMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 3){
                            pushAppMessageService.pushLawsuitExecuteCaseApplyRejectMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }else if(oldCase.getCaseTypeId() == 4){
                            pushAppMessageService.pushEntrustExecuteCaseApplyRejectMsg(oldCase.getAccountId(), oldCase.getCaseId(), DateUtil.getNormDateStr(oldCase.getCreateTime()));
                        }
                    }
                }catch (Exception e){
                    LOGGER.error("调用 pushAppMessageService 异常，异常信息:{}",JSON.toJSONString(e));

                }

            }

        }
        if (CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(tCase.getMainStatus())) {
            record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            record.setMainStatus(CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey());
            caseStatusUpdateRecordDao.insert(record);
            try{
                mqSender.caseRiskCheckSuccess(tCase.getCaseId());
            }catch (Exception e){
                LOGGER.info("调用 mqSender.caseRiskCheckSuccess 异常,信息：{}",e.getMessage());
            }

        }

        if (CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey().equals(tCase.getMainStatus())) {
            TCaseLawyerOrderCondition condition = new TCaseLawyerOrderCondition();
            condition.setDeleteFlag(0);
            condition.setCaseId(req.getCaseId());
            List<TCaseLawyerOrder> orderList = caseLawyerOrderDao.selectList(condition);
            if(orderList != null){
                for(TCaseLawyerOrder order : orderList){
                    if(String.valueOf(order.getLawyerAccountId()).equals(String.valueOf(oldCase.getLawyerAccountId()))){
                        pushAppMessageService.pushLawyerAcceptCaseApplyPassMsg(order.getLawyerAccountId(),req.getCaseId());
                    }else{
                        pushAppMessageService.pushLawyerAcceptCaseApplyRejectMsg(order.getLawyerAccountId(),req.getCaseId());
                    }
                }
            }

        }
        //审核通过 合同待签约 推送给全部律师
        if(CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(tCase.getMainStatus())){
            pushAppMessageService.newCasePushLawyer(req.getCaseId());

        }


        return caseDao.updateById(tCase) == 1;
    }

    private void checkAttachmentHasUpload(String caseId, String caseStatus) {
        TCaseAttachmentCondition condition = new TCaseAttachmentCondition();
        condition.setCaseId(caseId);
        condition.setCaseStatus(caseStatus);
        condition.setDeleteFlag(0);
        if (CaseEnum.CaseAttachmentMainStatus.SIGN_CONTRACT.getKey().equals(caseStatus)) {
            condition.setAttachType(CaseEnum.CaseAttachmentSubStatus.LAWSUIT_CONTRACT.getKey());
            List<TCaseAttachment> lawsuitList = caseAttachmentDao.selectList(condition);
            if (lawsuitList == null || lawsuitList.isEmpty()) {
                throw new BusinessException(ExceptionEnumImpl.MUST_UPLOAD_LAWSUIT);
            }
            condition.setAttachType(CaseEnum.CaseAttachmentSubStatus.EMPOWER_DOC.getKey());
            List<TCaseAttachment> empowerList = caseAttachmentDao.selectList(condition);
            if (empowerList == null || empowerList.isEmpty()) {
                throw new BusinessException(ExceptionEnumImpl.MUST_UPLOAD_EMPOWER_DOC);
            }
        } else if (CaseEnum.CaseAttachmentMainStatus.PRE_CHECK.getKey().equals(caseStatus)
                || CaseEnum.CaseAttachmentMainStatus.RISK_CHECK.getKey().equals(caseStatus)) {
            List<TCaseAttachment> preCheckList = caseAttachmentDao.selectList(condition);
            if (preCheckList == null || preCheckList.isEmpty()) {
                throw new BusinessException(ExceptionEnumImpl.MUST_UPLOAD_FILE);
            }
        }
    }

    @Transactional
    @Override
    public boolean endLawsuit(CaseCommReq req) {

        TCase tcase = caseDao.getCaseByCaseId(req.getCaseId());
        TCaseType caseType = caseTypeDao.selectById(tcase.getCaseTypeId());
        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(req.getCaseId());
        record.setOperatorId(req.getOperatorId());

        if (CaseEnum.CaseType.RISK.getValue().equals(caseType.getName())
                || CaseEnum.CaseType.LAWSUIT_EXECUTE.getValue().equals(caseType.getName())) {
            record.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
            record.setSubStaus(caseStepService.getFirstStep("2").getId() + "");
            record.setRemark(caseStepService.getFirstStep("2").getNameDesc());
            record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
            caseStatusUpdateRecordDao.insert(record);
            record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
            record.setMainStatus(CaseEnum.MainStatus.BEING_LAWSUIT.getKey());
            record.setSubStaus(CaseEnum.CaseStep.END_LAWSUIT.getKey());
            caseStatusUpdateRecordDao.insert(record);

            record.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
            record.setSubStaus(caseStepService.getFirstStep("2").getId() + "");
            record.setRemark(caseStepService.getFirstStep("2").getNameDesc());
            caseStatusUpdateRecordDao.insert(record);

            TCase updateCaseParam = new TCase();
            updateCaseParam.setCaseId(req.getCaseId());
            updateCaseParam.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
            updateCaseParam.setSubStatus(caseStepService.getFirstStep("2").getId() + "");
            boolean flag = caseDao.updateById(updateCaseParam) == 1;

            updateCaseStepRecord(req.getOperatorId(), tcase.getId()+"", caseStepService.getFirstStep("2").getId() + "", caseStepService.getFirstStep("2").getNameDesc(), CaseEnum.CaseStep.BEING_EXECUTE.getKey());

            return flag;
        }
        return false;
    }

    @Transactional
    @Override
    public boolean endExcute(CaseCommReq req) {
        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(req.getCaseId());
        record.setOperatorId(req.getOperatorId());
        record.setMainStatus(CaseEnum.MainStatus.WAIT_RECEIVED_PAY.getKey());
        //record.setSubStaus(getFirstStep("2").getId()+"");
        record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
        caseStatusUpdateRecordDao.insert(record);
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
        record.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
        record.setSubStaus(CaseEnum.CaseStep.END_EXECUTE.getKey());
        caseStatusUpdateRecordDao.insert(record);

        TCase updateCaseParam = new TCase();
        updateCaseParam.setCaseId(req.getCaseId());
        updateCaseParam.setMainStatus(CaseEnum.MainStatus.WAIT_RECEIVED_PAY.getKey());
        //updateCaseParam.setSubStatus(getFirstStep("2").getId()+"");
        return caseDao.updateById(updateCaseParam) == 1;
    }

    @Transactional
    @Override
    public boolean endSuccess(CaseCommReq req) {
        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(req.getCaseId());
        record.setOperatorId(req.getOperatorId());
        record.setMainStatus(CaseEnum.MainStatus.SUCCESS.getKey());
        record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
        caseStatusUpdateRecordDao.insert(record);
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
        record.setMainStatus(CaseEnum.MainStatus.SUCCESS.getKey());
        caseStatusUpdateRecordDao.insert(record);

        TCase tCase = caseDao.getCaseByCaseId(req.getCaseId());
        if (tCase != null && tCase.getLawyerAccountId() != null) {
            caseLawyerOrderDao.updateSuccessOrderStatus(req.getCaseId(), tCase.getLawyerAccountId(), req.getOperatorId());
        }

        TCase updateCaseParam = new TCase();
        updateCaseParam.setCaseId(req.getCaseId());
        updateCaseParam.setMainStatus(CaseEnum.MainStatus.SUCCESS.getKey());
        boolean flag = caseDao.updateById(updateCaseParam) == 1;
        if(flag){
            try{
                mqSender.caseSuccess(req.getCaseId());
            }catch (Exception e){
                LOGGER.info("调用 mqSender.caseSuccess 异常,信息：{}",e.getMessage());
            }
        }
        return  flag;
    }

    @Transactional
    @Override
    public boolean setLawyerForCase(CaseCommReq req) {

        TCase tCase = caseDao.getCaseByCaseId(req.getCaseId());
        if(!CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(tCase.getMainStatus())){
            throw new BusinessException(ExceptionEnumImpl.ONLY_RISK_SUCCESS_CAN_SET_LAWYER);
        }

        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(req.getCaseId());
        record.setOperatorId(req.getOperatorId());
        record.setMainStatus(CaseEnum.CaseStep.SCHEDULE_ACCEPTED_LAWYER.getKey());
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
        record.setLawyerAccountId(req.getLawyerAccountId());
        caseStatusUpdateRecordDao.insert(record);

        caseLawyerOrderDao.updateAcceptOrderStatus(req.getCaseId(), req.getLawyerAccountId(), req.getOperatorId());
        caseLawyerOrderDao.updateRefusedOrderStatus(req.getCaseId(), req.getLawyerAccountId(), req.getOperatorId());

        TCaseLawyerOrderCondition condition = new TCaseLawyerOrderCondition();
        condition.setCaseId(req.getCaseId());
        condition.setLawyerAccountId(req.getLawyerAccountId());
        condition.setDeleteFlag(0);
        List<TCaseLawyerOrder> orderList = caseLawyerOrderDao.selectList(condition);
        if (orderList == null || orderList.isEmpty()) {
            TCaseLawyerOrder order = new TCaseLawyerOrder();
            order.setId(RandomNumberGenerator.generatorOrderNum(5));
            order.setCaseId(req.getCaseId());
            order.setLawyerAccountId(req.getLawyerAccountId());
            order.setOrderStatus(CaseEnum.CaseLawyerOrderStatus.SUCCESS.getKey());
            order.setOperatorId(req.getOperatorId());
            caseLawyerOrderDao.insert(order);
        }


        tCase.setLawyerAccountId(req.getLawyerAccountId());


        boolean flag = caseDao.updateById(tCase) == 1;

        try {
            if(flag){
                connectService.createRoom(tCase.getId());
            }
        } catch (Exception e) {
            LOGGER.error("调用 connectService.createRoom 异常,异常信息:{}", JSON.toJSONString(e));
        }


        //推送消息给当事人
        pushAppMessageService.setCaseLawyerPushUser(tCase.getAccountId(),tCase.getCaseId());


        //推送给律师
        pushAppMessageService.pushLawyerAcceptCaseApplyApproveMsg(tCase.getLawyerAccountId(),req.getCaseId());

        return flag;

    }


    @Override
    public PageInfo<LawyerVo> searchLawyer(CaseCommReq req) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("q", req.getLawyerName());
        PageInfo<TLawyer> lawyerPageInfo = lawyerDao.getList(req.getPage(), req.getPerPage(), paramMap, "id asc");
        PageInfo<LawyerVo> voPageInfo = new PageInfo<>();
        List<LawyerVo> voList = new ArrayList<>();
        if (lawyerPageInfo != null && !lawyerPageInfo.getList().isEmpty()) {
            voPageInfo.setHasNextPage(lawyerPageInfo.isHasNextPage());
            voPageInfo.setTotal(lawyerPageInfo.getTotal());
            for (TLawyer lawyer : lawyerPageInfo.getList()) {
                LawyerVo vo = new LawyerVo();
                transLawyerVo(vo, lawyer);
                TCase tCase = caseDao.getCaseByCaseId(req.getCaseId());
                vo.setAcceptedFlag(String.valueOf(vo.getLawyerAccountId()).equals(String.valueOf(tCase.getLawyerAccountId())));
                voList.add(vo);
            }
            voPageInfo.setList(voList);
        }
        return voPageInfo;
    }

    private void transLawyerVo(LawyerVo vo, TLawyer lawyer) {
        vo.setLawyerAccountId(lawyer.getAccountId());
        vo.setLawyerId(lawyer.getId());
        TAccount account = accountService.findById(lawyer.getAccountId());
        vo.setLawyerMobile(account.getMobile());
        vo.setLawyerName(lawyer.getName());
        vo.setLawyerFirmName(lawyer.getLawFirm());
        vo.setLawyerLicenseNum(lawyer.getLawyerLicenseNumber());
        vo.setWorkYear(lawyer.getWorkYear());
        vo.setAreaName(cityService.getFullName(lawyer.getBusinessCityCode(), lawyer.getBusinessAreaCode()));
    }

    @Override
    public PageInfo<LawyerVo> getApplyAcceptLawyers(CaseCommReq req) {

        PageInfo<LawyerVo> voPageInfo = new PageInfo<>();
        List<LawyerVo> voList = new ArrayList<LawyerVo>();
        TCaseLawyerOrderCondition condition = new TCaseLawyerOrderCondition();
        condition.setCaseId(req.getCaseId());
        condition.setDeleteFlag(0);
        PageInfo<TCaseLawyerOrder> orderPageInfo = caseLawyerOrderDao.getApplyAcceptLawyers(req.getPage(), req.getPerPage(), "id desc", condition);
        if (orderPageInfo != null && !orderPageInfo.getList().isEmpty()) {
            voPageInfo.setHasNextPage(orderPageInfo.isHasNextPage());
            voPageInfo.setTotal(orderPageInfo.getTotal());
            for (TCaseLawyerOrder order : orderPageInfo.getList()) {
                TLawyer lawyer = lawyerService.findByAccountId(order.getLawyerAccountId());
                LawyerVo vo = new LawyerVo();
                if (lawyer != null) {
                    transLawyerVo(vo, lawyer);
                }
                vo.setCreateTime(DateUtil.formatNormDate(order.getCreateTime()));
                vo.setOrderStatus(order.getOrderStatus());
                voList.add(vo);
            }
            voPageInfo.setList(voList);
        }
        return voPageInfo;
    }

    @Transactional
    public boolean verifyPayMoney(Integer operatorId, String caseId) {
        TCase tcase = caseDao.getCaseByCaseId(caseId);
        TCaseType caseType = caseTypeDao.selectById(tcase.getCaseTypeId());
        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(caseId);
        record.setOperatorId(operatorId);


        if (CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey().equals(tcase.getMainStatus())) {
            if (CaseEnum.CaseType.RISK.getValue().equals(caseType.getName())
                    || CaseEnum.CaseType.LAWSUIT_EXECUTE.getValue().equals(caseType.getName())) {
                //下一步是诉讼


                record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
                record.setMainStatus(CaseEnum.MainStatus.BEING_LAWSUIT.getKey());
                record.setRemark(caseStepService.getFirstStep("1").getNameDesc());
                record.setSubStaus(caseStepService.getFirstStep("1").getId() + "");
                caseStatusUpdateRecordDao.insert(record);

                record.setMainStatus(CaseEnum.CaseStep.HAS_LOAN.getKey());
                record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
                caseStatusUpdateRecordDao.insert(record);

                record.setMainStatus(CaseEnum.CaseStep.BEING_LAWSUIT.getKey());
                record.setRemark(caseStepService.getFirstStep("1").getNameDesc());
                record.setSubStaus(caseStepService.getFirstStep("1").getId() + "");
                caseStatusUpdateRecordDao.insert(record);

                TCase updateCaseParam = new TCase();
                updateCaseParam.setCaseId(caseId);
                updateCaseParam.setMainStatus(CaseEnum.MainStatus.BEING_LAWSUIT.getKey());
                updateCaseParam.setSubStatus(caseStepService.getFirstStep("1").getId() + "");

                boolean flag = caseDao.updateById(updateCaseParam) == 1;

                updateCaseStepRecord(operatorId, tcase.getId()+"", caseStepService.getFirstStep("1").getId() + "", caseStepService.getFirstStep("1").getNameDesc(), CaseEnum.CaseStep.BEING_LAWSUIT.getKey());


                return flag;

            } else {
                //跳过诉讼，下一步是执行

                record.setRecordType(CaseEnum.CaseRecordType.STEP.getKey());
                record.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
                record.setSubStaus(caseStepService.getFirstStep("2").getId() + "");
                record.setRemark(caseStepService.getFirstStep("2").getNameDesc());
                caseStatusUpdateRecordDao.insert(record);

                record.setMainStatus(CaseEnum.CaseStep.HAS_LOAN.getKey());
                record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
                caseStatusUpdateRecordDao.insert(record);

                record.setMainStatus(CaseEnum.CaseStep.BEING_LAWSUIT.getKey());
                record.setSubStaus(caseStepService.getFirstStep("2").getId() + "");
                record.setRemark(caseStepService.getFirstStep("2").getNameDesc());
                caseStatusUpdateRecordDao.insert(record);

                TCase updateCaseParam = new TCase();
                updateCaseParam.setCaseId(caseId);
                updateCaseParam.setMainStatus(CaseEnum.MainStatus.BEING_EXECUTE.getKey());
                updateCaseParam.setSubStatus(caseStepService.getFirstStep("2").getId() + "");

                boolean flag = caseDao.updateById(updateCaseParam) == 1;
                updateCaseStepRecord(operatorId, tcase.getId()+"", caseStepService.getFirstStep("2").getId() + "", caseStepService.getFirstStep("2").getNameDesc(), CaseEnum.CaseStep.BEING_EXECUTE.getKey());


                return flag;
            }
        } else {
            record.setMainStatus(CaseEnum.CaseStep.HAS_LOAN.getKey());
            record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
            return caseStatusUpdateRecordDao.insert(record) == 1;
        }

    }

    @Override
    public boolean updateCaseOperate(UpdateCaseReq req) {
        LOGGER.info("开始调用 updateCaseOperate,参数:{}", JSON.toJSONString(req));
        TCase tCase = caseDao.selectById(req.getId());
        if (CaseEnum.CaseStep.LAWSUIT_EXECUTE.getKey().equals(req.getOperate())) {
            //诉讼或执行更新子状态
            return updateCaseSubStatus(req.getOperatorId(), tCase.getCaseId(), req.getSubStatusId(), req.getSubStatusDesc());
        } else if (CaseEnum.CaseStep.HAS_LOAN.getKey().equals(req.getOperate())) {
            //财务放款审核
            return verifyPayMoney(req.getOperatorId(), tCase.getCaseId());
        } else {
            //其它操作
            return updateOperate(req.getOperatorId(), tCase.getCaseId(), req.getOperate());
        }
    }


    @Override
    public boolean uploadFile(AttachmentReq req) {
        TCaseAttachment attachment = new TCaseAttachment();
        BeanUtils.copyProperties(req, attachment);
        if (!StringUtils.isEmpty(req.getAttachUrl())) {
            try {
                String result = HttpUtils.sendGet(req.getAttachUrl().split("\\?attname")[0] + "?stat");
                if (!StringUtils.isEmpty(result)) {
                    QNFileInfoDto dto = JSON.parseObject(result, QNFileInfoDto.class);
                    if (dto != null) {
                        attachment.setFileSize(dto.getFsize());
                        attachment.setFileType(dto.getMimeType());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return caseAttachmentDao.insert(attachment) == 1;
    }

    private boolean updateOperate(Integer operatorId, String caseId, String operate) {
        TCaseStatusUpdateRecord record = new TCaseStatusUpdateRecord();
        record.setCaseId(caseId);
        record.setOperatorId(operatorId);
        record.setMainStatus(operate);
        record.setRecordType(CaseEnum.CaseRecordType.OPERATE.getKey());
        return caseStatusUpdateRecordDao.insert(record) == 1;
    }


    private TCaseStep getFirstStep(String type) {
        //1 诉讼步骤 2 执行步骤
        TCaseStepCondition caseStepCondition = new TCaseStepCondition();
        caseStepCondition.setType(type);
        caseStepCondition.setOrderDesc(1);
        caseStepCondition.setDeleteFlag(false);
        return caseStepDao.selectFirst(caseStepCondition);
    }

    private TCaseStep getNextStep(String currentStep) {
        TCaseStep nextStep = new TCaseStep();
        TCaseStep caseStep = caseStepDao.selectById(currentStep);
        if (caseStep != null && caseStep.getNextId() != null) {
            TCaseStep nextTempStep = caseStepDao.selectById(caseStep.getNextId());

            if(nextTempStep != null){
                nextStep.setId(nextTempStep.getId());
                nextStep.setName(nextTempStep.getName());
                TCaseStepNoticeTemplateCondition condition = new TCaseStepNoticeTemplateCondition();
                condition.setStepId(nextStep.getId());
                condition.setDeleteFlag(false);
                TCaseStepNoticeTemplate caseStepNoticeTemplate = caseStepNoticeTemplateDao.selectFirst(condition);
                if(caseStepNoticeTemplate != null){
                    nextStep.setNameDesc(caseStepNoticeTemplate.getNameDesc());
                }
            }else{
                nextStep.setId(caseStep.getId());
                nextStep.setName(caseStep.getName());
                TCaseStepNoticeTemplateCondition condition = new TCaseStepNoticeTemplateCondition();
                condition.setDeleteFlag(false);
                condition.setStepId(caseStep.getId());

                TCaseStepNoticeTemplate caseStepNoticeTemplate = caseStepNoticeTemplateDao.selectFirst(condition);
                if(caseStepNoticeTemplate != null){
                    nextStep.setNameDesc(caseStepNoticeTemplate.getNameDesc());
                }
            }

        }
        return nextStep;
    }

    private String getRealName(Integer accountId, Integer operatorId) {
        if (accountId != null) {
            return accountService.getRealName(accountId);
        } else {
            return getAdminName(operatorId);
        }
    }

    private String getAdminName(Integer operatorId) {
        TSysStaff sysStaff = sysStaffDao.selectById(operatorId);
        return sysStaff == null ? "" : sysStaff.getName();
    }

    private List<CaseRecordVo> transAdminStaus(TCase tCase, List<TCaseStatusUpdateRecord> recordList){
        List<CaseRecordVo> recordVoList = new ArrayList<CaseRecordVo>();
        for(TCaseStatusUpdateRecord record : recordList){
            CaseRecordVo vo = new CaseRecordVo();

            vo.setCreateTime(DateUtil.getNormDateStr(record.getCreateTime()));
            if(CaseEnum.CaseStep.SCHEDULE_ACCEPTED_LAWYER.getKey().equals(record.getMainStatus())){
                vo.setOperatorName(getAdminName(record.getOperatorId()));
            }else{
                String realName = getRealName(record.getLawyerAccountId(), record.getOperatorId());
                if (StringUtils.isBlank(realName)) {
                    realName = tCase.getApplyName();
                }
                vo.setOperatorName(realName);
            }
            if(CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(record.getMainStatus()) ||
                CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(record.getMainStatus())){
                if(CaseEnum.CaseStep.END_LAWSUIT.getKey().equals(record.getSubStaus())
                    || CaseEnum.CaseStep.END_EXECUTE.getKey().equals(record.getSubStaus())){
                    vo.setCaseStatus(CaseEnum.CaseStep.getValueByKey(record.getSubStaus()));
                }else{
                    TCaseStep step = caseStepDao.selectById(record.getSubStaus());
                    if(step != null){
                        vo.setCaseStatus(step.getName());
                    }
                }


            }else{
                vo.setCaseStatus(CaseEnum.CaseStep.getValueByKey(record.getMainStatus()));
            }
            recordVoList.add(vo);

        }
        return recordVoList;
    }

    private List<CaseRecordVo> transStatus(List<TCaseStatusUpdateRecord> recordList, List<TCaseStatusUpdateRecord> lawsuitList, List<TCaseStatusUpdateRecord> excuteList) {
        List<CaseRecordVo> recordVoList = new ArrayList<CaseRecordVo>();
        boolean dealLawsuitFlag = false;
        boolean dealExcuteFlag = false;

        for (TCaseStatusUpdateRecord record : recordList) {


            if (CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(record.getMainStatus())
                    && lawsuitList != null && !lawsuitList.isEmpty() && !dealLawsuitFlag) {

                for (TCaseStatusUpdateRecord lawsuitRecord : lawsuitList) {
                    CaseRecordVo subVo = new CaseRecordVo();
                    subVo.setCreateTime(DateUtil.getNormDateStr(lawsuitRecord.getCreateTime()));
                    subVo.setOperatorName(getRealName(lawsuitRecord.getLawyerAccountId(), lawsuitRecord.getOperatorId()));
                    if (!CaseEnum.CaseStep.END_LAWSUIT.getKey().equals(lawsuitRecord.getSubStaus())) {
                        TCaseStep step = caseStepDao.selectById(lawsuitRecord.getSubStaus());
                        if (step != null) {
                            subVo.setCaseStatus(step.getName());
                            subVo.setCaseStatusDesc(lawsuitRecord.getRemark());
                        }
                    } else {
                        subVo.setCaseStatus(CaseEnum.CaseStep.END_LAWSUIT.getValue());
                        subVo.setCaseStatusDesc("诉讼结束描述");
                    }

                    recordVoList.add(subVo);
                }
                dealLawsuitFlag = true;
            }

            if (CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(record.getMainStatus()) && !dealExcuteFlag) {
                for (TCaseStatusUpdateRecord excuteRecord : excuteList) {
                    CaseRecordVo subVo = new CaseRecordVo();
                    if (!CaseEnum.CaseStep.END_EXECUTE.getKey().equals(excuteRecord.getSubStaus())) {
                        TCaseStep step = caseStepDao.selectById(excuteRecord.getSubStaus());
                        if (step != null) {
                            subVo.setCaseStatus(step.getName());
                            subVo.setCaseStatusDesc(excuteRecord.getRemark());
                        }
                    } else {
                        subVo.setCaseStatus(CaseEnum.CaseStep.END_EXECUTE.getValue());
                        subVo.setCaseStatusDesc("执行结束描述");
                    }

                    subVo.setCreateTime(DateUtil.getNormDateStr(excuteRecord.getCreateTime()));
                    subVo.setOperatorName(getRealName(excuteRecord.getLawyerAccountId(), excuteRecord.getOperatorId()));
                    recordVoList.add(subVo);
                }
                dealExcuteFlag = true;
            }

            CaseRecordVo vo = new CaseRecordVo();
            if (CaseEnum.CaseRecordType.STEP.getKey().equals(record.getRecordType())) {
                TCaseStatusDesc statusDesc = caseStatusDescDao.getStatusDescByStatus(record.getMainStatus());
                if(statusDesc != null){
                    vo.setCaseStatus(statusDesc.getStatusName());
                    if(statusDesc.getStatus().equals(CaseEnum.MainStatus.BEING_LAWSUIT.getKey())
                            || statusDesc.getStatus().equals(CaseEnum.MainStatus.BEING_EXECUTE.getKey())){
                        TCase tCase = caseDao.getCaseByCaseId(record.getCaseId());
                        if(tCase.getLawyerAccountId() != null){
                            TLawyer lawyer = lawyerService.findByAccountId(tCase.getLawyerAccountId());
                            TAccount account = accountService.findById(tCase.getLawyerAccountId());
                            vo.setCaseStatusDesc(statusDesc.getStatusDesc()+lawyer.getName()+","+account.getMobile());
                        }
                    }else{
                        vo.setCaseStatusDesc(statusDesc.getStatusDesc());
                    }
                }
                vo.setOperatorName(getRealName(record.getLawyerAccountId(), record.getOperatorId()));
            } else {
                vo.setCaseStatus(CaseEnum.CaseStep.getValueByKey(record.getMainStatus()));
                if(CaseEnum.CaseStep.SCHEDULE_ACCEPTED_LAWYER.getKey().equals(record.getMainStatus())){
                    vo.setOperatorName(getAdminName(record.getOperatorId()));
                }else{
                    vo.setOperatorName(getRealName(record.getLawyerAccountId(), record.getOperatorId()));
                }
            }
            vo.setCreateTime(DateUtil.getNormDateStr(record.getCreateTime()));
            recordVoList.add(vo);

        }
        return recordVoList;
    }

    private List<CaseAttachmentVo> transListToMapGroupByStatus(List<TCaseAttachment> list) {



            List<CaseAttachmentVo> caseAttachmentVoList = new ArrayList<CaseAttachmentVo>();
            for (TCaseAttachment caseAttachment : list) {
                CaseAttachmentVo vo = new CaseAttachmentVo();
                BeanUtils.copyProperties(caseAttachment, vo);
                if (!StringUtils.isEmpty(vo.getAttachUrl())) {
                    vo.setAttachUrl(vo.getAttachUrl().split("\\?attname")[0]);
                }
                vo.setCreateTime(DateUtil.formatNormDate(caseAttachment.getCreateTime()));
                try{
                    vo.setFileSize(new BigDecimal(caseAttachment.getFileSize()).divide(new BigDecimal(1024 * 1024), BigDecimal.ROUND_HALF_UP, 2) + "");
                }catch (Exception e){
                    vo.setFileSize(null);
                }
                caseAttachmentVoList.add(vo);
            }

        return caseAttachmentVoList;
    }

    private List<TCaseAttachment> getAttachmentByCaseIdAndStatus(String caseId, String status) {
        TCaseAttachmentCondition caseAttachmentCondition = new TCaseAttachmentCondition();
        caseAttachmentCondition.setCaseId(caseId);
        caseAttachmentCondition.setCaseStatus(status);
        caseAttachmentCondition.setDeleteFlag(0);
        return caseAttachmentDao.selectList(caseAttachmentCondition);
    }

    @Override
    public List<HomePageCaseVO> getRecommendedCaseList(Integer caseSize) {
        List<HomePageCaseVO> list = caseDao.getRecommendedCaseList(caseSize);
        for (HomePageCaseVO item : list) {
            item.setStatusDesc(CaseEnum.MainStatus.getValueByKey(item.getMainStatus()));
        }
        return list;
    }

    @Override
    public List<WorkBenchVO> getStatusCase() {

        return caseDao.getStatusCase();
    }

    private boolean isContainLawsuit(String caseId) {
        TCase tcase = caseDao.getCaseByCaseId(caseId);
        TCaseType caseType = caseTypeDao.selectById(tcase.getCaseTypeId());
        return CaseEnum.CaseType.RISK.getValue().equals(caseType.getName())
                || CaseEnum.CaseType.LAWSUIT_EXECUTE.getValue().equals(caseType.getName());
    }

    private boolean isContainInvoice(String caseId) {

        TCaseStatusUpdateRecordCondition condition = new TCaseStatusUpdateRecordCondition();
        condition.setCaseId(caseId);
        condition.setDeleteFlag(0);
        condition.setMainStatus(CaseEnum.CaseStep.HAS_RECEIVED_PAY.getKey());
        List<TCaseStatusUpdateRecord> list = caseStatusUpdateRecordDao.selectList(condition);
        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public List<String> transStatus2Step(String caseId, String status) {
        List<String> stepList = new ArrayList<String>();
        if (CaseEnum.MainStatus.INIT.getKey().equals(status)
                || CaseEnum.MainStatus.PRE_CHECK_FAIL.getKey().equals(status)) {
            stepList.add("1");
        } else if (CaseEnum.MainStatus.PRE_CHECK_SUCCESS.getKey().equals(status)) {
            stepList.add("1");
            stepList.add("2");
        } else if (CaseEnum.MainStatus.RISK_CHECK_SUCCESS.getKey().equals(status)) {
            stepList.add("1");
            stepList.add("2");
            stepList.add("3");
        } else if (CaseEnum.MainStatus.HAS_SIGN_CONTRACT.getKey().equals(status)) {
            stepList.add("1");
            stepList.add("2");
            stepList.add("3");
            stepList.add("4");
        } else if (CaseEnum.MainStatus.HAS_LOAN.getKey().equals(status)) {
            if (isContainLawsuit(caseId)) {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("5");
            } else {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("6");
            }
        } else if (CaseEnum.MainStatus.BEING_LAWSUIT.getKey().equals(status)) {
            stepList.add("1");
            stepList.add("2");
            stepList.add("3");
            stepList.add("4");
            stepList.add("5");
        } else if (CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(status)) {
            if (isContainLawsuit(caseId)) {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("5");
                stepList.add("6");
            } else {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("6");
            }
        } else if (CaseEnum.MainStatus.WAIT_RECEIVED_PAY.getKey().equals(status)) {
            if (isContainLawsuit(caseId)) {

                if (isContainInvoice(caseId)) {
                    stepList.add("1");
                    stepList.add("2");
                    stepList.add("3");
                    stepList.add("4");
                    stepList.add("5");
                    stepList.add("6");
                    stepList.add("7");
                    stepList.add("8");
                } else {
                    stepList.add("1");
                    stepList.add("2");
                    stepList.add("3");
                    stepList.add("4");
                    stepList.add("5");
                    stepList.add("6");
                    stepList.add("7");
                }


            } else {

                if (isContainInvoice(caseId)) {
                    stepList.add("1");
                    stepList.add("2");
                    stepList.add("3");
                    stepList.add("4");
                    stepList.add("6");
                    stepList.add("7");
                    stepList.add("8");
                } else {
                    stepList.add("1");
                    stepList.add("2");
                    stepList.add("3");
                    stepList.add("4");
                    stepList.add("6");
                    stepList.add("7");
                }


            }
        } else if (CaseEnum.MainStatus.SUCCESS.getKey().equals(status)) {
            if (isContainLawsuit(caseId)) {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("5");
                stepList.add("6");
                stepList.add("7");
                stepList.add("8");
            } else {
                stepList.add("1");
                stepList.add("2");
                stepList.add("3");
                stepList.add("4");
                stepList.add("6");
                stepList.add("7");
                stepList.add("8");
            }
        }
        return stepList;
    }

    @Override
    public PageInfoResp<Case> getProjectManagerAllocatedCaseList(CaseCommReq req) {
        PageInfoResp<Case> resp = new PageInfoResp<>();
        PageInfo<Case> pageInfo = caseDao.getProjectManagerAllocatedCaseList(req.getPage(), req.getPerPage(), req.getId(), "");
        for (Case item : pageInfo.getList()) {
            item.setCaseTypeName(caseTypeDao.getName(item.getCaseTypeId()));
            item.setCaseReason(caseBriefDao.getName(item.getCaseBriefId()));
            item.setEndFlag(item.getMainStatus().equals(CaseEnum.MainStatus.SUCCESS.getKey()));
            item.setStatusDesc(CaseEnum.MainStatus.getValueByKey(item.getMainStatus()));
        }
        resp.setList(pageInfo.getList());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Transactional
    @Override
    public Integer allocatedCaseToProjectManager(CaseCommReq req) {
        TAccount account = accountService.findById(req.getAccountId());
        if (account == null || !account.getProjectManagerFlag()) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TCase tCase = caseDao.getCaseByCaseId(req.getCaseId());
        if (tCase == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TCaseProjectManagerMap model = caseProjectManagerMapDao.findBy(req.getCaseId());
        if (model != null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        model = new TCaseProjectManagerMap();
        model.setAccountId(req.getAccountId());
        model.setCaseId(req.getCaseId());
        model.setOperatorId(req.getOperatorId());
        int result = caseProjectManagerMapDao.insert(model);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        //将项目经理添加进聊天室
        pushManagerToConnRoom(model);

        pushAppMessageService.pushCasePublishToProjectManagerMsg(account.getId(), req.getCaseId());
        return model.getId();
    }


    private void pushManagerToConnRoom(TCaseProjectManagerMap model) {
        //
        TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
        TCase tCase = caseDao.getCaseByCaseId(model.getCaseId());
        condition.setCaseId(tCase.getId());
        condition.setRelevanceId(model.getAccountId());
        condition.setType(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType());
        //
        TConnectRoomPerson roomPerson = connectRoomPersonDao.selectFirst(condition);

        //不为空时 直接
        if(roomPerson !=null){
            roomPerson.setDeleteFlag(false);
            roomPerson.setUpdateTime(new Date());
            connectRoomPersonDao.updateById(roomPerson);
            return;
        }

        //直接添加
        TConnectRoomPerson connectRoomPerson = new TConnectRoomPerson();
        connectRoomPerson.setRelevanceId(model.getAccountId());
        connectRoomPerson.setCaseId(tCase.getId());
        connectRoomPerson.setDeleteFlag(false);
        connectRoomPerson.setType(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType());
        connectRoomPerson.setCreateTime(DateUtil.getSysTime());
        connectRoomPerson.setShutupFlag(false);
        connectRoomPersonDao.insert(connectRoomPerson);
    }

    @Transactional
    @Override
    public Integer cancelAllocatedCaseToProjectManager(CaseCommReq req) {
        TCase tCase = caseDao.getCaseByCaseId(req.getCaseId());
        if (tCase == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TCaseProjectManagerMap model = caseProjectManagerMapDao.findBy(req.getCaseId());
        if (model == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        model.setDeleteFlag(true);
        model.setOperatorId(req.getOperatorId());
        model.setUpdateTime(new Date());
        int result = caseProjectManagerMapDao.updateById(model);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }

        //去掉聊天室的项目经理
        deleteManagerToConnRoom(model);

        return model.getId();
    }

    private void deleteManagerToConnRoom(TCaseProjectManagerMap model) {


        TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
        TCase tCase = caseDao.getCaseByCaseId(model.getCaseId());
        condition.setCaseId(tCase.getId());
        condition.setRelevanceId(model.getAccountId());
        condition.setType(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType());
        //
        TConnectRoomPerson roomPerson = connectRoomPersonDao.selectFirst(condition);

        //不为空时 直接
        if(roomPerson !=null){
            roomPerson.setDeleteFlag(true);
            roomPerson.setUpdateTime(new Date());
            connectRoomPersonDao.updateById(roomPerson);
            return;
        }

    }

    @Override
    public PageInfoResp<TCase> getMyManageCaseList(CaseCommReq req) {
        PageInfoResp<TCase> resp = new PageInfoResp<>();
        PageInfo<TCase> pageInfo = caseDao.getMyManageCaseList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setList(pageInfo.getList());
        return resp;
    }

    @Override
    public Integer getProjectManagerByCaseId(String caseId) {
        TCaseProjectManagerMap caseProjectManagerMap = caseProjectManagerMapDao.findBy(caseId);
        if (caseProjectManagerMap != null) {
            return caseProjectManagerMap.getAccountId();
        }
        return null;
    }


    private void allocatedCaseToDefaultProjectManager(String caseId) {
        try {
            String defaultProjectManager = systemProperties.getDefaultProjectManager();
            if (StringUtils.isBlank(defaultProjectManager)) {
                return;
            }
            List<String> mobiles = Arrays.asList(defaultProjectManager.split(","));
            if (mobiles.size() == 0) {
                return;
            }
            TAccount account = accountService.findByMobile(mobiles.get(0));
            if (account == null) {
                LOGGER.error("分配默认项目经理异常，手机号：{}不存在", mobiles.get(0));
                return;
            }
            if (!account.getProjectManagerFlag()) {
                account.setProjectManagerFlag(true);
                accountService.update(account);
            }
            CaseCommReq req = new CaseCommReq();
            req.setAccountId(account.getId());
            req.setCaseId(caseId);
            allocatedCaseToProjectManager(req);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("分配默认项目经理异常，caseId：{}，msg：{}", caseId, Throwables.getStackTraceAsString(e));
        }
    }
}
