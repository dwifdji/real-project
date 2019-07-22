package com._360pai.core.provider.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.finance.ServiceAdjustedRecordFacade;
import com._360pai.core.facade.finance.req.AdjustedRecordReq;
import com._360pai.core.service.finance.ServiceAdjustedRecordService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiaolei
 * @create 2018-10-03 14:56
 */
@Component
@Service(version = "1.0.0")
public class ServiceAdjustedRecordProvider implements ServiceAdjustedRecordFacade {

    @Autowired
    private ServiceAdjustedRecordService serviceAdjustedRecordService;

    @Override
    public PageInfoResp getAdjustedRecord(AdjustedRecordReq.GetAdjustedRecord req) {
        return serviceAdjustedRecordService.getAdjustedRecordByPartyIdAndStatus(req.getPartyId(), req.getAdjustedStatus(),
                req.getPage(), req.getPerPage());
    }

    @Override
    public PageInfoResp getAdjustedRecordByWithdrawNo(AdjustedRecordReq.GetAdjustedRecord req) {
        return serviceAdjustedRecordService.getAdjustedRecordByWithdrawNo(req.getWithdrawNo(), req.getPage(), req.getPerPage());
    }


}
