package com._360pai.core.service.h5.impl;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.dao.h5.TAnnualMeetingApplyRecordDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.h5.req.H5Req;
import com._360pai.core.model.h5.TAnnualMeetingApplyRecord;
import com._360pai.core.service.h5.H5Service;
import com._360pai.core.utils.ReqConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: H5ServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2019/1/8 13:27
 */
@Service
public class H5ServiceImpl implements H5Service {

    @Autowired
    private TAnnualMeetingApplyRecordDao annualMeetingApplyRecordDao;

    @Override
    public ResponseModel annualMeetingApply(H5Req.AnnualMeetingApplyReq req) {
        if (annualMeetingApplyRecordDao.isAlreadyApply(req.getMobile())) {
            throw new BusinessException(ApiCallResult.REPEAT_OPERATION);
        }
        TAnnualMeetingApplyRecord applyRecord = ReqConvertUtil.convertToTAnnualMeetingApplyRecord(req);
        int result = annualMeetingApplyRecordDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }
}
