
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.ArticleCondition;
import com._360pai.core.model.assistant.Article;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>Article数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: ArticleMapper
 * @Description: Article数据层的基础操作
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article, ArticleCondition> {

    int deleteArticle(@Param("paramsId") Integer paramsId);
}
