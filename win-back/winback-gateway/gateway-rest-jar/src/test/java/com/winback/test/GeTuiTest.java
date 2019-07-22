package com.winback.test;

import com.gexin.fastjson.JSON;
import com.gexin.fastjson.JSONObject;
import com.winback.arch.common.enums.DeviceType;
import com.winback.gateway.common.constants.PayEnum;
import com.winback.gateway.controller.req.pay.PayReq;
import com.winback.gateway.controller.req.pay.QueryReq;
import com.winback.gateway.controller.req.push.SinglePushReq;
import com.winback.gateway.facade.GeTuiFacade;
import com.winback.gateway.facade.PayFacade;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class GeTuiTest extends TestBase{

    @Resource
    private GeTuiFacade geTuiFacade;

    /**
     *
     *
     */
    @Test
    public void pushMessageToSingle() {


        SinglePushReq req  = new SinglePushReq();

        req.setClientId("4f3b9b3b270ab915edf07e7db44fdb44");
         req.setText("有新案件发布啦，请前往案源库了解详情，点击查看");
        req.setTitle("标题测试");
        req.setChannel(DeviceType.IOS.getKey());


        System.out.println("返回参数为....."+JSON.toJSONString(geTuiFacade.pushMessageToSingle(req)));
    }







    /**
     *
     *
     */
    @Test
    public void pushMessageToAll() {


        SinglePushReq req  = new SinglePushReq();

         req.setText("全体提醒方式推送测试0320");
        req.setTitle("全体提醒方式推送0320");

        req.setMajorKey("1");
        req.setMsgType(2);

        System.out.println("返回参数为....."+JSON.toJSONString(geTuiFacade.pushMessageToAll(req)));
    }





}




