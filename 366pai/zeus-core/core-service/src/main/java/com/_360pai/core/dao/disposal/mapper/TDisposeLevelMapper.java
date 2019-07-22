
package com._360pai.core.dao.disposal.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.disposal.TDisposeLevelCondition;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TDisposeLevel数据层的基础操作</p>
 * @ClassName: TDisposeLevelMapper
 * @Description: TDisposeLevel数据层的基础操作
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
@Mapper
public interface TDisposeLevelMapper extends BaseMapper<TDisposeLevel, TDisposeLevelCondition>{
    TDisposeLevel getRegionLevelProvider(Map<String, Object> map);
    List<TDisposeLevel> getFirstLevelCityPartnerByParam(Map<String, Object> params);
    List<TDisposeLevel> getCityPartnerByParam(Map<String, Object> params);
    List<TDisposeLevel> getCityPartnerByParamWithoutFirstLevel(Map<String, Object> params);
}
