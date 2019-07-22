package com._360pai.admin.controller.fastway;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.fastway.FundApplyAdminFacade;
import com._360pai.core.facade.fastway.req.FundApplyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-12-07 11:09
 */
@RestController
public class FundApplyAdminController extends AbstractController {
    @Reference(version = "1.0.0")
    private FundApplyAdminFacade fundApplyAdminFacade;

    @GetMapping("/admin/fastway/fund/apply/list")
    public ResponseModel fundApplyList(FundApplyReq.FundFindReq req) {
        return ResponseModel.succ(fundApplyAdminFacade.findFundApplyByParam(req));
    }

    @GetMapping("/admin/fastway/fund/user/detail")
    public ResponseModel fundUserDetail(Integer applyId) {
        return ResponseModel.succ(fundApplyAdminFacade.findUserFundDetailById(applyId));
    }

    @GetMapping("/admin/fastway/fund/company/detail")
    public ResponseModel fundCompanyDetail(Integer applyId) {
        return ResponseModel.succ(fundApplyAdminFacade.findCompanyFundDetailById(applyId));
    }

    @PostMapping("/admin/fastway/fund/user/modify")
    public ResponseModel userModify(@RequestBody FundApplyReq.UserUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.userUpdate(req, operatorId));
    }

    @PostMapping("/admin/fastway/fund/company/modify")
    public ResponseModel companyModify(@RequestBody FundApplyReq.CompanyUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.companyUpdate(req, operatorId));
    }

    @PostMapping("/admin/fastway/fund/user/verify/access")
    public ResponseModel userVerifyAccess(@RequestBody FundApplyReq.UserUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.userVerifyAccess(req, operatorId));
    }

    @PostMapping("/admin/fastway/fund/user/verify/deny")
    public ResponseModel userVerifyDeny(@RequestBody FundApplyReq.UserUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.userVerifyDeny(req, operatorId));
    }

    @PostMapping("/admin/fastway/fund/company/verify/access")
    public ResponseModel companyVerifyAccess(@RequestBody FundApplyReq.CompanyUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.companyVerifyAccess(req, operatorId));
    }

    @PostMapping("/admin/fastway/fund/company/verify/deny")
    public ResponseModel companyVerifyDeny(@RequestBody FundApplyReq.CompanyUpdateReq req) {
        Integer operatorId = loadCurLoginId();
        return ResponseModel.succ(fundApplyAdminFacade.companyVerifyDeny(req, operatorId));
    }

}
