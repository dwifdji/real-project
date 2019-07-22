package com._360pai.gateway.facade;

import com._360pai.gateway.controller.req.dfftpay.FBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberResp;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;

/**
 * 描述：东方付通对外提供的FACADE接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/8 15:26
 */
public interface DfftPayFacade {


    /**
     * 东方付通会员绑定接口
     *
     * @param req 请求参数
     *
     *  code :200000 请求成功码
     *  PayResultEnums.REQUEST_SUCCESS.getCode()
     *
     */
    BindMemberResp bindMember(FBindMemberReq req );

    /**
     * 会员绑定关系查询
     *
     * @param req 请求参数
     *
     * code :101034 请求成功码
     *
     *
     */
    FQueryBindMemberResp queryBindMember(FQueryBindMemberReq req);



    /**
     * 账户余额查询
     *
     * @param req 请求参数
     *
     * "code":101034"   已绑定
     *
     */
    QueryBalanceResp queryBalance (FQueryBindMemberReq req);







}
