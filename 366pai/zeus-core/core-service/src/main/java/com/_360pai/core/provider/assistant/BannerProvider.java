package com._360pai.core.provider.assistant;

import com._360pai.core.condition.assistant.BannerCondition;
import com._360pai.core.facade.assistant.BannerFacade;
import com._360pai.core.facade.assistant.req.BannerReq;
import com._360pai.core.model.assistant.Banner;
import com._360pai.core.service.assistant.BannerService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: BannerProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 18:52
 */
@Component
@Service(version = "1.0.0")
public class BannerProvider implements BannerFacade {

    @Autowired
    private BannerService bannerService;

    @Override
    public PageInfo selectBanner(BannerReq req) {
        BannerCondition condition = new BannerCondition();
        condition.setType(req.getType());
        condition.setIsAgency(req.getIsAgency());
        condition.setIsOnline(req.getIsOnline());
        return bannerService.selectBanner(req.getPage(), req.getPerPage(), condition, "order_num asc");
    }

    @Override
    public int addBanner(BannerReq req) {
        return bannerService.addBanner(copyBanner(req));
    }

    @Override
    public int editBanner(BannerReq req) {
        return bannerService.editBanner(copyBanner(req));
    }

    @Override
    public int deleteBanner(BannerReq req) {
        return bannerService.deleteBanner(copyBanner(req));
    }

    private Banner copyBanner(BannerReq req) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(req, banner);
        return banner;
    }
}
