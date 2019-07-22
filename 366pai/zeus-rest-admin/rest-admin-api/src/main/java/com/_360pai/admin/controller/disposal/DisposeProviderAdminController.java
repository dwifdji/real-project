package com._360pai.admin.controller.disposal;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.disposal.DisposeSurveyAdminFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-25 16:01
 */
@Slf4j
@RestController
public class DisposeProviderAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    private DisposeSurveyAdminFacade disposeSurveyAdminFacade;

    @GetMapping("/admin/cityPartner/firstLevelLawOffices")
    public ResponseModel getFirstLevelLawOffices(DisposeProviderReq.GetProviderList req) {
        PageInfoResp cityPartnerList = disposeSurveyAdminFacade.getFirstLevelCityPartnerList(req);
        return ResponseModel.succ(cityPartnerList);
    }

    @GetMapping("/admin/cityPartner/lawOffices")
    public ResponseModel getCityPartner(DisposeProviderReq.GetProviderList req) {
        PageInfoResp cityPartnerList = disposeSurveyAdminFacade.getCityPartnerList(req);
        return ResponseModel.succ(cityPartnerList);
    }

    @PostMapping("/admin/cityPartner/addOrReplaceLawOffices")
    public ResponseModel addOrReplacePartner(@RequestBody DisposeProviderReq.AddProvider req) {
        Integer operatorId = loadCurLoginId();
        int tag = disposeSurveyAdminFacade.addOrReplaceFirstPartner(req.getLevelId(), req.getProviderId(), operatorId);
        return ResponseModel.succ(tag);
    }

    @PostMapping("/admin/cityPartner/removeFirstPartner")
    public ResponseModel removeFirstPartner(@RequestBody DisposeProviderReq.AddProvider req) {
        Integer operatorId = loadCurLoginId();
        int tag = disposeSurveyAdminFacade.removeFirstPartner(req.getLevelId(), operatorId);
        return ResponseModel.succ(tag);
    }

    @PostMapping("/admin/cityPartner/updateContractInfo")
    public ResponseModel updateContractInfo(@RequestBody DisposeProviderReq.UpdateContractInfo req) {
        Integer operatorId = loadCurLoginId();
        req.setOperatorId(operatorId);
        int tag = disposeSurveyAdminFacade.updateContractInfo(req);
        return ResponseModel.succ(tag);
    }

}
