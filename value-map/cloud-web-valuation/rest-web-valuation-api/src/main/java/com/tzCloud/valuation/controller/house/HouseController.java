package com.tzCloud.valuation.controller.house;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.enums.HouseEnum;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.facade.house.HouseFacade;
import com.tzCloud.facade.house.req.HouseReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/open/valuationMap/house")
public class HouseController {

    @Reference(version = "1.0.0")
    private HouseFacade houseFacade;
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    /**
     * 根据经纬度获取半径
     * 一千米范围之内的数据
     * @param req
     * @return
     */
    @GetMapping("/getHouseTransactionList")
    public ResponseModel getHouseTransactionList(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getLat()) || StringUtils.isBlank(req.getLng())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setRadius(StringUtils.isBlank(req.getRadius())? "1": req.getRadius());

        return houseFacade.getHouseTransactionList(req);
    }


    /**
     * 根据经纬度获取半径
     * 一千米范围之内的最近六个月数据
     * @param req
     * @return
     */
    @GetMapping("/getSixMonthHouseList")
    public ResponseModel getSixMonthHouseList(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getLat()) || StringUtils.isBlank(req.getLng())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setRadius(StringUtils.isBlank(req.getRadius())? "1": req.getRadius());

        return houseFacade.getSixMonthHouseList(req);
    }


    /**
     * 根据经纬度获取半径
     * 一千米范围之内的数据
     * @param req
     * @return
     */
    @GetMapping("/getHouseListByCoordinate")
    public ResponseModel getHouseListByCoordinate(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getLat()) || StringUtils.isBlank(req.getLng())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return houseFacade.getHouseListByCoordinate(req);
    }


    /**
     * 根据id获取具体房源信息
     * @param req
     * @return
     */
    @GetMapping("/getHouseTransactionDetail")
    public ResponseModel getHouseTransactionDetail(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return houseFacade.getHouseTransactionDetail(req);
    }


    /**
     * 获取平均价
     * @param req
     * @return
     */
    @GetMapping("/getHouseAveragePrice")
    public ResponseModel getHouseAveragePrice(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getLat()) || StringUtils.isBlank(req.getLng())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setRadius(StringUtils.isBlank(req.getRadius())? "1": req.getRadius());

        return houseFacade.getHouseAveragePrice(req);
    }


    /**
     * 获取房价走势
     * @param req
     * @return
     */
    @GetMapping("/getHousePriceTrendList")
    public ResponseModel getHousePriceTrendList(HouseReq.HousePriceTrendReq req) {
        if(StringUtils.isBlank(req.getProvince()) ||
                StringUtils.isBlank(req.getCity())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setYear(StringUtils.isBlank(req.getYear()) ? HouseEnum.MonthLimit.HALF_YEAR.getKey() : req.getYear());


        return houseFacade.getHousePriceTrendList(req);
    }


    /**
     * 获取房价走势
     * @param req
     * @return
     */
    @GetMapping("/getHousePriceByKeyWord")
    public ResponseModel getHousePriceByKeyWord(HouseReq.HouseListReq req) {
        if(StringUtils.isBlank(req.getLat()) || StringUtils.isBlank(req.getLng())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setRadius(StringUtils.isBlank(req.getRadius())? "1": req.getRadius());


        return houseFacade.getHousePriceByKeyWord(req);
    }



}
