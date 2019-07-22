
package com._360pai.core.dao.enrolling;

import com._360pai.core.condition.enrolling.EnrollingActivityShareStatsCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityShareStats;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

import java.util.List;

/**
 * <p>EnrollingActivityShareStats的基础操作Dao</p>
 * @ClassName: EnrollingActivityShareStatsDao
 * @Description: EnrollingActivityShareStats基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public interface EnrollingActivityShareStatsDao extends BaseDao<EnrollingActivityShareStats,EnrollingActivityShareStatsCondition>{


    List<EnrollingInfoVo> getShareList(ActivityIdReqDto dto);
}
