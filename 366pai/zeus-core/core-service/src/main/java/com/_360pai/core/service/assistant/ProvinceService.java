package com._360pai.core.service.assistant;


import com._360pai.core.model.assistant.Province;

import java.util.List;

public interface ProvinceService{


    Province getById(Integer provinceId);

    /**
     *
     *获取所以的省份列表
     */
    List<Province> getAllProvince();
}