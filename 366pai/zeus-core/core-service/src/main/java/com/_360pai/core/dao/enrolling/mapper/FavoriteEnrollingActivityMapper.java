
package com._360pai.core.dao.enrolling.mapper;

import java.util.List;

import com._360pai.core.model.enrolling.EnrollingActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.FavoriteEnrollingActivityCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

/**
 * <p>FavoriteEnrollingActivity数据层的基础操作</p>
 * @ClassName: FavoriteEnrollingActivityMapper
 * @Description: FavoriteEnrollingActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
@Mapper
public interface FavoriteEnrollingActivityMapper extends BaseMapper<FavoriteEnrollingActivity, FavoriteEnrollingActivityCondition>{

    List<EnrollingInfoVo> getFocusList(ActivityIdReqDto dto);

	Integer deleteModel(String id);

	Integer deleteModelList(@Param("focusList")List<String> focusList);

	List<EnrollingActivity> getBeginIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);

	List<EnrollingActivity> getEndIn5MinuteList(@Param("accountId") Integer accountId, @Param("partyId") Integer partyId);
}
