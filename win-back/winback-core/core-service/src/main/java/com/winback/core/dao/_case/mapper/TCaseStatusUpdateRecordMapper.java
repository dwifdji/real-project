
package com.winback.core.dao._case.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseStatusUpdateRecordCondition;
import com.winback.core.model._case.TCaseStatusUpdateRecord;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseStatusUpdateRecord数据层的基础操作</p>
 * @ClassName: TCaseStatusUpdateRecordMapper
 * @Description: TCaseStatusUpdateRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
@Mapper
public interface TCaseStatusUpdateRecordMapper extends BaseMapper<TCaseStatusUpdateRecord, TCaseStatusUpdateRecordCondition>{

    List<TCaseStatusUpdateRecord> getPrecheckRecord(String caseId);

    List<TCaseStatusUpdateRecord> getRiskcheckRecord(String caseId);
    List<TCaseStatusUpdateRecord> getSignContractRecord(String caseId);
    List<TCaseStatusUpdateRecord> getStepRecordListByCaseId(@Param("caseId") String caseId, @Param("recordType") String recordType);



}
