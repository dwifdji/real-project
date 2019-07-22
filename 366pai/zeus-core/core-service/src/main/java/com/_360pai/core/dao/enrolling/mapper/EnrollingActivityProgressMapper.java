
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.enrolling.EnrollingActivityProgressCondition;
import com._360pai.core.model.enrolling.EnrollingActivityProgress;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>EnrollingActivityProgress数据层的基础操作</p>
 * @ClassName: EnrollingActivityProgressMapper
 * @Description: EnrollingActivityProgress数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分21秒
 */
@Mapper
public interface EnrollingActivityProgressMapper extends BaseMapper<EnrollingActivityProgress, EnrollingActivityProgressCondition>{
    List<EnrollingInfoVo> getProgressList(ActivityIdReqDto dto);
}
