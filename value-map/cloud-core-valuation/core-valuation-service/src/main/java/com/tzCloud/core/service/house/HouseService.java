package com.tzCloud.core.service.house;

import com.github.pagehelper.PageInfo;
import com.tzCloud.core.model.house.TMapHouseTransactionData;
import com.tzCloud.core.vo.house.HouseCoordinateStatisticsVO;
import com.tzCloud.facade.house.req.HouseReq;

import java.util.List;
import java.util.Map;

public interface HouseService {
    List<HouseCoordinateStatisticsVO>  getHouseTransactionList(HouseReq.HouseListReq req);

    TMapHouseTransactionData getHouseTransactionDetail(String id);

    Map<String, Object> getHouseAveragePrice(HouseReq.HouseListReq req);

    Map<String, Object> getHousePriceTrendList(HouseReq.HousePriceTrendReq req);

    PageInfo getHouseListByCoordinate(HouseReq.HouseListReq req);

    HouseCoordinateStatisticsVO getHousePriceByKeyWord(HouseReq.HouseListReq req);

    PageInfo getSixMonthHouseList(HouseReq.HouseListReq req);
}
