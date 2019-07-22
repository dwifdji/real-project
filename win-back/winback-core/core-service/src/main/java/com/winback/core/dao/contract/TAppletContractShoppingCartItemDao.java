
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TAppletContractShoppingCartItemCondition;
import com.winback.core.model.contract.TAppletContractShoppingCartItem;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model.contract.TContractShoppingCartItem;

import java.util.List;

/**
 * <p>TAppletContractShoppingCartItem的基础操作Dao</p>
 * @ClassName: TAppletContractShoppingCartItemDao
 * @Description: TAppletContractShoppingCartItem基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
public interface TAppletContractShoppingCartItemDao extends BaseDao<TAppletContractShoppingCartItem,TAppletContractShoppingCartItemCondition>{
    List<TAppletContractShoppingCartItem> findBy(Integer shoppingCartId);

    TAppletContractShoppingCartItem findBy(Integer shoppingCartId, Integer contractId);

    int clearShoppingCart(Integer shoppingCartId);
}
