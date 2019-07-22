package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.activity.AuctionActivityAlbumCondition;
import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com._360pai.core.dao.activity.AuctionActivityAlbumDao;
import com._360pai.core.dao.assistant.StickyAuctionActivityAlbumDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com._360pai.core.model.assistant.StickyAuctionActivityAlbum;
import com._360pai.core.service.assistant.StickyAuctionActivityAlbumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/20 14:58
 */

@Service
public class StickyAuctionActivityAlbumServiceImpl implements StickyAuctionActivityAlbumService {

    @Autowired
    private StickyAuctionActivityAlbumDao stickyAuctionActivityAlbumDao;
    @Autowired
    private AuctionActivityAlbumDao auctionActivityAlbumDao;


    @Override
    public PageInfo getAllByPage(int page, int perPage, StickyAuctionActivityAlbumCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<StickyAuctionActivityAlbum> list = stickyAuctionActivityAlbumDao.selectStickyAlbumList();
        return new PageInfo<>(list);
    }

    @Override
    public int deleteById(Integer id) {
        StickyAuctionActivityAlbum stickyAuctionActivityAlbumById = findStickyAuctionActivityAlbumById(id);
        if (stickyAuctionActivityAlbumById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的专场不存在");
        }
        return stickyAuctionActivityAlbumDao.deleteById(id);
    }

    @Override
    public int insert(Long albumId, Long orderNumber) {
        AuctionActivityAlbum stickyAuctionActivityAlbumByAlbumId = findAuctionActivityAlbumByAlbumId(albumId);
        if (stickyAuctionActivityAlbumByAlbumId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "专场不存在");
        }
        StickyAuctionActivityAlbum model = new StickyAuctionActivityAlbum();
        model.setAlbumId(albumId.intValue());
        model.setOrderNumber(orderNumber.intValue());
        return stickyAuctionActivityAlbumDao.insert(model);
    }

    @Override
    public PageInfo auctionOrder(int page, int perPage, StickyAuctionActivityAlbumCondition condition, String order_number_) {
        PageHelper.startPage(1, 3);
        if (StringUtils.isNotBlank(order_number_)) {
            PageHelper.orderBy(order_number_);
        }
        List<StickyAuctionActivityAlbum> list = stickyAuctionActivityAlbumDao.selectStickyAlbumList();
        return new PageInfo<>(list);
    }

    private AuctionActivityAlbum findAuctionActivityAlbumByAlbumId(Long albumId) {
        AuctionActivityAlbumCondition condition = new AuctionActivityAlbumCondition();
        condition.setId(Math.toIntExact(albumId));
        return auctionActivityAlbumDao.selectOneResult(condition);
    }

    private StickyAuctionActivityAlbum findStickyAuctionActivityAlbumById(Integer id) {
        StickyAuctionActivityAlbumCondition condition = new StickyAuctionActivityAlbumCondition();
        condition.setId(id);
        return stickyAuctionActivityAlbumDao.selectOneResult(condition);
    }
}