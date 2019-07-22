
package com.tzCloud.core.dao.account;

import com.tzCloud.core.condition.account.TAccountMembershipCardCondition;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAccountMembershipCard的基础操作Dao</p>
 * @ClassName: TAccountMembershipCardDao
 * @Description: TAccountMembershipCard基础操作的Dao
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
public interface TAccountMembershipCardDao extends BaseDao<TAccountMembershipCard,TAccountMembershipCardCondition>{
    List<TAccountMembershipCard> findAvailableCard(Integer id);
}
