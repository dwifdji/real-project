
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TPlatformArticle数据层的基础操作</p>
 * @ClassName: TPlatformArticleMapper
 * @Description: TPlatformArticle数据层的基础操作
 * @author Generator
 * @date 2018年09月25日 10时05分28秒
 */
@Mapper
public interface TPlatformArticleMapper extends BaseMapper<TPlatformArticle, TPlatformArticleCondition>{
    int addViewCount(@Param("id") Integer id);
}
