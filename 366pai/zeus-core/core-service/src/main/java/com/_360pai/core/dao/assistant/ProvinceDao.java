
package com._360pai.core.dao.assistant;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.ProvinceCondition;
import com._360pai.core.model.assistant.Province;

/**
 * <p>Province的基础操作Dao</p>
 * @ClassName: ProvinceDao
 * @Description: Province基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface ProvinceDao extends BaseDao<Province,ProvinceCondition>{

    Province selectById(Integer id);

    String getName(String id);

    String getName(Integer id);
}
