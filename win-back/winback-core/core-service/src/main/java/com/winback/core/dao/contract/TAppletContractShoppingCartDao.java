
package com.winback.core.dao.contract;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.contract.TAppletContractShoppingCartCondition;
import com.winback.core.model.contract.TAppletContractShoppingCart;

import java.util.List;

/**
 * <p>TAppletContractShoppingCart的基础操作Dao</p>
 * @ClassName: TAppletContractShoppingCartDao
 * @Description: TAppletContractShoppingCart基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
public interface TAppletContractShoppingCartDao extends BaseDao<TAppletContractShoppingCart,TAppletContractShoppingCartCondition>{
    TAppletContractShoppingCart findBy(Integer extBindId);

    List<Integer> getContractIdList(Integer extBindId);

    boolean isInShoppingCart(Integer extBindId, Integer contractId);

    TAppletContractShoppingCart create(Integer extBindId);
}
