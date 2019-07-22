
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.AuctionActivityAlbumMapCondition;
import com._360pai.core.dao.activity.mapper.AuctionActivityAlbumMapMapper;
import com._360pai.core.model.activity.AuctionActivityAlbumMap;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.AuctionActivityAlbumMapDao;

import java.util.List;

@Service
public class AuctionActivityAlbumMapDaoImpl extends AbstractDaoImpl<AuctionActivityAlbumMap, AuctionActivityAlbumMapCondition, BaseMapper<AuctionActivityAlbumMap,AuctionActivityAlbumMapCondition>> implements AuctionActivityAlbumMapDao{
	
	@Resource
	private AuctionActivityAlbumMapMapper auctionActivityAlbumMapMapper;
	
	@Override
	protected BaseMapper<AuctionActivityAlbumMap, AuctionActivityAlbumMapCondition> daoSupport() {
		return auctionActivityAlbumMapMapper;
	}

	@Override
	protected AuctionActivityAlbumMapCondition blankCondition() {
		return new AuctionActivityAlbumMapCondition();
	}

	@Override
	public Integer getAuctionActivityCountByAlbumId(Integer id) {
		return auctionActivityAlbumMapMapper.getAuctionActivityCountByAlbumId(id);
	}

	@Override
	public int delete(AuctionActivityAlbumMap model) {
		return auctionActivityAlbumMapMapper.delete(model);
	}

	@Override
	public AuctionActivityAlbumMap getByAlbumIdAndActivityId(Integer albumId, Integer activityId, String activityType) {
		AuctionActivityAlbumMapCondition condition = new AuctionActivityAlbumMapCondition();
		condition.setAlbumId(albumId);
		condition.setActivityId(activityId);
		condition.setActivityType(activityType);
		List<AuctionActivityAlbumMap> list = auctionActivityAlbumMapMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
