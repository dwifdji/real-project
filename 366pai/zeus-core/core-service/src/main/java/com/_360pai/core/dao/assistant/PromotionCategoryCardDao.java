
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.PromotionCategoryCardCondition;
import com._360pai.core.model.assistant.PromotionCategoryCard;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.vo.assistant.PromotionCategoryCardVo;

import java.util.List;

/**
 * <p>PromotionCategoryCard的基础操作Dao</p>
 * @ClassName: PromotionCategoryCardDao
 * @Description: PromotionCategoryCard基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface PromotionCategoryCardDao extends BaseDao<PromotionCategoryCard,PromotionCategoryCardCondition>{

    int deleteCard(Integer paramsId);

    List<PromotionCategoryCardVo> getCards();
}
