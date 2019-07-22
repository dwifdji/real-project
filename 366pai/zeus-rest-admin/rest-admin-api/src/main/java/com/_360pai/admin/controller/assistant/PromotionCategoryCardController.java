package com._360pai.admin.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.PromotionCategoryCardFacade;
import com._360pai.core.facade.assistant.req.PromotionCategoryCardReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: PromotionCategoryCardController  搜索卡片设置
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 10:13
 */
@RestController
public class PromotionCategoryCardController {

    @Reference(version = "1.0.0")
    private PromotionCategoryCardFacade promotionCategoryCardFacade;

    /**
     * 功能描述: 卡片列表
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 11:04
     */
    @GetMapping(value = "/admin/promotion_category_card/list")
    public ResponseModel selectPromotionCategoryCardList(PromotionCategoryCardReq req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        PageInfo pageInfo = promotionCategoryCardFacade.selectCard(req);
        model.setContent(pageInfo);
        return model;
    }

    /**
     * 功能描述: 新增卡片
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 11:05
     */
    @PostMapping(value = "/admin/promotion_category_card/add")
    public ResponseModel addPromotionCategoryCardList(@RequestBody PromotionCategoryCardReq req) {
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getHint(), "提示不能为空");
        Assert.notNull(req.getAssetCategoryGroupId(), "债权类型不能为空");
        Assert.notNull(req.getAssetCategoryGroupId(), "债权类型不能为空");
        Assert.notNull(req.getLink(), "链接不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        promotionCategoryCardFacade.addCard(req);
        return model;
    }

    /**
     * 功能描述: 修改卡片
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 11:05
     */
    @PostMapping(value = "/admin/promotion_category_card/edit")
    public ResponseModel editPromotionCategoryCardList(@RequestBody PromotionCategoryCardReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getTitle(), "标题不能为空");
        Assert.notNull(req.getHint(), "提示不能为空");
        Assert.notNull(req.getAssetCategoryGroupId(), "债权类型不能为空");
        Assert.notNull(req.getAssetCategoryGroupId(), "债权类型不能为空");
        Assert.notNull(req.getLink(), "链接不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        promotionCategoryCardFacade.editCard(req);
        return model;
    }

    /**
     * 功能描述: 删除卡片
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/21 11:05
     */
    @PostMapping(value = "/admin/promotion_category_card/delete")
    public ResponseModel deletePromotionCategoryCardList(@RequestBody PromotionCategoryCardReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        promotionCategoryCardFacade.deleteCard(req);
        return model;
    }
}
