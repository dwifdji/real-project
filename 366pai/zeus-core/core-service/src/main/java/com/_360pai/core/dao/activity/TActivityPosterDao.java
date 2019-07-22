
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.TActivityPosterCondition;
import com._360pai.core.model.activity.TActivityPoster;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TActivityPoster的基础操作Dao</p>
 * @ClassName: TActivityPosterDao
 * @Description: TActivityPoster基础操作的Dao
 * @author Generator
 * @date 2019年07月01日 10时42分57秒
 */
public interface TActivityPosterDao extends BaseDao<TActivityPoster,TActivityPosterCondition>{
    PageInfo<TActivityPoster> getBackendList(int page, int perPage, Map<String, Object> params);

    List<TActivityPoster> getFrontList(Map<String, Object> params);

    List<Map> getManualAuctionActivityList(@Param("activityIdList") List<Integer> activityIdList);

    List<Map> getManualEnrollingActivityList(@Param("activityIdList") List<Integer> activityIdList);
}
