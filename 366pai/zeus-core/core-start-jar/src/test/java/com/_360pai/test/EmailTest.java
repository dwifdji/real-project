package com._360pai.test;

import com._360pai.core.aspact.EnrollingEmailService;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.common.constants.ServiceMessageEnum;
import com._360pai.core.facade.assistant.CityFacade;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.service.enrolling.EnrollingActivityDataService;
import com._360pai.gateway.common.alisms.AliSmsTemplateEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.alisms.FAliSmsSendReq;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wuchuanqi
 * @create 2018-09-19 16:00
 */
public class EmailTest extends TestBase{

    @Autowired
    private GatewayMqSender gatewayMqSender;



    @Autowired
    private EnrollingEmailService enrollingEmailService;

    @Autowired
    private ExceptionEmailService exceptionEmailService;


    @Autowired
    private EnrollingFacade enrollingFacade;


    @Autowired
    private CityFacade cityFacade;



    @Autowired
    private CommonFacade commonFacade;



    @Autowired
    private EnrollingActivityDataService  enrollingActivityDataService;

    @Autowired
    private com._360pai.core.aspact.ServiceEmailService ServiceEmailService;


    @Test
    public void disposalEmailTest() {
        ServiceEmailService.sendServiceEmail("29", ServiceMessageEnum.DISPOSAL_REQUIREMENT_ADD);
    }

    /**
     *
     *发送邮件Mq测试
     */
    @Test
    public void sendEmail() {


        try {

            int i = 1/0;

        }catch (Exception e){



            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.BREAK_DOWN,ExceptionEmailEnum.MODULE_TYPE.ENROLLING,"测试一遍","[\"wuchaunqi@360pai.com\",\"766248878@qq.com\"]",exceptionEmailService.exceptionToStr(e));

            System.out.print(e);


        }






    }



    /**
     *
     *发送短信Mq测试
     */
    @Test
    public void sendSms() {

        FAliSmsSendReq req = new FAliSmsSendReq();
        req.setPhoneNumber("15651617585");
        req.setPartyId("12");
        req.setTemplateCode(AliSmsTemplateEnums.ACCOUNT_REGISTER_SUCCESS.getCode());

        gatewayMqSender.sendSms(req);

    }




    /**
     *
     *发送短信Mq测试
     */
    @Test
    public void enrollingSendEmail() {

        enrollingEmailService.sendEnrollingApply("DYZS-00001","1");


    }




    /**
     *
     *佣金回调
     */
    @Test
    public void payCommissionCallBack() {

        EnrollingReq.payCommissionCallBack payCommissionCallBack = new EnrollingReq.payCommissionCallBack();
        payCommissionCallBack.setPayStatus(PayResultEnums.PAY_SUCCESS.getCode());
        payCommissionCallBack.setOrderNum("100021");
        payCommissionCallBack.setBusId("153431422900770909");

        enrollingFacade.payCommissionCallBack(payCommissionCallBack);


    }




    /**
     *
     *老数据迁徙
     */
    @Test
    public void oldDataMigration() {
        //债权的 code

       enrollingActivityDataService.oldDataMigration("","6");


    }


    @Test
    public void cityFacade() {


        System.out.println(JSON.toJSONString(cityFacade.getAllCities()));


    }



    @Test
    public void commonFacade() {


        System.out.println(JSON.toJSONString(commonFacade.getAllCities()));


    }


}




