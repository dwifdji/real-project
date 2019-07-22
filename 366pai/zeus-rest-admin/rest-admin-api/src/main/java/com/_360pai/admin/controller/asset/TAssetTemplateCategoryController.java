package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.facade.asset.TAssetTemplateCategoryFacade;
import com._360pai.core.facade.asset.req.TAssetFieldItemReq;
import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com._360pai.core.facade.asset.req.TAssetTemplateFieldGroupMapReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author zxiao
 * @Title: TAssetTemplateCategoryController  债券模板表
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/31 13:03
 */
@RestController
public class TAssetTemplateCategoryController {
    @Reference(version = "1.0.0")
    private TAssetTemplateCategoryFacade tAssetTemplateCategoryFacade;

    /**
     * 功能描述:  模板列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:36
     */
    @GetMapping(value = "/admin/template/list")
    public ResponseModel TemplateCategoryList(TAssetTemplateCategoryReq req) {
        PageInfo pageInfo = tAssetTemplateCategoryFacade.TemplateCategoryList(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述:  添加模板
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:36
     */
    @PostMapping(value = "/admin/template/add")
    public ResponseModel addTemplateCategoryList(@RequestBody TAssetTemplateCategoryReq req) {
        Assert.notNull(req.getCategoryId(), "类型不能为空");
        Assert.notNull(req.getTemplateName(), "模板名称不能为空");
        Assert.notNull(req.getIsGroupDefault(), "默认选项不能为空");
        int i = tAssetTemplateCategoryFacade.addTemplateCategory(req);
        return ResponseModel.succ(i);
    }

    /**
     * 功能描述: 修改模板
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @PostMapping(value = "/admin/template/edit")
    public ResponseModel editTemplateCategoryList(@RequestBody TAssetTemplateCategoryReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getCategoryId(), "类型不能为空");
        Assert.notNull(req.getTemplateName(), "模板名称不能为空");
        Assert.notNull(req.getIsGroupDefault(), "默认选项不能为空");
        int i = tAssetTemplateCategoryFacade.editTemplateCategory(req);
        if (i <= 0) {
            return ResponseModel.fail();
        }
        return ResponseModel.succ(i);
    }

    /**
     * 功能描述: 模板添加分组字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @PostMapping(value = "/admin/template/group/add")
    public ResponseModel addTemplateGroup(@RequestBody TAssetTemplateCategoryReq req) {
        Assert.notNull(req.getGroupIds(), "分组字段不能为空");
        int i = tAssetTemplateCategoryFacade.addTemplateGroup(req);
        return ResponseModel.succ(i);
    }

    /**
     * 功能描述: 获取当前模板下的分组
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @GetMapping(value = "/admin/template/group/list")
    public ResponseModel getTemplateGroup(TAssetTemplateFieldGroupMapReq req) {
        Assert.notNull(req.getAssetTemplateCategoryId(), "模板ID不能为空");
        Object object = tAssetTemplateCategoryFacade.getTemplateGroup(req.getAssetTemplateCategoryId());
        return ResponseModel.succ(object);
    }

    /**
     * 功能描述: 将字段设置到分组中
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @PostMapping(value = "/admin/template/field/add")
    public ResponseModel setTemplateGroupFilter(@RequestBody TAssetFieldItemReq req) {
        Assert.notNull(req.getFieldId(), "字段ID不能为空");
        Assert.notNull(req.getTemplateId(), "模板ID不能为空");
        Object object = tAssetTemplateCategoryFacade.setTemplateGroupField(req);
        return ResponseModel.succ(object);
    }

    /**
     * 功能描述: 获取当前模板中分组下的设置过的字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @GetMapping(value = "/admin/template/field/list")
    public ResponseModel getTemplateGroupField(TAssetFieldItemReq req) {
        Assert.notNull(req.getFieldGroupId(), "字段ID不能为空");
        Assert.notNull(req.getTemplateId(), "模板ID不能为空");
        req.setSideType(SideType.ADMIN);
        Object object = tAssetTemplateCategoryFacade.getTemplateGroupField(req);
        return ResponseModel.succ(object);
    }

    /**
     * 功能描述: 删除当前模板中分组下的设置过的字段
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 13:57
     */
    @PostMapping(value = "/admin/template/field/delete")
    public ResponseModel deleteTemplateGroupField(@RequestBody TAssetFieldItemReq req) {
        Assert.notNull(req.getId(), "字段ID不能为空");
        req.setSideType(SideType.ADMIN);
        int i = tAssetTemplateCategoryFacade.deleteTemplateGroupField(req);
        return ResponseModel.succ(i);
    }

    /**
     * 功能描述: 首页获取模板
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/26 16:55
     */
    @PostMapping(value = "/admin/getTemplate")
    public ResponseModel getTemplateCopy(@RequestBody TAssetTemplateCategoryReq req) {
        req.setSideType(SideType.ADMIN);
        Object object = tAssetTemplateCategoryFacade.getTemplateCopy(req);
        return ResponseModel.succ(object);
    }

    @PostMapping(value = "/admin/getTemplateParty")
    public ResponseModel getTemplateParty(@RequestBody TAssetTemplateCategoryReq req) {
        Object object = tAssetTemplateCategoryFacade.getTemplateParty(2, 20);
        Map<String, Object> jsonObject = new TreeMap<>();
        jsonObject.put("fields", object);
        return ResponseModel.succ(jsonObject);
    }
}
