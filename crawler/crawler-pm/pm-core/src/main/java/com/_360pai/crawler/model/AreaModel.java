package com._360pai.crawler.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AreaModel {

    private String area;

    private String city;

    private String city1;

    private String city2;

    public AreaModel() {
    }

    public AreaModel(String area, String city, String city1, String city2) {
        this.area = area;
        this.city = city;
        this.city1 = city1;
        this.city2 = city2;
    }
}
