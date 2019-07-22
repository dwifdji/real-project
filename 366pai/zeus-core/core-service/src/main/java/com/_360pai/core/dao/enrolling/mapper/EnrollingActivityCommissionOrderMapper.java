
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.EnrollingActivityCommissionOrderCondition;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.model.enrolling.EnrollingActivityCommissionOrder;
import com._360pai.core.model.enrolling.EnrollingShareProfitInfo;
import com._360pai.core.vo.enrolling.EnrollingActivityCommissionOrderVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>EnrollingActivityCommissionOrder数据层的基础操作</p>
 * @ClassName: EnrollingActivityCommissionOrderMapper
 * @Description: EnrollingActivityCommissionOrder数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分20秒
 */
@Mapper
public interface EnrollingActivityCommissionOrderMapper extends BaseMapper<EnrollingActivityCommissionOrder, EnrollingActivityCommissionOrderCondition>{
    List<EnrollingActivityCommissionOrderVo> getCommissionOrderList(EnrollingListReqDto dto);


    EnrollingShareProfitInfo getEnrollingShareProfitInfo(@Param("orderId")String orderId);
}
