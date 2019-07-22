package com._360pai.core.provider.h5;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.h5.H5Facade;
import com._360pai.core.facade.h5.req.H5Req;
import com._360pai.core.service.h5.H5Service;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xdrodger
 * @Title: H5Provider
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/8 13:17
 */
@Component
@Service(version = "1.0.0")
public class H5Provider implements H5Facade {

    @Autowired
    private H5Service h5Service;

    @Override
    public ResponseModel annualMeetingApply(H5Req.AnnualMeetingApplyReq req) {
        return h5Service.annualMeetingApply(req);
    }
}
