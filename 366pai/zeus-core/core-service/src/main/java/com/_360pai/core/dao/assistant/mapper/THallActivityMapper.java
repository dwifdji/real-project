
package com._360pai.core.dao.assistant.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.THallActivityCondition;
import com._360pai.core.model.assistant.THallActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>THallActivity数据层的基础操作</p>
 * @ClassName: THallActivityMapper
 * @Description: THallActivity数据层的基础操作
 * @author Generator
 * @date 2018年09月18日 16时47分54秒
 */
@Mapper
public interface THallActivityMapper extends BaseMapper<THallActivity, THallActivityCondition>{

    List<Map> selectHallActivityList(@Param("haId") Integer id);
}
