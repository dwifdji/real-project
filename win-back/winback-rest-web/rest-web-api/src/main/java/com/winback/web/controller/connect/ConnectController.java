package com.winback.web.controller.connect;


import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade.connect.ConnectFacade;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.facade.risk.RiskFacade;
import com.winback.core.facade.risk.req.RiskReq;
import com.winback.web.controller.AbstractController;
import com.winback.web.vo.LoginInfo;
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
public class ConnectController extends AbstractController {



    @Reference(version = "1.0.0")
    private ConnectFacade connectFacade;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;

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

        LoginInfo info = loadCurLoginInfo();

        req.setAccountId(String.valueOf(info.getId()));
        req.setPersonType(info.isLawyerFlag()?PushEnum.PUSH_PERSON_TYPE.LAWYER.getType():PushEnum.PUSH_PERSON_TYPE.PARTY.getType());


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
        req.setSource(PushEnum.SOURCE.APP.getType());
        req.setPersonType(info.isLawyerFlag()?PushEnum.PUSH_PERSON_TYPE.LAWYER.getType():PushEnum.PUSH_PERSON_TYPE.PARTY.getType());
        return connectFacade.sendMsg(req);
    }


    /**
     * 创建房间并创建房间用户
     * @return
     */
    @PostMapping("/confined/connect/sendRoomMsg")
    public ResponseModel sendRoomMsg(@RequestBody ConnectReq.sendReq req) {
        LoginInfo info = loadCurLoginInfo();
        if(info == null || info.getId() == null) {
            return ResponseModel.fail(ApiCallResult.NOT_LOGIN.getDesc());
        }

        req.setAccountId(String.valueOf(info.getId()));
        String partyName = info.getLawyer() == null ? info.getNickName() : info.getLawyer().getName();
        req.setPartyName(partyName);
        req.setSource(PushEnum.SOURCE.APP.getType());
        String personType;

        personType = info.isLawyerFlag()?PushEnum.PUSH_PERSON_TYPE.LAWYER.getType():PushEnum.PUSH_PERSON_TYPE.PARTY.getType();

        req.setPersonType(personType);
        return connectFacade.sendRoomMsg(req);
    }


    /**
     * 获取历史消息列表
     * @return
     */
    @GetMapping("/confined/connect/getWebRoomHistoryMsgList")
    public ResponseModel getWebRoomHistoryMsgList(ConnectReq.sendReq req) {

        LoginInfo info = loadCurLoginInfo();

        req.setAccountId(String.valueOf(info.getId()));
        req.setPersonType(info.isLawyerFlag()?PushEnum.PUSH_PERSON_TYPE.LAWYER.getType():PushEnum.PUSH_PERSON_TYPE.PARTY.getType());


        return connectFacade.getWebRoomHistoryMsgList(req);
    }

}
