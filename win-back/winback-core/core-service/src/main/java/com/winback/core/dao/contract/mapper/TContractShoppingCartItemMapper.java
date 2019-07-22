
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractShoppingCartItemCondition;
import com.winback.core.model.contract.TContractShoppingCartItem;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TContractShoppingCartItem数据层的基础操作</p>
 * @ClassName: TContractShoppingCartItemMapper
 * @Description: TContractShoppingCartItem数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractShoppingCartItemMapper extends BaseMapper<TContractShoppingCartItem, TContractShoppingCartItemCondition>{
    int clearShoppingCart(@Param("shoppingCartId") Integer shoppingCartId);
}
