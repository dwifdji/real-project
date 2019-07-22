package com._360pai.core.provider.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.WithfudigConfigFacade;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigResp;
import com._360pai.core.service.withfudig.WithfudigConfigService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 描述 配资乐 设置数据
 *
 * @author : whisky_vip
 * @date : 2018/9/6 15:54
 */
@Component
@Service(version = "1.0.0")
public class WithfudigConfigProvider implements WithfudigConfigFacade {

    @Autowired
    private WithfudigConfigService withfudigConfigService;

    @Override
    public List<WithfudigConfigResp> getConfigList() {
        return withfudigConfigService.getConfigList();
    }

    @Override
    public PageUtils.Page getConfigListWithPages(ServiceConfigReq.ConfigList req) {
        return withfudigConfigService.getConfigListWithPages(req);
    }

    @Override
    public int delConfigById(WithfudigConfigReq.DelConfigData req) {
        return withfudigConfigService.delConfigById(req);
    }

    @Override
    public int updateConfig(WithfudigConfigReq.UpdateConfigData req) {
        return withfudigConfigService.updateConfig(req);
    }

    @Override
    public int addConfig(WithfudigConfigReq.AddConfigData req) {
        return withfudigConfigService.addConfig(req);
    }
}
