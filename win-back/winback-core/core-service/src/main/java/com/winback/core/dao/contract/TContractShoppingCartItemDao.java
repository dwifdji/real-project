
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractShoppingCartItemCondition;
import com.winback.core.model.contract.TContractShoppingCartItem;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TContractShoppingCartItem的基础操作Dao</p>
 * @ClassName: TContractShoppingCartItemDao
 * @Description: TContractShoppingCartItem基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractShoppingCartItemDao extends BaseDao<TContractShoppingCartItem,TContractShoppingCartItemCondition>{
    List<TContractShoppingCartItem> findBy(Integer shoppingCartId);

    TContractShoppingCartItem findBy(Integer shoppingCartId, Integer contractId);

    int clearShoppingCart(Integer shoppingCartId);
}
