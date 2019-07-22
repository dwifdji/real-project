
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.PartyChannelAgentCondition;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>PartyChannelAgent数据层的基础操作</p>
 * @ClassName: PartyChannelAgentMapper
 * @Description: PartyChannelAgent数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface PartyChannelAgentMapper extends BaseMapper<PartyChannelAgent, PartyChannelAgentCondition>{

    int deleteById(@Param("id") Integer id);
}
