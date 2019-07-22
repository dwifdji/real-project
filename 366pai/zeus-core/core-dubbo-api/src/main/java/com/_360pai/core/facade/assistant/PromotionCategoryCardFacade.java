package com._360pai.core.facade.assistant;

import com._360pai.core.facade.assistant.req.PromotionCategoryCardReq;
import com.github.pagehelper.PageInfo;

public interface PromotionCategoryCardFacade {

    PageInfo selectCard(PromotionCategoryCardReq req);

    int addCard(PromotionCategoryCardReq req);

    int editCard(PromotionCategoryCardReq req);

    int deleteCard(PromotionCategoryCardReq req);

}
