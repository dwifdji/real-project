package com._360pai.web.controller.h5;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.h5.H5Facade;
import com._360pai.core.facade.h5.req.H5Req;
import com._360pai.web.controller.account.AccountController;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdrodger
 * @Title: H5Controller
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/1/8 16:45
 */
@RestController
@RequestMapping(value = "/open/h5", produces = "application/json;charset=UTF-8")
public class H5Controller {
    public static final Logger LOGGER = LoggerFactory.getLogger(H5Controller.class);

    @Reference(version = "1.0.0")
    private H5Facade h5Facade;

    /**
     * 2019年会邀请函-申请参会接口
     */
    @PostMapping(value = "/annual/meeting/apply")
    public ResponseModel annualMeetingApply(@RequestBody H5Req.AnnualMeetingApplyReq req) {
        return h5Facade.annualMeetingApply(req);
    }
}
