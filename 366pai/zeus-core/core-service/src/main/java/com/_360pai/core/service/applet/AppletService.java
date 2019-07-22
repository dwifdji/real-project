package com._360pai.core.service.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.applet.req.AppletReq;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.vo.AppletVisitVo;
import com._360pai.core.facade.applet.vo.InviteRecord;

/**
 * @author xdrodger
 * @Title: AppletService
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:37
 */
public interface AppletService {
    PageInfoResp<InviteRecord> getInviteRecordList(AppletReq.InviteRecordReq req);


    /**
     *获取访客列表
     */
    PageInfoResp<AppletVisitVo> getVisitList(AssistantReq.comReq req);

    /**
     *保存或者更新访客信息
     */
    int saveOrUpdateViewShop(AssistantReq.comReq req);

}
