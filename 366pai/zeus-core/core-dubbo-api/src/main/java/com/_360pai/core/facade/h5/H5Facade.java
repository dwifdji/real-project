package com._360pai.core.facade.h5;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.h5.req.H5Req;

/**
 * @author xdrodger
 * @Title: H5Facade
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/8 13:16
 */
public interface H5Facade {

    ResponseModel annualMeetingApply(H5Req.AnnualMeetingApplyReq req);
}
