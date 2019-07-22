
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TAgency;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TDisposeProviderApplyCondition;
import com._360pai.core.model.account.TDisposeProviderApply;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TDisposeProviderApply数据层的基础操作</p>
 * @ClassName: TDisposeProviderApplyMapper
 * @Description: TDisposeProviderApply数据层的基础操作
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
@Mapper
public interface TDisposeProviderApplyMapper extends BaseMapper<TDisposeProviderApply, TDisposeProviderApplyCondition>{
    List<TDisposeProviderApply> getList(Map<String, Object> params);
}
