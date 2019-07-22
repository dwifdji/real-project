
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractSearchRecordCondition;
import com.winback.core.model.contract.TContractSearchRecord;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TContractSearchRecord数据层的基础操作</p>
 * @ClassName: TContractSearchRecordMapper
 * @Description: TContractSearchRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractSearchRecordMapper extends BaseMapper<TContractSearchRecord, TContractSearchRecordCondition>{
    List<String> getKeywordList(@Param("accountId") Integer accountId);

    int clearSearchRecord(@Param("accountId") Integer accountId);
}
