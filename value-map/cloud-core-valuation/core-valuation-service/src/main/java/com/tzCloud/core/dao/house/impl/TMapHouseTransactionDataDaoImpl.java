
package com.tzCloud.core.dao.house.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.house.TMapHouseTransactionDataCondition;
import com.tzCloud.core.dao.house.TMapHouseTransactionDataDao;
import com.tzCloud.core.dao.house.mapper.TMapHouseTransactionDataMapper;
import com.tzCloud.core.model.house.TMapHouseTransactionData;
import com.tzCloud.core.vo.house.FirstPriceVO;
import com.tzCloud.core.vo.house.HouseCoordinateStatisticsVO;
import com.tzCloud.core.vo.house.HousePriceTrendVO;
import com.tzCloud.core.vo.house.HouseTransactionListVO;
import com.tzCloud.facade.house.req.HouseReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TMapHouseTransactionDataDaoImpl extends AbstractDaoImpl<TMapHouseTransactionData, TMapHouseTransactionDataCondition, BaseMapper<TMapHouseTransactionData,TMapHouseTransactionDataCondition>> implements TMapHouseTransactionDataDao {
	
	@Resource
	private TMapHouseTransactionDataMapper tMapHouseTransactionDataMapper;
	
	@Override
	protected BaseMapper<TMapHouseTransactionData, TMapHouseTransactionDataCondition> daoSupport() {
		return tMapHouseTransactionDataMapper;
	}

	@Override
	protected TMapHouseTransactionDataCondition blankCondition() {
		return new TMapHouseTransactionDataCondition();
	}

	@Override
	public List<HouseCoordinateStatisticsVO> getHouseTransactionList(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHouseTransactionList(params);
	}

	@Override
	public String getHouseAveragePrice(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHouseAveragePrice(params);
	}

	@Override
	public List<HousePriceTrendVO> getHousePriceTrendList(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHousePriceTrendList(params);
	}

	@Override
	public List<HouseTransactionListVO> getHouseListByCoordinate(Map<String, String> params) {

		return tMapHouseTransactionDataMapper.getHouseListByCoordinate(params);
	}

	@Override
	public String getAllAreaTotalPrice(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getAllAreaTotalPrice(params);
	}

	@Override
	public FirstPriceVO getHouseFirstPrice(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHouseFirstPrice(params);
	}

	@Override
	public FirstPriceVO getHouseFirstPriceByKeyWord(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHouseFirstPriceByKeyWord(params);
	}

	@Override
	public HouseCoordinateStatisticsVO getHousePriceByKeyWord(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getHousePriceByKeyWord(params);
	}

	@Override
	public HouseCoordinateStatisticsVO getFirstHouse(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getFirstHouse(params);
	}

	@Override
	public List<HouseTransactionListVO> getSixMonthHouseList(Map<String, String> params) {
		return tMapHouseTransactionDataMapper.getSixMonthHouseList(params);
	}


}
