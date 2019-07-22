package com._360pai.core.facade.assistant.resp;

import com._360pai.core.facade.assistant.vo.CityVo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: CityListResp
 * @ProjectName zeus
 * @Description:
 * @date 06/09/2018 14:24
 */
public class CityListResp implements Serializable {

    private Map<String, CityVo> cities;

    public Map<String, CityVo> getCities() {
        return cities;
    }

    public void setCities(Map<String, CityVo> cities) {
        this.cities = cities;
    }
}
