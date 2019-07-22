
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TCompanyApplyRecordCondition;
import com._360pai.core.model.account.TCompanyApplyRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TCompanyApplyRecord数据层的基础操作</p>
 * @ClassName: TCompanyApplyRecordMapper
 * @Description: TCompanyApplyRecord数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TCompanyApplyRecordMapper extends BaseMapper<TCompanyApplyRecord, TCompanyApplyRecordCondition>{
    List<TCompanyApplyRecord> getList(Map<String, Object> params);
}
