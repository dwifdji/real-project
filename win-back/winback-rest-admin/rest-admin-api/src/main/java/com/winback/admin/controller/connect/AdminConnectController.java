package com.winback.admin.controller.connect;


import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.vo.LoginInfo;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.facade.connect.ConnectFacade;
import com.winback.core.facade.connect.req.ConnectReq;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * 描述：聊天室控制类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/24 11:09
 */
@Slf4j
@RestController
public class AdminConnectController extends AbstractController {



    @Reference(version = "1.0.0")
    private ConnectFacade connectFacade;


    /**
     * 获取提示信息
     * @return
     */
    @GetMapping("/confined/connect/getRemindInfo")
    public ResponseModel getComInfo(ConnectReq.sendReq req) {

        if(StringUtils.isBlank(req.getCaseId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return connectFacade.getRemindInfo(req);
    }


    /**
     * 获取历史消息列表
     * @return
     */
    @GetMapping("/confined/connect/getHistoryMsgList")
    public ResponseModel getHistoryMsgList(ConnectReq.sendReq req) {

        if(StringUtils.isBlank(req.getCaseId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }


        req.setAccountId(String.valueOf(loadCurLoginId()));
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());

        return connectFacade.getHistoryMsgList(req);
    }


    /**
     * 发送消息记录
     * @return
     */
    @PostMapping("/confined/connect/sendMsg")
    public ResponseModel sendMsg(@RequestBody ConnectReq.sendReq req) {

        if(StringUtils.isBlank(req.getCaseId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        LoginInfo info = loadCurLoginInfo();

        req.setAccountId(String.valueOf(info.getId()));
        req.setSource(PushEnum.SOURCE.ADMIN.getType());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());

        return connectFacade.sendMsg(req);
    }


    /**
     * 发送消息记录
     * @return
     */
    @GetMapping("/confined/connect/getRoomMsgList")
    public ResponseModel getRoomMsgList(ConnectReq.sendReq req) {


        return connectFacade.getRoomMsgList(req);
    }


    /**
     * 发送消息记录
     * @return
     */
    @GetMapping("/confined/connect/getRoomMsgHistoryList")
    public ResponseModel getRoomMsgHistoryList(ConnectReq.sendReq req) {
        LoginInfo info = loadCurLoginInfo();

        if(StringUtils.isBlank(req.getRoomId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setAdminAccountId(String.valueOf(info.getId()));
        return connectFacade.getRoomMsgHistoryList(req);
    }


    /**
     * 后端点开之后进行信息发送
     * @param req
     * @return
     */
    @PostMapping("/confined/connect/sendAdminRoomMsg")
    public ResponseModel sendAdminRoomMsg(@RequestBody ConnectReq.sendReq req) {
        LoginInfo info = loadCurLoginInfo();

        req.setAccountId(String.valueOf(info.getId()));
        req.setSource(PushEnum.SOURCE.ADMIN.getType());
        req.setPersonType(PushEnum.PUSH_PERSON_TYPE.SERVICE.getType());
        req.setRoomFlag("1");

        return connectFacade.sendAdminRoomMsg(req);
    }

    /**
     *
     * 回复之后关闭状态修改为已回复
     *
     */
    @PostMapping("/confined/connect/updateReplyStatus")
    public ResponseModel updateReplyStatus(@RequestBody ConnectReq.sendReq req) {
        if(StringUtils.isBlank(req.getRoomId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        LoginInfo info = loadCurLoginInfo();

        req.setAccountId(String.valueOf(info.getId()));
        return connectFacade.updateReplyStatus(req);
    }
}
