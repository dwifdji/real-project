
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContract;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TAppletFavoriteContractCondition;
import com.winback.core.model.contract.TAppletFavoriteContract;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletFavoriteContract数据层的基础操作</p>
 * @ClassName: TAppletFavoriteContractMapper
 * @Description: TAppletFavoriteContract数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
@Mapper
public interface TAppletFavoriteContractMapper extends BaseMapper<TAppletFavoriteContract, TAppletFavoriteContractCondition>{
    List<TContract> getFavoriteContractList(Map<String, Object> params);

    List<Integer> getContractIdList(@Param("extBindId") Integer extBindId);

    Integer favoriteContractCount(@Param("extBindId") Integer extBindId);
}
