
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractDownloadRecordCondition;
import com.winback.core.model.contract.TContractDownloadRecord;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TContractDownloadRecord数据层的基础操作</p>
 * @ClassName: TContractDownloadRecordMapper
 * @Description: TContractDownloadRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractDownloadRecordMapper extends BaseMapper<TContractDownloadRecord, TContractDownloadRecordCondition>{
    String getLatestEmail(@Param("accountId") Integer accountId);
}
