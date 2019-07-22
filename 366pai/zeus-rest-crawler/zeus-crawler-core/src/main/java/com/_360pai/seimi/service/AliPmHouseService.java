package com._360pai.seimi.service;

import com._360pai.seimi.model.AliPmHouseLoan;

import java.util.ArrayList;

public interface AliPmHouseService {

    void saveAliPmHouseList(ArrayList<AliPmHouseLoan> aliPmHouses);

    Integer getAliPmHouseByTitle(String title);
}
