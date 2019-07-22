
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.enrolling.NotifyPartyEnrollingActivityCondition;
import com._360pai.core.model.enrolling.NotifyPartyEnrollingActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>NotifyPartyEnrollingActivity数据层的基础操作</p>
 * @ClassName: NotifyPartyEnrollingActivityMapper
 * @Description: NotifyPartyEnrollingActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
@Mapper
public interface NotifyPartyEnrollingActivityMapper extends BaseMapper<NotifyPartyEnrollingActivity, NotifyPartyEnrollingActivityCondition>{
    List<EnrollingInfoVo> getNotifyList(ActivityIdReqDto dto);

	Integer deleteModel(String id);

    List<EnrollingActivity> getBeginIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);

    List<EnrollingActivity> getEndIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);
}
