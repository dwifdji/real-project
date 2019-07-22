
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.AgencyChannelAgentCondition;
import com._360pai.core.model.account.AgencyChannelAgent;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>AgencyChannelAgent数据层的基础操作</p>
 * @ClassName: AgencyChannelAgentMapper
 * @Description: AgencyChannelAgent数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
@Mapper
public interface AgencyChannelAgentMapper extends BaseMapper<AgencyChannelAgent, AgencyChannelAgentCondition>{
    int deleteById(@Param("id") Integer id);
}
