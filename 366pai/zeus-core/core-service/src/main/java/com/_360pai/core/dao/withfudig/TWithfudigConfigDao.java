
package com._360pai.core.dao.withfudig;

import com._360pai.core.condition.withfudig.TWithfudigConfigCondition;
import com._360pai.core.model.withfudig.TWithfudigConfig;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TWithfudigConfig的基础操作Dao</p>
 * @ClassName: TWithfudigConfigDao
 * @Description: TWithfudigConfig基础操作的Dao
 * @author Generator
 * @date 2018年09月06日 18时32分31秒
 */
public interface TWithfudigConfigDao extends BaseDao<TWithfudigConfig,TWithfudigConfigCondition>{

    Integer selectMaxRank();
}
