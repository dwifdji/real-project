
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractShoppingCartCondition;
import com.winback.core.model.contract.TContractShoppingCart;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TContractShoppingCart的基础操作Dao</p>
 * @ClassName: TContractShoppingCartDao
 * @Description: TContractShoppingCart基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractShoppingCartDao extends BaseDao<TContractShoppingCart,TContractShoppingCartCondition>{
    TContractShoppingCart findBy(Integer accountId);

    List<Integer> getContractIdList(Integer accountId);

    boolean isInShoppingCart(Integer accountId, Integer contractId);

    void migrateFromAppletToApp(Integer appletShoppingCartId, Integer shoppingCartId);

    TContractShoppingCart create(Integer accountId);
}
