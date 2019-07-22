package com.winback.core.service._case.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.condition._case.TCaseStepBranchCondition;
import com.winback.core.condition._case.TCaseStepCondition;
import com.winback.core.condition._case.TCaseStepNoticeTemplateCondition;
import com.winback.core.dao._case.*;
import com.winback.core.dto._case.CaseStatusStepDto;
import com.winback.core.dto._case.CaseStepRecordDto;
import com.winback.core.dto._case.UpdateCaseReq;
import com.winback.core.facade._case.req.CaseStepReq;
import com.winback.core.model._case.*;
import com.winback.core.service._case.CaseService;
import com.winback.core.service._case.TCaseStepService;
import com.winback.core.service.assistant.PushMessageService;
import com.winback.core.vo._case.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.geom.AreaOp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TCaseStepServiceImpl implements TCaseStepService {
    @Autowired
    private TCaseStepDao tCaseStepDao;
    @Autowired
    private TCaseStepBranchDao tCaseStepBranchDao;
    @Autowired
    private TCaseStepNoticeTemplateDao tCaseStepNoticeTemplateDao;
    @Autowired
    private TCaseStepRecordDao tCaseStepRecordDao;
    @Autowired
    private TCaseDao tCaseDao;
    @Autowired
    private CaseService caseService;
    @Autowired
    private PushMessageService pushMessageService;

    @Override
    public PageInfo getCaseSteps(CaseStepReq.CaseStepListReq caseStepListReq) {
        List<TCaseStepGroupVO> tCaseStepGroupVOS = tCaseStepDao.getCaseSteps(caseStepListReq.getType());
          //分组查询进行封装
        if (tCaseStepGroupVOS == null || tCaseStepGroupVOS.size() <= 0) {
            return new PageInfo(new ArrayList()) ;
        }

        for (TCaseStepGroupVO tCaseStepGroupVO:tCaseStepGroupVOS) {
            if (tCaseStepGroupVO.getCaseStepVOS() != null && tCaseStepGroupVO.getCaseStepVOS().size() > 0) {
                List<TCaseStepVO> newCaseStepVOS = new ArrayList<>();
                List<TCaseStepVO> oldCaseStepVOS = tCaseStepGroupVO.getCaseStepVOS();
                for (int i = 0, len = oldCaseStepVOS.size(); i < len; i++) {
                    TCaseStepVO oldCaseStepVO = oldCaseStepVOS.get(i);
                    changeOldCaseStepVOS(oldCaseStepVO, newCaseStepVOS);
                 }

                tCaseStepGroupVO.setCaseStepVOS(newCaseStepVOS);
            }
        }

        return new PageInfo(tCaseStepGroupVOS);
    }

    @Override
    public void updateNextIds(String type, String stepId) {
        List<TCaseStep> tCaseSteps = null;
        if(StringUtils.isBlank(type)){
            TCaseStep tCaseStep = tCaseStepDao.selectById(stepId);
            tCaseSteps = tCaseStepDao.getAllSmaSteps(tCaseStep.getType());
        }else {
            tCaseSteps = tCaseStepDao.getAllSmaSteps(type);
        }

        ArrayList<TCaseStep> newCaseSteps = new ArrayList<>();

        if(tCaseSteps != null && tCaseSteps.size() > 0) {
            for(int i = 0; i < tCaseSteps.size(); i++) {
                TCaseStep tCaseStep = tCaseSteps.get(i);
                if(i < tCaseSteps.size() - 1 &&
                        !tCaseStep.getGroupId().equals(tCaseSteps.get(i + 1).getGroupId())) {
                    tCaseStep.setNextId(tCaseSteps.get(i + 1).getId());
                    tCaseStep.setUpdateTime(new Date());
                    newCaseSteps.add(tCaseStep);
                }else if(i == tCaseSteps.size() - 1) {
                    tCaseStep.setNextId(null);
                    tCaseStep.setUpdateTime(new Date());
                    newCaseSteps.add(tCaseStep);
                }
            }

            if(newCaseSteps.size() > 0) {
                tCaseStepDao.batchUpdateCaseStep(newCaseSteps);
            }
        }
    }

    @Override
    @Transactional
    public Integer saveCaseStep(CaseStepReq.SaveCaseStepReq saveCaseStepReq) {
        //根据type 以及groupId查询最大的排序码
        TCaseStep tCaseStepModel = tCaseStepDao.getOrderDescByTypeGroup(saveCaseStepReq.getGroupId());

        TCaseStep tCaseStep = new TCaseStep();
        tCaseStep.setName(saveCaseStepReq.getName());
        tCaseStep.setNameDesc(saveCaseStepReq.getNameDesc());
        tCaseStep.setGroupId(Integer.parseInt(saveCaseStepReq.getGroupId()));
        tCaseStep.setBranchFlag(false);
        tCaseStep.setNextId(null);
        tCaseStep.setCreateTime(new Date());
        tCaseStep.setUpdateTime(new Date());
        tCaseStep.setType(saveCaseStepReq.getType());
        tCaseStep.setOrderDesc(tCaseStepModel == null ? 1: tCaseStepModel.getOrderDesc() + 1);

        Integer insert = tCaseStepDao.insert(tCaseStep);
        //如果非第一步骤进行nexyId更新
        if (tCaseStepModel != null) {
            tCaseStepModel.setNextId(tCaseStep.getId());
            tCaseStepDao.updateById(tCaseStepModel);
        }
        //如果子集不为空从而插入子集
        if (saveCaseStepReq.getCaseBranchReqList() != null && saveCaseStepReq.getCaseBranchReqList().size() > 0) {

            saveCaseBranchTypeList(tCaseStep.getId(), saveCaseStepReq.getCaseBranchReqList(),
                    saveCaseStepReq.getType(), saveCaseStepReq.getGroupId());
        }
        //更新步骤信息
        return insert;
    }

    private void saveBranchList(Integer id, List<CaseStepReq.CaseBranchReq> caseBranchReqList, String type) {
        List<TCaseStepBranch> tCaseStepBranches = new ArrayList<>();
        for (int i = 0, len = caseBranchReqList.size(); i < len; i++) {
            CaseStepReq.CaseBranchReq caseBranchReq = caseBranchReqList.get(i);

            TCaseStepBranch tCaseStepBranch = new TCaseStepBranch();
            tCaseStepBranch.setName(caseBranchReq.getName());
            tCaseStepBranch.setNameDesc(caseBranchReq.getName());
            tCaseStepBranch.setType(type);
            tCaseStepBranch.setDeleteFlag(false);
            tCaseStepBranch.setOrderDesc(i + 1);
            tCaseStepBranch.setNextId(Integer.parseInt(caseBranchReq.getNextId()));
            tCaseStepBranch.setStepId(id);
            tCaseStepBranch.setCreateTime(new Date());
            tCaseStepBranch.setUpdateTime(new Date());

            tCaseStepBranches.add(tCaseStepBranch);
        }

        tCaseStepBranchDao.saveBatchCaseBranch(tCaseStepBranches);
    }

    @Override
    public Integer updateCaseStep(CaseStepReq.UpdateOrDeleteReq req) {
        TCaseStep tCaseStep = tCaseStepDao.selectById(req.getId());
        //首先为排序修改
        if (StringUtils.isNotBlank(req.getOrderFlag())) {
            TCaseStep beforeCaseStep = getBeforeOrNextCaseStep(req.getGroupId(), tCaseStep.getOrderDesc(),"before");
            TCaseStep beforeTwoCaseStep = getBeforeOrNextCaseStep(req.getGroupId(), tCaseStep.getOrderDesc(),null);
            TCaseStep nextCaseStep = getBeforeOrNextCaseStep(req.getGroupId(), tCaseStep.getOrderDesc(), "next");
            return changeOrderCaseStep(beforeCaseStep, tCaseStep, nextCaseStep, req.getOrderFlag(), beforeTwoCaseStep);
        }else {

            tCaseStepDao.deleteBatchCaseStepBranch(tCaseStep.getId());
            if (req.getCaseBranchReqList() != null && req.getCaseBranchReqList().size() > 0) {
                saveCaseBranchTypeList(tCaseStep.getId(), req.getCaseBranchReqList(), req.getType(), tCaseStep.getGroupId().toString());
            }

            tCaseStep.setName(req.getName());
            tCaseStep.setNameDesc(req.getNameDesc());
            tCaseStep.setUpdateTime(new Date());
            return tCaseStepDao.updateById(tCaseStep);
        }
    }

    private void saveCaseBranchTypeList(Integer id, List<CaseStepReq.CaseBranchReq> caseBranchReqList, String type, String groupId) {
        List<TCaseStep> tCaseStepBranches = new ArrayList<>();
        for (int i = 0, len = caseBranchReqList.size(); i < len; i++) {
            CaseStepReq.CaseBranchReq caseBranchReq = caseBranchReqList.get(i);

            TCaseStep tCaseStepBranch = new TCaseStep();
            tCaseStepBranch.setName(caseBranchReq.getName());
            tCaseStepBranch.setNameDesc(caseBranchReq.getName());
            tCaseStepBranch.setType(type);
            tCaseStepBranch.setDeleteFlag(false);
            tCaseStepBranch.setOrderDesc(i + 1);
            tCaseStepBranch.setNextId(Integer.parseInt(caseBranchReq.getNextId()));
            tCaseStepBranch.setParentId(id);
            tCaseStepBranch.setCreateTime(new Date());
            tCaseStepBranch.setUpdateTime(new Date());
            tCaseStepBranch.setGroupId(Integer.parseInt(groupId));

            tCaseStepBranches.add(tCaseStepBranch);
        }

        tCaseStepDao.saveCaseBranchTypeList(tCaseStepBranches);

    }

    @Override
    @Transactional
    public Integer deleteCaseStep(CaseStepReq.UpdateOrDeleteReq req) {
        TCaseStep tCaseStep = tCaseStepDao.selectById(req.getId());

        //合到一个表进行修改
        tCaseStepDao.deleteBatchCaseStepBranch(tCaseStep.getId());
        //该数据之后全部进行修改
        tCaseStepDao.updateOrderDescByGroupId(tCaseStep.getGroupId(), tCaseStep.getOrderDesc());
        //调整nextId
        TCaseStep beforeCaseStep = getBeforeOrNextCaseStep(tCaseStep.getGroupId().toString(), tCaseStep.getOrderDesc(), "before");
        if (beforeCaseStep != null) {
            beforeCaseStep.setNextId(tCaseStep.getNextId());
            tCaseStepDao.updateById(beforeCaseStep);
        }
        tCaseStep.setDeleteFlag(true);

        return tCaseStepDao.updateById(tCaseStep);

    }

    @Override
    public Integer updateCaseStepBranch(CaseStepReq.UpdateBranchReq req) {
        TCaseStepBranch tCaseStepBranch = tCaseStepBranchDao.selectById(req.getId());
        TCaseStepBranch beforeStep = getBeforeOrNextCaseStepBranch(req.getStepId(), tCaseStepBranch.getOrderDesc(), "before");
        TCaseStep nextStep = getBeforeOrNextCaseStep(req.getStepId(), tCaseStepBranch.getOrderDesc(),"next");
        if (beforeStep == null || nextStep == null) {
            return null;
        }

        if ("0".equals(req.getOrderFlag())) {
            nextStep.setOrderDesc(tCaseStepBranch.getOrderDesc() - 1);
            tCaseStepBranchDao.updateById(beforeStep);

            tCaseStepBranch.setOrderDesc(tCaseStepBranch.getOrderDesc() + 1);
        }else {
            beforeStep.setOrderDesc(tCaseStepBranch.getOrderDesc());
            tCaseStepBranchDao.updateById(beforeStep);

            tCaseStepBranch.setOrderDesc(tCaseStepBranch.getOrderDesc() - 1);
        }

        return tCaseStepBranchDao.updateById(tCaseStepBranch);
    }

    @Override
    public Integer saveStepTemplate(CaseStepReq.StepTemplateSaveReq req) {

        TCaseStepNoticeTemplate tCaseStepNoticeTemplate = new TCaseStepNoticeTemplate();
        tCaseStepNoticeTemplate.setName(req.getName());
        tCaseStepNoticeTemplate.setNameDesc(req.getNameDesc());
        tCaseStepNoticeTemplate.setStepId(Integer.parseInt(req.getStepId()));
        tCaseStepNoticeTemplate.setDeleteFlag(false);
        tCaseStepNoticeTemplate.setType(1);
        tCaseStepNoticeTemplate.setCreateTime(new Date());
        tCaseStepNoticeTemplate.setUpdateTime(new Date());
        return tCaseStepNoticeTemplateDao.insert(tCaseStepNoticeTemplate);

    }

    @Override
    public Integer updateOrDeleteStepTemplate(CaseStepReq.StepTemplateUpdateReq req) {
        TCaseStepNoticeTemplate tCaseStepNoticeTemplate = tCaseStepNoticeTemplateDao.selectById(req.getId());
        if ("0".equals(req.getUpdateFlag())){
            tCaseStepNoticeTemplate.setName(req.getName());
            tCaseStepNoticeTemplate.setNameDesc(req.getNameDesc());
            tCaseStepNoticeTemplate.setUpdateTime(new Date());
        }else {
            tCaseStepNoticeTemplate.setDeleteFlag(true);
        }

        return tCaseStepNoticeTemplateDao.updateById(tCaseStepNoticeTemplate);
    }


    @Override
    public List<TCaseStepNoticeTemplateVO> getTemplateByStepId(CaseStepReq.CaseTemplateReq req) {

        return tCaseStepNoticeTemplateDao.getTemplateByStepId(req.getId());
    }

    @Override
    public TCaseStepDetailVO getCaseStepById(String id) {

        return tCaseStepDao.getCaseStepById(id);
    }

    @Override
    public List<TCaseStepSelectVO> getCaseStepNotBranch(CaseStepReq.CaseStepListReq req) {

        return tCaseStepDao.getCaseStepNotBranch(req.getType());
    }

    @Override
    @Transactional
    public Integer saveCaseStepRecord(CaseStepReq.CaseStepRecordSaveReq req) {
        TCaseStepRecord tCaseStepRecord = new TCaseStepRecord();

        tCaseStepRecord.setAccountId(StringUtils.isBlank(req.getAccountId())? null : Integer.parseInt(req.getAccountId()));
        tCaseStepRecord.setCaseId(Integer.parseInt(req.getCaseId()));
        tCaseStepRecord.setCaseType(req.getType());
        tCaseStepRecord.setPushMsg(req.getPushMsg());
        tCaseStepRecord.setCreateTime(new Date());
        tCaseStepRecord.setUpdateTime(new Date());
        tCaseStepRecord.setDeleteFlag(false);
        tCaseStepRecord.setStepId(req.getStepId());

        //案件进行更新操作
        if("admin".equals(req.getUpdateSource())) {
            UpdateCaseReq params = new UpdateCaseReq();
            params.setSubStatusId(req.getStepId());
            params.setOperate(CaseEnum.CaseStep.LAWSUIT_EXECUTE.getKey());
            params.setSubStatusDesc(req.getPushMsg());
            params.setId(Integer.parseInt(req.getCaseId()));
            params.setOperatorId(Integer.parseInt(req.getAccountId()));
            caseService.updateCaseOperate(params);
        }

        tCaseStepRecordDao.insert(tCaseStepRecord);

        //推送给客户
        pushMessageService.pushStepMsg(tCaseStepRecord.getId());

        return tCaseStepRecord.getId();
    }

    @Override
    public PageInfo getCaseStepRecordList(CaseStepReq.CaseStepRecordListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        CaseStepRecordDto params = new CaseStepRecordDto();
        params.setCaseId(req.getCaseId());
        params.setType(req.getType());

        List<TCaseStepRecordVO> caseStepRecordVOS = tCaseStepRecordDao.getCaseStepRecordList(params);

        return new PageInfo(caseStepRecordVOS == null?new ArrayList():caseStepRecordVOS);
    }

    @Override
    public List<TCaseCurrentStepVO> getCurrentSteps(CaseStepReq.CaseStepRecordListReq req) {
        List<TCaseCurrentStepVO> currentSteps = tCaseStepDao.getCurrentSteps(req.getCaseId(), req.getType());

        //当案件进入执行步骤，诉讼步骤展示最后一次更新的数据
        TCase tCase = tCaseDao.selectById(req.getCaseId());
        if("1".equals(req.getType()) && CaseEnum.MainStatus.BEING_EXECUTE.getKey().equals(tCase.getMainStatus())) {
            TCaseStep tCaseStep = tCaseStepDao.getLastLitigationStep("1", req.getCaseId());
            currentSteps.get(0).setStepName(tCaseStep.getName());
        }

        return currentSteps;
    }

    @Override
    public List<TCaseStepMsgTemplateVO> getAllStepMsg(CaseStepReq.CaseStepRecordListReq req) {

        return tCaseStepDao.getAllStepMsg(req.getType());
    }

    @Override
    public PageInfo getLawsuitManagements(CaseStepReq.StatusCaseReq statusCaseReq) {
        PageHelper.startPage(statusCaseReq.getPage(), statusCaseReq.getPerPage());

        CaseStatusStepDto params = new CaseStatusStepDto();
        BeanUtils.copyProperties(statusCaseReq, params);
        List<TCaseStatusStepVO> caseStatusStepVOS = tCaseStepDao.getLawsuitManagements(params);

        return new PageInfo(caseStatusStepVOS == null ? new ArrayList() : caseStatusStepVOS);
    }

    @Override
    public String getApplyPerson(String caseId) {
        return tCaseStepDao.getApplyPerson(caseId);
    }

    @Override
    public TCaseStep getFirstStep(String type) {
        return tCaseStepDao.getFirstStep(type);
    }

    private Integer changeOrderCaseStep(TCaseStep beforeCaseStep, TCaseStep tCaseStep,
                                        TCaseStep nextCaseStep, String orderFlag, TCaseStep beforeTwoCaseStep) {

        if ("0".equals(orderFlag)) {
            //前一设置移动项
            if (nextCaseStep == null) {
                return null;
            }

            if (beforeCaseStep != null) {
                beforeCaseStep.setNextId(nextCaseStep.getId());
                tCaseStepDao.updateById(beforeCaseStep);
            }
            //后者两个互换nextId以及排序号
            tCaseStep.setNextId(nextCaseStep.getNextId());
            nextCaseStep.setNextId(tCaseStep.getId());

            nextCaseStep.setOrderDesc(tCaseStep.getOrderDesc());
            tCaseStep.setOrderDesc(tCaseStep.getOrderDesc() + 1);

            tCaseStepDao.updateById(nextCaseStep);
        }else {
            if (beforeCaseStep == null) {
                return null;
            }

            beforeCaseStep.setNextId(tCaseStep.getNextId());
            beforeCaseStep.setOrderDesc(tCaseStep.getOrderDesc());

            if (beforeTwoCaseStep != null) {
                beforeTwoCaseStep.setNextId(tCaseStep.getId());
                tCaseStepDao.updateById(beforeTwoCaseStep);
            }

            tCaseStep.setNextId(beforeCaseStep.getId());
            tCaseStep.setOrderDesc(tCaseStep.getOrderDesc() - 1);
            tCaseStepDao.updateById(beforeCaseStep);

        }
        return tCaseStepDao.updateById(tCaseStep);
    }


    private TCaseStep getBeforeOrNextCaseStep(String groupId, Integer orderDesc, String type) {
        TCaseStepCondition tCaseStepCondition = new TCaseStepCondition();
        tCaseStepCondition.setGroupId(Integer.parseInt(groupId));
        tCaseStepCondition.setDeleteFlag(false);
        if ("before".equals(type)) {
            tCaseStepCondition.setOrderDesc(orderDesc - 1);
        }else if ("next".equals(type)) {
            tCaseStepCondition.setOrderDesc(orderDesc + 1);
        }else {
            tCaseStepCondition.setOrderDesc(orderDesc - 2);
        }
        TCaseStep tCaseStepBefore = tCaseStepDao.selectFirst(tCaseStepCondition);

        return tCaseStepBefore;
    }


    private TCaseStepBranch getBeforeOrNextCaseStepBranch(String stepId, Integer branchOrderDesc, String type) {
        TCaseStepBranchCondition tCaseStepBranchCondition = new TCaseStepBranchCondition();
        tCaseStepBranchCondition.setStepId(Integer.parseInt(stepId));
        tCaseStepBranchCondition.setDeleteFlag(false);
        if ("before".equals(type)) {
            tCaseStepBranchCondition.setOrderDesc(branchOrderDesc - 1);
        }else if ("next".equals(type)) {
            tCaseStepBranchCondition.setOrderDesc(branchOrderDesc + 1);
        }else {
            tCaseStepBranchCondition.setOrderDesc(branchOrderDesc - 2);
        }
        TCaseStepBranch tCaseStepBranch = tCaseStepBranchDao.selectFirst(tCaseStepBranchCondition);

        return tCaseStepBranch;
    }


    private void changeOldCaseStepVOS(TCaseStepVO oldCaseStep, List<TCaseStepVO> newCaseStepVOS) {
        List<TCaseStepBranchVO> oldCaseStepBranchVOS = oldCaseStep.getCaseStepBranchVOS();
        oldCaseStep.setHaveBranchFlag(0);

        if (oldCaseStepBranchVOS != null && oldCaseStepBranchVOS.size() > 0) {
            //原来的进行添加
            oldCaseStep.setCaseStepBranchVOS(null);
            newCaseStepVOS.add(oldCaseStep);
            oldCaseStep.setHaveBranchFlag(1);
            //遍历添加子集
            for (TCaseStepBranchVO caseStepBranchVO: oldCaseStepBranchVOS) {
                TCaseStepVO tCaseStepVO = new TCaseStepVO();
                tCaseStepVO.setBranchFlag(1);
                tCaseStepVO.setHaveBranchFlag(0);
                tCaseStepVO.setId(caseStepBranchVO.getStepId());
                tCaseStepVO.setGroupId(oldCaseStep.getGroupId());
                tCaseStepVO.setId(caseStepBranchVO.getBranchId());
                tCaseStepVO.setNextId(caseStepBranchVO.getBranchNextId());
                tCaseStepVO.setNextName(caseStepBranchVO.getBranchNextName());
                tCaseStepVO.setType(caseStepBranchVO.getBranchType());
                tCaseStepVO.setOrderDesc(oldCaseStep.getOrderDesc() + "." + caseStepBranchVO.getBranchOrder());
                tCaseStepVO.setNameDesc(caseStepBranchVO.getBranchNameDesc());
                tCaseStepVO.setName(caseStepBranchVO.getBranchName());
                newCaseStepVOS.add(tCaseStepVO);
            }
        }else {
            newCaseStepVOS.add(oldCaseStep);
        }
    }
}
