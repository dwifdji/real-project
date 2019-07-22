
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityCommissionOrder;
import com._360pai.core.model.enrolling.EnrollingShareProfitInfo;
import com._360pai.core.vo.enrolling.EnrollingActivityCommissionOrderVo;

import java.util.List;

/**
 * <p>EnrollingActivityCommissionOrder的基础操作Dao</p>
 * @ClassName: EnrollingActivityCommissionOrderDao
 * @Description: EnrollingActivityCommissionOrder基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分20秒
 */
public interface EnrollingActivityCommissionOrderDao extends BaseDao<EnrollingActivityCommissionOrder,EnrollingActivityCommissionOrderCondition>{

    List<EnrollingActivityCommissionOrderVo> getCommissionOrderList(EnrollingListReqDto dto);


    EnrollingShareProfitInfo getEnrollingShareProfitInfo(String orderId);
}
