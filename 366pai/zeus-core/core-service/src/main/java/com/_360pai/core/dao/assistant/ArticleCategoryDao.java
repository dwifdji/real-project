
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>ArticleCategory的基础操作Dao</p>
 * @ClassName: ArticleCategoryDao
 * @Description: ArticleCategory基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface ArticleCategoryDao extends BaseDao<ArticleCategory,ArticleCategoryCondition>{

    int deleteArticleCategory(Integer paramsId);
}
