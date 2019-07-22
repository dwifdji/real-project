
package com.tzCloud.core.dao.house;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.house.TMapHouseTransactionDataCondition;
import com.tzCloud.core.model.house.TMapHouseTransactionData;
import com.tzCloud.core.vo.house.FirstPriceVO;
import com.tzCloud.core.vo.house.HouseCoordinateStatisticsVO;
import com.tzCloud.core.vo.house.HousePriceTrendVO;
import com.tzCloud.core.vo.house.HouseTransactionListVO;
import com.tzCloud.facade.house.req.HouseReq;

import java.util.List;
import java.util.Map;

/**
 * <p>TMapHouseTransactionData的基础操作Dao</p>
 * @ClassName: TMapHouseTransactionDataDao
 * @Description: TMapHouseTransactionData基础操作的Dao
 * @author Generator
 * @date 2019年06月14日 09时52分19秒
 */
public interface TMapHouseTransactionDataDao extends BaseDao<TMapHouseTransactionData,TMapHouseTransactionDataCondition> {

    List<HouseCoordinateStatisticsVO>  getHouseTransactionList(Map<String, String> params);

    String getHouseAveragePrice(Map<String, String> params);

    List<HousePriceTrendVO> getHousePriceTrendList(Map<String, String> params);

    List<HouseTransactionListVO> getHouseListByCoordinate(Map<String, String> params);

    String getAllAreaTotalPrice(Map<String, String> params);

    FirstPriceVO getHouseFirstPrice(Map<String, String> params);

    FirstPriceVO getHouseFirstPriceByKeyWord(Map<String, String> params);

    HouseCoordinateStatisticsVO getHousePriceByKeyWord(Map<String, String> params);

    HouseCoordinateStatisticsVO getFirstHouse(Map<String, String> params);

    List<HouseTransactionListVO> getSixMonthHouseList(Map<String, String> params);
}
