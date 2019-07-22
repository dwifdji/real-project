
package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.account.TDisposeProviderApply;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TDisposeProviderCondition;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TDisposeProvider数据层的基础操作</p>
 * @ClassName: TDisposeProviderMapper
 * @Description: TDisposeProvider数据层的基础操作
 * @author Generator
 * @date 2018年08月27日 15时03分04秒
 */
@Mapper
public interface TDisposeProviderMapper extends BaseMapper<TDisposeProvider, TDisposeProviderCondition>{
    List<TDisposeProvider> getList(Map<String, Object> params);

    List<TDisposeProvider> getRecommendList(Map<String, Object> params);

    List<TDisposeProvider> findByName(@Param("name") String name);
}
