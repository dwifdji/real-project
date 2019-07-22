package com._360pai.core.facade.assistant;

import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;

/**
 * 描述 服务设置
 *
 * @author : whisky_vip
 * @date : 2018/9/7 09:47
 */
public interface ServiceConfigFacade {
    /**
     * 描述 获取汇总数据
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:28
     */
    WithfudigConfigTotalResp getTotalData();

    /**
     * 描述 根据type 获取value值
     *
     * @author : whisky_vip
     * @date : 2018/10/10 16:46
     */
    String selectByConfigType(ServiceConfigEnum serviceConfigEnum) ;

    /**
     * 描述 更新汇总数据 totalNum 总条数
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:28
     */
    int updateTotalNum(ServiceConfigReq.UpdateTotalNum req);

    /**
     * 描述 更新汇总数据 totalAmount 总资产
     *
     * @author : whisky_vip
     * @date : 2018/9/7 9:29
     */
    int updateTotalAmount(ServiceConfigReq.UpdateTotalAmount req);
}
