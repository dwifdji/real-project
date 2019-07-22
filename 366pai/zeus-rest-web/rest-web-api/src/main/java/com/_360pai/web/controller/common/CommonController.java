package com._360pai.web.controller.common;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.assistant.req.CommonReq;
import com._360pai.core.facade.assistant.resp.TokenResp;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：公共接口类
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/9/3 9:21
 */
@RestController
public class CommonController {

    @Reference(version = "1.0.0")
    private CommonFacade commonFacade;


    /**
     * 功能描述:获取tab值
     */
    @GetMapping(value = "/open/common/get_tab")
    public ResponseModel activityView(CommonReq.propertyReq req) {


        return commonFacade.getPropertyByType(req.getType());
    }


    /**
     * 获取七牛token信息
     */
    @GetMapping(value = "/common/get_qiniu_token")
    public ResponseModel enrollingActivities() {

        TokenResp resp = commonFacade.getQiNiuToken();
        return ResponseModel.succ(resp);

    }

    /**
     * 获取所有银行
     */
    @GetMapping(value = "/open/getAllBanks")
    public ResponseModel getAllBanks(@RequestParam(name="type", defaultValue = "0") String type) {
        return ResponseModel.succ(commonFacade.getAllBanks(type));
    }



    /**
     * 获取机构信息
     */
    @GetMapping(value = "/open/common/get_agency_info")
    public ResponseModel getAgencyInfo(CommonReq.agencyInfo req) {

        if(StringUtils.isEmpty(req.getAgencyCode())){
            return ResponseModel.fail("请输入机构号！");
        }

        return commonFacade.getAgencyInfo(req.getAgencyCode());
    }
}
