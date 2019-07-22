package com._360pai.core.service.assistant.impl;

import com._360pai.core.condition.assistant.ProvinceCondition;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.service.assistant.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 10:00
 */
@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceDao provinceDao;

    @Override
    public Province getById(Integer provinceId) {
        ProvinceCondition condition = new ProvinceCondition();
        condition.setId(provinceId);
        return provinceDao.selectFirst(condition);
    }

    @Override
    public List<Province> getAllProvince() {
        ProvinceCondition condition = new ProvinceCondition();
        return provinceDao.selectList(condition);
    }
}