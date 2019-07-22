
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.model.assistant.Article;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Article的基础操作Dao</p>
 * @ClassName: ArticleDao
 * @Description: Article基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface ArticleDao extends BaseDao<Article,ArticleCondition>{

    int deleteArticle(Integer paramsId);
}
