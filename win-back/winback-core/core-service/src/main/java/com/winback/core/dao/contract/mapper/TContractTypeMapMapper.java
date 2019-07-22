
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContractType;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractTypeMapCondition;
import com.winback.core.model.contract.TContractTypeMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TContractTypeMap数据层的基础操作</p>
 * @ClassName: TContractTypeMapMapper
 * @Description: TContractTypeMap数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractTypeMapMapper extends BaseMapper<TContractTypeMap, TContractTypeMapCondition>{
    String getFullName(@Param("contractId")  Integer contractId);

    TContractType getContractType(Integer contractId);
}
