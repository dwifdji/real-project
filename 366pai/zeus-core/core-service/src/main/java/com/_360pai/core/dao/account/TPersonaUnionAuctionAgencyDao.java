
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaUnionAuctionAgencyCondition;
import com._360pai.core.model.account.TPersonaUnionAuctionAgency;

/**
 * <p>TPersonaUnionAuctionAgency的基础操作Dao</p>
 * @ClassName: TPersonaUnionAuctionAgencyDao
 * @Description: TPersonaUnionAuctionAgency基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaUnionAuctionAgencyDao extends BaseDao<TPersonaUnionAuctionAgency, TPersonaUnionAuctionAgencyCondition> {
    TPersonaUnionAuctionAgency getByPersonaId(Integer personaId);
}
