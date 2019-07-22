package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.WorkingDayFacade;
import com._360pai.core.facade.assistant.req.WorkingDayReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: WorkingDayController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 19:22
 */
@RestController
public class WorkingDayController {
    @Reference(version = "1.0.0")
    private WorkingDayFacade workingDayFacade;

    /**
     * 功能描述:  工作日设置列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:46
     */
    @RequiresPermissions("yygl_gzrgl:list")
    @GetMapping(value = "/admin/working_days/list")
    public ResponseModel selectWorkingDay(WorkingDayReq req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = workingDayFacade.selectWorkingDayList(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 工作日添加
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:46
     */
    @PostMapping(value = "/admin/working_days/add")
    public ResponseModel addWorkingDay(@RequestBody WorkingDayReq req) {
        Assert.notNull(req.getDate(), "日期不能为空");
        Assert.notNull(req.getIsWorking(), "状态不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = workingDayFacade.addWorkingDay(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述:  工作日删除
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:46
     */
    @PostMapping(value = "/admin/working_days/delete")
    public ResponseModel editWorkingDay(@RequestBody WorkingDayReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        int i = workingDayFacade.deleteWorkingDay(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return model;
    }

}
