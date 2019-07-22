package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.PartnerAgenciesFacade;
import com._360pai.core.facade.assistant.req.PartnerAgencyReq;
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
 * @Title: PartnerAgenciesController 合作机构
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/22 18:47
 */
@RestController
public class PartnerAgenciesController {

    @Reference(version = "1.0.0")
    private PartnerAgenciesFacade partnerAgenciesFacade;

    /**
     * 功能描述:  合作机构列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:52
     */
    @RequiresPermissions("yygl_jpsjgl:list")
    @GetMapping(value = "/admin/partner_agencies/list")
    public ResponseModel selectPartnerAgency(PartnerAgencyReq req) {
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        PageInfo pageInfo = partnerAgenciesFacade.selectPartnerAgenciesList(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 合作机构新建
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:53
     */
    @PostMapping("/admin/partner_agencies/add")
    public ResponseModel addPartnerAgency(@RequestBody PartnerAgencyReq req) {
        Assert.notNull(req.getAgencyId(), "机构ID不能为空");
        Assert.notNull(req.getOrderNum(), "排序不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = partnerAgenciesFacade.addPartnerAgency(req);
        if (i < 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 合作机构修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:54
     */
    @PostMapping("/admin/partner_agencies/edit")
    public ResponseModel editPartnerAgency(@RequestBody PartnerAgencyReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getAgencyId(), "机构ID不能为空");
        Assert.notNull(req.getOrderNum(), "排序不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = partnerAgenciesFacade.editPartnerAgency(req);
        if (i < 0) {
            return ResponseModel.fail();
        }
        return model;
    }

    /**
     * 功能描述: 合作机构修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/22 19:54
     */
    @PostMapping("/admin/partner_agencies/delete")
    public ResponseModel deletePartnerAgency(@RequestBody PartnerAgencyReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        int i = partnerAgenciesFacade.deletePartnerAgency(req);
        if (i < 0) {
            return ResponseModel.fail();
        }
        return model;
    }

}
