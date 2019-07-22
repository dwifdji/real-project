
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.model.assistant.TComArea;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TComArea的基础操作Dao</p>
 * @ClassName: TComAreaDao
 * @Description: TComArea基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public interface TComAreaDao extends BaseDao<TComArea,TComAreaCondition>{
    String getName(String code);
}
