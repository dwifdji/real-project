
package com._360pai.core.dao.enrolling.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.enrolling.TEnrollingActivityCondition;
import com._360pai.core.model.enrolling.TEnrollingActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TEnrollingActivity数据层的基础操作</p>
 * @ClassName: TEnrollingActivityMapper
 * @Description: TEnrollingActivity数据层的基础操作
 * @author Generator
 * @date 2018年10月16日 13时57分16秒
 */
@Mapper
public interface TEnrollingActivityMapper extends BaseMapper<TEnrollingActivity, TEnrollingActivityCondition>{

    List<TEnrollingActivity> getOldList(@Param("ids") String ids);
}
