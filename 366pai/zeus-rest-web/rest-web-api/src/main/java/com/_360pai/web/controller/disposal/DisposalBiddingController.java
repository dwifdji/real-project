package com._360pai.web.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.facade.disposal.DisposalBiddingFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.resp.BiddingInfoResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
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

    /**
     * 描述 投标
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/confined/disposal/disposalBidding")
    public ResponseModel disposalBidding(@RequestBody DisposalBiddingReq.AddBiddingReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isDisposer()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_DISPOSAL);
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int resp = disposalBiddingFacade.addBiddingInfo(req);
        return ResponseModel.wrapCount(resp);
    }

    /**
     * 描述 处置服务商个人中心查看投标进度
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:35
     */
    @PostMapping("/confined/disposal/getBiddingProgress")
    public ResponseModel getBiddingProgress(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageInfoResp pageInfoResp = disposalBiddingFacade.findBiddingInfoListPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 描述 处置服务商个人中心查看投标详情
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:35
     */
    @PostMapping("/confined/disposal/getBiddingDetailInfo")
    public ResponseModel getBiddingDetailInfo(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        Assert.notNull(req.getBiddingId(), "biddingId不能为空");
        BiddingInfoResp biddingDetailInfo = disposalBiddingFacade.findBiddingDetailInfo(req);
        return ResponseModel.succ(biddingDetailInfo);
    }


    /**
     * 描述  服务处置 首页 招商情况列表
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:04
     */
    @PostMapping("/open/disposal/getInvestmentInfo")
    public ResponseModel getInvestmentInfo(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        PageInfoResp pageInfoResp = disposalBiddingFacade.findInvestmentInfo(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @PostMapping("/confined/disposal/getBidProgramInfo")
    public ResponseModel getBidProgramInfo(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        Assert.notNull(req.getBiddingId(), "biddingId 不能为空");
        Map<String, Object> detail =
                disposalBiddingFacade.findDisposalProgramDetail(req.getBiddingId());
        return ResponseModel.succ(detail);
    }

    @PostMapping("/confined/disposal/confirmBid")
    public ResponseModel confirmBid(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        Assert.notNull(req.getBiddingId(), "biddingId 不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setSideType(SideType.PLATFORM);
        disposalBiddingFacade.confirmBid(req.getBiddingId(), accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ();
    }

}
