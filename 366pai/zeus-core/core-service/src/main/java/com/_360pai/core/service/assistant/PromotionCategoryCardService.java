package com._360pai.core.service.assistant;


import com._360pai.core.model.assistant.PromotionCategoryCard;
import com.github.pagehelper.PageInfo;

public interface PromotionCategoryCardService{


    PageInfo selectCard(int page, int perPage, String order_num);

    int addCard(PromotionCategoryCard params);

    int editCard(PromotionCategoryCard params);

    int deleteCard(PromotionCategoryCard params);
}