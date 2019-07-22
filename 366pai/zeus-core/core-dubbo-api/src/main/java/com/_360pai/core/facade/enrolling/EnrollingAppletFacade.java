package com._360pai.core.facade.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.shop.req.ShopEnrollingReq;

/**
 * 描述：小程序预招商Facade接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:11
 */
public interface EnrollingAppletFacade {

    /**
     *
     *获取招商详情
     * @param  req
     */
    ResponseModel getEnrollingDetail(ShopEnrollingReq.comReq req);




    /**
     *
     * 获取报名列表
     * @param  req
     */
    ResponseModel getEnrollingApplyList(ShopEnrollingReq.comReq req);



    /**
     *
     * 获取公告等信息
     * @param  req
     */
    ResponseModel getEnrollingAnnouncement(ShopEnrollingReq.comReq req);




    /**
     *
     * 判断是否用人关注过该预招商
     * @param  req
     */
    ResponseModel getEnrollingConcernedFlag(ShopEnrollingReq.comReq req);

}
