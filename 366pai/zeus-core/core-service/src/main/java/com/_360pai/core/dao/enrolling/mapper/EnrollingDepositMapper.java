
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingAuditVo;
import com._360pai.core.vo.enrolling.EnrollingDepositListVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>EnrollingDeposit数据层的基础操作</p>
 * @ClassName: EnrollingDepositMapper
 * @Description: EnrollingDeposit数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
@Mapper
public interface EnrollingDepositMapper extends BaseMapper<EnrollingDeposit, EnrollingDepositCondition>{

    List<EnrollingDepositListVo> getEnrollingDepositList(ActivityIdReqDto dto);

    List<EnrollingAuditVo> getAuditList(EnrollingListReqDto req);

    List<EnrollingActivity> getBeginIn5MinuteList(@Param("partyId") Integer partyId);

    List<EnrollingActivity> getEndIn5MinuteList(@Param("partyId") Integer partyId);
}
