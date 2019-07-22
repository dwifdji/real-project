package com._360pai.admin.controller.fastway;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.fastway.AgencyApplyAdminFacade;
import com._360pai.core.facade.fastway.req.AgencyApplyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-11-29 17:32
 */
@RestController
public class AgencyApplyAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    private AgencyApplyAdminFacade agencyApplyAdminFacade;

    @GetMapping("/admin/fastway/agency/apply/list")
    public ResponseModel agencyApplyList(AgencyApplyReq.AgencyFindReq req) {
        return ResponseModel.succ(agencyApplyAdminFacade.findAgencyApplyByParam(req));
    }

    @GetMapping("/admin/fastway/agency/auction/detail")
    public ResponseModel agencyAuctionDetail(Integer applyId) {
        return ResponseModel.succ(agencyApplyAdminFacade.findAuctionDetailById(applyId));
    }

    @PostMapping("/admin/fastway/agency/auction/modify")
    public ResponseModel auctionUpdate(@RequestBody AgencyApplyReq.AuctionUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(agencyApplyAdminFacade.auctionUpdate(req, operatorId));
    }

    @PostMapping("/admin/fastway/agency/auction/verify/access")
    public ResponseModel auctionVerifyAccess(@RequestBody AgencyApplyReq.AuctionUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(agencyApplyAdminFacade.auctionVerifyAccess(req, operatorId));
    }

    @PostMapping("/admin/fastway/agency/auction/verify/deny")
    public ResponseModel auctionVerifyDeny(@RequestBody AgencyApplyReq.AuctionUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(agencyApplyAdminFacade.auctionVerifyDeny(req, operatorId));
    }

}
