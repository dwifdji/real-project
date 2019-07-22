package com._360pai.core.provider;

import com._360pai.core.model.DfftPay.BindMemberReq;
import com._360pai.core.model.DfftPay.QueryBindMemberReq;
import com._360pai.core.service.DfftPayService;
import com._360pai.gateway.controller.req.dfftpay.FBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberResp;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述：东方付通 Facade 接口实现
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/7 15:44
 */
@Component
@Service(version = "1.0.0")
public class DfftPayProvider implements DfftPayFacade {


    @Autowired
    private DfftPayService dfftPayService;

    @Override
    public BindMemberResp bindMember(FBindMemberReq req) {

        BindMemberReq bindReq = new BindMemberReq();

        BeanUtils.copyProperties(req, bindReq);

        return dfftPayService.bindMember(bindReq);

    }

    @Override
    public FQueryBindMemberResp queryBindMember(FQueryBindMemberReq req) {

        FQueryBindMemberResp resp = new FQueryBindMemberResp();

        QueryBindMemberReq queryReq = new QueryBindMemberReq();

        BeanUtils.copyProperties(req, queryReq);

        BeanUtils.copyProperties(dfftPayService.queryBindMember(queryReq), resp);

        return  resp;


    }

    @Override
    public QueryBalanceResp queryBalance (FQueryBindMemberReq req) {

        QueryBindMemberReq balanceReq = new QueryBindMemberReq();

        balanceReq.setMemCode(req.getMemCode());

        QueryBalanceResp balanceResp =dfftPayService.queryBalance(balanceReq);

        return  balanceResp;

    }



}
