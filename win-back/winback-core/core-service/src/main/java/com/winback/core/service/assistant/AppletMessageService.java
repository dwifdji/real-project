package com.winback.core.service.assistant;

import com.winback.arch.common.AppletReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.vo.AppletMessage;
import com.winback.core.model.applet.TAppletMessage;

/**
 * @author xdrodger
 * @Title: AppletMessageService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/12 15:33
 */
public interface AppletMessageService {

    int getUnreadMessageCount(Integer extBindId);

    PageInfoResp<AppletMessage> getAppletMessageList(AppletAccountReq.MessageReq req);

    AppletMessage getAppletMessage(AppletAccountReq.MessageReq req);

    void save(TAppletMessage message);
}
