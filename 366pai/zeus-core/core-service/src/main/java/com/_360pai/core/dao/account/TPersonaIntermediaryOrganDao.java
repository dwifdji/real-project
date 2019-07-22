
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaIntermediaryOrganCondition;
import com._360pai.core.model.account.TPersonaIntermediaryOrgan;

/**
 * <p>TPersonaIntermediaryOrgan的基础操作Dao</p>
 * @ClassName: TPersonaIntermediaryOrganDao
 * @Description: TPersonaIntermediaryOrgan基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaIntermediaryOrganDao extends BaseDao<TPersonaIntermediaryOrgan, TPersonaIntermediaryOrganCondition> {
    TPersonaIntermediaryOrgan getByPersonaId(Integer personaId);
}
