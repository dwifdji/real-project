package com._360pai.test;

import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.enrolling.EnrollingImportFacade;
import com._360pai.core.facade.enrolling.req.EnrollingImportReq;
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
public class AppletTest extends TestBase{

    @Resource
    private ShopFacade shopFacade;

    @Autowired
    private TAppletMessageService appletMessageService;

    @Resource
    private AppletFacade appletFacade;



    @Resource
    private EnrollingImportFacade enrollingImportFacade;




    @Test
    public void uploadActivity() {

        File file = new File("D:\\长城招商导入-山东-1.xlsx");

        EnrollingImportReq.uploadActivityReq req = new EnrollingImportReq.uploadActivityReq();
        req.setUserId("1");

        req.setFile(file);


        enrollingImportFacade.uploadActivity(req);
    }







    @Test
    public void disposalEmailTest() {
        ShopReq.HomePageReq homePageReq = new ShopReq.HomePageReq();
        homePageReq.setShopId("2");
        homePageReq.setHomePageArray(new String[]{"359","333","358","359","360"});

        shopFacade.setHomePage(homePageReq);
    }



    @Test
    public void createShopTest() {
        try {
            ShopReq.CreateReq req = new ShopReq.CreateReq();
            req.setAmount(BigDecimal.ZERO);
            req.setOpenId("oTWB75QB4xr1uKEG-mdW7AI7DvJs");
            req.setPartyId(300);
            shopFacade.createShop(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createsSendAccountRegisterMessageTest() {
        try {
            appletMessageService.sendAccountRegisterMessage(353);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void saveVisitRecordTest() {
        try {

            AssistantReq.comReq req = new AssistantReq.comReq();
            req.setOpenId("oTWB75YuAD8l3kEG5N0rh04MTKXM");
            req.setShopId("4");
            req.setType("1");
            appletFacade.saveVisitRecord(req);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getVisitList() {
        try {

            AssistantReq.comReq req = new AssistantReq.comReq();
            req.setShopId("4");
            req.setType("1");
            System.out.print(JSON.toJSONString(appletFacade.getVisitList(req)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getShopAuctionList() {

        ShopReq.ShopListReq shopListReq = new ShopReq.ShopListReq();
        shopListReq.setShopId("2");

        System.out.printf(JSON.toJSONString(shopFacade.getShopAuctionList(shopListReq)));

    }




    @Test
    public void getAuctionDetail() {

        AuctionReq.AuctionInfoReq req = new AuctionReq.AuctionInfoReq();
        req.setShopId("8");
        req.setAuctionId("569");
         System.out.printf(JSON.toJSONString(shopFacade.getAuctionDetail(req)));

    }


    @Test
    public void testFavorShop() {

    }
}




