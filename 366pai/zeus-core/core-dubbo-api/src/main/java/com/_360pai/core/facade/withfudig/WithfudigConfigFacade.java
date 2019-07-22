package com._360pai.core.facade.withfudig;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigResp;

import java.util.List;

/**
 * 描述 配资乐 运营设置
 *
 * @author : whisky_vip
 * @date : 2018/9/6 15:54
 */
public interface WithfudigConfigFacade {
    List<WithfudigConfigResp> getConfigList();
    /**
     * 描述 获取 配置的成功数据列表
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:28
     */
    PageUtils.Page getConfigListWithPages(ServiceConfigReq.ConfigList req);

    /**
     * 描述 删除 config 数据
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:29
     */
    int delConfigById(WithfudigConfigReq.DelConfigData req);

    /**
     * 描述 更新 config 数据
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:30
     */
    int updateConfig(WithfudigConfigReq.UpdateConfigData req);

    /**
     * 描述 新增 config 数据
     *
     * @author : whisky_vip
     * @date : 2018/9/7 10:14
     */
    int addConfig(WithfudigConfigReq.AddConfigData req);
}
