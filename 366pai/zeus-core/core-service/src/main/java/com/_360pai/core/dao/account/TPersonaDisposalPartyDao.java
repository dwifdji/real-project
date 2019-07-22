
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaDisposalPartyCondition;
import com._360pai.core.model.account.TPersonaDisposalParty;

/**
 * <p>TPersonaDisposalParty的基础操作Dao</p>
 * @ClassName: TPersonaDisposalPartyDao
 * @Description: TPersonaDisposalParty基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaDisposalPartyDao extends BaseDao<TPersonaDisposalParty, TPersonaDisposalPartyCondition> {
    TPersonaDisposalParty getByPersonaId(Integer personaId);
}
