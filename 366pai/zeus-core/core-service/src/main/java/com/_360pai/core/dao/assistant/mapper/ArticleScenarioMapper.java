
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.ArticleScenarioCondition;
import com._360pai.core.model.assistant.ArticleScenario;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>ArticleScenario数据层的基础操作</p>
 * @ClassName: ArticleScenarioMapper
 * @Description: ArticleScenario数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface ArticleScenarioMapper extends BaseMapper<ArticleScenario, ArticleScenarioCondition>{

    List<Map> selectArticleScenario();
}
