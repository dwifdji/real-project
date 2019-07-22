
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.InstructionsContentCondition;
import com._360pai.core.model.assistant.InstructionsContent;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>InstructionsContent数据层的基础操作</p>
 * @ClassName: InstructionsContentMapper
 * @Description: InstructionsContent数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface InstructionsContentMapper extends BaseMapper<InstructionsContent, InstructionsContentCondition>{

    int deleteInstructionsContent(@Param("paramId") Integer paramsId);
}
