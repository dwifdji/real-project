package com.winback.core.provider.systemsite;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.facade.systemsite.SystemSiteFacade;
import com.winback.core.facade.systemsite.req.SystemSiteReq;
import com.winback.core.service.systemsite.CaseSettingService;
import com.winback.core.service.systemsite.CaseStatusMsgTemplateService;
import com.winback.core.vo.systemsite.CaseMainStatusVO;
import com.winback.core.vo.systemsite.CaseSiteVO;
import com.winback.core.vo.systemsite.CaseStatusMsgVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * create by liuhaolei on 2019-1-23
 * 系统设置生产者
 */
@Component
@Service(version = "1.0.0")
public class SystemSiteProvider implements SystemSiteFacade {
    @Autowired
    private CaseSettingService caseSettingService;
    @Autowired
    private CaseStatusMsgTemplateService caseStatusMsgTemplateService;

    @Override
    public ResponseModel saveCaseSetting(SystemSiteReq.SaveCaseSiteReq req) {
        Integer count = caseSettingService.saveCaseSetting(req);

        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteCaseSetting(SystemSiteReq.UpdateCaseSiteReq req) {
        Integer count = caseSettingService.updateOrDeleteCaseSetting(req);

        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getCaseSettingList(SystemSiteReq.CaseSiteListReq req) {
        PageInfo pageInfo = caseSettingService.getCaseSettingList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);

    }

    @Override
    public ResponseModel saveCaseStatusMsg(SystemSiteReq.CaseStatusMsgSaveReq req) {
        Integer count = caseStatusMsgTemplateService.saveCaseStatusMsg(req);

        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel updateOrDeleteCaseStatusMsg(SystemSiteReq.CaseStatusMsgUpdateeReq req) {
        Integer count = caseStatusMsgTemplateService.updateOrDeleteCaseStatusMsg(req);

        if (count != null) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    @Override
    public ResponseModel getCaseStatusMsgList(SystemSiteReq.CaseStatusListReq req) {

        PageInfo pageInfo = caseStatusMsgTemplateService.getCaseStatusMsgList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);
        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel getCaseSettingById(SystemSiteReq.CaseSiteListReq req) {
        CaseSiteVO caseSiteVO = caseSettingService.getCaseSettingById(req);

        return ResponseModel.succ(caseSiteVO);
    }

    @Override
    public ResponseModel getCaseStatusMsgById(SystemSiteReq.CaseStatusListReq req) {
       CaseStatusMsgVO caseStatusMsgVO = caseStatusMsgTemplateService.getCaseStatusMsgById(req);

        return ResponseModel.succ(caseStatusMsgVO);
    }

    @Override
    public ResponseModel getCaseMainStatusList() {
        CaseEnum.MainStatus[] values = CaseEnum.MainStatus.values();
        List<CaseMainStatusVO> caseMainStatusVOS = new ArrayList<>();
        for (CaseEnum.MainStatus mainStatus : values) {
            CaseMainStatusVO caseMainStatusVO = new CaseMainStatusVO();
            caseMainStatusVO.setKey(mainStatus.getKey());
            caseMainStatusVO.setValue(mainStatus.getValue());
            caseMainStatusVOS.add(caseMainStatusVO);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("list", caseMainStatusVOS);
        return ResponseModel.succ(result);
}
}
