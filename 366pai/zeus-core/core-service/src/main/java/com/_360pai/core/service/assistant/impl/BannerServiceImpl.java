package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.dao.assistant.BannerDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.assistant.Banner;
import com._360pai.core.service.assistant.BannerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    @Autowired
    private BannerDao bannerDao;


    @Override
    public PageInfo selectBanner(int page, int perPage, BannerCondition condition, String order_num) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(order_num)) {
            PageHelper.orderBy(order_num);
        }
        List<Banner> banners = bannerDao.selectList(condition);
        return new PageInfo<>(banners);
    }

    @Override
    public int addBanner(Banner params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        if (StringUtils.isBlank(params.getLinkUrl()))
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "banner链接不能为空");
        return bannerDao.insert(params);
    }

    @Override
    public int editBanner(Banner params) {
        Banner bannerById = findBannerById(params);
        if (bannerById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的banner不存在");
        }
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        if (StringUtils.isBlank(params.getLinkUrl()))
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "banner链接不能为空");
        return bannerDao.updateById(params);
    }

    @Override
    public int deleteBanner(Banner params) {
        Banner bannerById = findBannerById(params);
        if (bannerById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的banner不存在");
        }
        return bannerDao.deleteBanner(params.getId());
    }

    private Banner findBannerById(Banner params) {
        BannerCondition condition = new BannerCondition();
        condition.setId(params.getId());
        return bannerDao.selectOneResult(condition);
    }
}