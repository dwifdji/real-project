
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractCondition;
import com.winback.core.model.contract.TContract;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TContract数据层的基础操作</p>
 * @ClassName: TContractMapper
 * @Description: TContract数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractMapper extends BaseMapper<TContract, TContractCondition>{
    List<TContract> getFrontList(Map<String, Object> params);

    List<TContract> getPossessedContractList(Map<String, Object> params);

    List<TContract> getBackgroundList(Map<String, Object> params);

    int addDownloadCount(Integer id);

    int addFavoriteCount(Integer id);

    int subFavoriteCount(Integer id);

    int addViewCount(Integer id);

    int addPurchaseCount(Integer id);

    List<TContract> findByNameContractTypeId(@Param("name") String name, @Param("contractTypeId") Integer contractTypeId);

    List<TContract> getWrongList(Map<String, Object> params);
}
