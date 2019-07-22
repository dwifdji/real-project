
package com._360pai.core.dao.enrolling;

import com._360pai.core.condition.enrolling.EnrollingActivityDataCondition;
import com._360pai.core.model.enrolling.EnrollingActivityData;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

/**
 * <p>EnrollingActivityData的基础操作Dao</p>
 * @ClassName: EnrollingActivityDataDao
 * @Description: EnrollingActivityData基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public interface EnrollingActivityDataDao extends BaseDao<EnrollingActivityData,EnrollingActivityDataCondition>{
    PageInfo<EnrollingActivityData> find(int page, int perPage);
}
