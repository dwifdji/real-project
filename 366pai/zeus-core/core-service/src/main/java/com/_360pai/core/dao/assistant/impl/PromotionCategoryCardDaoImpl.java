
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.vo.assistant.PromotionCategoryCardVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.PromotionCategoryCardCondition;
import com._360pai.core.dao.assistant.mapper.PromotionCategoryCardMapper;
import com._360pai.core.model.assistant.PromotionCategoryCard;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.PromotionCategoryCardDao;

import java.util.List;

@Service
public class PromotionCategoryCardDaoImpl extends AbstractDaoImpl<PromotionCategoryCard, PromotionCategoryCardCondition, BaseMapper<PromotionCategoryCard,PromotionCategoryCardCondition>> implements PromotionCategoryCardDao{
	
	@Resource
	private PromotionCategoryCardMapper promotionCategoryCardMapper;
	
	@Override
	protected BaseMapper<PromotionCategoryCard, PromotionCategoryCardCondition> daoSupport() {
		return promotionCategoryCardMapper;
	}

	@Override
	protected PromotionCategoryCardCondition blankCondition() {
		return new PromotionCategoryCardCondition();
	}

    @Override
    public int deleteCard(Integer paramsId) {
        return promotionCategoryCardMapper.deleteCard(paramsId);
    }

    @Override
    public List<PromotionCategoryCardVo> getCards() {
        return promotionCategoryCardMapper.getCards();
    }
}
