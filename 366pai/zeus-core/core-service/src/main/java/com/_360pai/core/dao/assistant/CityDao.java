
package com._360pai.core.dao.assistant;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.CityCondition;
import com._360pai.core.model.assistant.City;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.web.EnrollingCityVO;

import java.util.List;

/**
 * <p>City的基础操作Dao</p>
 * @ClassName: CityDao
 * @Description: City基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface CityDao extends BaseDao<City,CityCondition>{

    Object pageCities();

    Object pageProvinces();

	List<EnrollingCityVO> getCityList();

    City selectById(Integer id);

    List<ProvinceCityVo> getProvinceCityList(List<String> ids);

    String getName(Integer id);

    String getName(String id);

    Integer getProvinceId(Integer id);

    Integer getProvinceId(String id);

    List<ProvinceCityVo> getAllProvinceCityList();

    List<ProvinceCityVo> getAllProvinceCityAreaList();

    City findByName(String name);
}
