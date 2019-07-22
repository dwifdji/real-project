
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com._360pai.core.dao.assistant.mapper.StickyAuctionActivityAlbumMapper;
import com._360pai.core.model.assistant.StickyAuctionActivityAlbum;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.StickyAuctionActivityAlbumDao;

import java.util.List;

@Service
public class StickyAuctionActivityAlbumDaoImpl extends AbstractDaoImpl<StickyAuctionActivityAlbum, StickyAuctionActivityAlbumCondition, BaseMapper<StickyAuctionActivityAlbum, StickyAuctionActivityAlbumCondition>> implements StickyAuctionActivityAlbumDao {

    @Resource
    private StickyAuctionActivityAlbumMapper stickyAuctionActivityAlbumMapper;

    @Override
    protected BaseMapper<StickyAuctionActivityAlbum, StickyAuctionActivityAlbumCondition> daoSupport() {
        return stickyAuctionActivityAlbumMapper;
    }

    @Override
    protected StickyAuctionActivityAlbumCondition blankCondition() {
        return new StickyAuctionActivityAlbumCondition();
    }

	@Override
	public int deleteById(Integer id) {
		return stickyAuctionActivityAlbumMapper.deleteById(id);
	}
    @Override
    public List<StickyAuctionActivityAlbum> selectStickyAlbumList() {
        return stickyAuctionActivityAlbumMapper.selectStickyAlbumList();
    }

}
