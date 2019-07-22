package com.winback.test;

import com.gexin.fastjson.JSON;
import com.winback.core.commons.constants.OperateEnum;
import com.winback.core.facade.finance.FinanceFacade;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.facade.operate.OperationFacade;
import com.winback.core.facade.operate.req.OperationReq;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * @author wuchuanqi
 * @create 2018-11-29 16:00
 */
public class OperateTest extends TestBase{

    @Resource
    private OperationFacade operationFacade;

    /**
     * 获取banner信息
     *
     */
    @Test
    public void getBannerList() {
        OperationReq.AppBannerListReq req = new OperationReq.AppBannerListReq();

        req.setType("1");

         System.out.println("返回参数为....."+JSON.toJSONString(operationFacade.getBannerList(req)));
    }



    /**
     * 快速发布
     *
     */
    @Test
    public void getQuickRelease() {
        OperationReq.OperateIconListReq req = new OperationReq.OperateIconListReq();

        req.setGroupType(OperateEnum.OperateIconEnum.QUICK_RELEASE.getType());


        System.out.println("返回参数为....."+JSON.toJSONString(operationFacade.getQuickRelease(req)));
    }







}




