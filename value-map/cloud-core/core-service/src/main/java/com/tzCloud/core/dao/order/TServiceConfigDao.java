
package com.tzCloud.core.dao.order;

import com.tzCloud.core.condition.order.TServiceConfigCondition;
import com.tzCloud.core.model.order.TServiceConfig;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TServiceConfig的基础操作Dao</p>
 * @ClassName: TServiceConfigDao
 * @Description: TServiceConfig基础操作的Dao
 * @author Generator
 * @date 2019年06月19日 15时47分59秒
 */
public interface TServiceConfigDao extends BaseDao<TServiceConfig,TServiceConfigCondition>{

    List<TServiceConfig> selectByLike(String configStart);

}
