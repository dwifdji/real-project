
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.ArticleCategoryCondition;
import com._360pai.core.model.assistant.ArticleCategory;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>ArticleCategory数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: ArticleCategoryMapper
 * @Description: ArticleCategory数据层的基础操作
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface ArticleCategoryMapper extends BaseMapper<ArticleCategory, ArticleCategoryCondition> {

    int deleteArticleCategory(@Param("paramsId") Integer paramsId);
}
