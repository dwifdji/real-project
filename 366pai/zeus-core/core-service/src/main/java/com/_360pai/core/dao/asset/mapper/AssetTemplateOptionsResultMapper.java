
package com._360pai.core.dao.asset.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.AssetTemplateOptionsResultCondition;
import com._360pai.core.model.asset.AssetTemplateOptionsResult;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>AssetTemplateOptionsResult数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateOptionsResultMapper
 * @Description: AssetTemplateOptionsResult数据层的基础操作
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetTemplateOptionsResultMapper extends BaseMapper<AssetTemplateOptionsResult, AssetTemplateOptionsResultCondition> {
    int deleteForGroupId(@Param("groupId") Integer groupId);
}
