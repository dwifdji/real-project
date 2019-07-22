
package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.aspact.ServiceEmailService;
import com._360pai.core.common.constants.ServiceMessageEnum;
import com._360pai.core.dao.account.TCompanyApplyRecordDao;
import com._360pai.core.facade.disposal.DisposalAdminFacade;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.fastway.DisposeApplyAdminFacade;
import com._360pai.core.facade.fastway.DisposeApplyFacade;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeLawOfficeVO;
import com._360pai.core.facade.fastway.resp.DisposeLawyerVO;
import com._360pai.core.model.account.TCompanyApplyRecord;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.disposal.DisposalBiddingService;
import com._360pai.core.service.fastway.DisposeApplyService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 12:47
 */
public class FastwayTest extends TestBase{

    @Reference(version = "1.0.0")
    private DisposeApplyFacade disposeApplyFacade;
    @Reference(version = "1.0.0")
    private DisposeApplyAdminFacade disposeApplyAdminFacade;
    @Autowired
    private DisposalBiddingService disposalBiddingService;
    @Autowired
    private ServiceEmailService serviceEmailService;
    @Autowired
    private DisposalAdminFacade disposalAdminFacade;
    @Autowired
    private AccountBusinessService accountBusinessService;
    @Autowired
    private TCompanyApplyRecordDao tCompanyApplyRecordDao;
    @Autowired
    private DisposeApplyService disposeApplyService;

    @Test
    public void companyUpdate() {
        TDisposeProvider provider = new TDisposeProvider();
        provider.setBusinessOperatorId(1);
        provider.setOpenAccountOperatorId(1);
        System.out.println("s = " + provider);
        accountBusinessService.updateBusinessInfo(349, JSONObject.parseObject(JSON.toJSONString(provider)), AccountBusinessService.BusinessType.dispose);
    }

    @Test
    public void findBiddingInfoListTest() {
        DisposalRequirementReq.GetBiddingList req = new DisposalRequirementReq.GetBiddingList();
        req.setDisposalId(72);
        PageInfoResp biddingInfoList = disposalAdminFacade.findBiddingInfoList(req);
        System.out.println("biddingInfoList = " + JSON.toJSONString(biddingInfoList));
    }

    @Test
    public void sendServiceEmailTest() {
        serviceEmailService.sendServiceEmail("42", ServiceMessageEnum.FASTWAY_DISPOSE_APPLY);
    }

    @Test
    public void findDisposalProgramDetailTest() {
        Map<String, Object> disposalProgramDetail = disposalBiddingService.findDisposalProgramDetail(5);
        System.out.println("disposalProgramDetail = " + JSON.toJSONString(disposalProgramDetail));
    }

    @Test
    public void lawyOfficeVerifyTest() {
        DisposeApplyReq.DisposeApplyVerify req = new DisposeApplyReq.DisposeApplyVerify();
        req.setApplyId(34);
        req.setApplyStatus("20");
        req.setOpenAccountOperatorId(52);
        req.setBusinessOperatorId(31);
        int count = disposeApplyAdminFacade.lawOfficeVerify(req, 1);
        System.out.println("count = " + count);
    }

    @Test
    public void lawyerVerifyTest() {
        DisposeApplyReq.DisposeApplyVerify req = new DisposeApplyReq.DisposeApplyVerify();
        req.setApplyId(62);
        req.setApplyStatus("20");
        req.setOpenAccountOperatorId(52);
        req.setBusinessOperatorId(31);
        int count = disposeApplyAdminFacade.lawyerVerify(req, 1);
        System.out.println("count = " + count);
    }

    @Test
    public void findLawOfficeDetailByIdTest() {
        DisposeLawOfficeVO detail = disposeApplyAdminFacade.findLawOfficeDetailById(14);
        System.out.println("detail = " + detail);
    }


    @Test
    public void findLawyerDetailByIdTest() {
        DisposeLawyerVO detail = disposeApplyAdminFacade.findLawyerDetailById(7);
        System.out.println("detail = " + detail);
    }

    @Test
    public void findDisposeApplyByParamTest() {
        DisposeApplyReq.DisposeFindReq req = new DisposeApplyReq.DisposeFindReq();
        PageInfoResp disposeApplyByParam = disposeApplyAdminFacade.findDisposeApplyByParam(req);
        System.out.println("disposeApplyByParam = "+ JSON.toJSONString(disposeApplyByParam));
    }


    @Test
    public void lawyerApplyDisposeTest() {
        DisposeApplyReq.LawyerApplyReq lawyer = new DisposeApplyReq.LawyerApplyReq();
        lawyer.setCardImg1("http://img.1");
        lawyer.setSource("10");
        int i = disposeApplyFacade.lawyerApplyDispose(lawyer, 1);
        System.out.println("i = " + i);
    }

    @Test
    public void lawOfficeApplyDisposeTest() {
        DisposeApplyReq.LawOfficeApplyReq lawOffice = new DisposeApplyReq.LawOfficeApplyReq();
        lawOffice.setAdminAuthFile("http://img.1");
        lawOffice.setSource("10");
        int i = disposeApplyFacade.lawOfficeApplyDispose(lawOffice, 1);
        System.out.println("i = " + i);
    }

    @Test
    public void getLawyerAuthInfoByMobileTest() {
        Map<String, Object> lawyerAuthInfoByMobile = disposeApplyService.getLawyerAuthInfoByMobile("17612150211");
        System.out.println("lawyerAuthInfoByMobile = " + lawyerAuthInfoByMobile);
    }
}
