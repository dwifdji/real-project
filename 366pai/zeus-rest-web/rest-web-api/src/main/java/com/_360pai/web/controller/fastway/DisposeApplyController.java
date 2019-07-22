package com._360pai.web.controller.fastway;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.fastway.DisposeApplyFacade;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-11-26 13:48
 */
@RestController
public class DisposeApplyController extends AbstractController {

    @Reference(version = "1.0.0")
    private DisposeApplyFacade disposeApplyFacade;

    @PostMapping("/confined/fastWay/dispose/lawyer/apply")
    public ResponseModel lawyerApply (@RequestBody DisposeApplyReq.LawyerApplyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int id = disposeApplyFacade.lawyerApplyDispose(req, accountBaseInfo.getAccountId());
        return ResponseModel.succ(id);
    }

    @PostMapping("/confined/fastWay/dispose/lawOffice/apply")
    public ResponseModel lawOfficeApply (@RequestBody DisposeApplyReq.LawOfficeApplyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        int id = disposeApplyFacade.lawOfficeApplyDispose(req, accountBaseInfo.getAccountId());
        return ResponseModel.succ(id);
    }

    @PostMapping("/confined/fastWay/dispose/doc/powerOfAttorney")
    public ResponseModel sendDocByEmail(@RequestBody DisposeApplyReq.DisposeFindReq req) {
        return ResponseModel.succ(disposeApplyFacade.sendDocByEmail(req.getEmailAddress())) ;
    }

    @GetMapping("/confined/fastWay/dispose/lawyerAuthInfo")
    public ResponseModel getLawyerAuthInfoByMobile(String mobile) {
        return ResponseModel.succ( disposeApplyFacade.getLawyerAuthInfoByMobile(mobile));
    }

    @GetMapping("/confined/fastWay/dispose/lawOfficeAuthInfo")
    public ResponseModel getLawOfficeAuthInfoByMobile(String mobile) {
        return ResponseModel.succ(disposeApplyFacade.getLawOfficeAuthInfoByMobile(mobile));
    }


}
