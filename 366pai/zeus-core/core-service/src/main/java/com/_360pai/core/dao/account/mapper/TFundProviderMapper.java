
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TDisposeProvider;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TFundProviderCondition;
import com._360pai.core.model.account.TFundProvider;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFundProvider数据层的基础操作</p>
 * @ClassName: TFundProviderMapper
 * @Description: TFundProvider数据层的基础操作
 * @author Generator
 * @date 2018年08月27日 15时03分03秒
 */
@Mapper
public interface TFundProviderMapper extends BaseMapper<TFundProvider, TFundProviderCondition>{
    List<TFundProvider> getList(Map<String, Object> params);
}
