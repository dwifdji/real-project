package com._360pai.core.vo.enrolling;

import com._360pai.core.model.assistant.City;

import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 15:00
 */
public class AllCitiyVo implements java.io.Serializable{


    private String id;//省份id

    private String name;//省份名称

    private List<City> cities;//省份城市


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
