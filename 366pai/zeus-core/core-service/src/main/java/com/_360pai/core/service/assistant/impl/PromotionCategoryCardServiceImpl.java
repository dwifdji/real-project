package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.PromotionCategoryCardCondition;
import com._360pai.core.dao.assistant.PromotionCategoryCardDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.PromotionCategoryCard;
import com._360pai.core.service.assistant.PromotionCategoryCardService;
import com._360pai.core.vo.assistant.PromotionCategoryCardVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionCategoryCardServiceImpl implements PromotionCategoryCardService {

    @Autowired
    private PromotionCategoryCardDao promotionCategoryCardDao;


    @Override
    public PageInfo selectCard(int page, int perPage, String order_num) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(order_num)) {
            PageHelper.orderBy(order_num);
        }
        List<PromotionCategoryCardVo> promotionCategoryCards = promotionCategoryCardDao.getCards();
        return new PageInfo<>(promotionCategoryCards);
    }

    @Override
    public int addCard(PromotionCategoryCard params) {
        return promotionCategoryCardDao.insert(params);
    }

    @Override
    public int editCard(PromotionCategoryCard params) {
        PromotionCategoryCard promotionCategoryCardById = findPromotionCategoryCardById(params);
        if (promotionCategoryCardById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的卡片不存在");
        }
        return promotionCategoryCardDao.updateById(params);
    }

    @Override
    public int deleteCard(PromotionCategoryCard params) {
        PromotionCategoryCard promotionCategoryCardById = findPromotionCategoryCardById(params);
        if (promotionCategoryCardById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的卡片不存在");
        }
        return promotionCategoryCardDao.deleteCard(params.getId());
    }


    private PromotionCategoryCard findPromotionCategoryCardById(PromotionCategoryCard params) {
        PromotionCategoryCardCondition categoryCardCondition = new PromotionCategoryCardCondition();
        categoryCardCondition.setId(params.getId());
        return promotionCategoryCardDao.selectOneResult(categoryCardCondition);
    }
}