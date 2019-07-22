package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.disposal.DisposalBiddingFacade;
import com._360pai.core.facade.disposal.DisposalRequirementFacade;
import com._360pai.core.facade.disposal.DisposeSurveyAdminFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.finance.ServiceAccountMoneyFacade;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.core.facade.withfudig.WithfudigRequirementFacade;
import com._360pai.core.facade.withfudig.WithfudigRequirementInvestFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.core.provider.order.ServiceOrderProvider;
import com._360pai.core.service.assistant.TServiceFollowService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-19 16:00
 */
public class DisposalTest extends TestBase{

    @Autowired
    private DisposalBiddingFacade disposalBiddingFacade;
    @Autowired
    private DisposalRequirementFacade disposalRequirementFacade;
    @Autowired
    private ServiceOrderFacade serviceOrderFacade;
    @Autowired
    private DisposeSurveyAdminFacade disposeSurveyAdminFacade;
    @Autowired
    private ServiceAccountMoneyFacade serviceAccountMoneyFacade;
    @Autowired
    private WithfudigRequirementFacade withfudigRequirementFacade;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TServiceFollowService tServiceFollowService;

    @Test
    public void testBind() {
        tServiceFollowService.partyIdBind(363,355);
    }

    @Test
    public void testMqSender() {
        try {
            long timeout = 60;
            mqSender.compradorPayExpiredEnqueue("19", timeout);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void requirementDetailTest() {
        WithfudigRequirementReq.RequirementDetailReq req = new WithfudigRequirementReq.RequirementDetailReq();
        req.setAccountId(7);
        req.setPartyId(63);
        req.setRequirementId(3);
        WithfudigRequirementDetailResp resp = withfudigRequirementFacade.requirementDetail(req, false);
        System.out.println("|||" + JSON.toJSONString(resp));
    }

    @Test
    public void findDisposalFollowListPageTest() {
        DisposalRequirementReq.GetPublishInfoReq req = new DisposalRequirementReq.GetPublishInfoReq();
        req.setOrderBy("period_desc");
        req.setCityId("2");
        req.setPartyId(266);
        PageInfoResp disposalFollowListPage = disposalRequirementFacade.findDisposalFollowListPage(req);
        System.out.println("|||"+ JSON.toJSONString(disposalFollowListPage));
    }

    @Test
    public void findSimilarBidListPageTest() {
        DisposalBiddingReq.GetBiddingInfoReq req = new DisposalBiddingReq.GetBiddingInfoReq();
        req.setDisposalType("10");
        PageInfoResp similarBidListPage = disposalRequirementFacade.findSimilarBidListPage(req);
        System.out.println("|||"+JSON.toJSONString(similarBidListPage));
    }

    @Test
    public void findInvestmentInfoTest() {
        DisposalBiddingReq.GetBiddingInfoReq  req = new DisposalBiddingReq.GetBiddingInfoReq();
        req.setPage(1);
        req.setPerPage(20);
        PageInfoResp investmentInfo = disposalBiddingFacade.findInvestmentInfo(req);
        System.out.printf("===" + JSON.toJSONString(investmentInfo));
    }

    @Test
    public void findDisposalRequirementDetailTest() {
        Map<String, Object> detail = disposalRequirementFacade.findDisposalRequirementDetail(9,  0, 1);
        System.out.printf("|||" + detail.toString());
    }

    @Test
    public void confirmBidTest() {
        disposalBiddingFacade.confirmBid(5,1);
    }

    @Test
    public void queryStatusTest() {
        ServiceOrderReq.QueryStatus req = new ServiceOrderReq.QueryStatus();
        req.setServiceOrderId(287);
        ServiceOrderStatusResp statusResp = serviceOrderFacade.queryStatus(req);
        System.out.printf("|||" + JSON.toJSONString(statusResp));
    }

    @Test
    public void getCityPartnerListTest() {
        DisposeProviderReq.GetProviderList req = new DisposeProviderReq.GetProviderList();
//        req.setProvinceId(3);
//        req.setCityId(3);
        req.setSurveyType("10");
        req.setCompanyName("徐测");
//        req.setProviderId(null);
        PageInfoResp cityPartnerList = disposeSurveyAdminFacade.getCityPartnerList(req);
        System.out.println("|||" + JSON.toJSONString(cityPartnerList));
    }

    @Test
    public void addOrReplaceFirstPartnerTest() {
        disposeSurveyAdminFacade.addOrReplaceFirstPartner(249, 253, 1);
    }

    @Test
    public void removeFirstPartnerTest() {
        disposeSurveyAdminFacade.removeFirstPartner(253, 1);
    }

    @Test
    public void getAccountMoneyByPartyId() {
        serviceAccountMoneyFacade.getAccountMoneyByPartyId(468);
    }
}
