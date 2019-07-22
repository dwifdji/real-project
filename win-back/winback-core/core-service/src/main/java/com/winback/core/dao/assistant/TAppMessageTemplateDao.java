
package com.winback.core.dao.assistant;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.assistant.TAppMessageTemplateCondition;
import com.winback.core.model.assistant.TAppMessageTemplate;

/**
 * <p>TAppMessageTemplate的基础操作Dao</p>
 * @ClassName: TAppMessageTemplateDao
 * @Description: TAppMessageTemplate基础操作的Dao
 * @author Generator
 * @date 2019年02月26日 19时39分16秒
 */
public interface TAppMessageTemplateDao extends BaseDao<TAppMessageTemplate,TAppMessageTemplateCondition> {
    TAppMessageTemplate findBy(String sendType, String type);
}
