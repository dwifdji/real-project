
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TAppletContractShoppingCartCondition;
import com.winback.core.model.contract.TAppletContractShoppingCart;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAppletContractShoppingCart数据层的基础操作</p>
 * @ClassName: TAppletContractShoppingCartMapper
 * @Description: TAppletContractShoppingCart数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
@Mapper
public interface TAppletContractShoppingCartMapper extends BaseMapper<TAppletContractShoppingCart, TAppletContractShoppingCartCondition>{

    List<Integer> getContractIdList(@Param("extBindId") Integer extBindId);

    Integer isInShoppingCart(@Param("extBindId") Integer extBindId, @Param("contractId") Integer contractId);
}
