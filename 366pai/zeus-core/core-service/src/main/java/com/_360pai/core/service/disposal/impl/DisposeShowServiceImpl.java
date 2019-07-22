package com._360pai.core.service.disposal.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.condition.disposal.TDisposeShowCondition;
import com._360pai.core.dao.account.TDisposeProviderDao;
import com._360pai.core.dao.assistant.TActivityServiceProviderDao;
import com._360pai.core.dao.disposal.TDisposeShowDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.disposal.DisposeShowService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author xiaolei
 * @create 2018-10-30 10:23
 */
@Service
public class DisposeShowServiceImpl implements DisposeShowService {

    @Autowired
    private TDisposeShowDao disposeShowDao;
    @Autowired
    private TDisposeProviderDao disposeProviderDao;
    @Autowired
    private TActivityServiceProviderDao activityServiceProviderDao;

    @Override
    public PageInfo getShowProvider(Integer activityId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TDisposeProvider> showProvider = disposeShowDao.getShowProvider(activityId);
        PageInfo<TDisposeProvider> pageInfo = new PageInfo<>(showProvider);
        return pageInfo;
    }

    @Override
    public PageInfo getFirstLevelShowProvider(Integer cityId, Integer activityId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TDisposeProvider> showProvider = disposeShowDao.getFirstLevelShowProvider(cityId, activityId);
        PageInfo<TDisposeProvider> pageInfo = new PageInfo<>(showProvider);
        return pageInfo;
    }

    @Override
    public TDisposeShow getShowByProviderIdAndActivityId(Integer providerId, Integer activityId) {
        TDisposeShowCondition condition = new TDisposeShowCondition();
        condition.setProviderId(providerId);
        condition.setActivityId(activityId);
        condition.setDelFlag(false);
        List<TDisposeShow> tDisposeShows = disposeShowDao.selectList(condition);
        return CollectionUtils.isNotEmpty(tDisposeShows) ? tDisposeShows.get(0) : null;
    }

    @Transactional
    @Override
    public int addProviderShow(Integer providerId, Integer activityId) {
        TDisposeProvider provider = disposeProviderDao.selectById(providerId);
        TDisposeShow show = new TDisposeShow();
        show.setProviderId(providerId);
        show.setActivityId(activityId);
        show.setCityId(StringUtils.isNotBlank(provider.getRegion()) ? Integer.valueOf(provider.getRegion()) : null);
        int insert = disposeShowDao.insert(show);
        if (insert == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TActivityServiceProvider activityServiceProvider = new TActivityServiceProvider();
        activityServiceProvider.setActivityId(activityId);
        activityServiceProvider.setActivityType(ActivityServiceProviderEnum.ActivityType.Auction.getKey());
        activityServiceProvider.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
        activityServiceProvider.setProviderId(providerId);
        activityServiceProvider.setSource(ActivityServiceProviderEnum.Source.Web.getKey());
        insert = activityServiceProviderDao.insert(activityServiceProvider);
        if (insert == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return insert;
    }
}
