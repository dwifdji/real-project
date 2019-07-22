package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.InstructionsContentFacade;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
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
 * @Title: InstructionsContentController 竞拍公告
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 17:00
 */
@RestController
public class InstructionsContentController {

    @Reference(version = "1.0.0")
    private InstructionsContentFacade instructionsContentFacade;

    /**
     * 功能描述: 竞拍公告列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @RequiresPermissions("yygl_ggxzgl:list")
    @GetMapping(value = "/admin/instructions_contents/list")
    public ResponseModel selectInstructionsContentList(InstructionsContentReq req) {
        return ResponseModel.succ(instructionsContentFacade.selectInstructionsContentList(req));
    }

    /**
     * 功能描述: 竞拍公告详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @GetMapping(value = "/admin/instructions_contents/detail")
    public ResponseModel selectInstructionsContent(InstructionsContentReq req) {
        return instructionsContentFacade.findInstructionsContentById(req.getId());
    }

    /**
     * 功能描述: 添加竞拍公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @PostMapping(value = "/admin/instructions_contents/add")
    public ResponseModel addInstructionsContent(@RequestBody InstructionsContentReq req) {
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getStatus(), "状态不能为空");
        Assert.notNull(req.getContent(), "内容不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        instructionsContentFacade.addInstructionsContent(req);
        return model;
    }

    /**
     * 功能描述: 修改竞拍公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @PostMapping(value = "/admin/instructions_contents/edit")
    public ResponseModel editInstructionsContent(@RequestBody InstructionsContentReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getStatus(), "状态不能为空");
        Assert.notNull(req.getContent(), "内容不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        instructionsContentFacade.editInstructionsContent(req);
        return model;
    }

    /**
     * 功能描述: 删除竞拍公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @PostMapping(value = "/admin/instructions_contents/delete")
    public ResponseModel deleteInstructionsContent(@RequestBody InstructionsContentReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        instructionsContentFacade.deleteInstructionsContent(req);
        return model;
    }

}
