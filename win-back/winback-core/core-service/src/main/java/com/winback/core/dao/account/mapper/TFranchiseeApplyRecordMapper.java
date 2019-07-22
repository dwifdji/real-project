
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TLawyerApplyRecord;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TFranchiseeApplyRecordCondition;
import com.winback.core.model.account.TFranchiseeApplyRecord;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFranchiseeApplyRecord数据层的基础操作</p>
 * @ClassName: TFranchiseeApplyRecordMapper
 * @Description: TFranchiseeApplyRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Mapper
public interface TFranchiseeApplyRecordMapper extends BaseMapper<TFranchiseeApplyRecord, TFranchiseeApplyRecordCondition>{
    List<TFranchiseeApplyRecord> getList(Map<String, Object> params);
}
