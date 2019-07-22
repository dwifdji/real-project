package com._360pai.core.provider.assistant;

import com._360pai.core.facade.assistant.PromotionCategoryCardFacade;
import com._360pai.core.facade.assistant.req.PromotionCategoryCardReq;
import com._360pai.core.model.assistant.PromotionCategoryCard;
import com._360pai.core.service.assistant.PromotionCategoryCardService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: PromotionCategoryCardProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 10:16
 */
@Component
@Service(version = "1.0.0")
public class PromotionCategoryCardProvider implements PromotionCategoryCardFacade {

    @Autowired
    private PromotionCategoryCardService promotionCategoryCardService;


    @Override
    public PageInfo selectCard(PromotionCategoryCardReq req) {
        return promotionCategoryCardService.selectCard(req.getPage(), req.getPerPage(),"order_no");
    }

    @Override
    public int addCard(PromotionCategoryCardReq req) {
        return promotionCategoryCardService.addCard(copyPromotionCategoryCard(req));
    }

    @Override
    public int editCard(PromotionCategoryCardReq req) {
        return promotionCategoryCardService.editCard(copyPromotionCategoryCard(req));
    }

    @Override
    public int deleteCard(PromotionCategoryCardReq req) {
        return promotionCategoryCardService.deleteCard(copyPromotionCategoryCard(req));
    }

    private PromotionCategoryCard copyPromotionCategoryCard(PromotionCategoryCardReq req) {
        PromotionCategoryCard promotionCategoryCard = new PromotionCategoryCard();
        BeanUtils.copyProperties(req, promotionCategoryCard);
        return promotionCategoryCard;
    }
}
