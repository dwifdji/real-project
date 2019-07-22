package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.THallFacade;
import com._360pai.core.facade.assistant.req.AppletHallActivityReq;
import com._360pai.core.facade.assistant.req.THallReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author zxiao
 * @Title: THallController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 13:01
 */
@RestController
public class THallController {
    @Reference(version = "1.0.0")
    private THallFacade tHallFacade;

    @RequiresPermissions(value = {"yygl_yylxgl:pmdtlx_list", "yygl_yylxgl:qwyzslx_list"}, logical = Logical.OR)
    @RequestMapping(value = "/admin/hall/list")
    public ResponseModel hallList(THallReq req) {
        PageInfo pageInfo = tHallFacade.hallList(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hall/add")
    public ResponseModel addHall(@RequestBody THallReq req) {
        int pageInfo = tHallFacade.addHall(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hall/edit")
    public ResponseModel editHall(@RequestBody THallReq req) {
        int pageInfo = tHallFacade.editHall(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/hall/delete")
    public ResponseModel deleteHall(@RequestBody THallReq req) {
        int pageInfo = tHallFacade.deleteHall(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/admin/hall/detail")
    public ResponseModel detailHall(@RequestBody THallReq req) {
        Object pageInfo = tHallFacade.detailHall(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 小程序店铺首页推荐列表接口
     */
    @GetMapping(value = "/admin/applet/activity/hall/list")
    public ResponseModel getAppletHallActivityList(AppletHallActivityReq.QueryReq req) {
        return ResponseModel.succ(tHallFacade.getAppletHallActivityList(req));
    }

    /**
     * 小程序店铺首页推荐新增接口
     */
    @PostMapping(value = "/admin/applet/activity/hall/add")
    public ResponseModel addAppletHallActivity(@RequestBody AppletHallActivityReq.AddReq req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getType(), "type 参数不能为空");
        Assert.notNull(req.getOrderNum(), "orderNum 参数不能为空");
        return ResponseModel.succ(tHallFacade.addAppletHallActivity(req));
    }

    /**
     * 小程序店铺首页推荐修改接口
     */
    @PostMapping(value = "/admin/applet/activity/hall/edit")
    public ResponseModel editAppletHallActivity(@RequestBody AppletHallActivityReq.EditReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getOrderNum(), "orderNum 参数不能为空");
        return ResponseModel.succ(tHallFacade.editAppletHallActivity(req));
    }

    /**
     * 小程序店铺首页推荐删除接口
     */
    @PostMapping(value = "/admin/applet/activity/hall/delete")
    public ResponseModel deleteAppletHallActivity(@RequestBody AppletHallActivityReq.DeleteReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(tHallFacade.deleteAppletHallActivity(req));
    }
}
