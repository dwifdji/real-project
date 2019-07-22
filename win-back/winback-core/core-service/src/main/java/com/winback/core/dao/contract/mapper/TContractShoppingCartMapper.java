
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractShoppingCartCondition;
import com.winback.core.model.contract.TContractShoppingCart;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TContractShoppingCart数据层的基础操作</p>
 * @ClassName: TContractShoppingCartMapper
 * @Description: TContractShoppingCart数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractShoppingCartMapper extends BaseMapper<TContractShoppingCart, TContractShoppingCartCondition>{
    List<Integer> getContractIdList(@Param("accountId") Integer accountId);

    Integer isInShoppingCart(@Param("accountId") Integer accountId, @Param("contractId") Integer contractId);

    int migrateFromAppletToApp(@Param("appletShoppingCartId") Integer appletShoppingCartId, @Param("shoppingCartId") Integer shoppingCartId);
}
