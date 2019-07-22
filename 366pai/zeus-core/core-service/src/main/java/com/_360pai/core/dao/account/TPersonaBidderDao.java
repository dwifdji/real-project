
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPersonaBidderCondition;
import com._360pai.core.model.account.TPersonaBidder;

/**
 * <p>TPersonaBidder的基础操作Dao</p>
 * @ClassName: TPersonaBidderDao
 * @Description: TPersonaBidder基础操作的Dao
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
public interface TPersonaBidderDao extends BaseDao<TPersonaBidder, TPersonaBidderCondition> {

    TPersonaBidder getByPersonaId(Integer personaId);
}
