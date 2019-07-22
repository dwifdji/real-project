
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.EnrollingDepositCondition;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingDeposit;
import com._360pai.core.vo.enrolling.EnrollingAuditVo;
import com._360pai.core.vo.enrolling.EnrollingDepositListVo;

import java.util.List;

/**
 * <p>EnrollingDeposit的基础操作Dao</p>
 * @ClassName: EnrollingDepositDao
 * @Description: EnrollingDeposit基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
public interface EnrollingDepositDao extends BaseDao<EnrollingDeposit,EnrollingDepositCondition>{

    List<EnrollingDepositListVo> getEnrollingDepositList(ActivityIdReqDto dto);


    List<EnrollingAuditVo> getAuditList(EnrollingListReqDto req);

    List<EnrollingActivity> getBeginIn5MinuteList(Integer partyId);

    List<EnrollingActivity> getEndIn5MinuteList(Integer partyId);
}
