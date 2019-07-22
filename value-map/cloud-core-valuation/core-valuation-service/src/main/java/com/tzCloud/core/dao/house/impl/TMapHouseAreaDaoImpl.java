
package com.tzCloud.core.dao.house.impl;

import javax.annotation.Resource;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.house.TMapHouseAreaCondition;
import com.tzCloud.core.dao.house.TMapHouseAreaDao;
import com.tzCloud.core.dao.house.mapper.TMapHouseAreaMapper;
import com.tzCloud.core.model.house.TMapHouseArea;
import org.springframework.stereotype.Service;



@Service
public class TMapHouseAreaDaoImpl extends AbstractDaoImpl<TMapHouseArea, TMapHouseAreaCondition, BaseMapper<TMapHouseArea,TMapHouseAreaCondition>> implements TMapHouseAreaDao {
	
	@Resource
	private TMapHouseAreaMapper tMapHouseAreaMapper;
	
	@Override
	protected BaseMapper<TMapHouseArea, TMapHouseAreaCondition> daoSupport() {
		return tMapHouseAreaMapper;
	}

	@Override
	protected TMapHouseAreaCondition blankCondition() {
		return new TMapHouseAreaCondition();
	}

}
