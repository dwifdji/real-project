
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaFundingAgencyCondition;
import com._360pai.core.model.account.TPersonaFundingAgency;

/**
 * <p>TPersonaFundingAgency的基础操作Dao</p>
 * @ClassName: TPersonaFundingAgencyDao
 * @Description: TPersonaFundingAgency基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaFundingAgencyDao extends BaseDao<TPersonaFundingAgency, TPersonaFundingAgencyCondition> {
    TPersonaFundingAgency getByPersonaId(Integer personaId);
}
