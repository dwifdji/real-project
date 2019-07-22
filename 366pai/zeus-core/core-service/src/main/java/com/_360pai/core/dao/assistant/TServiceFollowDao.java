
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TServiceFollow的基础操作Dao</p>
 * @ClassName: TServiceFollowDao
 * @Description: TServiceFollow基础操作的Dao
 * @author Generator
 * @date 2018年09月17日 10时09分27秒
 */
public interface TServiceFollowDao extends BaseDao<TServiceFollow,TServiceFollowCondition>{

    int removeFollowList(List<TServiceFollow> list);
}
