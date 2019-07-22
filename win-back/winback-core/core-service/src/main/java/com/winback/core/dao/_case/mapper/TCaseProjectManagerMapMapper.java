
package com.winback.core.dao._case.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseProjectManagerMapCondition;
import com.winback.core.model._case.TCaseProjectManagerMap;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TCaseProjectManagerMap数据层的基础操作</p>
 * @ClassName: TCaseProjectManagerMapMapper
 * @Description: TCaseProjectManagerMap数据层的基础操作
 * @author Generator
 * @date 2019年05月06日 15时34分52秒
 */
@Mapper
public interface TCaseProjectManagerMapMapper extends BaseMapper<TCaseProjectManagerMap, TCaseProjectManagerMapCondition>{
    int countCaseNum(Integer accountId);

    int countSuccessCaseNum(Integer accountId);
}
