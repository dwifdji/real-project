package com._360pai.seimi.service.Impl;

import com._360pai.seimi.dao.AliPmHouseDao;
import com._360pai.seimi.model.AliPmHouseLoan;
import com._360pai.seimi.service.AliPmHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AliPmHouseServiceImpl implements AliPmHouseService {

    @Autowired
    private AliPmHouseDao aliPmHouseDao;

    @Override
    public void saveAliPmHouseList(ArrayList<AliPmHouseLoan> aliPmHouses) {
        aliPmHouseDao.save(aliPmHouses);
    }

    @Override
    public Integer getAliPmHouseByTitle(String title) {


        return aliPmHouseDao.getAliPmHouseByTitle(title);

    }
}
