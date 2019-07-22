
package com._360pai.core.dao.enrolling;

import com._360pai.core.condition.enrolling.FavoriteEnrollingActivityCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.FocusActivityDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

import java.util.List;

/**
 * <p>FavoriteEnrollingActivity的基础操作Dao</p>
 * @ClassName: FavoriteEnrollingActivityDao
 * @Description: FavoriteEnrollingActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public interface FavoriteEnrollingActivityDao extends BaseDao<FavoriteEnrollingActivity,FavoriteEnrollingActivityCondition>{

    List<EnrollingInfoVo> getFocusList(ActivityIdReqDto dto);

	Integer deleteModel(String id);

	Integer deleteModelList(List<String> focusList);

	List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

	List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}
