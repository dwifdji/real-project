package com.winback.test;

import com.gexin.fastjson.JSON;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.facade.connect.ConnectFacade;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.service.connect.ConnectService;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class PushTest extends TestBase{

    @Resource
    private ConnectService connectService;

    @Resource
    private ConnectFacade connectFacade;

    /**
     * 创建聊天房间
     *
     */
    @Test
    public void createRoom() {

        System.out.println("返回参数为....."+JSON.toJSONString(connectService.createRoom(1)));
    }



    /**
     * 创建聊天房间
     *
     */
    @Test
    public void getRemindInfo() {

        ConnectReq.sendReq req = new ConnectReq.sendReq();
        req.setCaseId("3");

        System.out.println("返回参数为....."+JSON.toJSONString(connectService.getRemindInfo(req)));
    }




    /**
     * 发送消息
     *
     */
    @Test
    public void sendMsg() {

        ConnectReq.sendReq req = new ConnectReq.sendReq();
        req.setCaseId("3");
        req.setAccountId("1");
        req.setSource(PushEnum.SOURCE.APP.getType());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.PARTY.getType());
        req.setMsgInfo("推送信息测试11");
        System.out.println("返回参数为....."+JSON.toJSONString(connectService.sendMsg(req)));
    }




    /**
     * 获取消息记录
     *
     */
    @Test
    public void getHistoryMsgList() {

        ConnectReq.sendReq req = new ConnectReq.sendReq();
        req.setCaseId("3");

        System.out.println("返回参数为....."+JSON.toJSONString(connectService.getHistoryMsgList(req)));
    }



    /**
     * 获取消息记录
     *
     */
    @Test
    public void getHistoryMsgList11() {

        ConnectReq.sendReq req = new ConnectReq.sendReq();
        req.setCaseId("3");
        req.setAccountId("5");
        System.out.println("返回参数为....."+JSON.toJSONString(connectFacade.getHistoryMsgList(req)));
    }





}




