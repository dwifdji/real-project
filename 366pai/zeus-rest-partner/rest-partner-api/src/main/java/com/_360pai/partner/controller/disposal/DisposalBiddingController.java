package com._360pai.partner.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.facade.disposal.DisposalBiddingFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-17 10:11
 */
@RestController
public class DisposalBiddingController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisposalBiddingController.class);

    @Reference(version = "1.0.0")
    DisposalBiddingFacade disposalBiddingFacade;

    @PostMapping("/agency/disposal/getBidProgramInfo")
    public ResponseModel getBidProgramInfo(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        Assert.notNull(req.getBiddingId(), "biddingId 不能为空");
        try {
            Map<String, Object> detail =
                    disposalBiddingFacade.findDisposalProgramDetail(req.getBiddingId());
            return ResponseModel.succ(detail);
        } catch (Exception e) {
            LOGGER.error("获取投标方案异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping("/agency/disposal/confirmBid")
    public ResponseModel confirmBid(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        Assert.notNull(req.getBiddingId(), "biddingId 不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setSideType(SideType.AGENCY);
        disposalBiddingFacade.confirmBid(req.getBiddingId(), accountBaseInfo.getAgencyId());
        return ResponseModel.succ();
    }
}
