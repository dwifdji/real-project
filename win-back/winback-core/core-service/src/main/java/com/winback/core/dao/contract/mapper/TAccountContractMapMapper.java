
package com.winback.core.dao.contract.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.contract.TAccountContractMapCondition;
import com.winback.core.model.contract.TAccountContractMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAccountContractMap数据层的基础操作</p>
 * @ClassName: TAccountContractMapMapper
 * @Description: TAccountContractMap数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TAccountContractMapMapper extends BaseMapper<TAccountContractMap, TAccountContractMapCondition>{
    List<Integer> getContractIdList(@Param("accountId") Integer accountId);
}
