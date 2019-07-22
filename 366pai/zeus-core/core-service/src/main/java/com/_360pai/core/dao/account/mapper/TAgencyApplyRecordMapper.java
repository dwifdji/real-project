
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TAgency;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TAgencyApplyRecordCondition;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAgencyApplyRecord数据层的基础操作</p>
 * @ClassName: TAgencyApplyRecordMapper
 * @Description: TAgencyApplyRecord数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TAgencyApplyRecordMapper extends BaseMapper<TAgencyApplyRecord, TAgencyApplyRecordCondition>{
    List<TAgencyApplyRecord> getList(Map<String, Object> params);
}
