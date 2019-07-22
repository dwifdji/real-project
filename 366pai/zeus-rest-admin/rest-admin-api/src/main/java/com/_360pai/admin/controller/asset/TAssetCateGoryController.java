package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.asset.req.TAssetCategoryOptionReq;
import com._360pai.core.facade.asset.req.TAssetCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TAssetCateGoryController  债券类型
 * @ProjectName zeus-core
 * @Description:
 * @date 2018/8/31 10:48
 */
@RestController
public class TAssetCateGoryController {

    @Reference(version = "1.0.0")
    private TAssetCateGoryFacade tAssetCateGoryFacade;

    /**
     * 功能描述:  获取全部类型 不分页 条件查询
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 11:16
     */
    @GetMapping(value = "/admin/t/categories")
    public ResponseModel getAllCateGores(TAssetCategoryReq req) {
        Object allCateGoryList = tAssetCateGoryFacade.getAllCateGoryList(req);
        return ResponseModel.succ(allCateGoryList);
    }

    /**
     * 功能描述: 获取债券列表 分页
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 11:16
     */
    @GetMapping(value = "/admin/t/categories/list")
    public ResponseModel getAllCateGoryByList(TAssetCategoryReq req) {
        PageInfo cateGoryList = tAssetCateGoryFacade.getCateGoryList(req);
        return ResponseModel.succ(cateGoryList);
    }

    /**
     * 功能描述: 添加债券类型
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 11:16
     */
    @PostMapping(value = "/admin/t/categories/add")
    public ResponseModel addCateGory(@RequestBody TAssetCategoryReq req) {
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getEnabled(), "状态不能为空");
        Assert.notNull(req.getBusinessType(), "业务不能为空");
        Assert.notNull(req.getDealMode(), "交易模式不能为空");
        Object o = tAssetCateGoryFacade.addCateGory(req);
        return ResponseModel.succ(o);
    }

    /**
     * 功能描述: 修改债券类型
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 11:16
     */
    @PostMapping(value = "/admin/t/categories/edit")
    public ResponseModel editCateGory(@RequestBody TAssetCategoryReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getName(), "名称不能为空");
        Assert.notNull(req.getEnabled(), "状态不能为空");
        Assert.notNull(req.getBusinessType(), "业务不能为空");
        Assert.notNull(req.getDealMode(), "交易模式不能为空");
        Object o = tAssetCateGoryFacade.updateCateGory(req);
        return ResponseModel.succ(o);
    }

    /**
     * 功能描述:   获取当前类型下的所有模板
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/31 12:58
     */
    @GetMapping(value = "/admin/t/categories/getTemp")
    public ResponseModel getTemplate(TAssetCategoryReq req) {
        PageInfo pageInfo = tAssetCateGoryFacade.getAllTempByCateGoryId(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/categories")
    public ResponseModel getAllCategories(@RequestBody TAssetCategoryReq req) {
        Object allCateGoryList = tAssetCateGoryFacade.getAllCateGoryList(req);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", allCateGoryList);
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"enabled", "businessType", "dealMode"}));
    }

    @PostMapping(value = "/admin/category/options")
    public ResponseModel categoryOptions(@RequestBody TAssetCategoryOptionReq req) {
        org.springframework.util.Assert.notNull(req.getAssetCategoryId(), "债券类型不能为空");
        Object allCateGoryList = tAssetCateGoryFacade.categoryOptions(req);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("list", allCateGoryList);
        return ResponseModel.succ(jsonObject, PropertyFilterFactory.create(new String[]{"enable"}));
    }
}
