
package com.winback.core.dao.operate.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.dto.operate.AdvertisingSpaceDto;
import com.winback.core.vo.operate.AdvertisingSpaceListVO;
import com.winback.core.vo.operate.HomePageBannerVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.operate.TOperateBannerCondition;
import com.winback.core.dao.operate.mapper.TOperateBannerMapper;
import com.winback.core.model.operate.TOperateBanner;
import com.winback.core.dao.operate.TOperateBannerDao;

import java.util.List;

@Service
public class TOperateBannerDaoImpl extends AbstractDaoImpl<TOperateBanner, TOperateBannerCondition, BaseMapper<TOperateBanner,TOperateBannerCondition>> implements TOperateBannerDao{
	
	@Resource
	private TOperateBannerMapper tOperateBannerMapper;
	
	@Override
	protected BaseMapper<TOperateBanner, TOperateBannerCondition> daoSupport() {
		return tOperateBannerMapper;
	}

	@Override
	protected TOperateBannerCondition blankCondition() {
		return new TOperateBannerCondition();
	}

	@Override
	public List<AdvertisingSpaceListVO> getAdvertisingSpaceList(AdvertisingSpaceDto params) {
		return tOperateBannerMapper.getAdvertisingSpaceList(params);
	}

	@Override
	public List<TOperateBanner> getAdvertisingSpaceListTest(AdvertisingSpaceDto params) {
		return tOperateBannerMapper.getAdvertisingSpaceListTest(params);
	}

	@Override
	public List<HomePageBannerVO> getBannerList(AdvertisingSpaceDto params) {

		return tOperateBannerMapper.getBannerList(params);
	}

}
