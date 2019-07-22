
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TDisposeProviderApply;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TFundProviderApplyCondition;
import com._360pai.core.model.account.TFundProviderApply;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFundProviderApply数据层的基础操作</p>
 * @ClassName: TFundProviderApplyMapper
 * @Description: TFundProviderApply数据层的基础操作
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
@Mapper
public interface TFundProviderApplyMapper extends BaseMapper<TFundProviderApply, TFundProviderApplyCondition>{
    List<TFundProviderApply> getList(Map<String, Object> params);
}
