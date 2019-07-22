package com.winback.core.service.connect;

import com.github.pagehelper.PageInfo;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.utils.PageUtils;
import com.winback.core.facade.connect.req.ConnectReq;

/**
 * 描述：沟通接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/24 16:15
 */
public interface ConnectService {



    /**
     *
     *为案件创建聊天室
     */
    ResponseModel createRoom(Integer caseId);


    /**
     *
     *获取聊天室历史记录信息
     */
    PageInfo getHistoryMsgList(ConnectReq.sendReq req);


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
     * 创建个人信息并发送数据
     * @param req
     * @return
     */
    Integer sendRoomMsg(ConnectReq.sendReq req);

    /**
     *
     * @param req
     * @return
     */
    PageUtils.Page getRoomMsgList(ConnectReq.sendReq req);


    PageInfo getRoomMsgHistoryList(ConnectReq.sendReq req);

    Integer sendAdminRoomMsg(ConnectReq.sendReq req);

    Integer updateReplyStatus(ConnectReq.sendReq req);

    PageInfo getWebRoomHistoryMsgList(ConnectReq.sendReq req);
}
