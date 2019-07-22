
package com._360pai.core.dao.assistant.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.CityCondition;
import com._360pai.core.model.assistant.City;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com._360pai.core.vo.enrolling.web.EnrollingCityVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>City数据层的基础操作</p>
 * @ClassName: CityMapper
 * @Description: City数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface CityMapper extends BaseMapper<City, CityCondition>{

    List<Map<String,String>> pageCities();

    List<Map<String,String>> pageProvinces();

	List<EnrollingCityVO> getCityList();

    List<ProvinceCityVo> getProvinceCityList(@Param("cityIdList")List<String> ids);


    List<ProvinceCityVo> getAllProvinceCityList();

    List<ProvinceCityVo> getAllProvinceCityAreaList();

}
