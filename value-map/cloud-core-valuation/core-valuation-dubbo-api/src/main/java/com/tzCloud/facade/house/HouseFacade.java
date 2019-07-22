package com.tzCloud.facade.house;


import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.facade.house.req.HouseReq;

public interface HouseFacade {

    ResponseModel getHouseTransactionList(HouseReq.HouseListReq req);

    ResponseModel getHouseTransactionDetail(HouseReq.HouseListReq req);

    ResponseModel getHouseAveragePrice(HouseReq.HouseListReq req);

    ResponseModel getHousePriceTrendList(HouseReq.HousePriceTrendReq req);

    ResponseModel getHouseListByCoordinate(HouseReq.HouseListReq req);

    ResponseModel getHousePriceByKeyWord(HouseReq.HouseListReq req);

    ResponseModel getSixMonthHouseList(HouseReq.HouseListReq req);
}
