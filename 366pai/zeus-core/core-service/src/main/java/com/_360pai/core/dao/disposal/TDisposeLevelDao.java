
package com._360pai.core.dao.disposal;

import com._360pai.core.condition.disposal.TDisposeLevelCondition;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TDisposeLevel的基础操作Dao</p>
 * @ClassName: TDisposeLevelDao
 * @Description: TDisposeLevel基础操作的Dao
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
public interface TDisposeLevelDao extends BaseDao<TDisposeLevel,TDisposeLevelCondition>{
    TDisposeLevel getRegionLevelProvider(Map<String, Object> map);
    List<TDisposeLevel> getFirstLevelCityPartnerByParam(Map<String, Object> params);
    List<TDisposeLevel> getCityPartnerByParam(Map<String, Object> params);
    List<TDisposeLevel> getCityPartnerByParamWithoutFirstLevel(Map<String, Object> params);

    TDisposeLevel getValidByProviderId(Integer providerId);

    PageInfo getListByPage(int page, int perPage);
}
