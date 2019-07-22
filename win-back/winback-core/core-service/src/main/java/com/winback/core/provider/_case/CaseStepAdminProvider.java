package com.winback.core.provider._case;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dto._case.CaseStepDto;
import com.winback.core.facade._case.CaseStepAdminFacade;
import com.winback.core.facade._case.req.CaseStepReq;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseStepNoticeTemplate;
import com.winback.core.service._case.CaseService;
import com.winback.core.service._case.TCaseStepService;
import com.winback.core.service.assistant.PushMessageService;
import com.winback.core.vo._case.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * create by liuhaolei on 2019-01-17
 * 诉讼以及执行步骤后台管理生产者
 */
@Component
@Service(version = "1.0.0")
public class CaseStepAdminProvider implements CaseStepAdminFacade {
    @Autowired
    private TCaseStepService tCaseStepService;

    @Autowired
    private TCaseDao caseDao;


    @Override
    public ResponseModel getCaseSteps(CaseStepReq.CaseStepListReq caseStepListReq) {
        //后续分页预留
        PageInfo pageInfo = tCaseStepService.getCaseSteps(caseStepListReq);

        return ResponseModel.succ(pageInfo.getList());
    }

    @Override
    public ResponseModel saveCaseStep(CaseStepReq.SaveCaseStepReq saveCaseStepReq) {
        Integer count = tCaseStepService.saveCaseStep(saveCaseStepReq);

        if (count != null) {
            tCaseStepService.updateNextIds(saveCaseStepReq.getType(), saveCaseStepReq.getStepId());
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateCaseStep(CaseStepReq.UpdateOrDeleteReq req) {
        Integer count = tCaseStepService.updateCaseStep(req);

        if (count != null) {
            tCaseStepService.updateNextIds(req.getType(), req.getId());
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel deleteCaseStep(CaseStepReq.UpdateOrDeleteReq req) {
        Integer count = tCaseStepService.deleteCaseStep(req);
        if (count != null) {
            tCaseStepService.updateNextIds(req.getType(), req.getId());
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateCaseStepBranch(CaseStepReq.UpdateBranchReq req) {
        Integer count = tCaseStepService.updateCaseStepBranch(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel saveStepTemplate(CaseStepReq.StepTemplateSaveReq req) {

        Integer count = tCaseStepService.saveStepTemplate(req);

        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteStepTemplate(CaseStepReq.StepTemplateUpdateReq req) {
        Integer count = tCaseStepService.updateOrDeleteStepTemplate(req);
        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getTemplateByStepId(CaseStepReq.CaseTemplateReq req) {

        List<TCaseStepNoticeTemplateVO> tCaseStepNoticeTemplateVOS = tCaseStepService.getTemplateByStepId(req);

        return ResponseModel.succ(tCaseStepNoticeTemplateVOS);
    }

    @Override
    public ResponseModel getCaseStepById(CaseStepReq.CaseStepListReq caseStepListReq) {

        TCaseStepDetailVO tCaseStepDetailVO = tCaseStepService.getCaseStepById(caseStepListReq.getId());
        if (tCaseStepDetailVO.getCaseBranchList() != null && tCaseStepDetailVO.getCaseBranchList().size() > 0) {
            tCaseStepDetailVO.setHaveBranchFlag(1);
        }else {
            tCaseStepDetailVO.setHaveBranchFlag(0);
        }
        return ResponseModel.succ(tCaseStepDetailVO);
    }

    @Override
    public ResponseModel getCaseStepNotBranch(CaseStepReq.CaseStepListReq req) {
        List<TCaseStepSelectVO> caseStepSelectVOS =  tCaseStepService.getCaseStepNotBranch(req);
        return ResponseModel.succ(caseStepSelectVOS);
    }

    @Override
    public ResponseModel saveCaseStepRecord(CaseStepReq.CaseStepRecordSaveReq req) {

        Integer id = tCaseStepService.saveCaseStepRecord(req);
        if (id != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }



    @Override
    public ResponseModel getCaseStepRecordList(CaseStepReq.CaseStepRecordListReq req) {
        PageInfo pageInfo = tCaseStepService.getCaseStepRecordList(req);

        PageInfoResp pageInfoResp1 = new PageInfoResp(pageInfo);
        return ResponseModel.succ(pageInfoResp1);
    }


    @Override
    public ResponseModel getCurrentStepMsg(CaseStepReq.CaseStepRecordListReq req) {
        List<TCaseCurrentStepVO> tCaseCurrentStepVOS = tCaseStepService.getCurrentSteps(req);

        List<TCaseStepMsgTemplateVO> allStepMsg = tCaseStepService.getAllStepMsg(req);

        String person = tCaseStepService.getApplyPerson(req.getCaseId());
        if(person.startsWith("-")) {
            person = person.substring(1, person.length());
        }

        TCaseCurrentStepVO tCaseCurrentStepVO = new TCaseCurrentStepVO();
        if(tCaseCurrentStepVOS != null && tCaseCurrentStepVOS.size() > 0) {
            tCaseCurrentStepVO = tCaseCurrentStepVOS.get(0);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("allStepMsg", allStepMsg);
        result.put("currentTypes", tCaseCurrentStepVOS);
        result.put("applyPerson", person);
        result.put("currentStepName", tCaseCurrentStepVO.getStepName());

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getLawsuitManagements(CaseStepReq.StatusCaseReq statusCaseReq) {
        //状态转换
        if("1".equals(statusCaseReq.getType())){
            statusCaseReq.setType(CaseEnum.CaseStep.BEING_LAWSUIT.getKey());
        }else {
            statusCaseReq.setType(CaseEnum.CaseStep.BEING_EXECUTE.getKey());
        }

        PageInfo pageInfo = tCaseStepService.getLawsuitManagements(statusCaseReq);

        PageInfoResp pageInfoResp = new PageInfoResp(pageInfo);
        return ResponseModel.succ(pageInfoResp);
    }

}
