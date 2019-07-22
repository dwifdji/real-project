package com.tzCloud.core.service.house.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.enums.HouseEnum;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.core.dao.house.TMapHouseTransactionDataDao;
import com.tzCloud.core.model.house.TMapHouseTransactionData;
import com.tzCloud.core.service.house.HouseService;
import com.tzCloud.core.utils.MapUtil;
import com.tzCloud.core.vo.house.FirstPriceVO;
import com.tzCloud.core.vo.house.HouseCoordinateStatisticsVO;
import com.tzCloud.core.vo.house.HousePriceTrendVO;
import com.tzCloud.core.vo.house.HouseTransactionListVO;
import com.tzCloud.facade.house.req.HouseReq;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class HouseServiceImpl implements HouseService {

    Logger log = LoggerFactory.getLogger(HouseServiceImpl.class);

    @Autowired
    private TMapHouseTransactionDataDao tMapHouseTransactionDataDao;


    @Override
    public  List<HouseCoordinateStatisticsVO>  getHouseTransactionList(HouseReq.HouseListReq req) {
        Map<String, String> params = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());
        params.put("useType", HouseEnum.UseType.getValueByKey(req.getAssetType()));
        params.put("lng", req.getLng());
        params.put("lat", req.getLat());
        params.put("radius", req.getRadius());
        if(StringUtils.isNotBlank(req.getSixMonFlag()) && "1".equals(req.getSixMonFlag())) {
            getTimeParams(params);
        }

        log.info("最新的参数数据是{}", params);
        List<HouseCoordinateStatisticsVO> houseCoordinateStatisticsVOS =
                tMapHouseTransactionDataDao.getHouseTransactionList(params);

        return houseCoordinateStatisticsVOS;
    }


    @Override
    public TMapHouseTransactionData getHouseTransactionDetail(String id) {
        TMapHouseTransactionData tMapHouseTransactionData = tMapHouseTransactionDataDao.selectById(id);
        tMapHouseTransactionData.setEndTime(tMapHouseTransactionData.getEndTime() == null ? null :
                tMapHouseTransactionData.getEndTime().replace(".","-"));
        tMapHouseTransactionData.setListingTime(tMapHouseTransactionData.getListingTime() == null ? null :
                tMapHouseTransactionData.getListingTime().replace(".","-"));

        return tMapHouseTransactionData;
    }


    @Override
    public Map<String, Object> getHouseAveragePrice(HouseReq.HouseListReq req) {
        Map<String, String> params = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());
        params.put("useType", HouseEnum.UseType.getValueByKey(req.getAssetType()));
        params.put("lng", new BigDecimal(req.getLng()).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
        params.put("lat", new BigDecimal(req.getLat()).setScale(6,BigDecimal.ROUND_HALF_UP).toString());
        params.put("radius", req.getRadius());
        params.put("keyWord", req.getKeyWord());

        log.info("最新的参数数据是{}", params);
        String averagePriceSt = tMapHouseTransactionDataDao.getHouseAveragePrice(params);

        BigDecimal averagePrice = null;
        if(StringUtils.isNotBlank(averagePriceSt)) {
            averagePrice = new BigDecimal(averagePriceSt).setScale(0,BigDecimal.ROUND_HALF_UP);
        }

        // 最近距离的成交价
        FirstPriceVO firstPriceVO = tMapHouseTransactionDataDao.getHouseFirstPriceByKeyWord(params);
        if(firstPriceVO == null){
            firstPriceVO = tMapHouseTransactionDataDao.getHouseFirstPrice(params);
        }

        if(firstPriceVO != null && StringUtils.isNotBlank(firstPriceVO.getFirstPrice())) {
            BigDecimal firstPrice = new BigDecimal(firstPriceVO.getFirstPrice()).setScale(0,BigDecimal.ROUND_HALF_UP);
            firstPriceVO.setFirstPrice(firstPrice.toString());
        }

        // 最近六个月成交均价
        params = getTimeParams(params);
        String sixMonthAveragePriceSt = tMapHouseTransactionDataDao.getHouseAveragePrice(params);
        BigDecimal sixMonthAveragePrice = null;
        if(StringUtils.isNotBlank(sixMonthAveragePriceSt)) {
            sixMonthAveragePrice = new BigDecimal(sixMonthAveragePriceSt).setScale(0,BigDecimal.ROUND_HALF_UP);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("averagePrice", averagePrice);
        result.put("firstPrice", firstPriceVO);
        result.put("sixMonthAveragePrice", sixMonthAveragePrice);

        return result;
    }

    private Map<String, String> getTimeParams(Map<String, String> params) {
        Date recentMonthDate = DateUtil.getRecentMonthDate(new Date(), -1);
        String endTime = DateUtil.format(recentMonthDate, DateUtil.SPECIAL_DATE_ONLY_PATTERN) + ".31";
        String recentDate = getRecentOnlyMonth(new Date(), -6);
        params.put("beginTime", recentDate);
        params.put("endTime", endTime);
        return params;
    }


    @Override
    public Map<String, Object>  getHousePriceTrendList(HouseReq.HousePriceTrendReq req) {
        Map<String, String> params = new HashMap<>();
        Map<String, Object> result = new HashMap<>();
        String city = req.getCity();
        String area = req.getArea();
        city =  (city.contains("市") || city.contains("区")) ? city.substring(0, city.length() -1): city;
        area =  area.contains("区") ? area.substring(0, area.length() -1): area;

        params.put("province", city);
        params.put("city", area);

        Date recentMonthDate = DateUtil.getRecentMonthDate(new Date(), -1);
        String recentDate = getRecentOnlyMonth(recentMonthDate, HouseEnum.MonthLimit.getValueByKey(req.getYear()));
        String endTime = DateUtil.format(recentMonthDate, DateUtil.SPECIAL_DATE_ONLY_PATTERN) + ".31";
        params.put("beginTime", recentDate);
        params.put("endTime", endTime);
        List<HousePriceTrendVO> housePriceTrendList = tMapHouseTransactionDataDao.getHousePriceTrendList(params);
        List<HousePriceTrendVO> newHousePriceTrendVOS = new ArrayList<>();
        if(housePriceTrendList == null || housePriceTrendList.size() <= 0) {
            newHousePriceTrendVOS = getNullHouseList(newHousePriceTrendVOS);
        }else{
            newHousePriceTrendVOS = getNewHousePriceTrendVOS(housePriceTrendList, newHousePriceTrendVOS);
        }

        recentDate = getRecentOnlyMonth(new Date(), HouseEnum.MonthLimit.getValueByKey(HouseEnum.MonthLimit.HALF_YEAR.getKey()));
        params.put("beginTime", recentDate);

        String totalPrice =  tMapHouseTransactionDataDao.getAllAreaTotalPrice(params);
        result.put("monthList", newHousePriceTrendVOS);
        result.put("risePercent", newHousePriceTrendVOS.get(5).getRisePercent());
        BigDecimal total = totalPrice != null ? new BigDecimal(totalPrice).setScale(0, BigDecimal.ROUND_HALF_UP) : null;
        result.put("totalPrice", total);

        return result;
    }


    private String getRecentOnlyMonth(Date date, int monthNum){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");

        c.setTime(date);
        c.add(Calendar.MONTH, monthNum);
        Date m = c.getTime();
        String mon = format.format(m) + ".01";

        return mon;
    }


    private List<HousePriceTrendVO> getNullHouseList(List<HousePriceTrendVO> newHousePriceTrendVOS) {
        for(int i = 6; i > 0; i--) {
            HousePriceTrendVO housePriceTrendVO = new HousePriceTrendVO(null, "0", "0", "0");
            String recentOnlyMonth = DateUtil.getRecentOnlyMonth(new Date(), -i);
            housePriceTrendVO.setMonth(recentOnlyMonth);
            newHousePriceTrendVOS.add(housePriceTrendVO);
        }

        return newHousePriceTrendVOS;
    }


    private List<HousePriceTrendVO> getNewHousePriceTrendVOS(
            List<HousePriceTrendVO> housePriceTrendList, List<HousePriceTrendVO> newHousePriceTrendVOS) {

        for(int j = 6; j > 0; j--) {
            String recentDate = DateUtil.getRecentOnlyMonth(new Date(),   -j);
            Integer flag = 0;
            for(int i = 0; i < housePriceTrendList.size(); i++) {
                if(i < housePriceTrendList.size() - 1) {
                    HousePriceTrendVO housePriceTrendModel = housePriceTrendList.get(i + 1);
                    String replace = recentDate.replace(".", "-");
                    if (replace.equals(housePriceTrendModel.getMonth())) {
                        newHousePriceTrendVOS.add(housePriceTrendModel);
                        flag = 1;
                    }
                }
            }
            if(flag == 0) {
                HousePriceTrendVO newHousePriceTrendVO = new HousePriceTrendVO(recentDate, "0", "0", null);
                newHousePriceTrendVOS.add(newHousePriceTrendVO);
            }

        }

        HousePriceTrendVO housePriceTrendVO = newHousePriceTrendVOS.get(4);
        HousePriceTrendVO housePriceTrendModel = newHousePriceTrendVOS.get(5);
        if("0".equals(housePriceTrendVO.getAvgPrice()) || "0".equals(housePriceTrendModel)) {
            housePriceTrendModel.setRisePercent("0");
        }else {
            BigDecimal bigDecimal = new BigDecimal(housePriceTrendVO.getAvgPrice());
            BigDecimal bigDecimalTwo = new BigDecimal(housePriceTrendModel.getAvgPrice());
            BigDecimal bigDecimalOne = new BigDecimal(1);
            BigDecimal bigDecimalThree = new BigDecimal(100);

            BigDecimal subtract = bigDecimalTwo.divide(bigDecimal, 4, BigDecimal.ROUND_HALF_UP).subtract(bigDecimalOne);
            housePriceTrendModel.setRisePercent(subtract.multiply(bigDecimalThree).setScale(2,BigDecimal.ROUND_HALF_UP).toString());
        }

        return newHousePriceTrendVOS;
    }

    @Override
    public PageInfo getHouseListByCoordinate(HouseReq.HouseListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        Map<String, String> params = new HashMap<>();
        params.put("lng", req.getLng());
        params.put("lat", req.getLat());
        params.put("useType", HouseEnum.UseType.getValueByKey(req.getAssetType()));

        if(StringUtils.isNotBlank(req.getSixMonFlag()) && "1".equals(req.getSixMonFlag())) {
            getTimeParams(params);
        }

        List<HouseTransactionListVO> houseTransactionListVOS =
                tMapHouseTransactionDataDao.getHouseListByCoordinate(params);

        if(houseTransactionListVOS != null && houseTransactionListVOS.size() > 0) {
            return new PageInfo(houseTransactionListVOS) ;
        }

        return new PageInfo(new ArrayList());
    }

    @Override
    public HouseCoordinateStatisticsVO getHousePriceByKeyWord(HouseReq.HouseListReq req) {
        Map<String, String> params = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());
        params.put("useType", HouseEnum.UseType.getValueByKey(req.getAssetType()));

        params.put("lng", req.getLng());
        params.put("lat", req.getLat());
        params.put("radius", req.getRadius());
        params.put("keyWord", req.getKeyWord());
        if(StringUtils.isNotBlank(req.getSixMonFlag()) && "1".equals(req.getSixMonFlag())) {
            getTimeParams(params);
        }

        HouseCoordinateStatisticsVO  houseCoordinateStatisticsVO =
                tMapHouseTransactionDataDao.getHousePriceByKeyWord(params);
        if(houseCoordinateStatisticsVO == null) {
            houseCoordinateStatisticsVO = tMapHouseTransactionDataDao.getFirstHouse(params);
        }
        return houseCoordinateStatisticsVO;
    }

    @Override
    public PageInfo getSixMonthHouseList(HouseReq.HouseListReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        Map<String, String> params = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());
        params.put("useType", HouseEnum.UseType.getValueByKey(req.getAssetType()));
        params.put("lng", req.getLng());
        params.put("lat", req.getLat());
        params.put("radius", req.getRadius());
        params = getTimeParams(params);

        List<HouseTransactionListVO> houseTransactionListVOS =
                tMapHouseTransactionDataDao.getSixMonthHouseList(params);
        if(houseTransactionListVOS != null && houseTransactionListVOS.size() > 0) {
            return new PageInfo(houseTransactionListVOS) ;
        }

        return new PageInfo(new ArrayList());
    }
}
