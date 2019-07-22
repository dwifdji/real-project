package com._360pai.web.controller.withfudig;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.WithfudigConfigFacade;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;
import com._360pai.web.controller.AbstractController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 配资乐 设置
 *
 * @author : whisky_vip
 * @date : 2018/9/6 15:59
 */
@RestController
public class WithfudigConfigController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigConfigFacade withfudigConfigFacade;

    @Reference(version = "1.0.0")
    ServiceConfigFacade serviceConfigFacade;

    /**
     * 描述: 获取配资乐汇总数据
     * 成功笔数  累计配资额
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:36
     */
    @PostMapping("/open/withfudig/getTotalData")
    public ResponseModel getTotalData() {
        WithfudigConfigTotalResp resp = serviceConfigFacade.getTotalData();
        return ResponseModel.succ(resp);
    }

    /**
     * 描述: 成功配资数据列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/open/withfudig/getConfigList")
    public ResponseModel getConfigList(@RequestBody ServiceConfigReq.ConfigList req) {
        PageUtils.Page result = withfudigConfigFacade.getConfigListWithPages(req);
        return  ResponseModel.succ(result);
    }
}
