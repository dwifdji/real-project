
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TAccount;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.model.account.TUserApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TUserApplyRecord数据层的基础操作</p>
 * @ClassName: TUserApplyRecordMapper
 * @Description: TUserApplyRecord数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TUserApplyRecordMapper extends BaseMapper<TUserApplyRecord, TUserApplyRecordCondition>{

    List<TUserApplyRecord> getList(Map<String, Object> params);

}
