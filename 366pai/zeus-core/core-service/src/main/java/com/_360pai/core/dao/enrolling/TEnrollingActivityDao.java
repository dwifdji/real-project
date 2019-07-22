
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.TEnrollingActivityCondition;
import com._360pai.core.model.enrolling.TEnrollingActivity;

import java.util.List;

/**
 * <p>TEnrollingActivity的基础操作Dao</p>
 * @ClassName: TEnrollingActivityDao
 * @Description: TEnrollingActivity基础操作的Dao
 * @author Generator
 * @date 2018年10月16日 13时57分16秒
 */
public interface TEnrollingActivityDao extends BaseDao<TEnrollingActivity,TEnrollingActivityCondition>{

    List<TEnrollingActivity> getOldList(String ids);





}
