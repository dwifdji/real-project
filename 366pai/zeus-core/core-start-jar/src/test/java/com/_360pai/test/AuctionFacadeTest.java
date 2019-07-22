package com._360pai.test;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AuctionOfflineEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.resp.SpvResp;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.AuctionOfflineEnterAccountReq;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.activity.resp.DfftResp;
import com._360pai.core.facade.activity.resp.SignContractResp;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberReq;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.resp.QueryBalanceResp;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionFacadeTest
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/17 19:55
 */
public class AuctionFacadeTest extends TestBase {
    private static Logger logger = LoggerFactory.getLogger(AuctionFacadeTest.class);
    @Resource
    private AuctionFacade auctionFacade;

    //@Reference(version = "1.0.0",retries = 0)
    private PayFacade payFacade;

    //@Reference(version = "1.0.0",retries = 0)
    private DfftPayFacade dfftPayFacade;

   @Resource
    private AccountFacade accountFacade;
    @Autowired
    private ActivityFacade activityFacade;
    @Autowired
    private AuctionOrderFacade auctionOrderFacade;

    @Resource
    private RedisCachemanager redisCachemanager;
    private static SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);

    @Autowired
    private AuctionActivityService auctionActivityService;

    @Test
    public void testGetPartyInfo(){
        AccountBaseDto dto =  accountFacade.getAccoutBaseByPartyId(191);
        System.out.println(JSON.toJSONString(dto));
    }

    @Test
    public void testGetSpv(){
        List<SpvResp> list = accountFacade.getSpvListByCompanyId(188);
        System.out.println(JSON.toJSONString(list));
        System.out.println(JSON.toJSONString(ResponseModel.succ(list)));
    }

    @Test
    public void testPayDeposit() {

        // 159 徐冬冬  145 李凯绅

//        AccountBaseDto accountBaseDto = accountFacade.getAccoutBaseByPartyId(145);
//
//        FQueryBindMemberReq queryBindMemberReq = new FQueryBindMemberReq();
//        queryBindMemberReq.setMemName(accountBaseDto.getName());
//        queryBindMemberReq.setMemCode(accountBaseDto.getDfftId());
//        QueryBalanceResp queryBalanceResp = dfftPayFacade.queryBalance(queryBindMemberReq);
//        System.out.println(JSON.toJSONString(queryBalanceResp));

        AuctionReq req = new AuctionReq();
        req.setPartyId(62);
        req.setActivityId(426);
        req.setAgencyCode("12345678");
        boolean flag = auctionFacade.payDeposit(req);
        System.out.println(flag);

    }

    @Test
    public void testBid(){
        AuctionReq req = new AuctionReq();
        req.setPartyId(62);
        req.setActivityId(426);
      req.setBidAmount(new BigDecimal(113));
        req.setAgencyCode("12345678");
        auctionFacade.bid(req);
    }

    @Test
    public void testSignTimeout(){
        auctionFacade.signTimeourDeal(572);
    }

    @Test
    public void testQueryDFFTAmt(){
        FQueryBindMemberReq queryBindMemberReq = new FQueryBindMemberReq();
//        AccountBaseDto accountBaseDto = accountFacade.getAccoutBaseByPartyId(63);
//        queryBindMemberReq.setMemName(accountBaseDto.getName());
//        queryBindMemberReq.setMemCode(accountBaseDto.getDfftId());

        // 百昌  5acc6997a182460de6b79016
        queryBindMemberReq.setMemName("汝晴测试上市企业");
        queryBindMemberReq.setMemCode("5b6b95bba182465f2bba401d");
        QueryBalanceResp queryBalanceResp = dfftPayFacade.queryBalance(queryBindMemberReq);
        System.out.println(JSON.toJSONString(queryBalanceResp));
    }

    @Test
    public void testActivityEndDeal(){
        try {
            boolean flag = auctionFacade.activityEndDeal(1332);
            System.out.println(flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testInsertAuctionPayOrder(){
        auctionFacade.insertAuctionPayOrder(SystemConstant.PAY_TYPE_OFFLINE,201905151156059500l,"");
    }

    @Test
    public void testSearchOfflineList(){
        ResponseModel responseModel = auctionFacade.searchOfflineFinanceList(new AuctionOfflineFinanceReq());
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testGetOfflineDetail(){
        AuctionOfflineFinanceReq req = new AuctionOfflineFinanceReq();
        req.setId(12);
        ResponseModel responseModel = auctionFacade.getOfflineFinanceDetailById(req);
        System.out.println(JSON.toJSONString(responseModel));
    }

    @Test
    public void testConfirmOffline(){
        AuctionOfflineFinanceReq req = new AuctionOfflineFinanceReq();
        req.setOperatorId(31);
        req.setId(4);
//        req.setReceiveRemainType(AuctionOfflineEnum.ReceiveRemainType.OTHER_RECEIVE.getKey());
//        req.setReceiveRemainRemark("皇后大道东上为何无皇宫,皇后大道中人民如潮涌");
        req.setReceiveCommissionType(AuctionOfflineEnum.ReceiveCommissionType.OFFLINE_CONSULT.getKey());
        req.setActualReceiveCommissionAmount("1.00");
        req.setActualReceiveTotalAmount("1.00");
        List<AuctionOfflineEnterAccountReq> enterAccountList = new ArrayList<>();
        AuctionOfflineEnterAccountReq accountReq = new AuctionOfflineEnterAccountReq();
        accountReq.setAmount("1.00");
        accountReq.setBankAccountId(34);
        accountReq.setBankOrderNo("3333333");
        accountReq.setEnterDate("2019/05/02");
        enterAccountList.add(accountReq);

//        AuctionOfflineEnterAccountReq accountReq1 = new AuctionOfflineEnterAccountReq();
//        accountReq1.setAmount("6.00");
//        accountReq1.setBankAccountId(34);
//        accountReq1.setBankOrderNo("22222222");
//        accountReq1.setEnterDate("2019/05/06");
//
//        enterAccountList.add(accountReq1);

        req.setEnterAccountList(enterAccountList);

        ResponseModel responseModel = auctionFacade.confirmOfflineFinance(req);
        System.out.println(JSON.toJSONString(responseModel));
    }


    @Test
    public void testOrder() {
        try {
            AuctionOrderReq.QueryReq req = new AuctionOrderReq.QueryReq();
            req.setSellerId(191);
            PageInfoResp<AuctionOrderVo> resp = auctionOrderFacade.getSellerOrderListByPage(req);
            System.out.println(JSON.toJSONString(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCaculateCommission(){
        //auctionFacade.insertAuctionPayOrder(201809181907166405l,"lock201809181608200751");
    }

    @Test
    public void testPayAmount(){
        AuctionReq req = new AuctionReq();
        req.setOrderId(201810161033558447l);
        req.setPartyId(193);
        DfftResp resp = auctionFacade.pay(req);
        System.out.println(JSON.toJSONString(req));
    }

    @Test
    public void testShareCommision(){
        auctionFacade.shareCommission(201810161120071689l);
    }

    @Test
    public void testSignContract(){
        AuctionReq req = new AuctionReq();
        req.setPartyId(144);
        req.setOrderId(201809181907166405l);
        SignContractResp resp = auctionFacade.signContract(req);
        System.out.println(JSON.toJSONString(resp));
    }

    @Test
    public void testPayRemain(){
        AuctionReq req = new AuctionReq();
        req.setPartyId(144);
        req.setOrderId(201809181907166405l);
        auctionFacade.pay(req);
    }

    @Test
    public void confirmSend(){
        AuctionReq req = new AuctionReq();
        req.setPartyId(160);
        req.setOrderId(201809282054510616l);
        auctionFacade.confirmSend(req);
    }

    @Test
    public void confirmRev(){
        AuctionReq req = new AuctionReq();
        req.setPartyId(192);
        req.setOrderId(201810161120071689l);
        auctionFacade.revSend(req);
    }

    @Test
    public void testPayCallBack(){
        try {
            auctionFacade.payCallBack("br_201810131810524622",SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testRepairActivityExpireTime() {
        try {
            ActivityReq.ActivityId req = new ActivityReq.ActivityId();
            req.setActivityId(435);
            ActivityResp resp = activityFacade.getActivity(req);
            setActivityExpireTime(resp.getActivity());
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setActivityExpireTime(AuctionActivityVo activity) {
        try {
            Date now = new Date();
            // 活动已经结束
            if (activity.getEndAt().before(now)) {
                logger.error("activityId={}，mode={}，end at {}，activity already finish", activity.getId(), activity.getMode(), sdf.format(activity.getEndAt()));
                return;
            }
            long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
            logger.info("activityId={}，mode={}，end at {}，remaining seconds={}", activity.getId(), activity.getMode(), sdf.format(activity.getEndAt()), timeout);
            redisCachemanager.set(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId(), activity.getId() + "", timeout);
            if (ActivityEnum.ActivityMode.DUTCH.getKey().equals(activity.getMode())) {
                // 活动已经开始
                if (activity.getBeginAt().before(new Date())) {
                    timeout = activity.getReductionPeriod() * 60;
                } else {
                    timeout = ((activity.getBeginAt().getTime() - System.currentTimeMillis()) / 1000) + activity.getReductionPeriod() * 60;

                }
                logger.info("activityId={}，mode={}，reduction period={}，begin at {}，remaining seconds plus reduction period={}", activity.getId(), activity.getMode(), activity.getReductionPeriod(), sdf.format(activity.getBeginAt()), timeout);
                redisCachemanager.set(SystemConstant.AUCTION_DUTCH_PRICE_PRE_KEY + activity.getId(), activity.getId() + "", timeout);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  testBuyerDealSignCallback() {
        try {
            auctionFacade.signCallBack(FddEnums.SING_ROLE_TYPE.BUYER.getType(), 144, 445, "num201810241413142452", true);
            System.out.println("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void  testSellerDealSignCallback() {
        try {
            auctionFacade.signCallBack(FddEnums.SING_ROLE_TYPE.SELLER.getType(), 159, 446, "num201810241632221278", true);
            auctionFacade.signCallBack(FddEnums.SING_ROLE_TYPE.BUYER.getType(), 144, 446, "num201810241632221278", true);
            System.out.println("-");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRepairAuctionActivity() {
        try {
            auctionActivityService.repairAuctionActivity(422);
            System.out.println("--");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDealOnlineData(){
        auctionFacade.dealOnlineActivityData();
    }

    @Test
    public void testIntegerEq(){
        Integer partyId = 10;
        Integer tempId = 10;
        boolean flag = String.valueOf(10).equals(String.valueOf(partyId));
        System.out.println(flag);
    }



    @Test
    public void getJointList(){

        ActivityReq.JointListReq req = new ActivityReq.JointListReq();
        req.setAssetName("");
        req.setAuctionType("");
        req.setBeginAtFrom("");
        req.setBeginAtTo("");
        req.setType("1");
        req.setAgencyId(1);
        System.out.println("返回参数"+JSON.toJSONString(activityFacade.getJointList(req)));

    }




    @Test
    public void saveOrCancelJoint(){

        ActivityReq.JointReq req = new ActivityReq.JointReq();
        req.setAgencyId(1);
        req.setType("2");
        req.setIds(new String[]{"888"});
        req.setIsDel(1);
        System.out.println(JSON.toJSONString(activityFacade.saveOrCancelJoint(req)));

    }

    @Test
    public void testLeaseDealContract(){
        ResponseModel responseModel = auctionFacade.invokeFddCreateLeaseDeal(201905151156059481l,new Date());
        System.out.println(responseModel);
    }
}
