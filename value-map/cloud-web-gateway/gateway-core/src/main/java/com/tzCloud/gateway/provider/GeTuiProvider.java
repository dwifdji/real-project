package com.tzCloud.gateway.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.gateway.service.check.QiChaChaService;
import com.tzCloud.gateway.service.push.GeTuiService;
import com.tzCloud.gateway.controller.req.push.SinglePushReq;
import com.tzCloud.gateway.facade.GeTuiFacade;
import com.tzCloud.gateway.facade.QiChaChaFacade;
import com.tzCloud.gateway.resp.risk.RiskComInfoResp;
import com.tzCloud.gateway.resp.risk.RiskInvestmentResp;
import com.tzCloud.gateway.service.push.GeTuiService;
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
}
