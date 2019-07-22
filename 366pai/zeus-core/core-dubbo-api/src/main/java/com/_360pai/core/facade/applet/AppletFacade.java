package com._360pai.core.facade.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.AppletReq;
import com._360pai.core.facade.applet.req.AssistantReq;
import com._360pai.core.facade.applet.vo.InviteRecord;

/**
 * @author xdrodger
 * @Title: AppletFacade
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:38
 */
public interface AppletFacade {

    PageInfoResp<InviteRecord> getInviteRecordList(AppletReq.InviteRecordReq req);

    ResponseModel getConfigInfo();


    /**
     *
     *保存用户的访问记录
     */
    ResponseModel saveVisitRecord(AssistantReq.comReq req);


    /**
     *
     *获取用户的访问记录列表
     */
    ResponseModel getVisitList(AssistantReq.comReq req);


    /**
     *
     *开店支付
     */
    ResponseModel openShopPay(AssistantReq.comReq req);



    /**
     *
     *开店支付回调处理
     */
    ResponseModel openShopPayCallBack(AssistantReq.payCallBackReq req);






    /**
     * 订单定时器
     * @param
     *
     */
    void queryAppletOrderQuartz();


}
