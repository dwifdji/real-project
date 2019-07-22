package com._360pai.core.service;

import com._360pai.core.model.DfftPay.*;
import com._360pai.core.model.pay.GatewayPayBackRecord;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;

import java.util.List;

/**
 * 描述：东方付通接口服务接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 14:48
 */
public interface DfftPayService {


    /**
     * 东方付通会员绑定接口
     *
     * @param req 请求参数
     *
     */
    BindMemberResp bindMember(BindMemberReq req );

    /**
     * 会员绑定关系查询
     *
     * @param req 请求参数
     *
     */
    QueryBindMemberResp queryBindMember(QueryBindMemberReq req);


    /**
     * 会员绑定关系查询
     *
     * @param req 请求参数
     *
     */
    QueryBalanceResp queryBalance(QueryBindMemberReq req);


    /**
     * 直接或者锁定支付
     * 01010：直接支付
     * 01021：关闭支付
     *
     * @param req 请求参数
     *
     */
    PayResp directOrLockPay(DirectOrLockPayReq req);



    /**
     * 批量直接支付
     * 01010：直接支付
     *
     * @param req 请求参数
     *
     */
    PayResp BatchDirectOrLockPay(List<DirectOrLockPayReq> req);


    /**
     * 保证金操作接口
     * 03031：保证金锁定
     * 03041：保证金释放
     * 03051：保证金支付
     * 03061：保证金追加
     * @param req 请求参数
     *
     */
    PayResp marginOperation(MarginOperationReq req);

    /**
     * 通道支付 直接打款到银行卡
     * @param req 请求参数
     *
     */
    PayResp channelPay(ChannelPayReq req);


    /**
     * 分润支付
     * @param req 请求参数
     *
     */
    PayResp fenRunPay(FenRunPayReq req);



    /**
     * 订单查询接口
     * @param payId 支付订单号
     *
     */
    CommResp queryOrder(String payId);


    /**
     * 保存回调记录
     * @param
     *
     */
    void saveCallBackInfo(GatewayPayBackRecord req);


    /**
     * 更新回调记录
     * @param
     *
     */
    void updateCallBackInfo(GatewayPayBackRecord req);


    /**
     * 主动查询订单记录
     * @param
     *
     */
     void queryOrderQuartz();
}
