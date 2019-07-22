
package com._360pai.core.dao.assistant;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.THallActivityCondition;
import com._360pai.core.model.assistant.THallActivity;

import java.util.List;
import java.util.Map;

/**
 * <p>THallActivity的基础操作Dao</p>
 * @ClassName: THallActivityDao
 * @Description: THallActivity基础操作的Dao
 * @author Generator
 * @date 2018年09月18日 16时47分54秒
 */
public interface THallActivityDao extends BaseDao<THallActivity, THallActivityCondition>{

    List<Map> getTHallActivityList(Integer id);
}
