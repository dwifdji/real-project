
package com.winback.core.dao.contract.mapper;

import com.winback.core.vo.operate.CaseBriefVO;
import com.winback.core.vo.operate.ContractTypeVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractTypeCondition;
import com.winback.core.model.contract.TContractType;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TContractType数据层的基础操作</p>
 * @ClassName: TContractTypeMapper
 * @Description: TContractType数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractTypeMapper extends BaseMapper<TContractType, TContractTypeCondition>{
    String getFullName(Integer id);

    List<ContractTypeVO> getContractTypeList();
}
