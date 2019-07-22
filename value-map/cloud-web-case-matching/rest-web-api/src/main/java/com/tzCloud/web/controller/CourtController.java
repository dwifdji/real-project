package com.tzCloud.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.caseMatching.ViewCourtNumFacade;
import com.tzCloud.core.facade.caseMatching.req.CourtReq;
import com.tzCloud.core.facade.caseMatching.req.ViewCourtNumReq;
import com.tzCloud.core.facade.legalEngine.CourtFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: CourtController
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 10:25
 */
@RestController
@Slf4j
public class CourtController {

    @Reference(version = "1.0.0")
    private ViewCourtNumFacade viewCourtNumFacade;
    @Reference(version = "1.0.0")
    private CourtFacade courtFacade;

    @RequestMapping("/open/court/searchName")
    public ResponseModel findByCourtName(@RequestBody ViewCourtNumReq req) {
        Assert.notNull(req.getCourtName(), "搜索名称不能为空");
        return ResponseModel.succ(viewCourtNumFacade.findByCourtName(req));
    }

    @RequestMapping("/open/court/searchCourt")
    public ResponseModel findByCourt(@RequestBody ViewCourtNumReq req) {
//        Assert.notNull(req.getProvince(), "province不能为空");
        return ResponseModel.succ(viewCourtNumFacade.findByCourtName(req));
    }

    @RequestMapping("/open/court/detail")
    public ResponseModel findByCourtDetail(@RequestBody CourtReq req) {
        Assert.notNull(req.getId(), "法院ID不能为空");
        return ResponseModel.succ(courtFacade.courtDetail(req));
    }

    @RequestMapping("/open/court/getAggs")
    public ResponseModel getAggs(@RequestBody CourtReq req) {
        return ResponseModel.succ(courtFacade.getAggs(req));
    }



}
