
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TPartyCondition;
import com._360pai.core.model.account.TParty;

/**
 * <p>TParty的基础操作Dao</p>
 * @ClassName: TPartyDao
 * @Description: TParty基础操作的Dao
 * @author Generator
 * @date 2018年08月20日 19时07分11秒
 */
public interface TPartyDao extends BaseDao<TParty,TPartyCondition>{
    TParty selectById(Integer id);

    int updateById(TParty party);
}
