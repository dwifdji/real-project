
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TPersonaCondition;
import com._360pai.core.model.account.TPersona;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TPersona数据层的基础操作</p>
 * @ClassName: TPersonaMapper
 * @Description: TPersona数据层的基础操作
 * @author Generator
 * @date 2018年08月29日 13时21分37秒
 */
@Mapper
public interface TPersonaMapper extends BaseMapper<TPersona, TPersonaCondition> {
    List<TPersona> getList(Map<String, Object> params);
}
