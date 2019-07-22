
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.AuctionActivityAlbumCondition;
import com._360pai.core.dao.activity.AuctionActivityAlbumDao;
import com._360pai.core.dao.activity.mapper.AuctionActivityAlbumMapper;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class AuctionActivityAlbumDaoImpl extends AbstractDaoImpl<AuctionActivityAlbum, AuctionActivityAlbumCondition, BaseMapper<AuctionActivityAlbum,AuctionActivityAlbumCondition>> implements AuctionActivityAlbumDao{
	
	@Resource
	private AuctionActivityAlbumMapper auctionActivityAlbumMapper;
	
	@Override
	protected BaseMapper<AuctionActivityAlbum, AuctionActivityAlbumCondition> daoSupport() {
		return auctionActivityAlbumMapper;
	}

	@Override
	protected AuctionActivityAlbumCondition blankCondition() {
		return new AuctionActivityAlbumCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AuctionActivityAlbum> list = auctionActivityAlbumMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<AuctionActivityAlbum> getStickyList() {
		return auctionActivityAlbumMapper.getStickyList();
	}

	@Override
	public PageInfo getRelatedActivityList(int page, int perPage, Integer albumId) {
		PageHelper.startPage(page, perPage);
		List<AuctionActivity> list = auctionActivityAlbumMapper.getRelatedActivityList(albumId);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getFrontRelatedActivityList(int page, int perPage, Integer albumId) {
		PageHelper.startPage(page, perPage);
		List<Map> list = auctionActivityAlbumMapper.getFrontRelatedActivityList(albumId);
		return new PageInfo<>(list);
	}
}
