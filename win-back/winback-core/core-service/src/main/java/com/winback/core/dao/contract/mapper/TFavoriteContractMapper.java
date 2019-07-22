
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContract;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TFavoriteContractCondition;
import com.winback.core.model.contract.TFavoriteContract;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TFavoriteContract数据层的基础操作</p>
 * @ClassName: TFavoriteContractMapper
 * @Description: TFavoriteContract数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TFavoriteContractMapper extends BaseMapper<TFavoriteContract, TFavoriteContractCondition>{
    List<TContract> getFavoriteContractList(Map<String, Object> params);

    List<Integer> getContractIdList(@Param("accountId") Integer accountId);

    int migrateFromAppletToApp(@Param("extBindId") Integer extBindId, @Param("accountId") Integer accountId);

    Integer favoriteContractCount(@Param("accountId") Integer accountId);
}
