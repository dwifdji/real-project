package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.aspact.ServiceEmailService;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ProtocolTypeConstants;
import com._360pai.core.common.constants.ServiceMessageEnum;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.core.facade.disposal.DisposeShowFacade;
import com._360pai.core.facade.disposal.DisposeSurveyAdminFacade;
import com._360pai.core.facade.disposal.DisposeSurveyFacade;
import com._360pai.core.facade.disposal.req.DisposeSurveyReq;
import com._360pai.core.facade.finance.ServiceBusinessRecordFacade;
import com._360pai.core.facade.finance.req.BusinessRecordReq;
import com._360pai.core.facade.finance.resp.PurchaseHistoryDTO;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.service.asset.AssetAuthorizationService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.math.BigDecimal;
import java.security.Provider;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author xiaolei
 * @create 2018-11-01 13:10
 */
public class Dispose345Test extends TestBase {

    @Autowired
    private DisposeShowFacade disposeShowFacade;
    @Autowired
    private DisposeSurveyFacade disposeSurveyFacade;
    @Autowired
    private DisposeSurveyAdminFacade disposeSurveyAdminFacade;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private ServiceOrderFacade serviceOrderFacade;
    @Autowired
    private AssetFacade assetFacade;
    @Autowired
    private DisposeLevelFacade disposeLevelFacade;
    @Autowired
    private AssetAuthorizationService assetAuthorizationService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private DisposeSurveyService disposeSurveyService;
    @Autowired
    private DisposeLevelService disposeLevelService;
    @Autowired
    private ServiceBusinessRecordFacade serviceBusinessRecordFacade;
    @Autowired
    private ServiceEmailService serviceEmailService;
    @Autowired
    private SmsHelperService smsHelperService;

    @Test
    public void disposalDeadlineEnqueueTest() {
        mqSender.disposalDeadlineEnqueue("64",20);
    }

    @Test
    public void disposalRequirementApplyNotPassNotifyTest() {
        smsHelperService.disposalRequirementApplyNotPassNotify("17612150371", "11111", "尽职调查");
    }

    @Test
    public void withfudigNotplatformInvestTest() {
        serviceEmailService.sendServiceEmail("41", ServiceMessageEnum.DISPOSAL_BIDDING_SUCCESS_TO_OPERATOR);
    }

    @Test
    public void getPurchaseHistoryByBuyerPartyIdTest() {
        BusinessRecordReq.GetBusinessRecord req = new BusinessRecordReq.GetBusinessRecord();
        req.setPartyId(195);
        req.setPage(1);
        req.setPerPage(20);
        PageInfoResp<PurchaseHistoryDTO> purchaseHistoryByBuyerPartyId = serviceBusinessRecordFacade.getPurchaseHistoryByBuyerPartyId(req);
        System.out.println("|||"+JSON.toJSONString(purchaseHistoryByBuyerPartyId));
    }


    @Test
    public void expireTimeTest() {
        String authSource = assetAuthorizationService.getAuthSource(593);
        if (ProtocolTypeConstants.THIRD_AUTH.equals(authSource)) {
            long timeout = 10;
            mqSender.providerSurveyDeadlineEnqueue("TS1811121311376561" + "", timeout);
        }
    }

    @Test
    public void createOrUpdateSurveyReportSaleTest() {
        AssetAuthorizationReq.PreSignTuneAuthProtocol req = new AssetAuthorizationReq.PreSignTuneAuthProtocol();
        assetFacade.preSignTuneAuthProtocol(req);
    }


    @Test
    public void redisDelKeyTest() {
        stringRedisTemplate.opsForValue().set(SystemConstant.PROVIDER_SURVEY_KEY + 3,
                "finish", 10 , TimeUnit.SECONDS);
    }


    @Test
    public void isFirstLevelPartnerTest() {
        boolean firstLevelPartner = disposeLevelFacade.isFirstLevelPartner(null);
        System.out.println(firstLevelPartner);
    }

    @Test
    public void signProtocolTests() {
        String[] reports = {"https://cdn-images.360pai.com/imfv4lvnqo04.doc?attname=尽调文件.doc"};
//        assetAuthorizationService.signProtocol(586,72, "20", null, reports, "200");
    }

    @Test
    public void getReportSourceTest() {
        String reportSource = assetAuthorizationService.getReportSource(586);
        System.out.println("|||"+reportSource);
    }

    @Test
    public void getCityPartnerListTest() {
        DisposeProviderReq.GetProviderList req = new DisposeProviderReq.GetProviderList();
//        req.setCompanyName("wen");
        req.setCityId(74);
        PageInfoResp cityPartnerList = disposeSurveyAdminFacade.getCityPartnerList(req);
        System.out.println("|||"+ JSON.toJSONString(cityPartnerList));
    }

    @Test
    public void getFirstLevelPartnerByActivityIdTest() {
        Integer partner = disposeLevelFacade.getFirstLevelPartnerByActivityId(584);
        System.out.println(partner);
    }

    @Test
    public void uploadSelfReport() {
        String[] reports = {"https://cdn-images.360pai.com/imfv4lvnqo04.doc?attname=尽调文件.doc"};
        AssetReq.UploadReportReq req = new AssetReq.UploadReportReq();
        req.setPartyId(63);
        req.setAssetId(1671);
        req.setPrice(new BigDecimal(100));
        req.setReports(reports);
        assetFacade.uploadSelfReport(req);
    }

    @Test
    public void queryStatus() {
        ServiceOrderReq.QueryStatus req = new ServiceOrderReq.QueryStatus();
        req.setServiceOrderId(83);
        ServiceOrderStatusResp resp = serviceOrderFacade.queryStatus(req);
        System.out.println("|||"+resp);
    }

    @Test
    public void thirdReportPayTest() {
        ServiceOrderReq.AdjustedPay req = new ServiceOrderReq.AdjustedPay();
        req.setAccountId(202);
        req.setPartyId(214);
        req.setActivityId(1050);
        Map map = serviceOrderFacade.thirdReportPay(req);
        System.out.println("|||" + JSON.toJSONString(map));
    }

    @Test
    public void providerShowPayTest() {
        ServiceOrderReq.ProviderShowPay req = new ServiceOrderReq.ProviderShowPay();
        req.setAccountId(7);
        req.setPartyId(63);
        req.setActivityId(581);
        ServiceOrderResp serviceOrderResp = serviceOrderFacade.providerShowPay(req);
        System.out.println("|||" + JSON.toJSONString(serviceOrderResp));
    }

    @Test
    public void getRecommendProviderTest() {
        PageInfoResp recommendProvider = disposeShowFacade.getRecommendProvider(495, 1, 20);
        System.out.println(JSON.toJSONString(recommendProvider));
    }

    @Test
    public void getSurveyByProviderIdTest() {
        DisposeSurveyReq.GetSurveyList req = new DisposeSurveyReq.GetSurveyList();
        req.setProviderId(249);
        PageInfoResp surveyByProviderId = disposeSurveyFacade.getSurveyByProviderId(req);

        System.out.println("|||" + JSON.toJSONString(surveyByProviderId));
    }

    @Test
    public void accessSurveyTest() {
        disposeSurveyFacade.accessSurvey(1,253);
    }

    @Test
    public void getFirstLevelCityPartnerList() {
        DisposeProviderReq.GetProviderList req = new DisposeProviderReq.GetProviderList();
        PageInfoResp firstLevelCityPartnerList = disposeSurveyAdminFacade.getFirstLevelCityPartnerList(req);
        System.out.println("|||"+JSON.toJSONString(firstLevelCityPartnerList));
    }

    @Test
    public void thirdConfirmToOperatorTest() {
        serviceMessageUtils.thirdConfirmToOperator(573);
    }

    @Test
    public void breakContract3ToOperatorTest() {
        serviceMessageUtils.breakContract3ToOperator(3);
    }
}
