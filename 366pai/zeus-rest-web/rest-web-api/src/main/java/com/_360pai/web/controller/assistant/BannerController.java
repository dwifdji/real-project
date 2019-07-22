package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.BannerFacade;
import com._360pai.core.facade.assistant.req.BannerReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxiao
 * @Title: BannerController  banner管理
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/20 13:46
 */
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class BannerController {

    @Reference(version = "1.0.0")
    private BannerFacade bannerFacade;

    /**
     * 功能描述: banner列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:20
     */
    @GetMapping(value = "/open/banners/list")
    public ResponseModel selectBannerList(BannerReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        req.setIsOnline(true);
        PageInfo pageInfo = bannerFacade.selectBanner(req);
        model.setContent(pageInfo);
        return model;
    }

}
