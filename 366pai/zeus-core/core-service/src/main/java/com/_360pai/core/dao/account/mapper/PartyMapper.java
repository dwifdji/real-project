
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.PartyCondition;
import com._360pai.core.model.account.Party;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>Party数据层的基础操作</p>
 * @ClassName: PartyMapper
 * @Description: Party数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface PartyMapper extends BaseMapper<Party, PartyCondition>{

}
