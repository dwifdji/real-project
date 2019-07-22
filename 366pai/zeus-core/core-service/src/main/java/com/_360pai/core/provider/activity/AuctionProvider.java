package com._360pai.core.provider.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberToCN;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.dto.SellerPayInfo;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.activity.resp.DfftResp;
import com._360pai.core.facade.activity.resp.SignContractResp;
import com._360pai.core.facade.activity.vo.AuctionOfflineFinaceVo;
import com._360pai.core.facade.activity.vo.ContractVo;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.activity.*;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetCategory;
import com._360pai.core.model.asset.TPreemptivePerson;
import com._360pai.core.model.assistant.Deposit;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.*;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.activity.AuctionOfflineFinanceService;
import com._360pai.core.service.activity.AuctionStepService;
import com._360pai.core.service.activity.BidService;
import com._360pai.core.service.agreement.DealAgreementService;
import com._360pai.core.service.agreement.DelegationAgreementService;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.asset.AssetCategoryService;
import com._360pai.core.service.asset.AssetLeaseDataService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.assistant.DepositService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.service.lease.LeaseStaffService;
import com._360pai.core.service.payment.AuctionOrderService;
import com._360pai.core.service.payment.AuctionPayOrderService;
import com._360pai.core.vo.asset.AssetLeaseDataVO;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.common.fddSignature.FddEnums;
import com._360pai.gateway.controller.req.dfftpay.*;
import com._360pai.gateway.controller.req.fdd.*;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/6 10:24
 */
@Component
@Service(version = "1.0.0")
public class AuctionProvider implements AuctionFacade {

    public static final Logger LOGGER = LoggerFactory.getLogger(AuctionProvider.class);

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private RedisCachemanager redisCachemanager;

    @Resource
    private AccountService accountService;

    @Resource
    private SmsHelperService smsHelperService;

    @Resource
    private SpvService spvService;

    @Resource
    private AuctionStepService auctionStepService;

    @Resource
    private AgencyService agencyService;

    @Resource
    private UserService userService;

    @Resource
    private CompanyService companyService;

    @Resource
    private PartyService partyService;

    @Resource
    private AuctionActivityService auctionActivityService;

    @Resource
    private AssetService assetService;

    @Resource
    private DealAgreementService dealAgreementService;

    @Resource
    private DelegationAgreementService delegationAgreementService;

    @Reference(version = "1.0.0")
    private PayFacade payFacade;

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    @Resource
    private DepositService depositService;

    @Resource
    private BidService bidService;

    @Resource
    private SystemProperties systemProperties;

    @Resource
    private AuctionOrderService auctionOrderService;

    @Resource
    private AssetCategoryService assetCategoryService;

    @Resource
    private PartyChannelAgentService partyChannelAgentService;

    @Resource
    private AuctionPayOrderService auctionPayOrderService;

    @Resource
    private AcctService acctService;

    @Autowired
    private GatewayMqSender mqSender;

    @Autowired
    private TAppletMessageService appletMessageService;

    @Autowired
    private AuctionOfflineFinanceService auctionOfflineFinanceService;

    @Autowired
    private AssetLeaseDataService assetLeaseDataService;

    @Autowired
    private LeaseStaffService leaseStaffService;


    @Override
    @Transactional
    public boolean payDeposit(AuctionReq req) {

        try {
            LOGGER.info("开始调用 payDeposit,参数:{}", JSON.toJSONString(req));

            Integer partyId = req.getPartyId();
            Integer activityId = req.getActivityId();


            //验证合法性
            AuctionActivity activity = auctionActivityService.getById(activityId);

            Asset asset = assetService.getAsset(activity.getAssetId());
            if (activity == null || asset == null) {
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }
            //参数安全检查
            commonCheckValid(req, activity, asset);

            String payType = asset.getOnlined() == 1 ? SystemConstant.PAY_TYPE_ONLINE : SystemConstant.PAY_TYPE_OFFLINE;
            if (payType.equals(SystemConstant.PAY_TYPE_OFFLINE)) {
                //if (StringUtils.isEmpty(req.getBankName())
                //        || StringUtils.isEmpty(req.getBankAccountName())
                //        || StringUtils.isEmpty(req.getBankAccountNumber())) {
                //    throw new BusinessException(ExceptionEnumImpl.PARAM_IS_NULL);
                //}
            }

            //是否已经交过保证金
            List<Deposit> list = checkDeposit(partyId, activityId);
            if (list != null && list.size() > 0 && !list.get(0).getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_INIT)) {
                throw new BusinessException(ExceptionEnumImpl.HAS_PAY_DEPOSIT);
            }

            if (payType.equals(SystemConstant.PAY_TYPE_OFFLINE) && list != null && list.size() > 0
                    && list.get(0).getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_INIT)) {
                throw new BusinessException(ExceptionEnumImpl.BE_CONFIRMING_DEPOSIT);
            }

            Long depositId;
            if (list == null || list.isEmpty() || list.size() <= 0) {
                //落表 deposit 分线上线下
                depositId = insertDeposit(activity, req, payType);
            } else {
                depositId = list.get(0).getId();
            }

            if (payType.equals(SystemConstant.PAY_TYPE_ONLINE)) {
                //调用东方付通锁定保证金
                UnifiedPayReq unifiedPayReq = invokeGatewayPayReq(SystemConstant.PAY_BUSINESS_TYPE_LOCK, partyId, asset, activity.getDeposit(), depositId);
                LOGGER.info("开始调用 payFacade unifiedPay,参数:{}", JSON.toJSONString(unifiedPayReq));
                UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
                LOGGER.info("结束调用 payFacade unifiedPay,参数:{}，结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
                insertAuctionStep(activityId, null, partyId, "保证金锁定", JSON.toJSONString(unifiedPayReq),
                        JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
                if (payResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
                    //更新deposit表status
                    auctionActivityService.addParticipantNumber(activityId);
                    return updateDeposit(depositId, SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED, "保证金锁定成功", payResp.getPayOrder());

                } else if ("101007".equals(payResp.getCode())) {
                    throw new BusinessException(ExceptionEnumImpl.DFFT_AMOUNT_NOT_ENOUGH);
                } else {

                    throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
                }
            }

            return true;
        } catch (Exception e) {
            insertAuctionStep(req.getActivityId(), null, req.getPartyId(), "保证金锁定", null,
                    null, e.getMessage(), "FAIL");
            throw e;
        }
    }

    @Override
    public void insertAuctionStep(Integer activityId, Long orderId, Integer partyId, String step, String req, String resp, String excep, String status) {
        TAuctionStepRecord record = new TAuctionStepRecord();
        record.setActivityId(activityId);
        if (orderId != null) {
            record.setOrderId(orderId);
        }
        if (partyId != null) {
            record.setPartyId(partyId);
        }
        record.setStep(step);
        record.setReq(req);
        record.setResp(resp);
        record.setCoreException(excep);
        record.setStatus(status);
        auctionStepService.saveAuctionStep(record);
    }

    @Override
    @Transactional
    public DfftResp pay(AuctionReq req) {
        AuctionOrder order = auctionOrderService.getById(req.getOrderId());
        try {
            if (String.valueOf(order.getBuyerId()).equals(String.valueOf(req.getPartyId()))) {
                return buyerPayRemain(req);
            } else if (String.valueOf(order.getSellerId()).equals(String.valueOf(req.getPartyId()))) {
                return sellerPayRemain(req);
            } else {
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
        } catch (Exception e) {
            LOGGER.error("拍卖订单付款异常，msg={}", Throwables.getStackTraceAsString(e));
            insertAuctionStep(order.getActivityId(), order.getId(), req.getPartyId(), "付款异常", null,
                    null, Throwables.getStackTraceAsString(e), "FAIL");
            mqSender.sendTryCatchExceptionEmail(req.getOrderId(), e);
        }
        return null;

    }

    @Override
    @Transactional
    public boolean activityEndDeal(Integer activityId) {

        try {
            //活动结束处理相关操作
            LOGGER.info("开始调用 activityEndDeal， activityId={}", activityId);
            AuctionActivity activity = auctionActivityService.getById(activityId);
            if (activity == null || !activity.getStatus().equals(ActivityEnum.Status.ONLINE)) {
                LOGGER.info("活动状态错误,停止处理");
                insertAuctionStep(activityId, null, null, "redis活动结束处理错误", null,
                        null, "activity状态不是ONLINE", "FAIL");
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }
            Asset asset = assetService.getAsset(activity.getAssetId());
            if (asset == null || !asset.getStatus().equals(AssetEnum.Status.SELLING)) {
                LOGGER.info("活动状态错误,停止处理");
                insertAuctionStep(activityId, null, null, "redis活动结束处理错误", null,
                        null, "asset状态不是SELLING", "FAIL");
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }
            //无人竞拍
            List<Deposit> depositList = depositService.getDepositByActivityId(activityId);

            List<Bid> bidList = bidService.getBidListByActivityId(activityId);

            if (depositList == null || depositList.isEmpty()) {
                //更新 auction_activity status FAILED
                updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
                updateAssetStatus(asset.getId(), AssetEnum.Status.FAILED);
            } else {

                if (bidList != null && !bidList.isEmpty()) {
                    Bid successBid = bidList.get(0);
                    BigDecimal highestPrice = successBid.getAmount();
                    if (highestPrice.compareTo(activity.getReservePrice()) < 0) {
                        //有人拍，没到保留价
                        //更新状态
                        updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
                        updateAssetStatus(asset.getId(), AssetEnum.Status.FAILED);


                        //释放竞拍者保证金
                        if (asset.getOnlined() == 1) {
                            for (Deposit deposit : depositList) {
                                if (deposit.getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED)) {
                                    relaseDeposit(asset, deposit);
                                }
                            }

                        }

                    } else {
                        //有人拍，超过保留价
                        //调东方付通，拍中者保证金支付，未拍中者释放
                        checkYXBuyerIsEnd(asset, activity);


                    }
                } else {
                    //无人出价
                    updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
                    updateAssetStatus(asset.getId(), AssetEnum.Status.FAILED);

                    //释放竞拍者保证金
                    if (asset.getOnlined() == 1) {
                        for (Deposit deposit : depositList) {
                            if (deposit.getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED)) {
                                relaseDeposit(asset, deposit);
                            }
                        }
                    }

                }


            }

        } catch (Exception e) {
            e.printStackTrace();
            insertAuctionStep(activityId, null, null, "redis活动结束处理异常", null,
                    null, e.getMessage(), "FAIL");
        }
        return true;
    }

    private void dealSuccess(Asset asset, AuctionActivity activity) {
        List<Deposit> depositList = depositService.getDepositByActivityId(activity.getId());
        List<Bid> bidList = bidService.getBidListByActivityId(activity.getId());
        Bid successBid = bidList.get(0);
        Integer buyerPartyId = bidList.get(0).getPartyId();
        Deposit successBuyerDeposit = new Deposit();
        for (Deposit deposit : depositList) {
            if (String.valueOf(deposit.getPartyId()).equals(String.valueOf(buyerPartyId))) {
                successBuyerDeposit = deposit;
            } else {
                //释放保证金
                if (asset.getOnlined() == 1 && deposit.getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED)) {
                    relaseDeposit(asset, deposit);
                }
            }
        }
        chooseBiderToTrans(successBuyerDeposit.getId(), buyerPartyId, successBid.getId(), successBid.getAmount(), successBuyerDeposit.getAgencyId(), activity, asset);

    }

    private void checkYXBuyerIsEnd(Asset asset, AuctionActivity activity) {

        AssetCategory assetCategory = assetCategoryService.getById(activity.getAssetCategoryGroupId());
        List<Deposit> unDealYXList = depositService.selectNoDealYX(activity.getId());
        if (assetCategory == null
                || !assetCategory.getName().substring(0, 2).equals("物权")
                || unDealYXList == null || unDealYXList.isEmpty()) {
            dealSuccess(asset, activity);
        } else {

            //更新已处理过得优先级别
            if (activity.getCurrentLevel() != null && activity.getCurrentLevel() > 0) {
                depositService.updateDealYX(activity.getCurrentLevel());
            }
            Integer nextDealLevel = unDealYXList.get(0).getLevel();
            //更新 activity current_level
            AuctionActivity updateActivityParam = new AuctionActivity();
            updateActivityParam.setId(activity.getId());
            updateActivityParam.setCurrentLevel(nextDealLevel);
            auctionActivityService.updateActivityById(updateActivityParam);

            //延迟5分钟
            updateExpireTime(activity);


        }
    }

    private String isYXUser(Integer assetId, Integer partyId) {
        List<TPreemptivePerson> yxBuyerList = assetService.getPreemptivePersons(assetId);
        if (yxBuyerList != null) {
            for (TPreemptivePerson person : yxBuyerList) {
                TUser user = userService.findUserByNameAndIdCard(person.getPreemptivePersonName(), person.getPreemptivePersonCard());
                if (user != null && String.valueOf(user.getId()).equals(String.valueOf(partyId))) {
                    Integer level = Integer.parseInt(person.getLevel()) + 1;
                    return level + "";
                }
            }
        }
        return null;
    }

    private void relaseDeposit(Asset asset, Deposit deposit) {
        UnifiedPayReq unifiedPayReq = invokeGatewayPayReq(SystemConstant.PAY_BUSINESS_TYPE_REALEASE, deposit.getPartyId(), asset, deposit.getAmount(), deposit.getId());
        LOGGER.info("开始调用 payFacade unifiedPay,参数:{}", JSON.toJSONString(unifiedPayReq));
        UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
        LOGGER.info("结束调用 payFacade unifiedPay,参数:{}，结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
        insertAuctionStep(deposit.getActivityId(), null, deposit.getPartyId(), "保证金释放", JSON.toJSONString(unifiedPayReq),
                JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
        if ((payResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()))) {
            updateDeposit(deposit.getId(), SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_RELEASED, "保证金释放成功", payResp.getPayOrder());
        }
    }

    @Override
    @Transactional
    public boolean dutchPriceDeal(Integer activityId) {
        AuctionActivity activity = auctionActivityService.getById(activityId);
        if (activity == null || activity.getId() == null
                || !activity.getStatus().equals(ActivityEnum.Status.ONLINE)
                || activity.getEndAt().getTime() - new Date().getTime() <= 0) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }


        if (activity.getCurrentPrice() != null
                && activity.getCurrentPrice().compareTo(activity.getReservePrice()) <= 0) {
            updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
            updateAssetStatus(activity.getAssetId(), AssetEnum.Status.FAILED);
            return true;
        }

        if (activity.getCurrentPrice() != null && activity.getCurrentPrice().compareTo(activity.getReduction()) <= 0) {
            updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
            updateAssetStatus(activity.getAssetId(), AssetEnum.Status.FAILED);
            return true;
        }

        List<Bid> bidList = bidService.getBidListByActivityId(activityId);
        if (bidList == null || bidList.isEmpty()) {
            AuctionActivity updateParam = new AuctionActivity();
            updateParam.setId(activityId);
            if (activity.getCurrentPrice() == null) {
                updateParam.setCurrentPrice(activity.getStartingPrice().subtract(activity.getReduction()));
            } else {
                updateParam.setCurrentPrice(activity.getCurrentPrice().subtract(activity.getReduction()));
            }
            updateParam.setReducedAt(DateUtil.nextMinute(activity.getReductionPeriod()));
            auctionActivityService.updateActivityById(updateParam);
            redisCachemanager.set(SystemConstant.AUCTION_DUTCH_PRICE_PRE_KEY + activityId, activity.getId() + "", activity.getReductionPeriod() * 60);
        }


        return true;
    }


    @Override
    @Transactional
    public boolean yxBuyerDeal(AuctionReq req) {
        LOGGER.info("开始调用 yxBuyerDeal,参数:{}", JSON.toJSONString(req));
        //验证合法性
        AuctionActivity activity = auctionActivityService.getById(req.getActivityId());
        Asset asset = assetService.getAsset((activity.getAssetId()));
        if (activity == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        if (activity == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        if (depositService.yxUserHasBuy(req.getActivityId())) {
            throw new BusinessException(ExceptionEnumImpl.AUCTION_HAS_END);
        }

        //是否已经交过保证金,未交保证金返回错误
        List<Deposit> depositList = checkDeposit(req.getPartyId(), activity.getId());
        if (depositList == null || depositList.isEmpty() ||
                depositList.get(0).getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_INIT)
                || !"0".equals(depositList.get(0).getAskResult())) {
            throw new BusinessException(ExceptionEnumImpl.NO_PAY_DEPOSIT);
        }

        if (req.getYxBuyFlag()) {
            //优先购买人要
            //更新deposit ask_result
            Deposit updateDepositParam = new Deposit();
            updateDepositParam.setId(depositList.get(0).getId());
            updateDepositParam.setAskResult("1");
            depositService.updateDepositById(updateDepositParam);

            //插入出价表
            List<Bid> bidList = bidService.getBidListByActivityId(activity.getId());
            req.setBidAmount(bidList.get(0).getAmount());
            insertBid(req);
            dealSuccess(asset, activity);

        } else {
            //优先购买人不要
            //更新deposit ask_result

            Deposit updateDepositParam = new Deposit();
            updateDepositParam.setId(depositList.get(0).getId());
            updateDepositParam.setAskResult("2");
            depositService.updateDepositById(updateDepositParam);

            List<Deposit> undealList = depositService.selectNoDealYX(activity.getId());
            if (undealList != null && !undealList.isEmpty()
                    && undealList.get(0).getLevel() != depositList.get(0).getLevel()) {
                checkYXBuyerIsEnd(asset, activity);
            }

        }

        return true;
    }

    @Override
    @Transactional
    public boolean bid(AuctionReq req) {
        LOGGER.info("开始调用 bid,参数:{}", JSON.toJSONString(req));
        //验证合法性
        AuctionActivity activity = auctionActivityService.getById(req.getActivityId());
        if (activity == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        Asset asset = assetService.getAsset((activity.getAssetId()));
        if (activity == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        //参数安全检查
        //req.setAgencyCode(null);
        commonCheckValid(req, activity, asset);

        Integer partyId = req.getPartyId();
        Integer activityId = req.getActivityId();
        String agencyCode = req.getAgencyCode();

        TAgency agency = getAgencyByCode(agencyCode);
        if (agency == null) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }


        //是否已经交过保证金,未交保证金返回错误
        List<Deposit> depositList = checkDeposit(partyId, activityId);
        if (depositList == null || depositList.isEmpty() || depositList.get(0).getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_INIT)) {
            throw new BusinessException(ExceptionEnumImpl.NO_PAY_DEPOSIT);
        }

        //出价金额是否合法
        checkBidValid(activity, req);
        //插入bid表
        final int[] bidArr = new int[1];
//        new AbstractRedisLock(SystemConstant.AUCTION_INSERT_BID_PRE_KEY + activity.getId(), 10,redisTemplate) {
//            @Override
//            public void dealBusiness() {
//                bidArr[0] = insertBid(req);
//            }
//        }.redisLock();

        bidArr[0] = insertBid(req);

        //更新activity当前价、出价次数
        updateActivityBidCount(req);

        //降价拍  成交
        if (asset.getExpectedMode().equals(AssetEnum.ExpectedMode.DUTCH)
                || asset.getExpectedMode().equals(AssetEnum.ExpectedMode.PUBLIC)) {

            List<Deposit> allDepositList = depositService.getDepositByActivityId(activityId);
            if (req.getBidAmount().compareTo(activity.getReservePrice()) < 0) {
                //出价金额<保留价
                updateActivityStatus(activityId, ActivityEnum.Status.FAILED, new Date());
                updateAssetStatus(asset.getId(), AssetEnum.Status.FAILED);
                for (Deposit deposit : allDepositList) {
                    //释放保证金
                    if (asset.getOnlined() == 1 && deposit.getStatus().equals(SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_LOCKED)) {
                        relaseDeposit(asset, deposit);
                    }
                }
            } else {
                checkYXBuyerIsEnd(asset, activity);
            }


        } else if (asset.getExpectedMode().equals(AssetEnum.ExpectedMode.ENGLISH)) {
            //增价拍  判断结束时间是否需要延时
            int tempMin = DateUtil.getMarginMin(activity.getEndAt(), new Date());
            if (tempMin < activity.getBiddingExtension() && !activity.getLockEndAt()) {
                //修改结束时间
                updateExpireTime(activity);
            }

        }
        return true;
    }

    private void updateExpireTime(AuctionActivity activity) {
        redisCachemanager.set(SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId(), activity.getId(), activity.getBiddingExtension() * 60);
        AuctionActivity updateParam = new AuctionActivity();
        updateParam.setId(activity.getId());
        updateParam.setIncrementAt(DateUtil.nextMinute(activity.getBiddingExtension()));
        auctionActivityService.updateActivityById(updateParam);
    }

    @Override
    public ContractVo getContractInfo(AuctionReq req) {
        LOGGER.info("开始调用 getContractInfo ,参数：{}", JSON.toJSONString(req));
        DealAgreement dealAgreement = dealAgreementService.getByOrderId(req.getOrderId(), AuctionOfflineEnum.ContractType.DEAL.getKey());
        ContractVo vo = new ContractVo();
        BeanUtils.copyProperties(dealAgreement, vo);
        return vo;
    }


    @Override
    public SignContractResp signLeaseContract(AuctionReq req) {
        LOGGER.info("开始调用 signLeaseContract，参数：{}", JSON.toJSONString(req));
        Integer partyId = req.getPartyId();
        Long orderId = req.getOrderId();
        AuctionOrder order = auctionOrderService.getById(orderId);

        Integer buyerId = order.getBuyerId();
        Integer sellerId = order.getSellerId();

        if (!String.valueOf(partyId).equals(String.valueOf(buyerId))
                && !String.valueOf(partyId).equals(String.valueOf(sellerId))) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }


        DealAgreement dealAgreement = dealAgreementService.getByOrderId(req.getOrderId(), AuctionOfflineEnum.ContractType.LEASE.getKey());
        if (dealAgreement == null || order == null || dealAgreement.getAllSigned()) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        SignContractResp signContractResp = new SignContractResp();

        ExtSignContractReq extSignContractReq = new ExtSignContractReq();
        extSignContractReq.setContract_id(dealAgreement.getContractId());
        extSignContractReq.setActivity_id(String.valueOf(order.getActivityId()));
        Asset asset = assetService.getAsset(auctionActivityService.getById(order.getActivityId()).getAssetId());
        AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());
        if (dataVO.getDeedFlag()) {
            extSignContractReq.setType(FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType());
        } else {
            extSignContractReq.setType(FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType());
        }

        List<FddSignInfo> fddSignInfoList = new ArrayList<FddSignInfo>();
        FddSignInfo signFddSignInfo = new FddSignInfo();
        signFddSignInfo.setParty_id(String.valueOf(partyId));
        if (String.valueOf(partyId).equals(String.valueOf(buyerId))) {
            signFddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.BUYER.getType());
        } else {
            signFddSignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.SELLER.getType());
        }
        signFddSignInfo.setIs_auto(FddEnums.SING_AUTO.NOT_AUTO.getType());
        AccountBaseDto signUserDto = accountService.getAccountBaseByPartyId(partyId);
        signFddSignInfo.setFdd_id(signUserDto.getFadadaId());
        signFddSignInfo.setMem_role(SystemConstant.ACCOUNT_USER_TYPE.equals(signUserDto.getType()) ? "1" : "2");

        fddSignInfoList.add(signFddSignInfo);

        FddSignInfo partyASignInfo = new FddSignInfo();
        partyASignInfo.setParty_id(order.getSellerId() + "");
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setIsDelete(false);
        condition.setPartId(order.getSellerId());
        TLeaseStaff leaseStaff = leaseStaffService.getLeaseStaffByCondition(condition);
        partyASignInfo.setFdd_id(leaseStaff.getFadadaId());
        partyASignInfo.setIs_auto(FddEnums.SING_AUTO.AUTO.getType());
        partyASignInfo.setSign_role(FddEnums.SING_ROLE_TYPE.AGENCY.getType());
        partyASignInfo.setMem_role("2");

        fddSignInfoList.add(partyASignInfo);
        extSignContractReq.setSign_list(fddSignInfoList);

        LOGGER.info("开始调用 fddSignatureFacade extSignContract，参数:{}", JSON.toJSONString(extSignContractReq));
        ExtSignContractResp resp = fddSignatureFacade.extSignContract(extSignContractReq);
        LOGGER.info("结束调用 fddSignatureFacade extSignContract，参数:{},结果:{}",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp));

        insertAuctionStep(order.getActivityId(), orderId, partyId, String.valueOf(partyId).equals(String.valueOf(buyerId)) ? "买家签订租赁协议" : "卖家签订租赁协议",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp), null, ApiCallResult.SUCCESS.getCode().equals(resp.getCode()) ? "SUCCESS" : "FAIL");

        if (ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
            signContractResp.setUrl(resp.getUrl());
            signContractResp.setParam(resp.getParam());
        }
        return signContractResp;
    }

    @Override
    public SignContractResp signContract(AuctionReq req) {
        LOGGER.info("开始调用 signContract，参数：{}", JSON.toJSONString(req));
        Integer partyId = req.getPartyId();
        Long orderId = req.getOrderId();
        DealAgreement dealAgreement = dealAgreementService.getByOrderId(req.getOrderId(), AuctionOfflineEnum.ContractType.DEAL.getKey());
        AuctionOrder order = auctionOrderService.getById(orderId);
        if (dealAgreement == null || order == null || dealAgreement.getAllSigned()) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        SignContractResp signContractResp = new SignContractResp();

        ExtSignContractReq extSignContractReq = new ExtSignContractReq();
        extSignContractReq.setContract_id(dealAgreement.getContractId());
        extSignContractReq.setActivity_id(String.valueOf(order.getActivityId()));

        if (assetService.getAssetById(auctionActivityService.getById(order.getActivityId()).getAssetId()).getCategoryId() == -1) {
            extSignContractReq.setType(FddEnums.SING_TYPE.LEASE_DEAL.getType());
        } else {
            extSignContractReq.setType(FddEnums.SING_TYPE.DEAL.getType());
        }


        Integer buyerId = order.getBuyerId();
        Integer sellerId = order.getSellerId();

        if (!String.valueOf(partyId).equals(String.valueOf(buyerId))
                && !String.valueOf(partyId).equals(String.valueOf(sellerId))) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        List<FddSignInfo> fddSignInfoList = new ArrayList<FddSignInfo>();
        fddSignInfoList.add(getFddSignInfo(partyId, buyerId, auctionActivityService.getById(order.getActivityId()).getAssetId()));
        transConranctReqInfo(dealAgreement, order, fddSignInfoList);
        extSignContractReq.setSign_list(fddSignInfoList);
        LOGGER.info("开始调用 fddSignatureFacade extSignContract，参数:{}", JSON.toJSONString(extSignContractReq));
        ExtSignContractResp resp = fddSignatureFacade.extSignContract(extSignContractReq);
        LOGGER.info("结束调用 fddSignatureFacade extSignContract，参数:{},结果:{}",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp));

        insertAuctionStep(order.getActivityId(), orderId, partyId, String.valueOf(partyId).equals(String.valueOf(buyerId)) ? "买家签订成交协议" : "卖家签订成交协议",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp), null, ApiCallResult.SUCCESS.getCode().equals(resp.getCode()) ? "SUCCESS" : "FAIL");

        if (ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
            signContractResp.setUrl(resp.getUrl());
            signContractResp.setParam(resp.getParam());
        }
        return signContractResp;
    }

    private void transConranctReqInfo(DealAgreement dealAgreement, AuctionOrder order, List<FddSignInfo> fddSignInfoList) {
//        if ((dealAgreement.getBuyerSigned() && !dealAgreement.getSellerSigned())
//                || (!dealAgreement.getBuyerSigned() && dealAgreement.getSellerSigned())) {

        //已经有一方签了，这个时候把三个平台带过去自动签

        Integer sellerAgencyId = order.getSellerAgencyId();
        Integer buyerAgencyId = order.getBuyerAgencyId();

        TAgency sellerAgency = agencyService.findByAgencyId(sellerAgencyId);
        FddSignInfo sellerFddSignInfo = new FddSignInfo();
        sellerFddSignInfo.setFdd_id(sellerAgency.getFadadaId());
        sellerFddSignInfo.setMem_role("2");
        sellerFddSignInfo.setIs_auto("1");
        sellerFddSignInfo.setParty_id(sellerAgencyId + "");
        sellerFddSignInfo.setSign_role("3");

        TAgency buyerAgency = agencyService.findByAgencyId(buyerAgencyId);
        FddSignInfo buyerFddSignInfo = new FddSignInfo();
        buyerFddSignInfo.setFdd_id(buyerAgency.getFadadaId());
        buyerFddSignInfo.setMem_role("2");
        buyerFddSignInfo.setIs_auto("1");
        buyerFddSignInfo.setParty_id(buyerAgencyId + "");
        buyerFddSignInfo.setSign_role("4");

        fddSignInfoList.add(buyerFddSignInfo);
        fddSignInfoList.add(sellerFddSignInfo);

/*            TAgency shbcAgency = getAgencyByCode(SystemConstant.DEFAULT_AGENCY_CODE);
            FddSignInfo fddSignInfo = new FddSignInfo();
            fddSignInfo.setFdd_id(shbcAgency.getFadadaId());
            fddSignInfo.setMem_role("2");
            fddSignInfo.setIs_auto("1");
            fddSignInfo.setParty_id(String.valueOf(shbcAgency.getId()));
            fddSignInfo.setSign_role("5");
            fddSignInfoList.add(fddSignInfo);*/

        //}
    }

    @Override
    public SignContractResp agencySignContract(AuctionReq req) {
        LOGGER.info("开始调用 signContract，参数：{}", JSON.toJSONString(req));
        Integer partyId = req.getPartyId();
        Long orderId = req.getOrderId();
        DealAgreement dealAgreement = dealAgreementService.getByOrderId(req.getOrderId(), AuctionOfflineEnum.ContractType.DEAL.getKey());
        AuctionOrder order = auctionOrderService.getById(orderId);
        if (dealAgreement == null || order == null || dealAgreement.getAllSigned()) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        SignContractResp signContractResp = new SignContractResp();

        ExtSignContractReq extSignContractReq = new ExtSignContractReq();
        extSignContractReq.setContract_id(dealAgreement.getContractId());
        extSignContractReq.setActivity_id(String.valueOf(order.getActivityId()));
        extSignContractReq.setType("3");

        Integer buyerId = order.getBuyerId();
        Integer sellerId = order.getSellerId();

        if (!String.valueOf(partyId).equals(String.valueOf(buyerId))
                && !String.valueOf(partyId).equals(String.valueOf(sellerId))) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        List<FddSignInfo> fddSignInfoList = new ArrayList<FddSignInfo>();
        fddSignInfoList.add(getAgencyFddSignInfo(partyId, buyerId));
        transConranctReqInfo(dealAgreement, order, fddSignInfoList);
        extSignContractReq.setSign_list(fddSignInfoList);
        LOGGER.info("开始调用 fddSignatureFacade extSignContract，参数:{}", JSON.toJSONString(extSignContractReq));
        ExtSignContractResp resp = fddSignatureFacade.extSignContract(extSignContractReq);
        LOGGER.info("结束调用 fddSignatureFacade extSignContract，参数:{},结果:{}",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp));
        insertAuctionStep(order.getActivityId(), orderId, partyId, String.valueOf(partyId).equals(String.valueOf(buyerId)) ? "买家签订成交协议" : "卖家签订成交协议",
                JSON.toJSONString(extSignContractReq), JSON.toJSONString(resp), null, ApiCallResult.SUCCESS.getCode().equals(resp.getCode()) ? "SUCCESS" : "FAIL");
        if (ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
            signContractResp.setUrl(resp.getUrl());
            signContractResp.setParam(resp.getParam());
        }

        return signContractResp;
    }


    @Override
    @Transactional
    public boolean signCallBack(String signRole, Integer partyId, Integer activityId, String contractId, boolean hasSuccess) {
        try {
            LOGGER.info("调用 signCallBack，partyId={},activityId={},contractId={}", partyId, activityId, contractId);
            AuctionActivity activity = auctionActivityService.getById(activityId);
            Asset asset = assetService.getAsset(activity.getAssetId());
            AuctionOrder order = auctionOrderService.getFirstByActivityId(activityId);
            // 更新 协议表
            //DealAgreement agreement = dealAgreementService.getByOrderId(order.getId(),AuctionOfflineEnum.ContractType.DEAL.getKey());
            DealAgreement agreement = dealAgreementService.getByContractId(contractId);

            if (agreement.getAllSigned()) {
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }

            if (agreement.getContractType().equals(AuctionOfflineEnum.ContractType.DEAL.getKey())) {
                //成交协议
                insertAuctionStep(activityId, order.getId(), partyId, FddEnums.SING_ROLE_TYPE.BUYER.getType().equals(signRole) ? "买家成交协议回调" : "卖家成交协议回调", contractId, null, null, hasSuccess ? "SUCCESS" : "FAIL");

            } else {
                //租赁协议
                insertAuctionStep(activityId, order.getId(), partyId, FddEnums.SING_ROLE_TYPE.BUYER.getType().equals(signRole) ? "买家租赁协议回调" : "卖家租赁协议回调", contractId, null, null, hasSuccess ? "SUCCESS" : "FAIL");

            }


            if (hasSuccess) {
                if (FddEnums.SING_ROLE_TYPE.BUYER.getType().equals(signRole)) {
                    agreement.setBuyerSigned(true);
                } else if (FddEnums.SING_ROLE_TYPE.SELLER.getType().equals(signRole)) {
                    agreement.setSellerSigned(true);
                } else {
                    throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
                }
                if (agreement.getBuyerSigned() && agreement.getSellerSigned()) {
                    agreement.setAllSigned(true);
                    //更新order表
                    if (asset.getOnlined() == 1) {
                        updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_NOT_PAID);
                        //支付时间超时设置
                        if (asset.getPayDays() != null && asset.getPayDays() > 0) {
                            long timeout = asset.getPayDays() * 24 * 60 * 60;
                            mqSender.auctionOrderPayTimeoutEnqueue(activity.getId() + "", timeout);
                        }
                    } else {
                        if (asset.getCategoryId() == -1) {
                            if (agreement.getContractType().equals(AuctionOfflineEnum.ContractType.DEAL.getKey())) {
                                //2019-05-27 牛逼的产品又说不签租赁协议了，不发货收货
                                //updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_NOT_SIGNED_LEASE);
                                updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_RECEIVED);
                            } else {
                                updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_PAID);
                            }

                        } else {
                            updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_PAID);
                        }
                    }
                }
                dealAgreementService.updateDealAgreement(agreement);
            }
        } catch (Exception e) {
            e.printStackTrace();
            insertAuctionStep(activityId, null, partyId, "法大大回调异常处理", null,
                    null, e.getMessage(), "FAIL");
        }

        return true;
    }

    @Override
    @Transactional
    public boolean confirmSend(AuctionReq req) {
        LOGGER.info("开始调用 confirmSend，参数：{}", JSON.toJSONString(req));
        Long orderId = req.getOrderId();
        Integer sellerPartyId = req.getPartyId();
        AuctionOrder order = auctionOrderService.getByIdAndPartyId(orderId, null, sellerPartyId);
        if (order == null || order.getId() == null) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        if (!SystemConstant.AUCTION_ORDER_STATUS_PAID.equals(order.getStatus())) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        boolean result = updateAuctionOrderStatus(orderId, SystemConstant.AUCTION_ORDER_STATUS_DELIVERED);
        if (!result) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        AccountBaseDto buyerBaseInfo = accountService.getAccountBaseByPartyId(order.getBuyerId());
        smsHelperService.auctionReceiptNotify(buyerBaseInfo.getMobile(), assetService.getAsset(auctionActivityService.getById(order.getActivityId()).getAssetId()).getName());

        return true;

    }

    @Override
    @Transactional
    public boolean revSend(AuctionReq req) {
        LOGGER.info("开始调用 revSend，参数：{}", JSON.toJSONString(req));
        Long orderId = req.getOrderId();
        Integer buyerPartyId = req.getPartyId();
        AuctionOrder order = auctionOrderService.getByIdAndPartyId(orderId, buyerPartyId, null);
        try {

            if (order == null || order.getId() == null) {
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            if (!SystemConstant.AUCTION_ORDER_STATUS_DELIVERED.equals(order.getStatus())) {
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
            Asset asset = assetService.getAsset(auctionActivityService.getById(order.getActivityId()).getAssetId());
            updateAuctionOrderStatus(orderId, SystemConstant.AUCTION_ORDER_STATUS_RECEIVED);

            if (asset.getOnlined() == 1) {
                //交易完成，解鎖保證金、尾款、各種佣金
                releaseAllAmount(orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            insertAuctionStep(order.getActivityId(), orderId, buyerPartyId, "收货异常", null,
                    null, e.getMessage(), "FAIL");
        }

        return true;
    }

    private boolean releaseAllAmount(Long orderId) {
        AuctionOrder order = auctionOrderService.getById(orderId);
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerInfo = getSellerPayInfo(asset);
        TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);
        if (!sellerInfo.isBank()) {
            //解锁保证金
            invokeGatewayRelease(SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + orderId, payOrder.getDepositPayId());
            //解锁尾款
            invokeGatewayRelease(SystemConstant.RELEASE_BUYER_REMAIN + "_" + orderId, payOrder.getBuyerRemainPayId());
        }

        //解锁送拍佣金
        invokeGatewayRelease(SystemConstant.RELEASE_SELLER_AGENCY_COMMISSION_PRE_KEY + "_" + orderId, payOrder.getSellerAgencyCommissionPayId());
        //解锁买方代理佣金
        invokeGatewayRelease(SystemConstant.RELEASE_BUYER_CHANNEL_COMMISSION_PRE_KEY + "_" + orderId, payOrder.getBuyerChannelCommissionPayId());
        //解锁卖方代理佣金
        invokeGatewayRelease(SystemConstant.RELEASE_SELLER_CHANNEL_COMMISSION_PRE_KEY + "_" + orderId, payOrder.getSellerChannelCommissionPayId());

        //解锁联拍佣金
        if (payOrder.getBuyerFromApplet() != 1) {
            invokeGatewayRelease(SystemConstant.RELEASE_BUYER_AGENCY_COMMISSION_PRE_KEY + "_" + orderId, payOrder.getBuyerAgencyCommissionPayId());
        } else {
            if (payOrder.getBelongShopCommission() != null
                    && payOrder.getBelongShopCommission().compareTo(BigDecimal.ZERO) > 0) {
                acctService.addAcctAmount(payOrder.getBelongAcctId(), payOrder.getBelongShopCommission(), AccountEnum.AcctOperateType.BUY_COMMISSION.getKey(), orderId + "", null);
                sendSmsWhenCommission(payOrder);


                if (payOrder.getParentCommission() != null
                        && payOrder.getParentCommission().compareTo(BigDecimal.ZERO) > 0) {
                    acctService.addAcctAmount(payOrder.getParentAcctId(), payOrder.getParentCommission(), AccountEnum.AcctOperateType.BUY_COMMISSION.getKey(), orderId + "", null);
                    sendSmsWhenCommission(payOrder);
                }
            }
        }

        return true;
    }

    private void sendSmsWhenCommission(TAuctionPayOrder payOrder) {
        if (AccountEnum.AcctType.USER.getKey().equals(payOrder.getBelongShopType())
                || AccountEnum.AcctType.COMPANY.getKey().equals(payOrder.getBelongShopType())) {
            smsHelperService.commissionReminderNotify(accountService.getNotifierMobile(Integer.parseInt(payOrder.getBelongShopCode())));
            appletMessageService.sendCommissionReminderMessage(Integer.parseInt(payOrder.getBelongShopCode()));
        } else {
            smsHelperService.commissionReminderNotify(agencyService.findByAgencyId(Integer.parseInt(payOrder.getBelongShopCode())).getMobile());
        }
    }

    private boolean invokeGatewayRelease(String busId, String payId) {
        String preKey = busId.split("_")[0];
        Long orderId = Long.parseLong(busId.split("_")[1]);
        AuctionOrder order = auctionOrderService.getById(orderId);

        if (!StringUtils.isEmpty(payId)) {

            UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
            unifiedPayReq.setBusId(busId);
            unifiedPayReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
            unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.RELEASE_DEPOSIT.getType());
            LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
            lockOrReleaseOrDirectReq.setOriginalPayID(payId);
            unifiedPayReq.setPayParam(lockOrReleaseOrDirectReq);
            LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
            UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
            LOGGER.info("结束调用 payFacade unifiedPay ,参数:{}，结果:{}",
                    JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
            String step = "";
            if (SystemConstant.RELEASE_BUYER_DEPOSIT.equals(preKey)) {
                step = "保证金解锁";
            } else if (SystemConstant.RELEASE_BUYER_REMAIN.equals(preKey)) {
                step = "尾款解锁";
            } else if (SystemConstant.RELEASE_BUYER_AGENCY_COMMISSION_PRE_KEY.equals(preKey)) {
                step = "分佣联拍机构解锁";
            } else if (SystemConstant.RELEASE_SELLER_AGENCY_COMMISSION_PRE_KEY.equals(preKey)) {
                step = "分佣送拍机构解锁";
            } else if (SystemConstant.RELEASE_BUYER_CHANNEL_COMMISSION_PRE_KEY.equals(preKey)) {
                step = "分佣买家代理解锁";
            } else if (SystemConstant.RELEASE_SELLER_CHANNEL_COMMISSION_PRE_KEY.equals(preKey)) {
                step = "分佣卖家代理解锁";
            }
            insertAuctionStep(order.getActivityId(), orderId, null, step, JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp),
                    null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
            if (payResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
                return true;
            }
        }
        return false;
    }

    private DfftResp buyerPayRemain(AuctionReq req) {
        LOGGER.info("开始调用  payRemain, 参数:{}", JSON.toJSONString(req));

        DfftResp dfftResp = new DfftResp();

        Long orderId = req.getOrderId();
        Integer partyId = req.getPartyId();
        //验证协议是否已签
        DealAgreement agreement = dealAgreementService.getByOrderId(orderId, AuctionOfflineEnum.ContractType.DEAL.getKey());
        if (!agreement.getAllSigned()) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        //验证订单表状态是否正确
        AuctionOrder order = auctionOrderService.getByIdAndPartyId(orderId, partyId, null);
        if (order == null || !order.getStatus().equals(SystemConstant.AUCTION_ORDER_STATUS_NOT_PAID)) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        if (!String.valueOf(order.getBuyerId()).equals(String.valueOf(partyId))) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }

        UnifiedPayReq unifiedPayReq = transBuyerReq(order);
        LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
        UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
        LOGGER.info("结束调用 payFacade unifiedPay ,参数:{}，结果:{}",
                JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
        insertAuctionStep(order.getActivityId(), orderId, partyId, "买家佣金尾款支付", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp),
                null, PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
        if (payResp != null && PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())) {
            dfftResp.setUrl(payResp.getUrl());
            dfftResp.setParam(payResp.getParam());

/*            List<BatchPayInfoVo> payInfoVoList = payResp.getBathPayInfo();
            TAuctionPayOrder payOrder = new TAuctionPayOrder();
            payOrder.setOrderId(order.getId());
            for (BatchPayInfoVo payInfoVo : payInfoVoList) {
                if (SystemConstant.BUYER_REMAIN_PRE_KEY.equals(payInfoVo.getBusId().split("_")[0])) {
                    payOrder.setBuyerRemainPayId(payInfoVo.getPayOrder());
                } else if (SystemConstant.BUYER_COMMISSION_PRE_KEY.equals(payInfoVo.getBusId().split("_")[0])) {
                    payOrder.setBuyerCommissionPayId(payInfoVo.getPayOrder());
                }
                auctionPayOrderService.updateAuctionPayOrder(payOrder);
            }*/

        }
        return dfftResp;
    }

    @Override
    @Transactional
    public void payCallBack(String busId, String payId, String callBackStatus) {
        LOGGER.info("开始调用 payCallBack ,busId={},payId={},callBackStatus={}", busId, payId, callBackStatus);
        String busKey = busId.split("_")[0];
        Long orderId = Long.parseLong(busId.split("_")[1]);

        AuctionOrder order = new AuctionOrder();
        order.setId(orderId);

        AuctionOrder auctionOrder = auctionOrderService.getById(orderId);
        AuctionActivity activity = auctionActivityService.getById(auctionOrder.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerInfo = getSellerPayInfo(asset);

        TAuctionPayOrder updateParam = new TAuctionPayOrder();
        updateParam.setOrderId(orderId);

        // buyerHasPayEnd 买家尾款
        // buyerPaidOrder 买家佣金
        // sellerPaidOrder卖家佣金
        // sellerHasPayEnd卖家佣金
        try {
            if (SystemConstant.BUYER_REMAIN_PRE_KEY.equals(busKey)) {

                insertAuctionStep(activity.getId(), orderId, null, "买家尾款回调", null,
                        null, null, SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus) ? "SUCCESS" : "FAIL");

                if (SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus) && sellerInfo.isBank()) {
                    //银行类
                    TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);
                    UnifiedPayReq payReq = invokeGatewayPayToBank(auctionOrder.getSellerId(), SystemConstant.RELEASE_BUYER_REMAIN + "_" + orderId, payOrder.getBuyerRemainPayAmount());
                    LOGGER.info("开始调用 payFacade unifiedPay，参数:{}", JSON.toJSONString(payReq));
                    UnifiedPayResp payResp = payFacade.unifiedPay(payReq);
                    LOGGER.info("结束调用 payFacade unifiedPay，参数:{},结果:{}", JSON.toJSONString(payReq), JSON.toJSONString(payResp));

                    insertAuctionStep(activity.getId(), orderId, null, "买家尾款回调银行类打款", JSON.toJSONString(payReq),
                            JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
                }
                order.setBuyerHasPayEnd(callBackStatus);
                updateParam.setBuyerRemainPayId(payId);
            } else if (SystemConstant.BUYER_COMMISSION_PRE_KEY.equals(busKey)) {
                //更新 order 表 buyer_paid_order
                order.setBuyerPaidOrder(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus));
                //更新 order 表 buyer_has_pay_end
                insertAuctionStep(activity.getId(), orderId, null, "买家佣金回调", null,
                        null, null, SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus) ? "SUCCESS" : "FAIL");
                updateParam.setBuyerCommissionPayId(payId);
            } else if (SystemConstant.SELLER_COMMISSION_PRE_KEY.equals(busKey)) {
                //更新 order 表 seller_paid_commission seller_has_pay_end
                order.setSellerPaidOrder(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus));
                order.setSellerHasPayEnd(callBackStatus);
                insertAuctionStep(activity.getId(), orderId, null, "卖家佣金回调", null,
                        null, null, SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS.equals(callBackStatus) ? "SUCCESS" : "FAIL");
                updateParam.setSellerCommissionPayId(payId);
            }

            auctionPayOrderService.updateAuctionPayOrder(updateParam);
            auctionOrderService.updateAuctionOrder(order);
            boolean hasAllPayFlag = hasAllPay(orderId);
            if (hasAllPayFlag) {
                order.setStatus(SystemConstant.AUCTION_ORDER_STATUS_PAID);
            }
            auctionOrderService.updateAuctionOrder(order);
            //送拍、联拍、平台、渠道代理分佣
            if (hasAllPayFlag) {
                shareCommission(orderId);
                smsHelperService.auctionShipNotify(sellerInfo.getMobile(), asset.getName());
            }


        } catch (Exception e) {
            insertAuctionStep(order.getActivityId(), order.getId(), null, "付款回调处理异常", busId,
                    null, e.getMessage(), "FAIL");
        }
    }

    private boolean hasAllPay(Long orderId) {
        // buyerHasPayEnd 买家尾款
        // buyerPaidOrder 买家佣金
        // sellerPaidOrder卖家佣金
        // sellerHasPayEnd卖家佣金
        AuctionOrder order = auctionOrderService.getById(orderId);
        boolean allPayFlag = false;
        if (order.getSellerPaidCommission()) {
            if (order.getBuyerPaidCommission()) {

                allPayFlag = order.getSellerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                        && order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                        && order.getBuyerPaidOrder();
            } else {
                allPayFlag = order.getSellerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                        && order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS);
            }

        } else {

            if (order.getBuyerPaidCommission()) {
                allPayFlag = order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS) && order.getBuyerPaidOrder();
            } else {
                allPayFlag = order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS);
            }

        }
        return allPayFlag;
    }

    public void shareCommission(Long orderId) {

        TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);

        boolean releaseBuyerCommissionFlag = false;
        boolean releaseSellerCommissionFlag = false;

        boolean shareCommissionFlag = payOrder.getBuyerCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0
                || payOrder.getSellerCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0;


        AuctionOrder order = auctionOrderService.getById(orderId);
        //解锁买家佣金给到平台
        if (payOrder.getBuyerCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
            UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
            unifiedPayReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
            unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
            unifiedPayReq.setBusId(SystemConstant.RELEASE_BUYER_COMMISSION_TO_BC_PRE_KEY + "_" + String.valueOf(orderId));
            LockOrReleaseOrDirectReq directReq = new LockOrReleaseOrDirectReq();
            directReq.setOriginalPayID(payOrder.getBuyerCommissionPayId());
            unifiedPayReq.setPayParam(directReq);
            LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
            UnifiedPayResp unifiedPayResp = payFacade.unifiedPay(unifiedPayReq);
            LOGGER.info("结束调用 payFacade unifiedPay ,参数:{},结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(unifiedPayResp));
            insertAuctionStep(order.getActivityId(), orderId, null, "买家佣金解锁到平台", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(unifiedPayResp),
                    null, PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode()) ? "SUCCESS" : "FAIL");
            if (unifiedPayResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode())) {
                return;
            } else {
                releaseBuyerCommissionFlag = true;
            }
        }
        //解锁賣家佣金给到平台
        if (payOrder.getSellerCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
            UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
            unifiedPayReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
            unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
            unifiedPayReq.setBusId(SystemConstant.RELEASE_SELLER_COMMISSION_TO_BC_PRE_KEY + "_" + String.valueOf(orderId));
            LockOrReleaseOrDirectReq directReq = new LockOrReleaseOrDirectReq();
            directReq.setOriginalPayID(payOrder.getSellerCommissionPayId());
            unifiedPayReq.setPayParam(directReq);
            LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
            UnifiedPayResp unifiedPayResp = payFacade.unifiedPay(unifiedPayReq);
            LOGGER.info("结束调用 payFacade unifiedPay ,参数:{},结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(unifiedPayResp));
            insertAuctionStep(order.getActivityId(), orderId, null, "卖家佣金解锁到平台", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(unifiedPayResp),
                    null, PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode()) ? "SUCCESS" : "FAIL");
            if (unifiedPayResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode())) {
                return;
            } else {
                releaseSellerCommissionFlag = true;
            }
        }

        if (shareCommissionFlag) {

            String buyerAgencyCommissionPayId = null;
            String sellerAgencyCommissionPayId = null;
            String buyerChannelCommissionPayId = null;
            String sellerChannelCommissionPayId = null;

            if (payOrder.getBuyerFromApplet() != 1) {
                if (payOrder.getBuyerAgencyCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //承拍机构分润
                    TAgency agency = agencyService.findByAgencyId(order.getBuyerAgencyId());
                    if (agency == null) {
                        throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
                    }
                    buyerAgencyCommissionPayId = getCommissionPayId(SystemConstant.BUYER_AGENCY_COMMISSION_PRE_KEY, orderId, payOrder.getBuyerAgencyCommissionPayAmount(), agency.getName(), agency.getDfftId());
                }
            }
            if (payOrder.getSellerAgencyCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
                //送拍机构分润
                TAgency agency = agencyService.findByAgencyId(order.getSellerAgencyId());
                if (agency == null) {
                    throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
                }
                sellerAgencyCommissionPayId = getCommissionPayId(SystemConstant.SELLER_AGENCY_COMMISSION_PRE_KEY, orderId, payOrder.getSellerAgencyCommissionPayAmount(), agency.getName(), agency.getDfftId());

            }
            if (payOrder.getBuyerChannelCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
                //竞买人渠道分润
                PartyChannelAgent channelAgent = partyChannelAgentService.findChannelByPartyId(order.getBuyerId());
                if (channelAgent != null) {
                    AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(channelAgent.getChannelAgentPartyId());
                    buyerChannelCommissionPayId = getCommissionPayId(SystemConstant.BUYER_CHANNEL_COMMISSION_PRE_KEY, orderId, payOrder.getBuyerChannelCommissionPayAmount(), accountBaseDto.getName(), accountBaseDto.getDfftId());
                }
            }
            if (payOrder.getSellerChannelCommissionPayAmount().compareTo(BigDecimal.ZERO) > 0) {
                //委托人渠道分润
                PartyChannelAgent channelAgent = partyChannelAgentService.findChannelByPartyId(order.getSellerId());
                if (channelAgent != null) {
                    AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(channelAgent.getChannelAgentPartyId());
                    sellerChannelCommissionPayId = getCommissionPayId(SystemConstant.SELLER_CHANNEL_COMMISSION_PRE_KEY, orderId, payOrder.getSellerChannelCommissionPayAmount(), accountBaseDto.getName(), accountBaseDto.getDfftId());
                }
            }

            TAuctionPayOrder updateParam = new TAuctionPayOrder();
            updateParam.setOrderId(orderId);
            updateParam.setBuyerAgencyCommissionPayId(buyerAgencyCommissionPayId);
            updateParam.setSellerAgencyCommissionPayId(sellerAgencyCommissionPayId);
            updateParam.setBuyerChannelCommissionPayId(buyerChannelCommissionPayId);
            updateParam.setSellerChannelCommissionPayId(sellerChannelCommissionPayId);
            auctionPayOrderService.updateAuctionPayOrder(updateParam);
        }


    }

    private String getCommissionPayId(String busPreKey, Long orderId, BigDecimal amount, String revName, String revDfftId) {
        UnifiedPayReq req = new UnifiedPayReq();
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
        req.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        req.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
        req.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        req.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
        req.setBusId(busPreKey + "_" + orderId);
        req.setAmount(amount);
        req.setWhoPay(PayEnums.WHO_PAY.WEB_PAY.getType());

        AuctionOrder order = auctionOrderService.getById(orderId);
        TAgency shbcAgency = getAgencyByCode(SystemConstant.DEFAULT_AGENCY_CODE);
        LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
        lockOrReleaseOrDirectReq.setPayMemName(shbcAgency.getName());
        lockOrReleaseOrDirectReq.setPayMemCode(shbcAgency.getDfftId());
        lockOrReleaseOrDirectReq.setRecMemName(revName);
        lockOrReleaseOrDirectReq.setRecMemCode(revDfftId);
        lockOrReleaseOrDirectReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        req.setPayParam(lockOrReleaseOrDirectReq);
        LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(req));
        UnifiedPayResp unifiedPayResp = payFacade.unifiedPay(req);
        LOGGER.info("结束调用 payFacade unifiedPay ,参数:{},结果:{}", JSON.toJSONString(req), JSON.toJSONString(unifiedPayResp));
        String step = "";
        if (SystemConstant.BUYER_AGENCY_COMMISSION_PRE_KEY.equals(busPreKey)) {
            step = "分佣联拍机构锁定";
        } else if (SystemConstant.SELLER_AGENCY_COMMISSION_PRE_KEY.equals(busPreKey)) {
            step = "分佣送拍机构锁定";
        } else if (SystemConstant.BUYER_CHANNEL_COMMISSION_PRE_KEY.equals(busPreKey)) {
            step = "分佣买家代理锁定";
        } else if (SystemConstant.SELLER_CHANNEL_COMMISSION_PRE_KEY.equals(busPreKey)) {
            step = "分佣卖家代理锁定";
        }
        insertAuctionStep(order.getActivityId(), orderId, null, step, JSON.toJSONString(req), JSON.toJSONString(unifiedPayResp),
                null, PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode()) ? "SUCCESS" : "FAIL");
        if (unifiedPayResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode())) {
            return unifiedPayResp.getPayOrder();
        } else {
            return null;
        }
    }


    private UnifiedPayReq transBuyerReq(AuctionOrder order) {

        AccountBaseDto buyerBaseInfo = accountService.getAccountBaseByPartyId(order.getBuyerId());

        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerBaseInfo = getSellerPayInfo(asset);

        //1.尾款= 成交价-保证金
        BigDecimal remainAmt = order.getAmount().subtract(order.getDeposit());

        UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
        unifiedPayReq.setAmount(remainAmt);
        unifiedPayReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.JUMP_PAY.getType());
        unifiedPayReq.setPartyId(order.getBuyerId());
        unifiedPayReq.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        unifiedPayReq.setPayType(PayEnums.PAY_TYPE.BATCH_PAY.getType());
        unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
        unifiedPayReq.setBusId(String.valueOf(order.getId()));
        if (sellerBaseInfo.isBank()) {
            unifiedPayReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        } else {
            unifiedPayReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        }
        unifiedPayReq.setAmount(remainAmt);
        List<BatchDirectReq> reqList = new ArrayList<BatchDirectReq>();

        BatchDirectReq remainReq = new BatchDirectReq();
        remainReq.setPayMemCode(buyerBaseInfo.getDfftId());
        remainReq.setPayMemName(buyerBaseInfo.getName());
        if (sellerBaseInfo.isBank()) {
            remainReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
            remainReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
        } else {
            remainReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
            remainReq.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
            remainReq.setRecMemCode(sellerBaseInfo.getDfftId());
            remainReq.setRecMemName(sellerBaseInfo.getName());
        }
        remainReq.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
        remainReq.setBusId(SystemConstant.BUYER_REMAIN_PRE_KEY + "_" + String.valueOf(order.getId()));
        remainReq.setAmount(remainAmt);

        reqList.add(remainReq);

        if (order.getBuyerPaidCommission()) {
            //付佣金
            unifiedPayReq.setAmount(remainAmt.add(caculateBuyerCommission(order)));
            BatchDirectReq commissionReq = new BatchDirectReq();
            commissionReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
            commissionReq.setPayMemCode(buyerBaseInfo.getDfftId());
            commissionReq.setPayMemName(buyerBaseInfo.getName());
            commissionReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
            commissionReq.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
            commissionReq.setBusId(SystemConstant.BUYER_COMMISSION_PRE_KEY + "_" + String.valueOf(order.getId()));
            commissionReq.setAmount(caculateBuyerCommission(order));
            reqList.add(commissionReq);

        }
        unifiedPayReq.setPayParam(reqList);

        return unifiedPayReq;
    }

    private BigDecimal caculateBuyerCommission(AuctionOrder order) {
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());
        BigDecimal commission = BigDecimal.ZERO;

        if (asset.getCategoryId() == -1) {
            //租赁权项目
            AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());
            commission = order.getAmount().multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).multiply(new BigDecimal(dataVO.getLesseeCommissionRate())).divide(new BigDecimal(1200), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            if (activity.getCommissionPercentBuyer() == null) {
                commission = activity.getCommissionBuyer();
            } else {
                commission = order.getAmount().multiply(activity.getCommissionPercentBuyer()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            }
        }


        return commission;
    }

    private BigDecimal caculateSellerCommission(AuctionOrder order) {
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());

        BigDecimal commission = BigDecimal.ZERO;

        if (asset.getCategoryId() == -1) {
            //租赁权项目
            AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());
            commission = order.getAmount().multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).multiply(new BigDecimal(dataVO.getLessorCommissionRate())).divide(new BigDecimal(1200), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            if (activity.getCommissionPercentSeller() == null) {
                commission = activity.getCommissionSeller();
            } else {
                commission = order.getAmount().multiply(activity.getCommissionPercentSeller()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
            }
        }


        return commission;
    }


    private DfftResp sellerPayRemain(AuctionReq req) {
        LOGGER.info("开始调用  sellerPayRemain, 参数:{}", JSON.toJSONString(req));

        DfftResp dfftResp = new DfftResp();

        Long orderId = req.getOrderId();
        Integer partyId = req.getPartyId();
        //验证协议是否已签
        DealAgreement agreement = dealAgreementService.getByOrderId(orderId, AuctionOfflineEnum.ContractType.DEAL.getKey());
        if (!agreement.getAllSigned()) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        //验证订单表状态是否正确
        AuctionOrder order = auctionOrderService.getByIdAndPartyId(orderId, null, partyId);
        if (order == null || !order.getStatus().equals(SystemConstant.AUCTION_ORDER_STATUS_NOT_PAID)) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }


        if (!order.getSellerPaidCommission()) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if (SystemConstant.PAY_ORDER_STATUS__PAYING.equals(order.getSellerHasPayEnd())
                || order.getSellerPaidOrder()) {
            throw new BusinessException(ExceptionEnumImpl.BE_PAYING_ERROR);
        }

        if (!String.valueOf(order.getSellerId()).equals(String.valueOf(partyId))) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        UnifiedPayReq unifiedPayReq = transSellerReq(orderId, partyId);

        LOGGER.info("开始调用 payFacade unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
        UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
        LOGGER.info("结束调用 payFacade unifiedPay ,参数:{}，结果:{}",
                JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
        insertAuctionStep(order.getActivityId(), orderId, partyId, "卖家佣金支付", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp),
                null, PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
        if (payResp != null && PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())) {
            dfftResp.setUrl(payResp.getUrl());
            dfftResp.setParam(payResp.getParam());

/*            TAuctionPayOrder auctionPayOrder = new TAuctionPayOrder();
            auctionPayOrder.setOrderId(order.getId());
            auctionPayOrder.setSellerCommissionPayId(payResp.getPayOrder());
            auctionPayOrderService.updateAuctionPayOrder(auctionPayOrder);*/

        }
        return dfftResp;
    }

    private UnifiedPayReq transSellerReq(Long orderId, Integer partyId) {
        UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
        unifiedPayReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.JUMP_PAY.getType());
        unifiedPayReq.setPayType(PayEnums.PAY_TYPE.DIRECT_PAY.getType());
        unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.BALANCE_PAY.getType());
        unifiedPayReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
        unifiedPayReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
        unifiedPayReq.setBusId(SystemConstant.SELLER_COMMISSION_PRE_KEY + "_" + String.valueOf(orderId));
        unifiedPayReq.setPartyId(partyId);
        TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);
        unifiedPayReq.setAmount(payOrder.getSellerCommissionPayAmount());

        LockOrReleaseOrDirectReq directReq = new LockOrReleaseOrDirectReq();
        directReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());

        AuctionOrder order = auctionOrderService.getById(orderId);
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());

        SellerPayInfo sellerInfo = getSellerPayInfo(asset);
        directReq.setPayMemCode(sellerInfo.getDfftId());
        directReq.setPayMemName(sellerInfo.getName());
        unifiedPayReq.setPayParam(directReq);
        return unifiedPayReq;
    }

    private boolean updateAuctionOrderStatus(Long orderId, String status) {
        AuctionOrder updateOrderParam = new AuctionOrder();
        updateOrderParam.setId(orderId);
        updateOrderParam.setStatus(status);
        return auctionOrderService.updateAuctionOrder(updateOrderParam);
    }

    private FddSignInfo getFddSignInfo(Integer partyId, Integer buyerId, Integer assetId) {
        AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(partyId);
        Asset asset = assetService.getAsset(assetId);
        FddSignInfo fddSignInfo = new FddSignInfo();
        fddSignInfo.setParty_id(String.valueOf(partyId));
        fddSignInfo.setFdd_id(baseDto.getFadadaId());
        if (String.valueOf(partyId).equals(String.valueOf(buyerId))) {
            fddSignInfo.setSign_role("2");
        } else {
            if (asset.getCategoryId() == -1) {
                TLeaseStaffCondition condition = new TLeaseStaffCondition();
                condition.setPartId(partyId);
                condition.setIsDelete(false);
                TLeaseStaff leaseStaff = leaseStaffService.getLeaseStaffByCondition(condition);
                fddSignInfo.setFdd_id(leaseStaff.getFadadaId());
            }
            fddSignInfo.setSign_role("1");
        }
        fddSignInfo.setIs_auto("2");
        if (SystemConstant.ACCOUNT_COMPANY_TYPE.equals(baseDto.getType())) {
            fddSignInfo.setMem_role("2");
        } else if (SystemConstant.ACCOUNT_USER_TYPE.equals(baseDto.getType())) {
            fddSignInfo.setMem_role("1");
        } else {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        return fddSignInfo;
    }

    private FddSignInfo getAgencyFddSignInfo(Integer partyId, Integer buyerId) {
        TAgency baseDto = agencyService.findByAgencyId(partyId);
        FddSignInfo fddSignInfo = new FddSignInfo();
        fddSignInfo.setParty_id(String.valueOf(partyId));
        fddSignInfo.setFdd_id(baseDto.getFadadaId());
        fddSignInfo.setSign_role("1");
        fddSignInfo.setIs_auto("2");
        fddSignInfo.setMem_role("2");
        return fddSignInfo;
    }

    private void chooseBiderToTrans(Long depositId, Integer buyerPartyId, Integer bidId, BigDecimal bidAmount, Integer agencyId, AuctionActivity activity, Asset asset) {
        Date currentTime = new Date();
        Long orderId = Long.parseLong(RandomNumberGenerator.generatorOrderNum(4));
        Deposit deposit = depositService.getDepositById(depositId);
        SellerPayInfo accountBaseDto = getSellerPayInfo(asset);
        if (asset.getOnlined() == 1) {
            //调用东方付通保证金支付
            UnifiedPayReq unifiedPayReq = invokeGatewayPayReq(SystemConstant.PAY_BUSINESS_TYPE_PAID, buyerPartyId, asset, activity.getDeposit(), depositId);

            LOGGER.info("开始调用 payFacade unifiedPay,参数:{}", JSON.toJSONString(unifiedPayReq));
            UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
            LOGGER.info("结束调用 payFacade unifiedPay,参数:{}，结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));
            insertAuctionStep(activity.getId(), orderId, buyerPartyId, "保证金支付", JSON.toJSONString(unifiedPayReq),
                    JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");
            if (PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {


                if (accountBaseDto.isBank()) {
                    //银行类保证金支付第二步
                    UnifiedPayReq payReq = invokeGatewayPayToBank(asset.getPartyId(), SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + String.valueOf(orderId), activity.getDeposit());
                    LOGGER.info("开始调用 payFacade unifiedPay，参数:{}", JSON.toJSONString(payReq));
                    UnifiedPayResp unifiedPayResp = payFacade.unifiedPay(payReq);
                    LOGGER.info("结束调用 payFacade unifiedPay，参数:{},结果:{}", JSON.toJSONString(payReq), JSON.toJSONString(unifiedPayResp));

                    insertAuctionStep(activity.getId(), orderId, buyerPartyId, "保证金支付银行打款", JSON.toJSONString(payReq),
                            JSON.toJSONString(unifiedPayResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");

                    if (unifiedPayResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(unifiedPayResp.getCode())) {
                        updateDeposit(depositId, SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_TRANSFERRED, "银行类保证金支付成功", unifiedPayResp.getPayOrder());
                    }

                } else {
                    updateDeposit(depositId, SystemConstant.DEPOSIT_PAY_STATUS_ONLINE_TRANSFERRED, "保证金支付成功", payResp.getPayOrder());
                }
                //落表 auction_order
                insertAuctionOrder(buyerPartyId, bidAmount, agencyId, activity, asset, bidId, orderId);
                //插入 t_auction_pay_order 表
                insertAuctionPayOrder(SystemConstant.PAY_TYPE_ONLINE, orderId, payResp.getPayOrder());
                //更新auction_activity status success

                updateActivityStatus(activity.getId(), ActivityEnum.Status.SUCCESS, currentTime);
                //更新 asset status CLOSED
                updateAssetStatus(asset.getId(), AssetEnum.Status.CLOSED);
            } else {
                //保证金支付失败，流拍，解锁保证金
                updateActivityStatus(activity.getId(), ActivityEnum.Status.FAILED, currentTime);
                //更新 asset status CLOSED
                updateAssetStatus(asset.getId(), AssetEnum.Status.FAILED);
                //竞买人保证金解锁
                relaseDeposit(asset, deposit);
            }
        } else {
            //线下
            //更新auction_activity status success

            updateActivityStatus(activity.getId(), ActivityEnum.Status.SUCCESS, currentTime);
            //更新 asset status CLOSED
            updateAssetStatus(asset.getId(), AssetEnum.Status.CLOSED);

            //更新deposit表
            updateDeposit(depositId, SystemConstant.DEPOSIT_PAY_STATUS_OFFLINE_FINISHED, "", "");

            //落表 auction_order
            insertAuctionOrder(buyerPartyId, bidAmount, agencyId, activity, asset, bidId, orderId);

            //插入 t_auction_pay_order 表
            insertAuctionPayOrder(SystemConstant.PAY_TYPE_OFFLINE, orderId, "");

            if (asset.getCategoryId() == -1) {
                //生成租赁合同
                GenerateContractResp resp = invokeFddCreateLeaseContract(orderId);
                if (resp != null && resp.getCode().equals(ApiCallResult.SUCCESS.getCode())) {
                    //落表 deal_agreement
                    insertDealAgreement(orderId, resp, AuctionOfflineEnum.ContractType.LEASE.getKey());
                }
            }
        }

        if (asset.getCategoryId() != -1) {
            //调用法大大生成合同
            GenerateContractResp resp = invokeFddCreateContract(activity, asset, buyerPartyId, agencyId, bidAmount, currentTime);
            if (resp != null && resp.getCode().equals(ApiCallResult.SUCCESS.getCode())) {
                //落表 deal_agreement
                insertDealAgreement(orderId, resp, AuctionOfflineEnum.ContractType.DEAL.getKey());
            }
        } else {
            GenerateContractResp resp = invokeFddCreateLeaseDealContract(orderId, currentTime);
            if (resp != null && resp.getCode().equals(ApiCallResult.SUCCESS.getCode())) {
                //落表 deal_agreement
                insertDealAgreement(orderId, resp, AuctionOfflineEnum.ContractType.DEAL.getKey());
            }
        }
        AccountBaseDto buyerBaseInfo = accountService.getAccountBaseByPartyId(buyerPartyId);

        try {
            smsHelperService.toSignDealAgreementNotify(buyerBaseInfo.getMobile(), buyerBaseInfo.getName(), asset.getName());
            smsHelperService.toSignDealAgreementNotify(accountBaseDto.getMobile(), buyerBaseInfo.getName(), asset.getName());
            smsHelperService.auctionSuccessfulNotify(buyerBaseInfo.getMobile(), buyerBaseInfo.getName(), asset.getName());
        } catch (Exception e) {
            LOGGER.info("发送短信异常:{}", e.getMessage());
            e.printStackTrace();
        }
        long timeout = 24 * 60 * 60;
        mqSender.auctionActivitySignTimeoutEnqueue(activity.getId() + "", timeout);
    }

    public void insertAuctionPayOrder(String channel, Long orderId, String depositPayId) {

        TAuctionPayOrder payOrder = new TAuctionPayOrder();

        payOrder.setDepositPayId(depositPayId);
        payOrder.setOrderId(orderId);
        AuctionOrder order = auctionOrderService.getById(orderId);
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());


        payOrder.setChannel(channel);
        payOrder.setBuyerPartyId(order.getBuyerId());
        payOrder.setBuyerAgencyId(order.getBuyerAgencyId());

        payOrder.setDepositPayAmount(order.getDeposit());
        BigDecimal buyerRemainAmount = BigDecimal.ZERO;
        if (asset.getCategoryId() == -1) {
            AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());

            buyerRemainAmount = calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()).add(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP)).subtract(order.getDeposit());
        } else {
            buyerRemainAmount = order.getAmount().subtract(order.getDeposit());
        }
        payOrder.setBuyerRemainPayAmount(buyerRemainAmount);
        BigDecimal buyerCommission = BigDecimal.ZERO;
        if (order.getBuyerPaidCommission()) {
            buyerCommission = caculateBuyerCommission(order);
        }
        BigDecimal sellerCommission = BigDecimal.ZERO;
        if (order.getSellerPaidCommission()) {
            sellerCommission = caculateSellerCommission(order);
        }
        payOrder.setBuyerCommissionPayAmount(buyerCommission);
        payOrder.setSellerCommissionPayAmount(sellerCommission);

        BigDecimal totalCommission = buyerCommission.add(sellerCommission);
        TAgency buyerAgency = agencyService.findByAgencyId(order.getBuyerAgencyId());
        TAgency sellerAgency = agencyService.findByAgencyId(order.getSellerAgencyId());
        BigDecimal buyerAgencyCommsion = totalCommission.multiply(new BigDecimal(buyerAgency.getServeBuyerPercent())).divide(new BigDecimal(100));
        BigDecimal sellerAgencyCommsion = totalCommission.multiply(new BigDecimal(sellerAgency.getServeSellerPercent())).divide(new BigDecimal(100));
        BigDecimal SHBCCommission = totalCommission.subtract(buyerAgencyCommsion).subtract(sellerAgencyCommsion);

        payOrder.setBuyerAgencyCommissionPayAmount(buyerAgencyCommsion);
        payOrder.setSellerAgencyCommissionPayAmount(sellerAgencyCommsion);
        payOrder.setPlatformCommissionPayAmount(SHBCCommission);

        Integer buyerId = order.getBuyerId();
        Integer sellerId = order.getSellerId();
        PartyChannelAgent buyerChannelAgent = partyChannelAgentService.findChannelByPartyId(buyerId);
        BigDecimal buyerChannelCommission = BigDecimal.ZERO;
        if (buyerChannelAgent != null && String.valueOf(buyerAgency.getCode()).equals(SystemConstant.DEFAULT_AGENCY_CODE)) {
            buyerChannelCommission = SHBCCommission.multiply(buyerChannelAgent.getCommissionPercentChannelAgent()).divide(new BigDecimal(100));
        }

        PartyChannelAgent sellerChannelAgent = partyChannelAgentService.findChannelByPartyId(sellerId);
        BigDecimal sellerChannelCommission = BigDecimal.ZERO;
        if (sellerChannelAgent != null && String.valueOf(sellerAgency.getCode()).equals(SystemConstant.DEFAULT_AGENCY_CODE)) {
            sellerChannelCommission = SHBCCommission.multiply(sellerChannelAgent.getCommissionPercentChannelAgent()).divide(new BigDecimal(100));
        }

        payOrder.setBuyerChannelCommissionPayAmount(buyerChannelCommission);
        payOrder.setSellerChannelCommissionPayAmount(sellerChannelCommission);


        AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(order.getBuyerId());
        if (accountBaseDto.isFromApplet()) {
            //来自小程序  设置分佣
            payOrder.setBuyerFromApplet(1);
            payOrder.setBelongShopCode(accountBaseDto.getParentId() + "");
            payOrder.setBelongShopType(accountBaseDto.getParentType());
            TAcct belongAcct = acctService.findAcctByPartyIdAndType(accountBaseDto.getParentId(), accountBaseDto.getParentType());
            payOrder.setBelongAcctId(belongAcct.getId());
            if (!StringUtils.isEmpty(accountBaseDto.getPparentType())) {
                payOrder.setParentCode(accountBaseDto.getPparentId() + "");
                payOrder.setParentType(accountBaseDto.getPparentType());
                payOrder.setParentCommission(totalCommission.multiply(new BigDecimal(systemProperties.getSecondDealCommissionPercent())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));
                TAcct parentAcct = acctService.findAcctByPartyIdAndType(accountBaseDto.getPparentId(), accountBaseDto.getPparentType());
                payOrder.setParentAcctId(parentAcct.getId());
                payOrder.setBelongShopCommission(totalCommission.multiply(new BigDecimal(systemProperties.getFirstDealCommissionPercent())).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));

            } else {
                BigDecimal totalPercent = new BigDecimal(systemProperties.getFirstDealCommissionPercent()).add(new BigDecimal(systemProperties.getSecondDealCommissionPercent()));
                payOrder.setBelongShopCommission(totalCommission.multiply(totalPercent).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP));

            }
        }

        auctionPayOrderService.saveAuctionPayOrder(payOrder);


        if (asset.getCategoryId() == -1) {
            //租赁

            TAuctionOfflineFinance buyerFinance = new TAuctionOfflineFinance();
            buyerFinance.setOrderId(orderId);
            buyerFinance.setAuctionName(activity.getAssetName());
            buyerFinance.setStaus(AuctionOfflineEnum.ConfirmStatus.NO_CONFIRM.getKey());
            buyerFinance.setFinanceType(AuctionOfflineEnum.FinanceType.REMAIN_AND_COMMISSION.getKey());
            buyerFinance.setRoleType(AuctionOfflineEnum.RoleType.BUYER.getKey());
            buyerFinance.setRemainAmount(buyerRemainAmount.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
            buyerFinance.setShouldReceiveCommissionAmount(buyerCommission.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
            buyerFinance.setShouldReceiveTotalAmount(buyerRemainAmount.add(buyerCommission).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
            buyerFinance.setUserName(accountBaseDto.getName());
            buyerFinance.setUserMobile(accountBaseDto.getMobile());
            auctionOfflineFinanceService.saveFinanceInfo(buyerFinance);

            if (order.getSellerPaidCommission()) {
                TAuctionOfflineFinance finance = new TAuctionOfflineFinance();
                finance.setOrderId(orderId);
                finance.setAuctionName(activity.getAssetName());
                finance.setStaus(AuctionOfflineEnum.ConfirmStatus.NO_CONFIRM.getKey());
                finance.setFinanceType(AuctionOfflineEnum.FinanceType.COMMISSION.getKey());
                finance.setRoleType(AuctionOfflineEnum.RoleType.SELLER.getKey());
                finance.setShouldReceiveCommissionAmount(sellerCommission.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                finance.setShouldReceiveTotalAmount(sellerCommission.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
                AccountBaseDto sellerDto = accountService.getAccountBaseByPartyId(order.getSellerId());
                finance.setUserName(sellerDto.getName());
                finance.setUserMobile(sellerDto.getMobile());
                auctionOfflineFinanceService.saveFinanceInfo(finance);
            }


        }


    }

    private void insertDealAgreement(Long orderId, GenerateContractResp resp, String contractType) {
        DealAgreement dealAgreement = new DealAgreement();
        dealAgreement.setViewpdfUrl(resp.getViewPdfUrl());
        dealAgreement.setDownloadUrl(resp.getDownloadUrl());
        dealAgreement.setContractId(resp.getContractId());
        dealAgreement.setOrderId(orderId);
        dealAgreement.setSellerSigned(false);
        dealAgreement.setBuyerSigned(false);
        dealAgreement.setAllSigned(false);
        dealAgreement.setContractType(contractType);
        dealAgreementService.saveDealAgreement(dealAgreement);
    }

    private void updateAssetStatus(Integer assetId, AssetEnum.Status status) {
        Asset asset = new Asset();
        asset.setId(assetId);
        asset.setStatus(status);
        assetService.updateAssetById(asset);
    }


    public GenerateContractResp invokeFddCreateLeaseDealContract(Long orderId, Date currentTime) {
        AuctionOrder order = auctionOrderService.getById(orderId);
        if (order == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());

        GenerateContractComReq generateContractComReq = new GenerateContractComReq();
        generateContractComReq.setActivityId(String.valueOf(activity.getId()));
        generateContractComReq.setType(FddEnums.SING_TYPE.LEASE_DEAL.getType());

        LeaseGenerateDealReq leaseGenerateDealReq = new LeaseGenerateDealReq();
        leaseGenerateDealReq.setActivityCode(activity.getCode());
        leaseGenerateDealReq.setSeller(getLeaseSellerInfo(order.getSellerId()).getName());
        leaseGenerateDealReq.setBuyer(accountService.getAccountBaseByPartyId(order.getBuyerId()).getName());
        leaseGenerateDealReq.setAuctionFirm(agencyService.findByAgencyId(order.getSellerAgencyId()).getName());
        leaseGenerateDealReq.setUnionAuctionFirm(agencyService.findByAgencyId(order.getBuyerAgencyId()).getName());
        leaseGenerateDealReq.setAuctionPeriod(DateUtil.dateToStrLong(activity.getBeginAt()) + "~" + DateUtil.dateToStrLong(currentTime));
        leaseGenerateDealReq.setOrderTime(DateUtil.dateToStrLong(currentTime));
        leaseGenerateDealReq.setLotCode(activity.getCode());
        //成交金额
        leaseGenerateDealReq.setHammerPrice(order.getAmount() + "");
        leaseGenerateDealReq.setLotName(activity.getAssetName());
        leaseGenerateDealReq.setCommission(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP) + "");


        AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());

        //改版后
        leaseGenerateDealReq.setBuyerPercent(new BigDecimal(dataVO.getLesseeCommissionRate()).multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        leaseGenerateDealReq.setBuyerCommission(order.getAmount().multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).multiply(new BigDecimal(dataVO.getLesseeCommissionRate())).divide(new BigDecimal(1200), 2, BigDecimal.ROUND_HALF_UP) + "");
        leaseGenerateDealReq.setSellerPercent(new BigDecimal(dataVO.getLessorCommissionRate()).multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        leaseGenerateDealReq.setSellerCommission(order.getAmount().multiply(auctionOrderService.getLeaseCommissionDiscount(order.getAmount())).multiply(new BigDecimal(dataVO.getLessorCommissionRate())).divide(new BigDecimal(1200), 2, BigDecimal.ROUND_HALF_UP) + "");

        //竞买牌号
        Bid bid = bidService.getBidById(order.getBidId());
        DepositCondition depositCondition = new DepositCondition();
        depositCondition.setActivityId(activity.getId());
        depositCondition.setPartyId(bid.getPartyId());
        List<Deposit> depositList = depositService.findDeposit(depositCondition);
        if (depositList != null && !depositList.isEmpty()) {
            leaseGenerateDealReq.setDepositCode(depositList.get(0).getCode());
        }
        //首次实际支付成 交价金额
        leaseGenerateDealReq.setDealAmount(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()) + "");
        //租赁保证金
        leaseGenerateDealReq.setDeposit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP) + "");
        //合计金额
        leaseGenerateDealReq.setTotalAmount(new BigDecimal(leaseGenerateDealReq.getDealAmount()).add(new BigDecimal(leaseGenerateDealReq.getDeposit())).add(new BigDecimal(leaseGenerateDealReq.getBuyerCommission())).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
        leaseGenerateDealReq.setTotalAmountChn(NumberToCN.number2CNMontrayUnit(new BigDecimal(leaseGenerateDealReq.getTotalAmount())));
        leaseGenerateDealReq.setPaymentPeriod(asset.getPayDays() + "");
        leaseGenerateDealReq.setDiscount(auctionOrderService.getLeaseCommissionDiscount(order.getAmount()).multiply(new BigDecimal(100)).setScale(0,BigDecimal.ROUND_HALF_UP)+"");
        LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(leaseGenerateDealReq));
        GenerateContractResp resp = fddSignatureFacade.generateContract(generateContractComReq, leaseGenerateDealReq);
        LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{},result:{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(leaseGenerateDealReq), JSON.toJSONString(resp));
        return resp;
    }

    private GenerateContractResp invokeFddCreateLeaseContract(Long orderId) {
        AuctionOrder order = auctionOrderService.getById(orderId);
        if (order == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());

        GenerateContractComReq generateContractComReq = new GenerateContractComReq();
        generateContractComReq.setActivityId(String.valueOf(activity.getId()));
        AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(asset.getId(), AssetEnum.WebFlag.WEB.getKey());
        if (dataVO == null) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        if (dataVO.getDeedFlag()) {
            //有证
            generateContractComReq.setType(FddEnums.SING_TYPE.LEASE_HAS_LICENSE.getType());
            LeaseHasLicenseReq hasLicenseReq = new LeaseHasLicenseReq();
            hasLicenseReq.setAssetCode(asset.getCode());
            hasLicenseReq.setActivityCode(activity.getCode());
            hasLicenseReq.setSeller(getLeaseSellerInfo(order.getSellerId()).getName());
            hasLicenseReq.setBuyer(accountService.getAccountBaseByPartyId(order.getBuyerId()).getName());
            hasLicenseReq.setAddress(dataVO.getLeaseAddress());
            hasLicenseReq.setStructure(AssetEnum.HouseStructure.getValueByKey(dataVO.getHouseStructure()));
            hasLicenseReq.setArea(dataVO.getLeaseArea() + "");
            hasLicenseReq.setUse(dataVO.getLegalPurposes());
            hasLicenseReq.setBusinessProject(dataVO.getBusinessNow());
            hasLicenseReq.setLeaseYears(calculateTimeBetYear(DateUtil.formatNormDate(dataVO.getLeaseStartTime()), DateUtil.formatNormDate(dataVO.getLeaseEndTime())) + "");
            hasLicenseReq.setLeaseStartTime(DateUtil.formatNormDate(dataVO.getLeaseStartTime()));
            hasLicenseReq.setLeaseEndTime(DateUtil.formatNormDate(dataVO.getLeaseEndTime()));
            hasLicenseReq.setDeal_amount(order.getAmount() + "");
            hasLicenseReq.setIncrease(dataVO.getAnnualIncrementRate());
            hasLicenseReq.setPayWay(AssetEnum.PaymentCycle.getValueByKey(dataVO.getPaymentCycle()));
            hasLicenseReq.setDeposit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP) + "");
            hasLicenseReq.setRent(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()) + "");
            hasLicenseReq.setTotalAmount(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP).add(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle())) + "");
            hasLicenseReq.setDepositChn(NumberToCN.number2CNMontrayUnit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP)));
            hasLicenseReq.setRentChn(NumberToCN.number2CNMontrayUnit(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle())));
            hasLicenseReq.setTotalAmountChn(NumberToCN.number2CNMontrayUnit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP).add(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()))));
            hasLicenseReq.setSide("出租方".equals(AssetEnum.CostBearer.getValueByKey(dataVO.getCostBearer())) ? "甲" : "乙");
            hasLicenseReq.setSupplement(dataVO.getLesseeOtherAgreement());
            hasLicenseReq.setSellerAddress(dataVO.getLessorAddress());
            hasLicenseReq.setSellerPhone(getLeaseSellerInfo(order.getSellerId()).getMobile());
            AccountBaseDto buyerDto = accountService.getAccountBaseByPartyId(order.getBuyerId());
            hasLicenseReq.setBuyerAddress(buyerDto.getAddress());
            hasLicenseReq.setBuyerNum(buyerDto.getIdOrLicenceNo());
            hasLicenseReq.setBuyerPhone(buyerDto.getMobile());
            hasLicenseReq.setUser(accountService.getAccountBaseByPartyId(order.getSellerId()).getName());

            LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(hasLicenseReq));
            GenerateContractResp resp = fddSignatureFacade.generateContract(generateContractComReq, hasLicenseReq);
            LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{},result:{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(hasLicenseReq), JSON.toJSONString(resp));
            return resp;

        } else {
            //无证
            generateContractComReq.setType(FddEnums.SING_TYPE.LEASE_WITHOUT_LICENSE.getType());
            LeaseWithoutLicenseReq withoutLicenseReq = new LeaseWithoutLicenseReq();
            withoutLicenseReq.setAssetCode(asset.getCode());
            withoutLicenseReq.setActivityCode(activity.getCode());
            withoutLicenseReq.setSeller(getLeaseSellerInfo(order.getSellerId()).getName());
            withoutLicenseReq.setBuyer(accountService.getAccountBaseByPartyId(order.getBuyerId()).getName());
            withoutLicenseReq.setAddress(dataVO.getLeaseAddress());
            withoutLicenseReq.setStructure(AssetEnum.HouseStructure.getValueByKey(dataVO.getHouseStructure()));
            withoutLicenseReq.setArea(dataVO.getLeaseArea() + "");
            withoutLicenseReq.setUse(dataVO.getLegalPurposes());
            withoutLicenseReq.setBusinessProject(dataVO.getBusinessNow());
            withoutLicenseReq.setLeaseYears(calculateTimeBetYear(DateUtil.formatNormDate(dataVO.getLeaseStartTime()), DateUtil.formatNormDate(dataVO.getLeaseEndTime())) + "");
            withoutLicenseReq.setLeaseStartTime(DateUtil.formatNormDate(dataVO.getLeaseStartTime()));
            withoutLicenseReq.setLeaseEndTime(DateUtil.formatNormDate(dataVO.getLeaseEndTime()));
            withoutLicenseReq.setDeal_amount(order.getAmount() + "");
            withoutLicenseReq.setIncrease(dataVO.getAnnualIncrementRate());
            withoutLicenseReq.setPayWay(AssetEnum.PaymentCycle.getValueByKey(dataVO.getPaymentCycle()));
            withoutLicenseReq.setDeposit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP) + "");
            withoutLicenseReq.setRent(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()) + "");
            withoutLicenseReq.setTotalAmount(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP).add(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle())) + "");
            withoutLicenseReq.setDepositChn(NumberToCN.number2CNMontrayUnit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP)));
            withoutLicenseReq.setRentChn(NumberToCN.number2CNMontrayUnit(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle())));
            withoutLicenseReq.setTotalAmountChn(NumberToCN.number2CNMontrayUnit(order.getAmount().divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP).add(calculateLeaseRent(order.getAmount(), dataVO.getPaymentCycle()))));
            withoutLicenseReq.setSide("出租方".equals(AssetEnum.CostBearer.getValueByKey(dataVO.getCostBearer())) ? "甲" : "乙");
            withoutLicenseReq.setSupplement(dataVO.getLesseeOtherAgreement());
            withoutLicenseReq.setSellerAddress(dataVO.getLessorAddress());
            withoutLicenseReq.setSellerPhone(getLeaseSellerInfo(order.getSellerId()).getMobile());
            AccountBaseDto buyerDto = accountService.getAccountBaseByPartyId(order.getBuyerId());
            withoutLicenseReq.setBuyerAddress(buyerDto.getAddress());
            withoutLicenseReq.setBuyerNum(buyerDto.getIdOrLicenceNo());
            withoutLicenseReq.setBuyerPhone(buyerDto.getMobile());
            withoutLicenseReq.setUser(accountService.getAccountBaseByPartyId(order.getSellerId()).getName());

            LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(withoutLicenseReq));
            GenerateContractResp resp = fddSignatureFacade.generateContract(generateContractComReq, withoutLicenseReq);
            LOGGER.info("开始调用 fddSignatureFacade.generateContract，参数:{},{},result:{}", JSON.toJSONString(generateContractComReq), JSON.toJSONString(withoutLicenseReq), JSON.toJSONString(resp));
            return resp;
        }
    }

    private BigDecimal calculateLeaseRent(BigDecimal dealAmount, String payWay) {
        if (AssetEnum.PaymentCycle.YEAR.getKey().equals(payWay)) {
            return dealAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        } else if (AssetEnum.PaymentCycle.HALF_YEAR.getKey().equals(payWay)) {
            return dealAmount.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
        } else if(AssetEnum.PaymentCycle.MONTH.getKey().equals(payWay)) {
            // 2019-05-31 呵呵，产品又加了个付款方式，按月
            return dealAmount.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        } else {
            return dealAmount.divide(new BigDecimal(4), 2, BigDecimal.ROUND_HALF_UP);
        }
    }

    private int calculateTimeBetYear(String startStr, String endStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();

        try {
            startTime.setTime(sdf.parse(startStr));
            endTime.setTime(sdf.parse(endStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endTime.get(Calendar.YEAR) - startTime.get(Calendar.YEAR);
    }

    private AccountBaseDto getLeaseSellerInfo(Integer sellerPartyId) {
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setPartId(sellerPartyId);
        condition.setIsDelete(false);
        TLeaseStaff staff = leaseStaffService.getLeaseStaffByCondition(condition);
        if (staff != null) {
            AccountBaseDto baseDto = accountService.getAccountBaseByPartyId(staff.getComId());
            return baseDto;
        } else {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
    }

    private GenerateContractResp invokeFddCreateContract(AuctionActivity activity, Asset asset, Integer partyId, Integer agencyId, BigDecimal bidAmount, Date finishAt) {
        GenerateContractComReq contractComReq = new GenerateContractComReq();
        contractComReq.setActivityId(String.valueOf(activity.getId()));
        contractComReq.setPartyId(String.valueOf(partyId));
        AccountBaseDto buyerBaseInfo = accountService.getAccountBaseByPartyId(partyId);
        SellerPayInfo sellerBaseInfo = getSellerPayInfo(asset);
        contractComReq.setFddId(buyerBaseInfo.getFadadaId());
        contractComReq.setType(FddEnums.SING_TYPE.DEAL.getType());

        GenerateDealReq generateDealReq = new GenerateDealReq();
        generateDealReq.setActivityCode(activity.getCode());
        generateDealReq.setSeller(sellerBaseInfo.getName());
        generateDealReq.setAuctionFirm(agencyService.findByAgencyId(activity.getAgencyId()).getName());
        generateDealReq.setBuyer(buyerBaseInfo.getName());
        generateDealReq.setUnionAuctionFirm(agencyService.findByAgencyId(agencyId).getName());
        //竞拍时间
        generateDealReq.setAuctionPeriod(DateUtil.dateToStrLong(activity.getBeginAt()) + "~" + DateUtil.dateToStrLong(finishAt));
        generateDealReq.setOrderTime(DateUtil.dateToStrLong(finishAt));
        generateDealReq.setLotCode(activity.getCode());
        generateDealReq.setHammerPrice(String.valueOf(bidAmount.setScale(2, BigDecimal.ROUND_HALF_UP)));
        generateDealReq.setLotName(activity.getAssetName());
        generateDealReq.setSellerCommission(BigDecimal.ZERO + "");
        generateDealReq.setBuyerCommission(BigDecimal.ZERO + "");
        generateDealReq.setSellerCommissionRate(BigDecimal.ZERO + "");
        generateDealReq.setBuyerCommissionRate(BigDecimal.ZERO + "");
        if (true) {
            if (activity.getCommissionPercentSeller() != null
                    && activity.getCommissionPercentSeller().compareTo(BigDecimal.ZERO) > 0) {
                generateDealReq.setSellerCommissionRate(String.valueOf(activity.getCommissionPercentSeller().setScale(2, BigDecimal.ROUND_HALF_UP)));
                generateDealReq.setSellerCommission(bidAmount.multiply(activity.getCommissionPercentSeller()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
            }
            if (activity.getCommissionPercentBuyer() != null
                    && activity.getCommissionPercentBuyer().compareTo(BigDecimal.ZERO) > 0) {
                generateDealReq.setBuyerCommissionRate(String.valueOf(activity.getCommissionPercentBuyer().setScale(2, BigDecimal.ROUND_HALF_UP)));
                generateDealReq.setBuyerCommission(bidAmount.multiply(activity.getCommissionPercentBuyer()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP) + "");

            }
        } else {
            if (activity.getCommissionSeller() != null
                    && activity.getCommissionSeller().compareTo(BigDecimal.ZERO) > 0) {
                generateDealReq.setSellerCommissionFee(String.valueOf(activity.getCommissionSeller().setScale(2, BigDecimal.ROUND_HALF_UP)));
                generateDealReq.setSellerCommission(String.valueOf(activity.getCommissionSeller().setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
            if (activity.getCommissionBuyer() != null
                    && activity.getCommissionBuyer().compareTo(BigDecimal.ZERO) > 0) {
                generateDealReq.setBuyerCommissionFee(String.valueOf(activity.getCommissionBuyer().setScale(2, BigDecimal.ROUND_HALF_UP)));
                generateDealReq.setBuyerCommission(String.valueOf(activity.getCommissionBuyer().setScale(2, BigDecimal.ROUND_HALF_UP)));
            }
        }

        generateDealReq.setTotalAmount(bidAmount.add(new BigDecimal(generateDealReq.getBuyerCommission())).add(new BigDecimal(generateDealReq.getSellerCommission())).setScale(2, BigDecimal.ROUND_HALF_UP) + "");
        generateDealReq.setTotalAmountChn(NumberToCN.number2CNMontrayUnit(new BigDecimal(generateDealReq.getTotalAmount())));
        generateDealReq.setPaymentPeriod(asset.getPayDays() + "");
        LOGGER.info("开始调用 fddSignatureFacade generateContract ,参数:{}", JSON.toJSONString(generateDealReq));
        GenerateContractResp resp = fddSignatureFacade.generateContract(contractComReq, generateDealReq);
        LOGGER.info("结束调用 fddSignatureFacade generateContract ,参数:{},返回结果:{}", JSON.toJSONString(generateDealReq), JSON.toJSONString(resp));
        insertAuctionStep(activity.getId(), null, null, "生成法大大成交协议", "公共参数:" + JSON.toJSONString(contractComReq) + ",补充参数:" + JSON.toJSONString(generateDealReq),
                JSON.toJSONString(resp), null, ApiCallResult.SUCCESS.getCode().equals(resp.getCode()) ? "SUCCESS" : "FAIL");

        return resp;
    }

    private void insertAuctionOrder(Integer partyId, BigDecimal bidAmount, Integer agencyId, AuctionActivity activity, Asset asset, Integer bidId, Long orderId) {
        AuctionOrder order = new AuctionOrder();
        order.setId(orderId);
        order.setActivityId(activity.getId());
        order.setDeposit(activity.getDeposit());
        order.setAmount(bidAmount);
        order.setBidId(bidId);
        //AssetCategory assetCategory = assetCategoryService.getById(asset.getCategoryId());
        //order.setAssetCategory(assetCategory.getName());
        order.setBuyerId(partyId);
        order.setBuyerAgencyId(agencyId);
        order.setSellerId(asset.getPartyId());
        order.setSellerAgencyId(activity.getAgencyId());
        order.setBuyerPaidCommission(activity.getCommissionPercentBuyer().compareTo(BigDecimal.ZERO) > 0);
        order.setSellerPaidCommission(activity.getCommissionPercentSeller().compareTo(BigDecimal.ZERO) > 0);
        order.setServeBuyerPercent(new BigDecimal(agencyService.findByAgencyId(agencyId).getServeBuyerPercent()));
        order.setServeSellerPercent(new BigDecimal(agencyService.findByAgencyId(activity.getAgencyId()).getServeSellerPercent()));
        order.setStatus(SystemConstant.AUCTION_ORDER_STATUS_NOT_SIGNED);
        order.setComeFrom(asset.getComeFrom());
        auctionOrderService.saveAuctionOrder(order);
    }


    private boolean updateActivityStatus(Integer activityId, ActivityEnum.Status status, Date endDate) {
        AuctionActivity auctionActivity = new AuctionActivity();
        auctionActivity.setId(activityId);
        auctionActivity.setStatus(status);
        auctionActivity.setFinishAt(endDate);
        return auctionActivityService.updateActivityById(auctionActivity);
    }

    private boolean updateActivityBidCount(AuctionReq req) {
        AuctionActivity auctionActivity = new AuctionActivity();
        auctionActivity.setBidCount(1);
        auctionActivity.setCurrentPrice(req.getBidAmount());
        auctionActivity.setId(req.getActivityId());
        return auctionActivityService.updateActivityById(auctionActivity);

    }

    private Integer insertBid(AuctionReq req) {
        Bid bid = new Bid();
        bid.setActivityId(req.getActivityId());
        bid.setAmount(req.getBidAmount());
        bid.setPartyId(req.getPartyId());
        bid.setAgencyId(getAgencyByCode(req.getAgencyCode()).getId());
        boolean flag = bidService.saveBid(bid);
        if (!flag) {
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        return bid.getId();
    }

    private TAgency getAgencyByCode(String agencyCode) {
        TAgency agency = new TAgency();
        if (!StringUtils.isEmpty(agencyCode)) {
            agency = agencyService.findByAgencyCode(agencyCode);
        } else {
            agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
        }
        return agency;
    }

    private void checkBidValid(AuctionActivity activity, AuctionReq req) {
        if (req.getBidAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if (!ActivityEnum.Status.ONLINE.equals(activity.getStatus())) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if (ActivityEnum.ActivityMode.PUBLIC.getKey().equals(activity.getMode())
                && req.getBidAmount().compareTo(activity.getReservePrice()) != 0) {
            throw new BusinessException(ExceptionEnumImpl.AMOUNT_MUST_BE_EQ_REPRICE);
        }

        if (ActivityEnum.ActivityMode.ENGLISH.getKey().equals(activity.getMode())
                && req.getBidAmount().compareTo(activity.getStartingPrice()) < 0) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        if (ActivityEnum.ActivityMode.ENGLISH.getKey().equals(activity.getMode())
                && activity.getCurrentPrice() != null
                && req.getBidAmount().compareTo(activity.getCurrentPrice()) <= 0) {
            throw new BusinessException(ExceptionEnumImpl.PRICE_TOO_LESS);
        }

        if (ActivityEnum.ActivityMode.ENGLISH.getKey().equals(activity.getMode())) {

            try {
                int times = req.getBidAmount().subtract(activity.getStartingPrice()).divide(activity.getIncrement()).intValue();
            } catch (Exception e) {
                throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
            }
        }

        if (ActivityEnum.ActivityMode.DUTCH.getKey().equals(activity.getMode())) {
            if (activity.getCurrentPrice() == null && req.getBidAmount().compareTo(activity.getStartingPrice()) > 0) {
                throw new BusinessException(ExceptionEnumImpl.PRICE_TOO_HIGH);
            }
            if (activity.getCurrentPrice() != null && req.getBidAmount().compareTo(activity.getCurrentPrice()) > 0) {
                throw new BusinessException(ExceptionEnumImpl.PRICE_TOO_HIGH);
            }
        }


        List<Bid> bidList = bidService.getBidListByActivityId(activity.getId());
        if (bidList != null && !bidList.isEmpty()) {
            if (ActivityEnum.ActivityMode.DUTCH.getKey().equals(activity.getMode())
                    || ActivityEnum.ActivityMode.PUBLIC.getKey().equals(activity.getMode())) {
                //降价拍、明标
                throw new BusinessException(ExceptionEnumImpl.AUCTION_HAS_END);
            } else if (ActivityEnum.ActivityMode.ENGLISH.getKey().equals(activity.getMode())) {
                //增价拍
                if (bidList.get(0).getPartyId().equals(req.getPartyId())) {
                    throw new BusinessException(ExceptionEnumImpl.BEEN_HIGH_PRICE);

                }
            } else if (ActivityEnum.ActivityMode.SEALED.getKey().equals(activity.getMode())) {
                bidList = bidService.getBidListByActivityIdAndPartyId(activity.getId(), req.getPartyId());
                if (bidList.size() >= 3) {
                    throw new BusinessException(ExceptionEnumImpl.SEALED_ONLY_THREE);
                }
            }
        }


    }


    public UnifiedPayReq invokeGatewayPayToBank(Integer sellerId, String busId, BigDecimal amount) {
        AccountBaseDto sellerInfo = accountService.getAccountBaseByPartyId(sellerId);
        UnifiedChannelPayReq req = new UnifiedChannelPayReq();
        req.setBankCode(sellerInfo.getBankCode());
        req.setAccName(sellerInfo.getBankAccountName());
        req.setAccNO(sellerInfo.getBankAccountNo());
        UnifiedPayReq payReq = new UnifiedPayReq();
        payReq.setWhoPay(PayEnums.WHO_PAY.WEB_PAY.getType());
        payReq.setPayParam(req);
        payReq.setPayType(PayEnums.PAY_TYPE.CHANNEL_PAY.getType());
        payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.CHANNEL_PAY.getType());
        payReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        payReq.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
        payReq.setAmount(amount);
        payReq.setBusId(busId);
        return payReq;
    }


    private SellerPayInfo getSellerPayInfo(Asset asset) {
        SellerPayInfo sellerPayInfo = new SellerPayInfo();
        if ("0".equals(asset.getComeFrom())) {
            //平台
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(asset.getPartyId());
            sellerPayInfo.setMobile(accountBaseDto.getMobile());
            //if (String.valueOf(asset.getSpvId()).equals("0")) {

            sellerPayInfo.setBank(accountBaseDto.isBank());
            sellerPayInfo.setDfftId(accountBaseDto.getDfftId());
            sellerPayInfo.setFddId(accountBaseDto.getFadadaId());
            sellerPayInfo.setName(accountBaseDto.getName());
           /* } else {
                TSpv spv = spvService.getSpvById(asset.getSpvId());
                sellerPayInfo.setBank(false);
                sellerPayInfo.setDfftId(spv.getDfftId());
                sellerPayInfo.setFddId(spv.getFddId());
                sellerPayInfo.setName(spv.getName());
            }*/

        } else if ("1".equals(asset.getComeFrom())) {
            //机构
            TAgency agency = agencyService.findByAgencyId(asset.getPartyId());
            sellerPayInfo.setBank(false);
            sellerPayInfo.setName(agency.getName());
            sellerPayInfo.setDfftId(agency.getDfftId());
            sellerPayInfo.setFddId(agency.getFadadaId());
            sellerPayInfo.setMobile(agency.getMobile());
        }
        return sellerPayInfo;
    }


    private UnifiedPayReq invokeGatewayPayReq(String busType, Integer buyerPartyId, Asset asset, BigDecimal amount, Long depositId) {

        Deposit deposit = depositService.getDepositById(depositId);

        UnifiedPayReq dfftReq = new UnifiedPayReq();
        dfftReq.setAmount(amount);
        dfftReq.setPartyId(buyerPartyId);
        dfftReq.setBusId(String.valueOf(depositId));


        LockOrReleaseOrDirectReq lockOrReleaseOrDirectReq = new LockOrReleaseOrDirectReq();
        AccountBaseDto buyerInfo = accountService.getAccountBaseByPartyId(buyerPartyId);
        lockOrReleaseOrDirectReq.setPayMemCode(buyerInfo.getDfftId());
        lockOrReleaseOrDirectReq.setPayMemName(buyerInfo.getName());

        SellerPayInfo sellerInfo = getSellerPayInfo(asset);

        if (SystemConstant.PAY_BUSINESS_TYPE_LOCK.equals(busType)) {
            //保证金锁定
            dfftReq.setPayType(PayEnums.PAY_TYPE.LOCK_DEPOSIT.getType());
            dfftReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
            //是否是通道类、银行、AMC
            if (!sellerInfo.isBank()) {
                lockOrReleaseOrDirectReq.setRecMemCode(sellerInfo.getDfftId());
                lockOrReleaseOrDirectReq.setRecMemName(sellerInfo.getName());
                dfftReq.setPayBusCode(PayEnums.PAY_BUS_CODE.LOCK_DEPOSIT.getType());
                dfftReq.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
            } else {
                dfftReq.setPayBusCode(PayEnums.PAY_BUS_CODE.CHANNEL_LOCK_DEPOSIT.getType());
                dfftReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
            }
        } else if (SystemConstant.PAY_BUSINESS_TYPE_PAID.equals(busType)) {
            //保证金支付
            dfftReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
            dfftReq.setPayType(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType());
            dfftReq.setPayBusCode(PayEnums.PAY_BUS_CODE.DEPOSIT_PAY.getType());
            if (!sellerInfo.isBank()) {
                lockOrReleaseOrDirectReq.setRecMemCode(sellerInfo.getDfftId());
                lockOrReleaseOrDirectReq.setRecMemName(sellerInfo.getName());
                lockOrReleaseOrDirectReq.setOriginalPayID(deposit.getPayId());
                dfftReq.setLockTag(PayEnums.LOCK_TAG.LOCK_PAY.getType());
                dfftReq.setPayTo(PayEnums.PAY_TO.PAY_TO_MEM.getType());
            } else {
                dfftReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
                dfftReq.setPayTo(PayEnums.PAY_TO.PAY_TO_WEB.getType());
                lockOrReleaseOrDirectReq.setOriginalPayID(deposit.getPayId());
            }
        } else if (SystemConstant.PAY_BUSINESS_TYPE_REALEASE.equals(busType)) {
            //释放
            dfftReq.setJumpPay(PayEnums.JUMP_PAY_TYPE.AUTO_PAY.getType());
            dfftReq.setPayType(PayEnums.PAY_TYPE.RELEASE_DEPOSIT.getType());
            dfftReq.setPayBusCode(PayEnums.PAY_BUS_CODE.RELEASE_DEPOSIT.getType());
            lockOrReleaseOrDirectReq.setOriginalPayID(deposit.getPayId());
            lockOrReleaseOrDirectReq.setPayMemName(null);
            lockOrReleaseOrDirectReq.setPayMemCode(null);
        }


        dfftReq.setPayParam(lockOrReleaseOrDirectReq);
//        LOGGER.info("开始调用 payFacade unifiedPay,参数:{}", JSON.toJSONString(dfftReq));
//        UnifiedPayResp payResp = payFacade.unifiedPay(dfftReq);
//        LOGGER.info("结束调用 payFacade unifiedPay,参数:{}，结果:{}",
//                JSON.toJSONString(dfftReq), JSON.toJSONString(payResp));
        return dfftReq;
    }


    private Long insertDeposit(AuctionActivity activity, AuctionReq req, String payType) {
        Deposit deposit = new Deposit();
        deposit.setAmount(activity.getDeposit());
        deposit.setActivityId(req.getActivityId());
        deposit.setPartyId(req.getPartyId());
        deposit.setPayType(payType);

        deposit.setAgencyId(getAgencyByCode(req.getAgencyCode()).getId());
        String randomStr = RandomNumberGenerator.wordGenerator(3);
        String code = randomStr + String.valueOf(req.getPartyId());


        String isYXUser = isYXUser(activity.getAssetId(), req.getPartyId());
        if (StringUtils.isNotEmpty(isYXUser)) {
            deposit.setAskResult("0");
            //deposit.setCode("优"+isYXUser+code);
            deposit.setLevel(Integer.parseInt(isYXUser));
        }
        deposit.setCode(code);

        if (payType.equals(SystemConstant.PAY_TYPE_OFFLINE)) {
            deposit.setBankName(req.getBankName());
            deposit.setBankAccountName(req.getBankAccountName());
            deposit.setBankAccountNumber(req.getBankAccountNumber());
        }

        try {
            boolean flag = depositService.saveDeposit(deposit);
            if (!flag) {
                throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
            }
        } catch (Exception e) {
            throw new BusinessException(ExceptionEnumImpl.HAS_PAY_DEPOSIT);
        }
        return deposit.getId();
    }


    private List<Deposit> checkDeposit(Integer partyId, Integer activityId) {
        DepositCondition depositCondition = new DepositCondition();
        depositCondition.setActivityId(activityId);
        depositCondition.setPartyId(partyId);
        List<Deposit> list = depositService.findDeposit(depositCondition);
        return list;
    }

    private boolean updateDeposit(Long depositId, String status, String remark, String payId) {
        Deposit deposit = new Deposit();
        deposit.setId(depositId);
        deposit.setStatus(status);
        deposit.setRemark(remark);
        deposit.setPayId(payId);
        return depositService.updateDepositById(deposit);
    }

    private void commonCheckValid(AuctionReq req, AuctionActivity activity, Asset asset) {


        //是否是委托人
        if (asset.getComeFrom().equals("0") && String.valueOf(req.getPartyId()).equals(String.valueOf(asset.getPartyId()))) {
            throw new BusinessException(ExceptionEnumImpl.SELLER_CANNOT_PAY);
        }

        if (asset.getComeFrom().equals("1")) {
            TAgency agency = agencyService.findByAgencyId(asset.getPartyId());
            AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(req.getPartyId());
            if (agency.getMobile().equals(accountBaseDto.getMobile())) {
                throw new BusinessException(ExceptionEnumImpl.SELLER_CANNOT_PAY);
            }
        }

        //是否签订送拍协议
//        DelegationAgreement delegationAgreement = delegationAgreementService.getByActivityId(req.getActivityId());
//        if (delegationAgreement == null || !delegationAgreement.getAllSigned()) {
//            throw new BusinessException(ExceptionEnumImpl.NO_DELEGATION_AGREEMENT);
//        }

        //联拍机构是否开通东方付通、法大大
        if (!StringUtils.isEmpty(req.getAgencyCode())) {
            TAgency agency = agencyService.findByAgencyCode(req.getAgencyCode());
            if (agency == null || agency.getPayBind() != 1 || StringUtils.isEmpty(agency.getFadadaId())) {
                throw new BusinessException(ExceptionEnumImpl.AGENCY_NO_REQUIRED_AUTH);
            }
        }
        //验证保证金 金额的合法性
        if (activity.getDeposit().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_DEPOSIT);
        }
        //是否是即将开拍、正在开拍状态
        if (!activity.getStatus().equals(ActivityEnum.Status.ONLINE)) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }

        //是否在优先竞拍环节
        if (activity.getCurrentLevel() != null) {
            throw new BusinessException(ExceptionEnumImpl.YX_BEING_ERROR);
        }

        if (asset.getOnlined() == 1) {
            //线上支付需检验 参拍人、委托人是否开通东方付通
            //参拍人是否开通东方付通、法大大
            AccountBaseDto buyerBaseInfo = accountService.getAccountBaseByPartyId(req.getPartyId());
            if (buyerBaseInfo == null || !buyerBaseInfo.isPayBind() || StringUtils.isEmpty(buyerBaseInfo.getFadadaId())) {
                throw new BusinessException(ExceptionEnumImpl.NO_REQUIRED_AUTH);
            }


            //委托人是否开通东方付通、法大大
            //银行类不需要判断东方付通
/*            AccountBaseDto sellerBaseInfo = accountService.getAccountBaseByPartyId(asset.getPartyId());
            if (sellerBaseInfo.getType().equals(SystemConstant.ACCOUNT_COMPANY_TYPE)
                    && sellerBaseInfo.isBank()) {
                if (StringUtils.isEmpty(sellerBaseInfo.getFadadaId())) {
                    throw new BusinessException(ExceptionEnumImpl.NO_REQUIRED_AUTH);
                }
            } else {
                if (!sellerBaseInfo.isIs_pay_bind() || StringUtils.isEmpty(sellerBaseInfo.getFadadaId())) {
                    throw new BusinessException(ExceptionEnumImpl.NO_REQUIRED_AUTH);
                }
            }*/
        }
    }

    @Override
    public Integer dealOnlineActivityData() {
        List<AuctionActivity> onlinedActivityList = auctionActivityService.getOnlinedActivity("ONLINE");
        int count = 0;
        for (AuctionActivity activity : onlinedActivityList) {
            long timeout = (activity.getEndAt().getTime() - System.currentTimeMillis()) / 1000;
            String key = SystemConstant.AUCTION_EXPIRE_PRE_KEY + activity.getId();
            redisCachemanager.set(key, activity.getId() + "", timeout);
            LOGGER.info("已处理:" + key);
            count++;
        }
        LOGGER.info("总共处理数据:{}条" + count);
        return count;
    }

    @Override
    public void payTimeoutDeal(Integer activityId) {
        AuctionOrder order = auctionOrderService.getFirstByActivityId(activityId);
        AuctionActivity activity = auctionActivityService.getById(activityId);
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerPayInfo = getSellerPayInfo(asset);
        if (order != null && order.getStatus().equals(SystemConstant.AUCTION_ORDER_STATUS_NOT_PAID)
                && !sellerPayInfo.isBank() && asset.getOnlined() == 1) {
            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(order.getId());
            if (payOrder != null) {


                if (order.getSellerPaidCommission()) {
                    //委托方要付佣金的情况下

                    //竞买方未付、委托方未付 退保证金
                    if (!order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                            && !order.getSellerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)) {

                        invokeGatewayReturn(SystemConstant.RETURN_BUYER_DEPOSIT_PRE_KEY + "_" + order.getId(), payOrder.getDepositPayId(), activityId, order.getId(), "支付过期退回保证金");

                    }
                    //竞买方未付、委托方付了 保证金解锁给委托方
                    else if (!order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                            && order.getSellerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)) {
                        boolean flag = invokeGatewayRelease(SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + order.getId(), payOrder.getDepositPayId());
                        if (flag) {
                            updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_CLOSED);
                            return;
                        }
                    } else if (order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)
                            && !order.getSellerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)) {
                        //竞买方付了、委托方未付 退保证金+尾款
                        invokeGatewayReturn(SystemConstant.RETURN_BUYER_DEPOSIT_PRE_KEY + "_" + order.getId(), payOrder.getDepositPayId(), activityId, order.getId(), "支付过期退回保证金");
                        invokeGatewayReturn(SystemConstant.RETURN_BUYER_REMAIN_PRE_KEY + "_" + order.getId(), payOrder.getBuyerRemainPayId(), activityId, order.getId(), "支付过期退回尾款");


                    }


                } else {
                    //委托方不付佣金的情况下
                    if (!order.getBuyerHasPayEnd().equals(SystemConstant.PAY_ORDER_STATUS_PAID_SUCCESS)) {
                        //竞买人没付，将委托人锁定的保证金，解锁
                        boolean flag = invokeGatewayRelease(SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + order.getId(), payOrder.getDepositPayId());
                        if (flag) {
                            updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_CLOSED);
                            return;
                        }
                    }

                }
            }
        }
    }


    @Override
    public boolean forceExecute(Long orderId) {
        LOGGER.info("开始调用 forceExecute,orderId={}", orderId);
        AuctionOrder order = auctionOrderService.getById(orderId);
        if (!SystemConstant.AUCTION_ORDER_STATUS_PAID.equals(order.getStatus())
                && !SystemConstant.AUCTION_ORDER_STATUS_DELIVERED.equals(order.getStatus())) {
            throw new BusinessException(ExceptionEnumImpl.INVALID_REQUEST);
        }
        AuctionActivity activity = auctionActivityService.getById(order.getActivityId());
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerPayInfo = getSellerPayInfo(asset);


        if (asset.getOnlined() == 1 && !sellerPayInfo.isBank()) {
            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);

            invokeGatewayReturn(SystemConstant.RETURN_BUYER_DEPOSIT_PRE_KEY + "_" + order.getId(), payOrder.getDepositPayId(), activity.getId(), order.getId(), "强制执行退回保证金");
            invokeGatewayReturn(SystemConstant.RETURN_BUYER_REMAIN_PRE_KEY + "_" + order.getId(), payOrder.getBuyerRemainPayId(), activity.getId(), order.getId(), "强制执行退回尾款");
            AuctionOrder updateParam = new AuctionOrder();
            updateParam.setId(orderId);
            updateParam.setAutoHandleDelay(true);
            auctionOrderService.updateAuctionOrder(updateParam);
        }


        return true;
    }


    @Override
    public void signTimeourDeal(Integer activityId) {
        AuctionOrder order = auctionOrderService.getFirstByActivityId(activityId);
        AuctionActivity activity = auctionActivityService.getById(activityId);
        Asset asset = assetService.getAsset(activity.getAssetId());
        SellerPayInfo sellerPayInfo = getSellerPayInfo(asset);
        if (order != null && order.getStatus().equals(SystemConstant.AUCTION_ORDER_STATUS_NOT_SIGNED)
                && !sellerPayInfo.isBank() && asset.getOnlined() == 1) {
            DealAgreement dealAgreement = dealAgreementService.getByOrderId(order.getId(), AuctionOfflineEnum.ContractType.DEAL.getKey());
            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(order.getId());
            if (dealAgreement != null) {
                if (dealAgreement.getSellerSigned() && !dealAgreement.getBuyerSigned()) {
                    //委托方签了，竞买方没签，吃掉保证金
                    boolean flag = invokeGatewayRelease(SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + order.getId(), payOrder.getDepositPayId());
                    if (flag) {
                        updateAuctionOrderStatus(order.getId(), SystemConstant.AUCTION_ORDER_STATUS_CLOSED);
                    }
                } else if (!dealAgreement.getSellerSigned()) {
                    //委托方没签，不管竞买方签没签，保证金都要退
                    invokeGatewayReturn(SystemConstant.RETURN_BUYER_DEPOSIT_PRE_KEY + "_" + order.getId(), payOrder.getDepositPayId(), activityId, order.getId(), "签协议过期退回保证金");
                }
            }

        }

/*            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(order.getId());
            if(payOrder != null){
                //将委托人锁定的保证金，解锁
                invokeGatewayRelease(SystemConstant.RELEASE_BUYER_DEPOSIT + "_" + order.getId(), payOrder.getDepositPayId());
                updateAuctionOrderStatus(order.getId(),SystemConstant.AUCTION_ORDER_STATUS_CLOSED);
            }*/

    }


    private void invokeGatewayReturn(String busId, String payId, Integer activityId, Long orderId, String remark) {
        UnifiedPayReq unifiedPayReq = new UnifiedPayReq();
        unifiedPayReq.setBackTag(PayEnums.BACK_TAG.BACK.getType());
        unifiedPayReq.setLockTag(PayEnums.LOCK_TAG.DIRECT_PAY.getType());
        unifiedPayReq.setPayType(PayEnums.PAY_TYPE.TRANSFER_DEPOSIT.getType());
        unifiedPayReq.setPayBusCode(PayEnums.PAY_BUS_CODE.DEPOSIT_PAY.getType());
        unifiedPayReq.setBusId(busId);

        LockOrReleaseOrDirectReq releaseOrDirectReq = new LockOrReleaseOrDirectReq();
        releaseOrDirectReq.setOriginalPayID(payId);

        unifiedPayReq.setPayParam(releaseOrDirectReq);

        LOGGER.info("开始调用 payFacade.unifiedPay ,参数:{}", JSON.toJSONString(unifiedPayReq));
        UnifiedPayResp payResp = payFacade.unifiedPay(unifiedPayReq);
        LOGGER.info("开始调用 payFacade.unifiedPay ,参数:{},结果:{}", JSON.toJSONString(unifiedPayReq), JSON.toJSONString(payResp));

        insertAuctionStep(activityId, orderId, null, remark, JSON.toJSONString(unifiedPayReq),
                JSON.toJSONString(payResp), null, PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode()) ? "SUCCESS" : "FAIL");

        if (payResp != null && PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
            updateAuctionOrderStatus(orderId, SystemConstant.AUCTION_ORDER_STATUS_CLOSED);
        }
    }


    @Override
    public ResponseModel searchOfflineFinanceList(AuctionOfflineFinanceReq req) {
        LOGGER.info("开始调用 searchOfflineFinanceList ，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(auctionOfflineFinanceService.searchOfflineFinanceList(req));
    }

    @Override
    public ResponseModel getOfflineFinanceDetailById(AuctionOfflineFinanceReq req) {
        LOGGER.info("开始调用 getOfflineFinanceDetailById ，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(auctionOfflineFinanceService.getDetailById(req.getId()));
    }

    @Override
    public ResponseModel confirmOfflineFinance(AuctionOfflineFinanceReq req) {
        LOGGER.info("开始调用 confirmOfflineFinance ，参数:{}", JSON.toJSONString(req));
        return ResponseModel.succ(auctionOfflineFinanceService.updateFinanceInfo(req));
    }

    @Override
    public List<AuctionOfflineFinaceVo> searchAllOfflineFinanceList(AuctionOfflineFinanceReq req) {
        LOGGER.info("开始调用 searchAllOfflineFinanceList ，参数:{}", JSON.toJSONString(req));
        return auctionOfflineFinanceService.searchAllOfflineFinanceList(req);
    }

    @Override
    public ResponseModel invokeFddCreateLeaseDeal(Long orderId, Date currentTime) {
        return ResponseModel.succ(invokeFddCreateLeaseDealContract(orderId, currentTime));
    }

}
