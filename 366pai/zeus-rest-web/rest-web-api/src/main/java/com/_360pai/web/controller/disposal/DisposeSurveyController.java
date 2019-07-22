package com._360pai.web.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.account.resp.DisposeProviderResp;
import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.core.facade.disposal.DisposeShowFacade;
import com._360pai.core.facade.disposal.DisposeSurveyFacade;
import com._360pai.core.facade.disposal.req.DisposeSurveyReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-10-26 09:34
 */
@RestController
public class DisposeSurveyController extends AbstractController {

    @Reference(version = "1.0.0")
    private DisposeSurveyFacade disposeSurveyFacade;
    @Reference(version = "1.0.0")
    private DisposeShowFacade disposeShowFacade;
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private DisposeLevelFacade disposeLevelFacade;

    @PostMapping("/confined/survey/listByProviderId")
    public ResponseModel getSurveyByProviderId(@RequestBody DisposeSurveyReq.GetSurveyList req) {
        isAuth();
        req.setProviderId( getProviderId());
        PageInfoResp pageInfoResp = disposeSurveyFacade.getSurveyByProviderId(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @PostMapping("/confined/survey/accessSurvey")
    public ResponseModel accessSurvey(@RequestBody DisposeSurveyReq.GetSurveyList req) {
        isAuth();
        disposeSurveyFacade.accessSurvey(req.getSurveyId(), getProviderId());
        return ResponseModel.succ();
    }

    @GetMapping("/confined/survey/getReportTemplate")
    public ResponseModel getReport() {
        Map<String, Object> report = disposeSurveyFacade.getReportTemplate();
        return ResponseModel.succ(report);
    }

    @PostMapping("/confined/survey/uploadProviderReport")
    public ResponseModel uploadProviderReport(@RequestBody DisposeSurveyReq.UploadReport req) {
        isAuth();
        req.setProviderId(getProviderId());
        disposeSurveyFacade.uploadProviderReport(req);
        return ResponseModel.succ();
    }

    @GetMapping("/confined/survey/firstLevelProvider")
    public ResponseModel firstLevelProvider(Integer activityId) {
        Integer partner = disposeLevelFacade.getFirstLevelPartnerByActivityId(activityId);
        Map<String, Object> resp = new HashMap<>(1);
        resp.put("isExist", null != partner ? "10":"00");
        return ResponseModel.succ(resp);
    }

    private Integer getProviderId() {
        AccountBaseInfo accountBaseInfo    = loadCurLoginAccountInfo();
        DisposeProviderReq.BaseReq baseReq = new DisposeProviderReq.BaseReq();
        baseReq.setPartyId(accountBaseInfo.getPartyPrimaryId());
        DisposeProviderResp.DetailResp disposeProvider = accountFacade.getDisposeProvider(baseReq);
        return disposeProvider.getProvider().getId();
    }

}
