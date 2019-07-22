
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.TPlatformArticleCondition;
import com._360pai.core.model.assistant.TPlatformArticle;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TPlatformArticle的基础操作Dao</p>
 * @ClassName: TPlatformArticleDao
 * @Description: TPlatformArticle基础操作的Dao
 * @author Generator
 * @date 2018年09月25日 10时05分28秒
 */
public interface TPlatformArticleDao extends BaseDao<TPlatformArticle,TPlatformArticleCondition>{
    int addViewCount(Integer id);
}
