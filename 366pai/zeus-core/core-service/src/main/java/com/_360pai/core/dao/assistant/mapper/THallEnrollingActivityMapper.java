
package com._360pai.core.dao.assistant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.THallEnrollingActivityCondition;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com._360pai.core.vo.enrolling.web.HomeEnrollingActivityVO;

/**
 * <p>THallEnrollingActivity数据层的基础操作</p>
 * @ClassName: THallEnrollingActivityMapper
 * @Description: THallEnrollingActivity数据层的基础操作
 * @author Generator
 * @date 2018年09月18日 16时47分54秒
 */
@Mapper
public interface THallEnrollingActivityMapper extends BaseMapper<THallEnrollingActivity, THallEnrollingActivityCondition>{

	List<HomeEnrollingActivityVO> getHallEnrollingActivities(@Param("hallId")Integer hallId);

}
