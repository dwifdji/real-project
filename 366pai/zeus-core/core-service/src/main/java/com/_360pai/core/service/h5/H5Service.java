package com._360pai.core.service.h5;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.h5.req.H5Req;

/**
 * @author xdrodger
 * @Title: H5Service
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/8 13:26
 */
public interface H5Service {
    ResponseModel annualMeetingApply(H5Req.AnnualMeetingApplyReq req);
}
