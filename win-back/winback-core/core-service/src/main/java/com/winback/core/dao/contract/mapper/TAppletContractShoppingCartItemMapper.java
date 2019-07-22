
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TAppletContractShoppingCartItemCondition;
import com.winback.core.model.contract.TAppletContractShoppingCartItem;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TAppletContractShoppingCartItem数据层的基础操作</p>
 * @ClassName: TAppletContractShoppingCartItemMapper
 * @Description: TAppletContractShoppingCartItem数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
@Mapper
public interface TAppletContractShoppingCartItemMapper extends BaseMapper<TAppletContractShoppingCartItem, TAppletContractShoppingCartItemCondition>{
    int clearShoppingCart(@Param("shoppingCartId") Integer shoppingCartId);
}
