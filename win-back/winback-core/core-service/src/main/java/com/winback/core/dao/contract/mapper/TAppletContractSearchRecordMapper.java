
package com.winback.core.dao.contract.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TAppletContractSearchRecordCondition;
import com.winback.core.model.contract.TAppletContractSearchRecord;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAppletContractSearchRecord数据层的基础操作</p>
 * @ClassName: TAppletContractSearchRecordMapper
 * @Description: TAppletContractSearchRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
@Mapper
public interface TAppletContractSearchRecordMapper extends BaseMapper<TAppletContractSearchRecord, TAppletContractSearchRecordCondition>{

    List<String> getKeywordList(@Param("extBindId") Integer extBindId);

    int clearSearchRecord(@Param("extBindId") Integer extBindId);

    int deleteSearchRecord(@Param("extBindId") Integer extBindId, @Param("keyword") String keyword);
}
