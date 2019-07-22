
package com._360pai.core.dao.enrolling;

import java.util.List;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.NotifyPartyEnrollingActivityCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

/**
 * <p>NotifyPartyEnrollingActivity的基础操作Dao</p>
 * @ClassName: NotifyPartyEnrollingActivityDao
 * @Description: NotifyPartyEnrollingActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public interface NotifyPartyEnrollingActivityDao extends BaseDao<NotifyPartyEnrollingActivity,NotifyPartyEnrollingActivityCondition>{


    List<EnrollingInfoVo> getNotifyList(ActivityIdReqDto dto);

	Integer deleteModel(String id);

    List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId);

    List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId);
}
