
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.PartyChannelAgentCondition;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>PartyChannelAgent的基础操作Dao</p>
 * @ClassName: PartyChannelAgentDao
 * @Description: PartyChannelAgent基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface PartyChannelAgentDao extends BaseDao<PartyChannelAgent,PartyChannelAgentCondition>{
    PartyChannelAgent getByPartyId(Integer partyId);

    List<PartyChannelAgent> getByChannelAgentPartyId(Integer channelAgentPartyId);

    int deleteById(Integer id);
}
