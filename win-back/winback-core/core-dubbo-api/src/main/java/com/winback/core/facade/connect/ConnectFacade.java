package com.winback.core.facade.connect;

import com.github.pagehelper.PageInfo;
import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.connect.req.ConnectReq;
import com.winback.core.facade.risk.req.RiskReq;

/**
 * 描述：聊天室
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:32
 */
public interface ConnectFacade {


    /**
     *
     *获取聊天室历史记录信息
     */
    ResponseModel getHistoryMsgList(ConnectReq.sendReq req);


    /**
     *
     *聊天室发送消息
     */
    ResponseModel sendMsg(ConnectReq.sendReq req);


    /**
     *
     *获取聊天室提示信息
     */
    ResponseModel getRemindInfo(ConnectReq.sendReq req);

    /**
     * 发送
     *
     */
    ResponseModel sendRoomMsg(ConnectReq.sendReq req);

    /**
     * 获取列表
     * @param req
     * @return
     */
    ResponseModel getRoomMsgList(ConnectReq.sendReq req);

    /**
     * 获取每个用户咨询历史记录
     * @param req
     * @return
     */
    ResponseModel getRoomMsgHistoryList(ConnectReq.sendReq req);

    /**
     * 后端小房间推送信息
     * @param req
     * @return
     */
    ResponseModel sendAdminRoomMsg(ConnectReq.sendReq req);

    /**
     * 关闭房间之后修改为已回复状态
     * @param req
     * @return
     */
    ResponseModel updateReplyStatus(ConnectReq.sendReq req);


    ResponseModel getWebRoomHistoryMsgList(ConnectReq.sendReq req);
}
