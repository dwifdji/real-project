
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TAccount;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TLawyerApplyRecordCondition;
import com.winback.core.model.account.TLawyerApplyRecord;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TLawyerApplyRecord数据层的基础操作</p>
 * @ClassName: TLawyerApplyRecordMapper
 * @Description: TLawyerApplyRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
@Mapper
public interface TLawyerApplyRecordMapper extends BaseMapper<TLawyerApplyRecord, TLawyerApplyRecordCondition>{
    List<TLawyerApplyRecord> getList(Map<String, Object> params);
}
