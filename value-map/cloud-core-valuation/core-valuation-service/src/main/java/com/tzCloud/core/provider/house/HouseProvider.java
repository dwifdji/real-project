package com.tzCloud.core.provider.house;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.core.model.house.TMapHouseTransactionData;
import com.tzCloud.core.service.house.HouseService;
import com.tzCloud.core.vo.house.HouseCoordinateStatisticsVO;
import com.tzCloud.facade.house.HouseFacade;
import com.tzCloud.facade.house.req.HouseReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service(version = "1.0.0")
public class HouseProvider implements HouseFacade {

    @Autowired
    private HouseService houseService;

    @Override
    public ResponseModel getHouseTransactionList(HouseReq.HouseListReq req) {

        List<HouseCoordinateStatisticsVO> houseCoordinateStatisticsVOS = houseService.getHouseTransactionList(req);

        if(houseCoordinateStatisticsVOS == null || houseCoordinateStatisticsVOS.size() <= 0) {
            return ResponseModel.succ(new ArrayList<>());
        }

        return ResponseModel.succ(houseCoordinateStatisticsVOS);
    }

    @Override
    public ResponseModel getHouseTransactionDetail(HouseReq.HouseListReq req) {

        TMapHouseTransactionData houseTransactionData = houseService.getHouseTransactionDetail(req.getId());

        return ResponseModel.succ(houseTransactionData);
    }

    @Override
    public ResponseModel getHouseAveragePrice(HouseReq.HouseListReq req) {

        Map<String, Object> result = houseService.getHouseAveragePrice(req);

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getHousePriceTrendList(HouseReq.HousePriceTrendReq req) {
        Map<String, Object> result = houseService.getHousePriceTrendList(req);


        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getHouseListByCoordinate(HouseReq.HouseListReq req) {

        PageInfo pageInfo = houseService.getHouseListByCoordinate(req);

        PageInfoResp page = new PageInfoResp(pageInfo);

        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel getHousePriceByKeyWord(HouseReq.HouseListReq req) {
        HouseCoordinateStatisticsVO houseCoordinateStatisticsVO = houseService.getHousePriceByKeyWord(req);

        return ResponseModel.succ(houseCoordinateStatisticsVO);
    }


    @Override
    public ResponseModel getSixMonthHouseList(HouseReq.HouseListReq req) {
        PageInfo pageInfo = houseService.getSixMonthHouseList(req);

        PageInfoResp page = new PageInfoResp(pageInfo);

        return ResponseModel.succ(page);
    }
}
