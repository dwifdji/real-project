package com._360pai.web.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.disposal.DisposeShowFacade;
import com._360pai.core.facade.disposal.req.DisposeSurveyReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-30 13:52
 */
@RestController
public class DisposeShowController {

    @Reference(version = "1.0.0")
    private DisposeShowFacade disposeShowFacade;

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @GetMapping("/open/dispose/show/provider")
    public ResponseModel getShowProvider(DisposeSurveyReq.GetSurveyList req) {
        PageInfoResp pageInfoResp
                = disposeShowFacade.getRecommendProvider(req.getActivityId(), req.getPage(), req.getPerPage());
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 优质服务商推荐列表接口(分页)
     */
    @GetMapping("/open/recommend/dispose/provider/list")
    public ResponseModel getShowProvider(AccountReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getRecommendDisposeProviderList(req));
    }
}
