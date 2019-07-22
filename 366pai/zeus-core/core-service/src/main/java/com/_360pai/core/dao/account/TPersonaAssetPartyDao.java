
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaAssetPartyCondition;
import com._360pai.core.model.account.TPersonaAssetParty;

/**
 * <p>TPersonaAssetParty的基础操作Dao</p>
 * @ClassName: TPersonaAssetPartyDao
 * @Description: TPersonaAssetParty基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaAssetPartyDao extends BaseDao<TPersonaAssetParty, TPersonaAssetPartyCondition> {
    TPersonaAssetParty getByPersonaId(Integer personaId);
}
