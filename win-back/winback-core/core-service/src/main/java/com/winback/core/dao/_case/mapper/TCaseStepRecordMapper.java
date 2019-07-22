
package com.winback.core.dao._case.mapper;

import com.winback.core.dto._case.CaseStepRecordDto;
import com.winback.core.vo._case.TCaseStepRecordVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseStepRecordCondition;
import com.winback.core.model._case.TCaseStepRecord;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TCaseStepRecord数据层的基础操作</p>
 * @ClassName: TCaseStepRecordMapper
 * @Description: TCaseStepRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月28日 18时47分10秒
 */
@Mapper
public interface TCaseStepRecordMapper extends BaseMapper<TCaseStepRecord, TCaseStepRecordCondition>{

    List<TCaseStepRecordVO> getCaseStepRecordList(CaseStepRecordDto params);
}
