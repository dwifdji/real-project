package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.disposal.DisposeShowFacade;
import com._360pai.core.facade.disposal.resp.RecommendProviderResp;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.disposal.DisposeShowService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-10-30 13:21
 */
@Component
@Service(version = "1.0.0")
public class DisposeShowProvider implements DisposeShowFacade {

    @Autowired
    private AssetService assetService;
    @Autowired
    private DisposeShowService disposeShowService;
    @Autowired
    private AuctionActivityService auctionActivityService;

    @Override
    public PageInfoResp getRecommendProvider(Integer activityId, int pageNum, int pageSize) {
        // 如果只有一个城市则取一级合伙人数据 否则只取我要处置数据
        Asset asset = auctionActivityService.getAssetByActivityId(activityId);
        //if (null == asset.getCityId()) {
        //    throw new BusinessException("参数异常");
        //}
        if (StringUtils.isNotBlank(asset.getCityId())) {
            String[] split = asset.getCityId().split(",");
            if (split.length> 0) {
                if (StringUtils.isNotBlank(split[0])) {
                    Integer cityId = Integer.valueOf(split[0]);
                    PageInfo pageInfo;
                    if (split.length == 1) {
                        // 只有一个城市，包含一级服务商
                        pageInfo = disposeShowService.getFirstLevelShowProvider(cityId, activityId, pageNum, pageSize);
                    } else {
                        // 多个城市，不包含一级服务商
                        pageInfo = disposeShowService.getShowProvider(activityId, pageNum, pageSize);
                    }
                    pageInfo.setList(convertRecommendProvider(pageInfo.getList()));

                    return new PageInfoResp(pageInfo);
                }

            }
        }
        return new PageInfoResp();
    }

    private List<RecommendProviderResp> convertRecommendProvider(List<TDisposeProvider> source) {
        List<RecommendProviderResp> target = new LinkedList<>();
        source.forEach(u -> {
            RecommendProviderResp recommendProviderResp = new RecommendProviderResp();
            BeanUtils.copyProperties(u, recommendProviderResp);
            recommendProviderResp.setProviderId(u.getId());
            target.add(recommendProviderResp);
        });
        return target;
    }
}
