
package com.winback.core.dao.contract;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.contract.TContractTypeCondition;
import com.winback.core.model.contract.TContractType;
import com.winback.core.vo.operate.ContractTypeVO;

import java.util.List;

/**
 * <p>TContractType的基础操作Dao</p>
 * @ClassName: TContractTypeDao
 * @Description: TContractType基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractTypeDao extends BaseDao<TContractType,TContractTypeCondition>{
    List<TContractType> findFrontBy(Integer bigTypeId);

    List<TContractType> findBackBy(Integer bigTypeId);

    String getName(Integer id);

    List<ContractTypeVO> getContractTypeList();

    boolean isExist(String name);

    TContractType save(String name, Integer bigTypeId);

    TContractType findBy(String name);

    TContractType findBy(String name, Integer bigTypeId);
}
