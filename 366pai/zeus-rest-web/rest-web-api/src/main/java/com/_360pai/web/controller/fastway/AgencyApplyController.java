package com._360pai.web.controller.fastway;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.fastway.AgencyApplyFacade;
import com._360pai.core.facade.fastway.req.AgencyApplyReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-11-29 15:36
 */
@RestController
public class AgencyApplyController extends AbstractController {

    @Reference(version = "1.0.0")
    private AgencyApplyFacade agencyApplyFacade;

    @PostMapping("/confined/fastWay/agency/auction/apply")
    public ResponseModel auctionApply(@RequestBody AgencyApplyReq.AuctionApply req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ(agencyApplyFacade.auctionApply(req, accountBaseInfo.getAccountId()));
    }

    @GetMapping("/confined/fastWay/agency/agencyAuthInfo")
    public ResponseModel getAgencyAuthInfoByMobile() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ( agencyApplyFacade.agencyAuthInfo(accountBaseInfo.getAccountId(), accountBaseInfo.getPartyPrimaryId(), accountBaseInfo.getAgencyId()));
    }

    @GetMapping("/open/fastWay/agency/checkAgencyAbbr")
    public ResponseModel checkAgencyAbbr(String abbr) {
        agencyApplyFacade.checkAgencyAbbr(abbr);
        return ResponseModel.succ();
    }
}
