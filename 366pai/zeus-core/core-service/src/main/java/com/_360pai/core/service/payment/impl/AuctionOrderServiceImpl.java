package com._360pai.core.service.payment.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.AuctionOfflineEnum;
import com._360pai.core.common.constants.OrderEnum;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.dao.account.TAcctDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.agreement.DealAgreementDao;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.dao.payment.AuctionOrderDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.asset.vo.AssetVo;
import com._360pai.core.facade.payment.req.AuctionOrderReq;
import com._360pai.core.facade.payment.resp.AuctionOrderResp;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.facade.payment.vo.FeeInfo;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.TAuctionPayOrder;
import com._360pai.core.model.agreement.DealAgreement;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.agreement.DealAgreementService;
import com._360pai.core.service.asset.AssetLeaseDataService;
import com._360pai.core.service.asset.AssetService;
import com._360pai.core.service.payment.AuctionOrderService;
import com._360pai.core.service.payment.AuctionPayOrderService;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.vo.asset.AssetLeaseDataVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author: whisky_vip
 * @date: 2018/8/15 17:09
 */
@Service
public class AuctionOrderServiceImpl implements AuctionOrderService {

    @Resource
    private AuctionOrderDao auctionOrderDao;
    @Resource
    private AccountService accountService;
    @Resource
    private AuctionActivityService auctionActivityService;
    @Resource
    private AgencyService agencyService;
    @Resource
    private DealAgreementService dealAgreementService;
    @Resource
    private DealAgreementDao dealAgreementDao;
    @Resource
    private AssetService assetService;
    @Resource
    private UserService userService;
    @Resource
    private CompanyService companyServicee;
    @Autowired
    private TPartyDao partyDao;
    @Autowired
    private TAcctDao tAcctDao;
    @Autowired
    private TAppletShopDao shopDao;
    @Autowired
    private GatewayMqSender mqSender;

    @Autowired
    private AuctionPayOrderService auctionPayOrderService;

    @Autowired
    private AssetLeaseDataService assetLeaseDataService;

    @Override
    public AuctionOrder getById(Long orderId) {
        AuctionOrderCondition condition = new AuctionOrderCondition();
        condition.setId(orderId);
        return auctionOrderDao.selectFirst(condition);
    }

    @Override
    public AuctionOrder getByIdAndPartyId(Long orderId, Integer buyerId, Integer sellerId) {
        AuctionOrderCondition condition = new AuctionOrderCondition();
        condition.setId(orderId);
        condition.setSellerId(sellerId);
        condition.setBuyerId(buyerId);
        return auctionOrderDao.selectFirst(condition);
    }

    public BigDecimal getLeaseCommissionDiscount(BigDecimal dealAmount) {
        BigDecimal discount = BigDecimal.ZERO;
        if (dealAmount.compareTo(new BigDecimal(100000)) <= 0) {
            discount = new BigDecimal(0.8);
        } else if (dealAmount.compareTo(new BigDecimal(100000)) > 0 && dealAmount.compareTo(new BigDecimal(500000)) <= 0) {
            discount = new BigDecimal(0.7);
        } else if (dealAmount.compareTo(new BigDecimal(500000)) > 0 && dealAmount.compareTo(new BigDecimal(1000000)) <= 0) {
            discount = new BigDecimal(0.6);
        } else if (dealAmount.compareTo(new BigDecimal(1000000)) > 0 && dealAmount.compareTo(new BigDecimal(1500000)) <= 0) {
            discount = new BigDecimal(0.5);
        } else {
            discount = new BigDecimal(0.4);
        }
        return discount;
    }

    @Override
    public AuctionOrderResp getAuctionOrder(AuctionOrderReq.OrderIdReq req) {
        AuctionOrderResp resp = new AuctionOrderResp();
        AuctionOrder order = auctionOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getSellerId() != null) {
            if (!order.getSellerId().equals(req.getSellerId())) {
                throw new BusinessException(ApiCallResult.FAILURE, "订单不属于该用户");
            }
        }
        resp.setAuctionOrder(processAuctionOrder(order));
        return resp;
    }

    @Override
    public AuctionOrderResp getMyBuyerOrder(AuctionOrderReq.OrderIdReq req) {
        AuctionOrderResp resp = new AuctionOrderResp();
        AuctionOrder order = auctionOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getSellerId() != null) {
            if (!order.getSellerId().equals(req.getSellerId())) {
                throw new BusinessException(ApiCallResult.FAILURE, "订单不属于该用户");
            }
        }
        resp.setAuctionOrder(processAuctionOrder(order, "buyer"));
        return resp;
    }

    @Override
    public AuctionOrderResp getMySellerOrder(AuctionOrderReq.OrderIdReq req) {
        AuctionOrderResp resp = new AuctionOrderResp();
        AuctionOrder order = auctionOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (req.getSellerId() != null) {
            if (!order.getSellerId().equals(req.getSellerId())) {
                throw new BusinessException(ApiCallResult.FAILURE, "订单不属于该用户");
            }
        }
        resp.setAuctionOrder(processAuctionOrder(order, "seller"));
        return resp;
    }

    @Override
    public PageInfoResp<AuctionOrderVo> getAllByPage(AuctionOrderReq.QueryReq req) {
        PageInfoResp<AuctionOrderVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("buyerAgencyId", req.getBuyerAgencyId());
        params.put("sellerAgencyId", req.getSellerAgencyId());
        params.put("buyerId", req.getBuyerId());
        params.put("sellerId", req.getSellerId());
        params.put("comeFrom", req.getComeFrom());
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getSellerAgencyName())) {
            params.put("sellerAgencyName", req.getSellerAgencyName());
        }
        if (StringUtils.isNotBlank(req.getFinishAtFrom()) && StringUtils.isNotBlank(req.getFinishAtTo())) {
            params.put("createdAtFrom", req.getFinishAtFrom());
            params.put("createdAtTo", req.getFinishAtTo());
        }
        if (StringUtils.isNotBlank(req.getAmountFrom()) && !"0".equals(req.getAmountFrom())) {
            params.put("amountFrom", req.getAmountFrom());
        }
        if (StringUtils.isNotBlank(req.getAmountTo()) && !"0".equals(req.getAmountTo())) {
            params.put("amountTo", req.getAmountTo());
        }
        PageInfo pageInfo = auctionOrderDao.getAuctionOrderList(req.getPage(), req.getPerPage(), params, "o.id desc");
        List<AuctionOrder> auctionOrders = pageInfo.getList();
        List<AuctionOrderVo> resultList = new ArrayList<>();
        for (AuctionOrder order : auctionOrders) {
            try {
                resultList.add(processAuctionOrder(order));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        resp.setList(resultList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public PageInfoResp<AuctionOrderVo> getSellerOrderListByPage(AuctionOrderReq.QueryReq req) {
        PageInfoResp<AuctionOrderVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("sellerId", req.getSellerId());
        params.put("comeFrom", req.getComeFrom());
        params.put("name", req.getName());
        params.put("categoryId", req.getCategoryId());

        PageInfo pageInfo = auctionOrderDao.getAuctionOrderList(req.getPage(), req.getPerPage(), params, "o.id desc");
        List<AuctionOrder> auctionOrders = pageInfo.getList();
        List<AuctionOrderVo> resultList = new ArrayList<>();
        for (AuctionOrder order : auctionOrders) {
            try{
                resultList.add(processAuctionOrder(order, "seller"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        resp.setList(resultList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    @Override
    public PageInfo getAllByPage(int page, int perPage, AuctionOrderCondition condition, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionOrder> list = auctionOrderDao.selectList(condition);
        return new PageInfo<>(list);
    }

    @Override
    public AuctionOrder getFirstByActivityId(Integer activityId) {
        AuctionOrderCondition condition = new AuctionOrderCondition();
        condition.setActivityId(activityId);
        return auctionOrderDao.selectFirst(condition);
    }

    @Override
    public boolean toggleAutoHandleDelay(Long orderId) {
        AuctionOrder order = getById(orderId);
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (order.getAutoHandleDelay() != null && order.getAutoHandleDelay()) {
            order.setAutoHandleDelay(false);
        } else {
            order.setAutoHandleDelay(true);
        }
        return auctionOrderDao.updateById(order) > 0;
    }

    private AuctionOrderVo processAuctionOrder(AuctionOrder order) {
        return processAuctionOrder(order, "");
    }

    private AuctionOrderVo processAuctionOrder(AuctionOrder order, String sideType) {
        AuctionOrderVo vo = RespConvertUtil.convertAuctionOrderVo(order);
        if (OrderEnum.Status.NOT_SIGNED.getKey().equals(order.getStatus()) && "seller".equals(sideType)) { // 委托人查询时如果
            DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(vo.getId()), AuctionOfflineEnum.ContractType.DEAL.getKey());
            if (dealAgreement !=null && dealAgreement.getSellerSigned()) {
                vo.setStatusDesc("已签署");
            }
        }
        if (OrderEnum.Status.NOT_SIGNED.getKey().equals(order.getStatus()) && "buyer".equals(sideType)) { // 买受人查询时如果
            DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(vo.getId()),AuctionOfflineEnum.ContractType.DEAL.getKey());
            if (dealAgreement !=null && dealAgreement.getBuyerSigned()) {
                vo.setStatusDesc("已签署");
            }
        }


        if (OrderEnum.Status.NOT_SIGNED_LEASE.getKey().equals(order.getStatus()) && "seller".equals(sideType)) { // 委托人查询时如果
            DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(vo.getId()), AuctionOfflineEnum.ContractType.LEASE.getKey());
            if (dealAgreement !=null && dealAgreement.getSellerSigned()) {
                vo.setStatusDesc("已签署");
            }
        }
        if (OrderEnum.Status.NOT_SIGNED_LEASE.getKey().equals(order.getStatus()) && "buyer".equals(sideType)) { // 买受人查询时如果
            DealAgreement dealAgreement = dealAgreementDao.getByOrderId(Long.valueOf(vo.getId()),AuctionOfflineEnum.ContractType.LEASE.getKey());
            if (dealAgreement !=null && dealAgreement.getBuyerSigned()) {
                vo.setStatusDesc("已签署");
            }
        }


        AuctionActivityVo auctionActivity = auctionActivityService.getActivityById(order.getActivityId());
        if(auctionActivity.getCategoryId()!=null&&"-1".equals(String.valueOf(auctionActivity.getCategoryId()))){
            auctionActivity.setCategoryName("租赁权拍卖");
        }
        vo.setActivity(auctionActivity);
        AssetVo asset = assetService.getAssetById(auctionActivity.getAssetId());
        vo.setAsset(asset);
        TAgency sellerAgency = agencyService.findByAgencyId(order.getSellerAgencyId());
        vo.setSellerAgencyName(sellerAgency.getName());
        PartyAccount seller;
        if (AssetEnum.ComeFrom.AGENCY.getKey().equals(order.getComeFrom())) {
            seller = new PartyAccount();
            seller.setId(sellerAgency.getId());
            seller.setName(sellerAgency.getName());
            seller.setMobile(sellerAgency.getMobile());
            seller.setCertificateNumber(sellerAgency.getLicense());
        } else {
           seller = accountService.getPartyAccountById(order.getSellerId());
        }
        seller.setContact(vo.getAsset().getContactPerson().getName());
        vo.setSeller(seller);
        vo.setSellerContactPerson(vo.getAsset().getContactPerson());
        TAgency buyerAgency = agencyService.findByAgencyId(order.getBuyerAgencyId());
        vo.setBuyerAgencyName(buyerAgency.getName());
        PartyAccount buyer = accountService.getPartyAccountById(order.getBuyerId());
        buyer.setContact(buyer.getName());
        TAccount account = accountService.findAccountByMobile(buyer.getMobile());
        if (AccountEnum.RegisterSource.APPLET.getKey().equals(account.getRegisterSource())) {
            vo.setOrderSource(OrderEnum.OrderSource.APPLET.getValue());
        } else {
            vo.setOrderSource(OrderEnum.OrderSource.PLATFORM.getValue());
        }
        vo.setBuyer(buyer);
        vo.setDealAgreement(RespConvertUtil.convertToFileInfo(dealAgreementService.getByOrderId(order.getId(),AuctionOfflineEnum.ContractType.DEAL.getKey())));
        List<FeeInfo> feeInfos = new ArrayList<>();


        if(auctionActivity.getCategoryId() == -1){

            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(order.getId());
            if(payOrder != null){

                vo.setBuyerCommission(payOrder.getBuyerCommissionPayAmount());

                AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(auctionActivity.getAssetId(), AssetEnum.WebFlag.WEB.getKey());
                vo.setBuyerNeedPayAmount(calculateFirstAmt(order.getAmount(),dataVO.getPaymentCycle()).add(order.getAmount().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP)).subtract(order.getDeposit()).add(vo.getBuyerCommission()));


                vo.setSellerCommission(payOrder.getSellerCommissionPayAmount());
                vo.setSellerNeedPayAmount(vo.getSellerCommission());
            }
        }else{
            vo.setBuyerCommission(order.getAmount().multiply(auctionActivity.getCommissionPercentBuyer()).divide(new BigDecimal("100")));
            vo.setBuyerNeedPayAmount(order.getAmount().subtract(order.getDeposit()).add(vo.getBuyerCommission()));


            vo.setSellerCommission(order.getAmount().multiply(auctionActivity.getCommissionPercentSeller()).divide(new BigDecimal("100")));
            vo.setSellerNeedPayAmount(vo.getSellerCommission());
        }





        feeInfos.add(new FeeInfo("佣金", vo.getBuyerCommission().setScale(2, BigDecimal.ROUND_HALF_UP), "买受方", order.getBuyerPaidOrder()));
        feeInfos.add(new FeeInfo("保证金", order.getDeposit().setScale(2, BigDecimal.ROUND_HALF_UP), "买受方", true));
        feeInfos.add(new FeeInfo("成交价", order.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP), "买受方", order.getBuyerPaidOrder()));
        feeInfos.add(new FeeInfo("佣金", vo.getSellerCommission().setScale(2, BigDecimal.ROUND_HALF_UP), "委托方", order.getSellerPaidOrder()));
        vo.setFeeInfos(feeInfos);

        // 送拍机构佣金收入
        BigDecimal serveSellerCommission = vo.getBuyerCommission().add(vo.getSellerCommission()).multiply(order.getServeSellerPercent()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        vo.setServeSellerCommission(serveSellerCommission);
        // 参拍机构佣金收入
        BigDecimal serveBuyerCommission = vo.getBuyerCommission().add(vo.getSellerCommission()).multiply(order.getServeBuyerPercent()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
        vo.setServeBuyerCommission(serveBuyerCommission);

        // 金额保留两位小数
        vo.setBuyerNeedPayAmount(vo.getBuyerNeedPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setBuyerCommission(vo.getBuyerCommission().setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setSellerCommission(vo.getSellerCommission().setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setSellerNeedPayAmount(vo.getSellerNeedPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setAmount(vo.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        vo.setDeposit(vo.getDeposit().setScale(2, BigDecimal.ROUND_HALF_UP));


        vo.setAmountDesc(NumberValidationUtils.formatPrice(vo.getAmount()));
        vo.setDepositDesc(NumberValidationUtils.formatPrice(vo.getDeposit()));
        vo.setSellerCommissionDesc(NumberValidationUtils.formatPrice(vo.getSellerCommission()));
        vo.setBuyerCommissionDesc(NumberValidationUtils.formatPrice(vo.getBuyerCommission()));
        vo.setSellerNeedPayAmountDesc(NumberValidationUtils.formatPrice(vo.getSellerNeedPayAmount()));
        vo.setBuyerNeedPayAmountDesc(NumberValidationUtils.formatPrice(vo.getBuyerNeedPayAmount()));

         return vo;
    }

    private BigDecimal calculateFirstAmt(BigDecimal dealAmount,String payWay){
        if(AssetEnum.PaymentCycle.HALF_YEAR.getKey().equals(payWay)){
            return dealAmount.divide(new BigDecimal(2),2,BigDecimal.ROUND_HALF_UP);
        } else if(AssetEnum.PaymentCycle.QUARTER.getKey().equals(payWay)){
            return dealAmount.divide(new BigDecimal(4),2,BigDecimal.ROUND_HALF_UP);
        }else if(AssetEnum.PaymentCycle.MONTH.getKey().equals(payWay)) {
            // 2019-05-31 呵呵，产品又加了个付款方式，按月
            return dealAmount.divide(new BigDecimal(12), 2, BigDecimal.ROUND_HALF_UP);
        }else{
            return dealAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
        }
    }

    @Override
    public boolean saveAuctionOrder(AuctionOrder order) {
        return auctionOrderDao.insert(order) == 1;
    }

    @Override
    public boolean updateAuctionOrder(AuctionOrder order) {
        return auctionOrderDao.updateById(order) == 1;
    }

    @Override
    public Object getMyOrders(int page, int perPage, Integer partyId,String categoryId,String name) {
        Map<String, Object> map = new HashMap<>();
        // 参拍人
        map.put("partyId", partyId);
        map.put("categoryId", categoryId);
        map.put("name", name);

        List<Map<String, Object>> auctionOrders = auctionOrderDao.getAuctionOrders(map);
        for (Map<String, Object> order : auctionOrders) {
            try {
                Integer sellerId = (Integer) order.get("sellerId");
                Integer buyerId = (Integer) order.get("buyerId");
                Long orderId = (Long) order.get("orderId");
                order.put("orderId", orderId.toString());
                if (OrderEnum.Status.NOT_SIGNED.getKey().equals(order.get("orderStatus"))) {
                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(orderId,AuctionOfflineEnum.ContractType.DEAL.getKey());
                    if (dealAgreement.getBuyerSigned()) {
                        order.put("orderStatusStr", "已签署");
                    }
                }

                if (OrderEnum.Status.NOT_SIGNED_LEASE.getKey().equals(order.get("orderStatus"))) {
                    DealAgreement dealAgreement = dealAgreementDao.getByOrderId(orderId,AuctionOfflineEnum.ContractType.LEASE.getKey());
                    if (dealAgreement.getBuyerSigned()) {
                        order.put("orderStatusStr", "已签署");
                    }else{
                        order.put("orderStatusStr", "待签租赁协议");
                    }
                }

                PartyAccount seller;
                if (AssetEnum.ComeFrom.AGENCY.getKey().equals(order.get("comeFrom"))) {
                    seller = new PartyAccount();
                    TAgency agency = agencyService.findByAgencyId(sellerId);
                    seller.setId(agency.getId());
                    seller.setName(agency.getName());
                    seller.setMobile(agency.getMobile());
                } else {
                    seller = accountService.getPartyAccountById(sellerId);
                }
                order.put("ucId", seller.getId());
                order.put("ucName", seller.getName());
                order.put("ucMobile", seller.getMobile());
                order.put("commissionSeller", NumberValidationUtils.formatPrice(order.get("commissionSeller")));

                AuctionOrder auctionOrder = auctionOrderDao.selectById(orderId);
                AuctionActivity auctionActivity = auctionActivityService.getById(auctionOrder.getActivityId());
                if(auctionActivity.getCategoryId() == -1){
                    TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(orderId);
                    if(payOrder != null){
                        AssetLeaseDataVO dataVO = assetLeaseDataService.getLeaseAssetById(auctionActivity.getAssetId(), AssetEnum.WebFlag.WEB.getKey());
                        order.put("commissionBuyer",NumberValidationUtils.formatPrice(payOrder.getBuyerCommissionPayAmount()));
                        order.put("buyerAmount",NumberValidationUtils.formatPrice(calculateFirstAmt(auctionOrder.getAmount(),dataVO.getPaymentCycle()).add(auctionOrder.getAmount().divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP)).subtract(auctionOrder.getDeposit()).add(payOrder.getBuyerCommissionPayAmount())));
                    }
                }else{
                    order.put("commissionBuyer",NumberValidationUtils.formatPrice(order.get("commissionBuyer")));
                    order.put("buyerAmount",NumberValidationUtils.formatPrice(order.get("buyerAmount")));
                }
                PartyAccount buyer = accountService.getPartyAccountById(buyerId);
                order.put("buyerName", buyer.getName());
                order.put("buyerMobile", buyer.getMobile());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new PageInfo<>(auctionOrders);
    }

    @Override
    public PageInfoResp<ShopAuctionOrderVo> getShopDealCommissionListByPage(AuctionOrderReq.QueryReq req) {
        PageInfoResp<ShopAuctionOrderVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getOnlined())) {
            params.put("onlined", req.getOnlined());
        }
        if (StringUtils.isNotBlank(req.getOrderId())) {
            params.put("orderId", req.getOrderId());
        }
        if (StringUtils.isNotBlank(req.getInviteCode())) {
            params.put("inviteCode", req.getInviteCode());
        }
        PageInfo pageInfo = auctionOrderDao.getShopDealCommissionListByPage(req.getPage(), req.getPerPage(), params, "");
        List<ShopAuctionOrderVo> list = pageInfo.getList();
        for (ShopAuctionOrderVo vo : list) {
            try {
                processShopAuctionOrderVo(vo);
            } catch (Exception e) {
                e.printStackTrace();
                mqSender.sendTryCatchExceptionEmail(Long.parseLong(vo.getOrderId()), e);
            }
        }
        resp.setList(pageInfo.getList());
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    private void processShopAuctionOrderVo(ShopAuctionOrderVo vo) {
        if (vo.getBelongAcctId() != null) {
            TAcct acct = tAcctDao.selectById(vo.getBelongAcctId());
            if (acct != null) {
                if (AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())) {
                    vo.setBelongShopCode(AccountEnum.InviteType.JG.getKey() + acct.getPartyId());
                } else if (AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())) {
                    vo.setBelongShopCode(AccountEnum.InviteType.DP.getKey() + acct.getPartyId());
                } else if (AccountEnum.AcctType.USER.getKey().equals(acct.getType()) || AccountEnum.AcctType.COMPANY.getKey().equals(acct.getType())) {
                    TAppletShop shop = shopDao.getByPartyId(acct.getPartyId());
                    if (shop != null) {
                        vo.setBelongShopCode(AccountEnum.InviteType.DP.getKey() + shop.getId());
                    }
                }
            }
        }
        if (vo.getParentAcctId() != null) {
            TAcct acct = tAcctDao.selectById(vo.getParentAcctId());
            if (acct != null) {
                if (AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())) {
                    vo.setParentCode(AccountEnum.InviteType.JG.getKey() + acct.getPartyId());
                } else if (AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())) {
                    vo.setParentCode(AccountEnum.InviteType.DP.getKey() + acct.getPartyId());
                } else if (AccountEnum.AcctType.USER.getKey().equals(acct.getType()) || AccountEnum.AcctType.COMPANY.getKey().equals(acct.getType())) {
                    TAppletShop shop = shopDao.getByPartyId(acct.getPartyId());
                    if (shop != null) {
                        vo.setParentCode(AccountEnum.InviteType.DP.getKey() + shop.getId());
                    }
                }
            }
        }
        if (StringUtils.isNotEmpty(vo.getParentCode())) {
            if (vo.getParentCode().startsWith(AccountEnum.InviteType.DP.getKey())) {
                vo.setParentShopCode(vo.getParentCode());
                vo.setParentShopCommission(vo.getParentCommission());
            }
            if (vo.getParentCode().startsWith(AccountEnum.InviteType.JG.getKey())) {
                vo.setParentAgencyCode(vo.getParentCode());
                vo.setParentAgencyCommission(vo.getParentCommission());
            }
        }
    }

    @Override
    public ShopAuctionOrderVo getShopDealCommissionDetail(AuctionOrderReq.OrderIdReq req) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", req.getOrderId());
        PageInfo pageInfo = auctionOrderDao.getShopDealCommissionListByPage(req.getPage(), req.getPerPage(), params, "");
        List<ShopAuctionOrderVo> list = pageInfo.getList();
        for (ShopAuctionOrderVo vo : list) {
            processShopAuctionOrderVo(vo);
        }
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AuctionOrder> getBuyerNeedToPaidList(Integer partyId) {
        return auctionOrderDao.getBuyerNeedToPaidList(partyId);
    }
}