package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.facade.disposal.DisposalBiddingFacade;
import com._360pai.core.facade.disposal.DisposalRequirementFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.finance.ServiceWithdrawRecordFacade;
import com._360pai.core.facade.finance.req.WithdrawRecordReq;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.core.service.finance.ServiceWithdrawRecordService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-19 16:00
 */
public class FinanceTest extends TestBase{

    @Autowired
    private ServiceWithdrawRecordService serviceWithdrawRecordService;
    @Autowired
    private ServiceWithdrawRecordFacade serviceWithdrawRecordFacade;


    @Test
    public void getWithdrawRecordPage() {
        WithdrawRecordReq.GetWithdrawRecord req = new WithdrawRecordReq.GetWithdrawRecord();
        req.setAccountId(63);
//        req.setWithdrawNo("TX-80200231");
//        req.setOrderBy("createTime");
        req.setBeginDate(DateUtil.parse("2018-10-08 21:22:11", DateUtil.NORM_DATETIME_PATTERN));
        PageInfoResp pageInfoResp = serviceWithdrawRecordFacade.getWithdrawRecordPage(req);
        System.out.printf(JSON.toJSONString(pageInfoResp));
    }

}
