package com._360pai.core.service.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigResp;

import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:21
 */
public interface WithfudigConfigService {
    List<WithfudigConfigResp> getConfigList();
    PageUtils.Page getConfigListWithPages(ServiceConfigReq.ConfigList req);

    int delConfigById(WithfudigConfigReq.DelConfigData req);

    int updateConfig(WithfudigConfigReq.UpdateConfigData req);

    int addConfig(WithfudigConfigReq.AddConfigData req);
}
