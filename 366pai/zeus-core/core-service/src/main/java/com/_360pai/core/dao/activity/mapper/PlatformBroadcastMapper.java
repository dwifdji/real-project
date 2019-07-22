
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.PlatformBroadcastCondition;
import com._360pai.core.model.activity.PlatformBroadcast;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>PlatformBroadcast数据层的基础操作</p>
 * @ClassName: PlatformBroadcastMapper
 * @Description: PlatformBroadcast数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface PlatformBroadcastMapper extends BaseMapper<PlatformBroadcast, PlatformBroadcastCondition>{

    List<PlatformBroadcast> getBroadcastList();
}
