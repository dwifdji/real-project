
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.dao.assistant.mapper.BannerMapper;
import com._360pai.core.model.assistant.Banner;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.BannerDao;

@Service
public class BannerDaoImpl extends AbstractDaoImpl<Banner, BannerCondition, BaseMapper<Banner,BannerCondition>> implements BannerDao{
	
	@Resource
	private BannerMapper bannerMapper;
	
	@Override
	protected BaseMapper<Banner, BannerCondition> daoSupport() {
		return bannerMapper;
	}

	@Override
	protected BannerCondition blankCondition() {
		return new BannerCondition();
	}

    @Override
    public int deleteBanner(Integer paramsId) {
        return bannerMapper.deleteBanner(paramsId);
    }
}
