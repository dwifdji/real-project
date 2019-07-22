package com._360pai.admin.controller.fastway;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.fastway.DisposeApplyAdminFacade;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeLawOfficeVO;
import com._360pai.core.facade.fastway.resp.DisposeLawyerVO;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-11-26 14:03
 */
@RestController
public class DisposeApplyAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    private DisposeApplyAdminFacade disposeApplyAdminFacade;

    @RequiresPermissions("yhgl_kstdsh:dispose_apply_list")
    @GetMapping("/admin/fastway/dispose/apply/list")
    public ResponseModel disposeApplyList(DisposeApplyReq.DisposeFindReq req) {
        PageInfoResp pageInfoResp = disposeApplyAdminFacade.findDisposeApplyByParam(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @GetMapping("/admin/fastway/dispose/lawyer/detail")
    public ResponseModel disposeLawyerDetail(Integer applyId) {
        DisposeLawyerVO detail = disposeApplyAdminFacade.findLawyerDetailById(applyId);
        return ResponseModel.succ(detail);
    }

    @GetMapping("/admin/fastway/dispose/lawOffice/detail")
    public ResponseModel disposeLawOfficeDetail(Integer applyId) {
        DisposeLawOfficeVO detail = disposeApplyAdminFacade.findLawOfficeDetailById(applyId);
        return ResponseModel.succ(detail);
    }


    @PostMapping("/admin/fastway/dispose/lawyer/verify")
    public ResponseModel lawyerVerify(@RequestBody DisposeApplyReq.DisposeApplyVerify req) {
        Integer operatorId = loadCurLoginId();
        int count = disposeApplyAdminFacade.lawyerVerify(req, operatorId);
        return ResponseModel.succ(count);
    }

    @PostMapping("/admin/fastway/dispose/lawOffice/verify")
    public ResponseModel lawOfficeVerify(@RequestBody DisposeApplyReq.DisposeApplyVerify req) {
        Integer operatorId = loadCurLoginId();
        int count = disposeApplyAdminFacade.lawOfficeVerify(req, operatorId);
        return ResponseModel.succ(count);
    }

    @PostMapping("/admin/fastway/dispose/lawyer/modify")
    public ResponseModel lawyerModify(@RequestBody DisposeApplyReq.DisposeApplyVerify req) {
        Integer operatorId = loadCurLoginId();
        int count = disposeApplyAdminFacade.lawyerUpdate(req, operatorId);
        return ResponseModel.succ(count);
    }

    @PostMapping("/admin/fastway/dispose/lawOffice/modify")
    public ResponseModel lawOfficeModify(@RequestBody DisposeApplyReq.DisposeApplyVerify req) {
        Integer operatorId = loadCurLoginId();
        int count = disposeApplyAdminFacade.lawOfficeUpdate(req, operatorId);
        return ResponseModel.succ(count);
    }

}
