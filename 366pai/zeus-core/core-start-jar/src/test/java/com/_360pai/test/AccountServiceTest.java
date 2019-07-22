package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.exception.BaseException;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.CompanyResp;
import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.applet.TAppletOpenShopOrderService;
import com.alibaba.fastjson.JSON;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountServiceTest extends TestBase {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AcctService acctService;

    @Autowired
    private AccountFacade accountFacade;


    @Autowired
    private TAppletOpenShopOrderService tAppletOpenShopOrderService;

    private static final String path = "/lock_path";


    @Test
    public void getAppletNotPayOrderList() {

        System.out.println("结果。。。。。。。"+JSON.toJSONString(tAppletOpenShopOrderService.getAppletNotPayOrderList()));
    }

    @Test
    public void testQueryByMobile() {

        try {

            String mobile = "17749776215";
            TAccount account = accountService.findAccountByMobile(mobile);
            System.out.println(JSON.toJSONString(account));

        } catch (BaseException e) {

            System.out.println(e.getMessage());

        }

    }

    @Test
    public void testGetCompanyByAccountId() {
        Integer accountId = 189;
        List<CompanyResp> companyRespList = accountFacade.getCompanyByAccountId(accountId);
        System.out.println(JSON.toJSONString(companyRespList));
    }

    @Test
    public void testUpdateAccount() {
        AccountReq req = new AccountReq();
        req.setId(189);
        boolean flag = accountFacade.updateAccountById(req);
        System.out.println(flag);
    }

    @Test
    public void testTmp() {
        try {
            AgencyReq.QueryReq req = new AgencyReq.QueryReq();
            PageInfoResp<AgencyVo> resp = accountFacade.getAgencyListByPage(req);
            System.out.println(JSON.toJSONString(resp));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSpvList() {
        PageInfoResp resp = accountFacade.getSpvListByPage(1, 1, 10, "admin");
        System.out.println(JSON.toJSONString(resp));
    }

    @Test
    public void testSpvOperate() {
        boolean flag = accountFacade.verifySpv(6, SystemConstant.OPERATION_APPROVE, 6);
        System.out.println(flag);
    }


    @Test
    public void test(){
        TAcct acct = acctService.findAcctByPartyIdAndType(215,SystemConstant.ACCOUNT_COMPANY_TYPE);
        System.out.println(JSON.toJSONString(acct));
        //acctService.updateAcctAmount(acct);
    }

    @Test
    public void testHC(){
        boolean flag = acctService.hc(201901021534387362l,31,"测试红冲1");
        System.out.println(flag);
    }

    @Test
    public void testGetByPartyId(){
        AccountBaseDto dto = accountService.getAccountBaseByPartyId(215);
        System.out.println(JSON.toJSONString(dto));
    }


    @Test
    public void testAddAmt(){
        //boolean flag = acctService.addAcctAmount(2,new BigDecimal(200), AccountEnum.AcctOperateType.BUY_COMMISSION.getKey(),"12345");
        //System.out.println(flag);
    }

    @Test
    public void testSubAmt(){
        boolean flag = false;
                //acctService.withdrawAcctAmount(SystemConstant.ACCOUNT_USER_TYPE,2,new BigDecimal(50),1);
        System.out.println(flag);
    }

    @Test
    public void testReleaseAmt(){
        //acctService.releaseAcctLockedAmount()
    }


    @Test
    public void testSubString(){
        String s = "20181205001";
        System.out.println(s.substring(0,8));
        System.out.println(s.substring(0,9));
    }

/*    @Test
    public void testZKLock() {

        CuratorFramework client = getClient();
        final InterProcessMutex lock = new InterProcessMutex(client, path);

        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final long startTime = new Date().getTime();
        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        lock.acquire();

                        //boolean flag = acctService.updateAcctAmount(null);
                        boolean flag = false;
                        System.out.println(Thread.currentThread().getName()+"执行结果:"+flag);
                        lock.release();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        System.out.println("创建线程花费时间:" + (new Date().getTime() - startTime) + "ms");
        countDownLatch.countDown();
        //client.close();
    }*/


    private static CuratorFramework getClient() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(6000)
                .connectionTimeoutMs(3000)
                .namespace("demo")
                .build();
        client.start();
        return client;
    }


    @Test
    public void testSyncShopIfNeed(){
        try {
            accountService.syncShopIfNeed(374, 354, PartyEnum.Type.user);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
