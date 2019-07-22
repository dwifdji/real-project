
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.AgencyChannelAgentCondition;
import com._360pai.core.model.account.AgencyChannelAgent;
import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.model.account.PartyChannelAgent;

import java.util.List;

/**
 * <p>AgencyChannelAgent的基础操作Dao</p>
 * @ClassName: AgencyChannelAgentDao
 * @Description: AgencyChannelAgent基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分12秒
 */
public interface AgencyChannelAgentDao extends BaseDao<AgencyChannelAgent,AgencyChannelAgentCondition>{

    AgencyChannelAgent getByAgencyId(Integer agencyId);

    List<AgencyChannelAgent> getByChannelAgentAgencyId(Integer channelAgentAgencyId);

    int deleteById(Integer id);
}
