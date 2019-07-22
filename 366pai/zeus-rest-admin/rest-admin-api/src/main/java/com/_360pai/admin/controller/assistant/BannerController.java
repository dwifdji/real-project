package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.BannerFacade;
import com._360pai.core.facade.assistant.req.BannerReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxiao
 * @Title: BannerController  banner管理
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 18:46
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
    @RequiresPermissions("yygl_bannergl:list")
    @GetMapping(value = "/admin/banners/list")
    public ResponseModel selectBannerList(BannerReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = bannerFacade.selectBanner(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 添加banner
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:20
     */
    @PostMapping(value = "/admin/banners/add")
    public ResponseModel addBannerList(@RequestBody BannerReq req) {
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getImgUrl(), "图片链接不能为空");
        Assert.notNull(req.getLinkUrl(), "链接不能为空");
        Assert.notNull(req.getType(), "类型不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        bannerFacade.addBanner(req);
        return model;
    }

    /**
     * 功能描述: 修改banner
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:21
     */
    @PostMapping(value = "/admin/banners/edit")
    public ResponseModel editBannerList(@RequestBody BannerReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getImgUrl(), "图片链接不能为空");
        Assert.notNull(req.getLinkUrl(), "链接不能为空");
        Assert.notNull(req.getLinkUrl(), "类型不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        bannerFacade.editBanner(req);
        return model;
    }

    /**
     * 功能描述: 删除banner
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:21
     */
    @PostMapping(value = "/admin/banners/delete")
    public ResponseModel deleteBannerList(@RequestBody BannerReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        bannerFacade.deleteBanner(req);
        return model;
    }

}
