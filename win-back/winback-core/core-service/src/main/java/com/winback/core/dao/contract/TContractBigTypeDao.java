
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractBigTypeCondition;
import com.winback.core.model.contract.TContractBigType;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TContractBigType的基础操作Dao</p>
 * @ClassName: TContractBigTypeDao
 * @Description: TContractBigType基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractBigTypeDao extends BaseDao<TContractBigType,TContractBigTypeCondition>{
    List<TContractBigType> getFrontList();

    List<TContractBigType> getBackList();

    boolean isExist(String name);

    TContractBigType save(String name);

    TContractBigType findBy(String name);
}
