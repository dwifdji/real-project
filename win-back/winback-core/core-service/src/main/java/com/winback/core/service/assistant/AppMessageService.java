package com.winback.core.service.assistant;

import com.winback.arch.common.AppReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.vo.AppMessage;

/**
 * @author xdrodger
 * @Title: AppMessageService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 16:17
 */
public interface AppMessageService {
    int getUnreadMessageCount(Integer accountId);

    PageInfoResp<AppMessage> getAppMessageListByPage(AppAccountReq.MessageReq req);

    AppMessage getAppMessage(AppAccountReq.MessageReq req);


    /**
     *
     *获取沟通未读消息
     */
    int getUnreadConnectCount(Integer accountId,Boolean lawyerFlag);
}
