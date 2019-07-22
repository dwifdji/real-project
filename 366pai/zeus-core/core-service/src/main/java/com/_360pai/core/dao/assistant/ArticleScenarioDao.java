
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.ArticleScenarioCondition;
import com._360pai.core.model.assistant.ArticleScenario;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>ArticleScenario的基础操作Dao</p>
 * @ClassName: ArticleScenarioDao
 * @Description: ArticleScenario基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
public interface ArticleScenarioDao extends BaseDao<ArticleScenario,ArticleScenarioCondition>{

    List<Map>  selectArticleScenario();
}
