
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.TComProvinceCondition;
import com.winback.core.model.assistant.TComProvince;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TComProvince的基础操作Dao</p>
 * @ClassName: TComProvinceDao
 * @Description: TComProvince基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public interface TComProvinceDao extends BaseDao<TComProvince,TComProvinceCondition>{
    String getName(String code);
}
