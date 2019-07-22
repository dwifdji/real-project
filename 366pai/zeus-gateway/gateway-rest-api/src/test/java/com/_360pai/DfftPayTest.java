package com._360pai;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.model.DfftPay.MarginOperationReq;
import com._360pai.core.model.DfftPay.PayResp;
import com._360pai.core.service.DfftPayService;
import com._360pai.core.service.EmailService;
import com._360pai.gateway.common.dfftpay.DfftPayUtils;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.controller.req.dfftpay.*;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;
import com.alibaba.fastjson.JSON;
import com.easterpay.util.EastPayUtil;
import com.itrus.cvm.CVM;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DfftPayTest extends BaseJunit {



    @Autowired
    private DfftPayFacade dfftPayFacade;

    @Autowired
    private PayFacade payFacade;


    @Autowired
    private DfftPayService dfftPayService;


    @Autowired
    private EmailService emailService;


    @Autowired
    private GatewayProperties gatewayProperties;
    /**
     *
     * 会员绑定查询
     * @param
     * @param
     */
    @Test
    public void testBindMember() {

        FBindMemberReq req = new FBindMemberReq();

        req.setMemCode("5ab468b9a182462basabf6677");
        req.setMemName("阿里网络技术有限公司");
        req.setPayMemType("1");

        BindMemberResp resp = dfftPayFacade.bindMember(req);

        System.out.println(JSON.toJSONString(resp));
    }


    /**
     *
     * 会员绑定查询
     * @param
     * @param
     */
    @Test
    public void queryOrder() {



        System.out.println(JSON.toJSONString(dfftPayService.queryOrder("direct201811231321109441")));
    }




    /**
     *
     * 会员绑定查询
     * @param
     * @param
     */
    @Test
    public void testQueryBindMember() {

        FQueryBindMemberReq req = new FQueryBindMemberReq();

        req.setMemCode("tzsad855dcexjbbyhuqejnso");
        req.setMemName("喵喵企业认账04");

        FQueryBindMemberResp resp = dfftPayFacade.queryBindMember(req);

        System.out.println(JSON.toJSONString(resp));
    }


    /**
     *
     * 查询账号信息
     *
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3501033.11","totalAmt":"9350180189.13"}
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3501033.11","totalAmt":"9350180189.13"}
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3502033.11","totalAmt":"9350181189.13"}
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3501033.11","totalAmt":"9350180189.13"}
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3502033.11","totalAmt":"9350181189.13"}
     *{"code":"000000","desc":"操作成功","freeAmt":"9346679156.02","lockedAmt":"3501033.11","totalAmt":"9350180189.13"}
     *
     *
     * 上海名晨投资有限公司  {"code":"000000","desc":"操作成功","freeAmt":"0.00","lockedAmt":"0.00","totalAmt":"0.00"}
     *
     *
     *                       {"code":"000000","desc":"操作成功","freeAmt":"50000.00","lockedAmt":"0.00","totalAmt":"50000.00"}
     *
     *
     *
     *
     * 中溢长茂有限公司  {"code":"000000","desc":"操作成功","freeAmt":"0.00","lockedAmt":"50000.00","totalAmt":"50000.00"}  backPay201902220001
     *
     *
     *
     *                      {"code":"000000","desc":"操作成功","freeAmt":"0.00","lockedAmt":"0.00","totalAmt":"0.00"}
     *
     *
     *
     *
     *
     *
     * test 平台   {"code":"000000","desc":"操作成功","freeAmt":"8876688832.55","lockedAmt":"3501113.47","totalAmt":"8880189946.02"}
     *
     *              {"code":"000000","desc":"操作成功","freeAmt":"8876688832.55","lockedAmt":"3501113.47","totalAmt":"8880189946.02"}
     *
     *             {"code":"000000","desc":"操作成功","freeAmt":"8876688832.55","lockedAmt":"3601113.47","totalAmt":"8880289946.02"}
     *
     *
     *              {"code":"000000","desc":"操作成功","freeAmt":"8876688832.55","lockedAmt":"3501113.47","totalAmt":"8880189946.02"}
     *
     * test 用户  {"code":"000000","desc":"操作成功","freeAmt":"185546.60","lockedAmt":"50850.40","totalAmt":"236397.00"}  lock201902221230072122
     *
     *            {"code":"000000","desc":"操作成功","freeAmt":"85546.60","lockedAmt":"150850.40","totalAmt":"236397.00"}   tranRelease201902221236053558
     *
     *            {"code":"000000","desc":"操作成功","freeAmt":"85546.60","lockedAmt":"50850.40","totalAmt":"136397.00"}
     *
     *            {"code":"000000","desc":"操作成功","freeAmt":"185546.60","lockedAmt":"50850.40","totalAmt":"236397.00"}
     */



    @Test
    public void testQueryBalance() {

        FQueryBindMemberReq req = new FQueryBindMemberReq();

        req.setMemCode("831e025421e64e1da9f6e672");//         5abb4171a182461a1821d8dc  5ad6ca57a182461413716ee9

        QueryBalanceResp resp = dfftPayFacade.queryBalance(req);

        System.out.println("返回参数为。。。"+JSON.toJSONString(resp));


    }




    @Test
    public void delectPay() {



        UnifiedPayReq req = new UnifiedPayReq();

        LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
        lockOrReleaseOrDirectReq.setOriginalPayID("direct201810221252126503");


        req.setPayType(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType());
        req.setBackTag(PayEnums.BACK_TAG.BACK.getType());
        req.setPayParam(lockOrReleaseOrDirectReq);




        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));
    }




    //保证金锁定到平台   lock201809170956424186
    @Test
    public void lockToWeb() throws Exception{

        UnifiedPayReq req = new UnifiedPayReq();

        LockOrReleaseOrDirectReq payLock = new LockOrReleaseOrDirectReq();

        payLock.setPayMemName("独孤应醉");
        payLock.setPayMemCode("5abb4171a182461a1821d8dc");

        req.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
         req.setPartyId(111);
        req.setPayType(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType());
        req.setAmount(new BigDecimal("100000.00"));
        req.setBusId("12012112323");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
        req.setPayParam(payLock);
        System.out.println("返回参数。。。"+JSON.toJSONString(payFacade.unifiedPay(req)));

     }



    //保证金释放
    @Test
    public void release() throws Exception{

        UnifiedPayReq req = new UnifiedPayReq();
        LockOrReleaseOrDirectReq payLock = new LockOrReleaseOrDirectReq();

        payLock.setOriginalPayID("lock201901041117120172");
        payLock.setRecMemCode("rdor96khb4jkge6vtonz1xke");
        payLock.setRecMemName("上海名晨投资有限公司");

        req.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        req.setPartyId(495);
         req.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
        req.setAmount(new BigDecimal("50000"));
        req.setBusId("153258565202846018");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
        req.setPayParam(payLock);



        System.out.println("返回参数为。。。"+JSON.toJSONString(payFacade.unifiedPay(req)));

    }



    //保证金释放
    @Test
    public void batchPay() throws Exception{

        UnifiedPayReq req = new UnifiedPayReq();


        List<BatchDirectReq> payList = new ArrayList<>();

        BatchDirectReq req1 = new BatchDirectReq();
        req1.setAmount(new BigDecimal("100"));
        req1.setBusId("br_201809201631277529122121");
        req1.setLockTag("1");
        req1.setPayMemCode("5qx428aca182972bd6bf3398");
        req1.setPayMemName("吴传奇");
        req1.setRecMemCode("5b7e4a17a1824678ccfe1f46");
        req1.setRecMemName("徐冬冬");
        req1.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());



        BatchDirectReq req2 = new BatchDirectReq();
        req2.setAmount(new BigDecimal("30"));
        req2.setBusId("bc_201809201631277529");
        req2.setLockTag("1");
        req2.setPayMemCode("5qx428aca182972bd6bf3398");
        req2.setPayMemName("吴传奇");
        req2.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());



        payList.add(req1);


        req.setPartyId(144);
        req.setPayType(PayEnums.PAY_TYPE.BATCH_PAY.getType());
        req.setAmount(new BigDecimal("500"));
        req.setBusId("1212");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.BATCH_PAY.getType());
        req.setPayParam(payList);
        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));

    }


    //保证金支付
    @Test
    public void lockPay() throws Exception{

        UnifiedPayReq req = new UnifiedPayReq();

        LockOrReleaseOrDirectReq payLock = new LockOrReleaseOrDirectReq();

        payLock.setOriginalPayID("lock201902221230072122");

        req.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
        req.setPartyId(11);
        req.setPayType(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType());
        req.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        req.setAmount(new BigDecimal("100000.00"));
        req.setBusId("11111");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
        req.setPayParam(payLock);
        System.out.println("返回参数为。。。"+JSON.toJSONString(payFacade.unifiedPay(req)));

    }

    //保证经返回
    @Test
    public void backPay() throws Exception{


        MarginOperationReq lock = new MarginOperationReq();

        String orderNum = "backPay201902220001";

        lock.setPayAmt("50000");
        lock.setPayMemCode("5b91d9233d6d3f185725d382");
        lock.setPayMemName("中溢长茂有限公司");
        lock.setRecMemCode("rdor96khb4jkge6vtonz1xke");
        lock.setRecMemName("上海名晨投资有限公司");
        lock.setPayID(orderNum);
        lock.setPayType(PayEnums.DFFT_PAY_CODE.PAY_TYPE_TRANSFER_DEPOSIT.getType());
        lock.setTradeOrder("order201901041117127976");
        lock.setOriginalPayID("tranRelease201901071700005931");
        lock.setNotifyUrl(gatewayProperties.getNotifyUrl());
        lock.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        lock.setSummary("马前卒标的保证金退回操作");



        PayResp resp = dfftPayService.marginOperation(lock);

        System.out.println("返回参数为。。。"+JSON.toJSONString(resp));


    }




    //保证金支付
    @Test
    public void adaPay() throws Exception{

        UnifiedPayReq req = new UnifiedPayReq();

        LockOrReleaseOrDirectReq payLock = new LockOrReleaseOrDirectReq();

        payLock.setOriginalPayID("lock201809181940578217");

        req.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
        req.setPartyId(11);
        req.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
        req.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        req.setAmount(new BigDecimal("500.12"));
        req.setBusId("11111");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.ENROLLING_LOCK_DEPOSIT.getType());
        req.setPayParam(payLock);
        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));

    }





    //直接支付返回
    @Test
    public void directPay() throws Exception{


        
        //direct201810221252126503


        UnifiedPayReq req = new UnifiedPayReq();

        LockOrReleaseOrDirectReq payLock = new LockOrReleaseOrDirectReq();

        payLock.setPayMemName("360PAi.com");
        payLock.setPayMemCode("5ad6ca57a182461413716ee9");

        payLock.setRecMemName("企业-K-15951569965");
        payLock.setRecMemCode("mdkauhtrjeaecqzxpar15xhg");


        req.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        req.setPartyId(11);
        req.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
        req.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        req.setAmount(new BigDecimal("50000000"));

        req.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
        req.setBusId("11111");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
        req.setPayParam(payLock);
        req.setPayMemType("1");
        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));

    }






    //通道支付
    @Test
    public void channelPay() throws Exception{




        UnifiedPayReq req = new UnifiedPayReq();

        UnifiedChannelPayReq payLock = new UnifiedChannelPayReq();
        req.setWhoPay(PayEnums.WHO_PAY.WEB_PAY.getType());

        payLock.setAccName("顾燚银行类企业");
        payLock.setAccNO("6212264100011335373");
        payLock.setBankCode("005");

        req.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        req.setPartyId(11);
        req.setPayType(PayEnums.PAY_TYPE.CHANNEL_PAY.getType());
        req.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        req.setAmount(new BigDecimal("500"));
         req.setBusId("11111");
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
        req.setPayParam(payLock);
        System.out.println(JSON.toJSONString(payFacade.unifiedPay(req)));

    }



    //通道支付
    @Test
    public void responseJson() throws Exception{
        String filePath = DfftPayUtils.class.getClassLoader().getResource(gatewayProperties.getPayFileName()).getPath();//获取

        CVM.config(filePath);

        String responseJson = "{\"freeAmt\":\"306642872.64\",\"frozenAmt\":\"0.00\",\"holdAmt\":\"0.00\",\"lockedAmt\":\"11533533.00\",\"memCode\":\"F1004778\",\"payMessage\":\"操作成功\",\"payStatus\":\"000000\",\"signature\":\"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\",\"totalAmt\":\"318176405.64\"}";
        ObjectMapper objectMapper = new ObjectMapper();


        Map<String,String > map = objectMapper.readValue(responseJson, Map.class);
        String signature = map.get("signature");


        System.out.println(!EastPayUtil.verifySignedDataByDfft(responseJson, signature));

    }


    //支付查询
    @Test
    public void queryPay() throws Exception{
        UnifiedQueryReq req = new UnifiedQueryReq();
        req.setPayOrder("APPLET201901230931005812");
        req.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());

        System.out.println(JSON.toJSONString( payFacade.unifiedPayQuery(req)));

    }



    //支付查询
    @Test
    public void b() throws Exception{
        UnifiedQueryReq req = new UnifiedQueryReq();
        req.setPayOrder("direct201809281040526289");
        req.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());


        System.out.println(JSON.toJSONString( payFacade.unifiedPayQuery(req)));

    }


}
