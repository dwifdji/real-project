package com._360pai.core.facade.assistant.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: CityResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 30/08/2018 14:02
 */
public class CityResp implements Serializable {

    private List<Province> provinces = Collections.EMPTY_LIST;

    private List<City> cities = Collections.EMPTY_LIST;

    private List<Area> areas = Collections.EMPTY_LIST;

    private List<Town> towns = Collections.EMPTY_LIST;

    public List<Town> getTowns() {
        return towns;
    }

    public void setTowns(List<Town> towns) {
        this.towns = towns;
    }

    public List<Province> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<Province> provinces) {
        this.provinces = provinces;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    @Data
    public static class Province implements Serializable {
        private String id;
        private String name;
    }

    @Data
    public static class City implements Serializable {
        private String id;
        private String name;
    }

    @Data
    public static class Area implements Serializable {
        private String id;
        private String name;
    }

    @Data
    public static class Town implements Serializable {
        private String id;
        private String name;
    }
}
