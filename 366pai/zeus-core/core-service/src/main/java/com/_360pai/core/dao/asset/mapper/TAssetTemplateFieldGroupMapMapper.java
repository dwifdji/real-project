
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetTemplateFieldGroupMapCondition;
import com._360pai.core.model.asset.TAssetTemplateFieldGroupMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetTemplateFieldGroupMap数据层的基础操作</p>
 * @ClassName: TAssetTemplateFieldGroupMapMapper
 * @Description: TAssetTemplateFieldGroupMap数据层的基础操作
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
@Mapper
public interface TAssetTemplateFieldGroupMapMapper extends BaseMapper<TAssetTemplateFieldGroupMap, TAssetTemplateFieldGroupMapCondition>{

    List<Map> getTemplateGroup(@Param("paramsId") Integer paramsId);
}
