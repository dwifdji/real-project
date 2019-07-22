
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.vo.assistant.PromotionCategoryCardVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.PromotionCategoryCardCondition;
import com._360pai.core.model.assistant.PromotionCategoryCard;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>PromotionCategoryCard数据层的基础操作</p>
 * @ClassName: PromotionCategoryCardMapper
 * @Description: PromotionCategoryCard数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface PromotionCategoryCardMapper extends BaseMapper<PromotionCategoryCard, PromotionCategoryCardCondition>{

    int deleteCard(@Param("paramId") Integer paramsId);

    List<PromotionCategoryCardVo> getCards();
}
