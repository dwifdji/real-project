
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.TServiceFollowCondition;
import com._360pai.core.model.assistant.TServiceFollow;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TServiceFollow数据层的基础操作</p>
 * @ClassName: TServiceFollowMapper
 * @Description: TServiceFollow数据层的基础操作
 * @author Generator
 * @date 2018年09月17日 10时09分27秒
 */
@Mapper
public interface TServiceFollowMapper extends BaseMapper<TServiceFollow, TServiceFollowCondition>{

    int removeFollowList(List<TServiceFollow> list);
}
