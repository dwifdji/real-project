package com.winback.gateway.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.common.ResponseModel;
import com.winback.gateway.service.check.QiChaChaService;
import com.winback.gateway.service.push.GeTuiService;
import com.winback.gateway.controller.req.push.SinglePushReq;
import com.winback.gateway.facade.GeTuiFacade;
import com.winback.gateway.facade.QiChaChaFacade;
import com.winback.gateway.resp.risk.RiskComInfoResp;
import com.winback.gateway.resp.risk.RiskInvestmentResp;
import com.winback.gateway.service.push.GeTuiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:50
 */
@Component
@Service(version = "1.0.0")
public class GeTuiProvider implements GeTuiFacade {

    @Autowired
    private GeTuiService geTuiService;


    @Override
    public ResponseModel pushMessageToSingle(SinglePushReq req) {
        return geTuiService.pushMessageToSingle(req);
    }

    @Override
    public ResponseModel pushMessageToAll(SinglePushReq req) {
        return geTuiService.pushMessageToAll(req);
    }
}
