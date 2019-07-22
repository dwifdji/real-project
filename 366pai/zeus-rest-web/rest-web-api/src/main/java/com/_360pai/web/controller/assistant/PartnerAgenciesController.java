package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.assistant.PartnerAgenciesFacade;
import com._360pai.core.facade.assistant.req.PartnerAgencyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: PartnerAgenciesController 金牌商家
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 18:47
 */
@RestController
public class PartnerAgenciesController {

    @Reference(version = "1.0.0")
    private PartnerAgenciesFacade partnerAgenciesFacade;

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 功能描述:
     *
     * @param: 首页金牌商家 默认取前六个
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 9:16
     */
    @GetMapping(value = "/open/partner_agencies")
    public ResponseModel selectPartnerAgency(PartnerAgencyReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = partnerAgenciesFacade.selectPartnerAgenciesList(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述:
     *
     * @param: 首页金牌商家 从平台中选出12个
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 9:16
     */
    @GetMapping(value = "/open/partner_agency_list")
    public ResponseModel partnerAgency(AgencyReq.QueryReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = accountFacade.getPartnerAgencyList(req);
        model.setContent(pageInfo);
        return model;
    }


}
