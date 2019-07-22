
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.AgencyPortalActivityCondition;
import com._360pai.core.model.activity.AgencyPortalActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>AgencyPortalActivity数据层的基础操作</p>
 * @ClassName: AgencyPortalActivityMapper
 * @Description: AgencyPortalActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface AgencyPortalActivityMapper extends BaseMapper<AgencyPortalActivity, AgencyPortalActivityCondition>{

    int deleteNotify(@Param("paramsId") Integer id);

    List<AgencyPortalActivity> getByActivity(@Param("activityId") Integer activityId);
}
