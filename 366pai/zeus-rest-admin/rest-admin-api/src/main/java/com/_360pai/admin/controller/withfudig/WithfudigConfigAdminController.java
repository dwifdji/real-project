package com._360pai.admin.controller.withfudig;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.WithfudigConfigFacade;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
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
public class WithfudigConfigAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigConfigFacade withfudigConfigFacade;

    @Reference(version = "1.0.0")
    ServiceConfigFacade serviceConfigFacade;

    /**
     * 描述 修改总成功条数
     *
     * @author : whisky_vip
     * @date : 2018/9/6 17:10
     */
    @PostMapping("/admin/withfudig/updateTotalNum")
    public ResponseModel updateTotalNum(@RequestBody ServiceConfigReq.UpdateTotalNum req) {
        Assert.notNull(req.getTotalNum(), "totalNum 参数不对");
        req.setOperatorId(loadCurLoginId());
        int count = serviceConfigFacade.updateTotalNum(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 修改总金额
     *
     * @author : whisky_vip
     * @date : 2018/9/6 17:12
     */
    @PostMapping("/admin/withfudig/updateTotalAmount")
    public ResponseModel updateTotalAmount(@RequestBody ServiceConfigReq.UpdateTotalAmount req) {
        Assert.notNull(req.getTotalAmount(), "totalAmount 参数不对");
        req.setOperatorId(loadCurLoginId());
        int count = serviceConfigFacade.updateTotalAmount(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述: 获取配资乐汇总数据
     * 成功笔数  累计配资额
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:36
     */
    @PostMapping("/admin/withfudig/getTotalData")
    public ResponseModel getTotalData() {
        WithfudigConfigTotalResp resp = serviceConfigFacade.getTotalData();
        return ResponseModel.succ(resp);
    }

    /**
     * 描述: 成功配资数据 列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @RequiresPermissions("yygl_pdysz:pzlpdy_list")
    @PostMapping("/admin/withfudig/getConfigListWithPages")
    public ResponseModel getConfigList(@RequestBody ServiceConfigReq.ConfigList req) {
        PageUtils.Page  result = withfudigConfigFacade.getConfigListWithPages(req);
        return ResponseModel.succ(result);
    }

    /**
     * 描述: 删除 成功配资数据
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/delConfig")
    public ResponseModel delConfigSuccess(@RequestBody WithfudigConfigReq.DelConfigData req) {
        Assert.notNull(req.getConfigId(), "configId 参数不对");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigConfigFacade.delConfigById(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述: 删除 成功配资数据
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/addConfig")
    public ResponseModel addConfig(@RequestBody WithfudigConfigReq.AddConfigData req) {
        Assert.notNull(req.getDescription(), "description 参数不对");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigConfigFacade.addConfig(req);
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述:修改 成功配资数据
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/updateConfig")
    public ResponseModel updateConfig(@RequestBody WithfudigConfigReq.UpdateConfigData req) {
        Assert.notNull(req.getConfigId(), "configId 参数不对");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigConfigFacade.updateConfig(req);
        return ResponseModel.wrapCount(count);
    }


}
