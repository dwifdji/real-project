package com._360pai.core.provider.disposal;

import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2018-10-30 13:24
 */
@Component
@Service(version = "1.0.0")
public class DisposeLevelProvider implements DisposeLevelFacade {

    @Autowired
    private DisposeLevelService disposeLevelService;
    @Autowired
    private AuctionActivityService auctionActivityService;


    @Override
    public boolean isFirstLevelPartner(Integer providerId, String cityId) {
        return disposeLevelService.isFirstLevel(providerId, cityId);
    }

    @Override
    public Integer getFirstLevelPartner(String cityId) {
        return disposeLevelService.getFirstLevelLowOfficeByCityId(cityId);
    }

    @Override
    public Integer getFirstLevelPartnerByActivityId(Integer activityId) {
        Asset asset = auctionActivityService.getAssetByActivityId(activityId);
        if (null != asset) {

            String[] split = asset.getCityId().split(",");

            if (split.length > 1) {
                // 暂不支持多地区标的
                return null;
            }

            Integer partner = disposeLevelService.getFirstLevelLowOfficeByCityId(split[0]);
            return partner;
        }
        return null;
    }

    @Override
    public boolean isFirstLevelPartner(Integer partyId) {
        return disposeLevelService.isFirstLevel(partyId);
    }
}
