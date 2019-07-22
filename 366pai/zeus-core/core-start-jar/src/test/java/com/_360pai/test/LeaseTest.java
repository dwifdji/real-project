package com._360pai.test;

import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
import com._360pai.core.facade.lease.LeaseAuctionFacade;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.service.applet.TAppletMessageService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigDecimal;

/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class LeaseTest extends TestBase{

    @Resource
    private LeaseAuctionFacade leaseAuctionFacade;




    @Test
    public void getLeadAuditList() {

        LeaseReq.leaseAsset req = new LeaseReq.leaseAsset();
        req.setAccountId(202);
        System.out.printf("返回参数为....."+JSON.toJSONString(leaseAuctionFacade.getLeadAuditList(req)));

    }



    @Test
    public void leadAudit() {

        LeaseReq.leaseAsset req = new LeaseReq.leaseAsset();
        req.setType("1");
        req.setActivityId("1270");
        req.setAssetId("2609");
        req.setAccountId(202);
        System.out.printf("返回参数为....."+JSON.toJSONString(leaseAuctionFacade.leadAudit(req)));

    }



}




