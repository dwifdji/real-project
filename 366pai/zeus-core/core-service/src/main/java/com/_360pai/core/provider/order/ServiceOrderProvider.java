package com._360pai.core.provider.order;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.order.ServiceOrderFacade;
import com._360pai.core.facade.order.req.ServiceOrderReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.core.facade.order.resp.ServiceOrderStatusResp;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.core.model.order.TServiceOrder;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.disposal.DisposeLevelService;
import com._360pai.core.service.disposal.DisposeShowService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com._360pai.core.service.finance.ServiceBusinessRecordService;
import com._360pai.core.service.order.ServiceOrderService;
import com._360pai.core.utils.ServiceConfigUtils;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.core.utils.ServicePayUtils;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.dfftpay.UnifiedQueryReq;
import com._360pai.gateway.controller.req.dfftpay.WxScanPayReq;
import com._360pai.gateway.facade.PayFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/12 14:57
 */
@Component
@Service(version = "1.0.0")
@Slf4j
public class ServiceOrderProvider implements ServiceOrderFacade {

    private final Logger logger = LoggerFactory.getLogger(ServiceOrderProvider.class);

    private static String compradorWxPayBody      = "资产大买办需求单支付";
    private static String withdifugWxPayBody      = "配资乐需求单支付";
    private static String adjustedWxPayBody       = "尽调报告支付";
    private static String disposalWxPayBody       = "处置服务支付";
    private static String basisReportWxPayBody    = "基础尽调报告支付";
    private static String CompleteReportWxPayBody = "完整尽调报告支付";
    private static String disposalWantShow        = "我要处置支付";
    private static String userReportType          = "10";
    private static String basisReportType         = "20";
    private static String completeReportType      = "30";

    @Reference(version = "1.0.0")
    PayFacade payFacade;

    @Autowired
    private ServiceOrderService          serviceOrderService;
    @Autowired
    private TServiceConfigService        tServiceConfigService;
    @Autowired
    private DisposalRequirementService   disposalRequirementService;
    @Autowired
    private AuctionActivityService       auctionActivityService;
    @Autowired
    private ServiceBusinessRecordService serviceBusinessRecordService;
    @Autowired
    private ServiceMessageUtils          serviceMessageUtils;
    @Autowired
    private DisposeSurveyService         disposeSurveyService;
    @Autowired
    private DisposeShowService           disposeShowService;
    @Autowired
    private DisposeService               disposeService;
    @Autowired
    private ServicePayUtils              servicePayUtils;
    @Autowired
    private AccountService               accountService;
    @Autowired
    private DisposeLevelService          disposeLevelService;

    @Override
    public ServiceOrderResp compradorRequirementPay(ServiceOrderReq.PayReq req) {
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.COMPRADOR_REQUIREMENT_PRICE);
        BigDecimal     price          = new BigDecimal(tServiceConfig.getConfigValue());
        //如果价格小于或等于0 或者 配置该人不需付钱
        if (price.compareTo(BigDecimal.ZERO) <= 0 || servicePayUtils.checkNotPayContainsPartyId(req.getPartyId(), ServiceOrderEnum.OrderType.COMPRADOR_REQUIREMENT)) {
            // 发送通知
            serviceMessageUtils.compradorAdd(req.getRequirementId());
            return new ServiceOrderResp();
        }
        DoPayRequestVo doPayRequestVo = new DoPayRequestVo(req.getRequirementId().toString(), req.getAccountId(), req.getPartyId(), price, ServiceOrderEnum.OrderType.COMPRADOR_REQUIREMENT, compradorWxPayBody);
        return getServiceOrderResp(doPayRequestVo);
    }

    @Override
    public ServiceOrderResp withfudigRequirementPay(ServiceOrderReq.PayReq req) {
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.WITHFUDIG_REQUIREMENT_PRICE);
        BigDecimal     price          = new BigDecimal(tServiceConfig.getConfigValue());

        //如果价格小于或等于0 或者 配置该人不需付钱
        if (price.compareTo(BigDecimal.ZERO) <= 0 || servicePayUtils.checkNotPayContainsPartyId(req.getPartyId(), ServiceOrderEnum.OrderType.WITHFUDIG_REQUIREMENT)) {
            // 发送通知
            serviceMessageUtils.withfudigAdd(req.getRequirementId());
            return new ServiceOrderResp();
        }
        DoPayRequestVo doPayRequestVo = new DoPayRequestVo(req.getRequirementId().toString(), req.getAccountId(), req.getPartyId(), new BigDecimal(tServiceConfig.getConfigValue()), ServiceOrderEnum.OrderType.WITHFUDIG_REQUIREMENT, withdifugWxPayBody);
        return getServiceOrderResp(doPayRequestVo);
    }

    @Override
    public ServiceOrderResp adjustedPay(ServiceOrderReq.AdjustedPay req) {
        Asset            asset            = auctionActivityService.getAssetByActivityId(req.getActivityId());
        DoPayRequestVo   doPayRequestVo   = new DoPayRequestVo(req.getActivityId().toString(), req.getAccountId(), req.getPartyId(), asset.getTuneReport(), ServiceOrderEnum.OrderType.ADJUSTED, adjustedWxPayBody);
        ServiceOrderResp serviceOrderResp = getServiceOrderResp(doPayRequestVo);
        // 创建订单业务记录
        createBusinessRecord(asset, doPayRequestVo, serviceOrderResp.getOrderNum(), asset.getTuneReportUrl().toJSONString());
        return serviceOrderResp;
    }

    @Override
    public ServiceOrderResp providerShowPay(ServiceOrderReq.ProviderShowPay req) {
        /*
            1.生成支付信息
            2.生成购买记录
            3.支付成功后生成 修改记录状态 t_dispose_show 记录
         */
        // 判断是否已经支付过

        TDisposeProvider provider = disposeService.getDisposeProviderByPartyId(req.getPartyId());

        TDisposeShow show = disposeShowService.getShowByProviderIdAndActivityId(provider.getId(), req.getActivityId());

        if (show != null) {
            throw new BusinessException("您已成为推荐处置服务商");
        }

        Asset asset = auctionActivityService.getAssetByActivityId(req.getActivityId());
        String[] split = asset.getCityId().split(",");
        TDisposeLevel level = disposeLevelService.getByProviderIdAndCityId(provider.getId(), Integer.valueOf(split[0]));
        if (level != null) {
            throw new BusinessException("您已成为推荐处置服务商");
        }

        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.DISPOSE_WANT_SHOW);
        BigDecimal     price          = new BigDecimal(tServiceConfig.getConfigValue());
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            disposeShowService.addProviderShow(provider.getId(), req.getActivityId());
            return new ServiceOrderResp();
        }
        DoPayRequestVo doPayRequestVo = new DoPayRequestVo(
                req.getActivityId().toString(),
                req.getAccountId(), req.getPartyId(),
                price,
                ServiceOrderEnum.OrderType.DISPOSAL_WANT_SHOW,
                disposalWantShow);
        ServiceOrderResp serviceOrderResp = getServiceOrderResp(doPayRequestVo);
        // 创建订单业务记录
        createBusinessRecordMode2(asset, doPayRequestVo, serviceOrderResp.getOrderNum());
        return serviceOrderResp;
    }

    @Override
    public Map thirdReportPay(ServiceOrderReq.AdjustedPay req) {
        /*
           根据获取尽调的类型判断：
           是否授权该尽调
           是否有上传尽调报告
         */

        Asset            asset          = auctionActivityService.getAssetByActivityId(req.getActivityId());
        ServiceOrderResp basisReport    = createBasisReport(req, asset);
        ServiceOrderResp completeReport = createCompleteReport(req, asset);
        Map              map            = new HashMap(2);
        if (null != basisReport) {
            basisReport.setOrderFlag(getOrderFlag(req.getPartyId(), asset.getId(), ServiceOrderEnum.OrderType.REPORT_BASIS.getValue()));
            map.put("basis", basisReport);
        }
        if (null != completeReport) {
            completeReport.setOrderFlag(getOrderFlag(req.getPartyId(), asset.getId(), ServiceOrderEnum.OrderType.REPORT_COMPLETE.getValue()));
            map.put("complete", completeReport);
        }
        return map;
    }


    @Override
    public ServiceOrderResp disposalRequirementPay(ServiceOrderReq.DisposalRequirementPay req) {

        BigDecimal price = BigDecimal.ZERO;
        for (Integer requirementId : req.getDisposalRequirementList()) {
//            TDisposalRequirement requirementDetail = disposalRequirementService.findRequirementDetail(requirementId);
            TServiceConfig       tServiceConfig    = tServiceConfigService.selectByConfigType(ServiceConfigEnum.DISPOSAL_REQUIREMENT_PRICE);
            price = price.add(new BigDecimal(tServiceConfig.getConfigValue()));
        }
        //如果价格小于或等于0 或者 配置该人不需付钱
        if (price.compareTo(BigDecimal.ZERO) <= 0 || servicePayUtils.checkNotPayContainsPartyId(req.getPartyId(), ServiceOrderEnum.OrderType.DIPOSAL_REQUIREMENT)) {
            return new ServiceOrderResp();
        }
        List<Integer> disposalRequirementList = req.getDisposalRequirementList();
        StringBuilder payBusCode              = new StringBuilder();
        disposalRequirementList.forEach(model -> payBusCode.append(model).append(","));
        DoPayRequestVo doPayRequestVo = new DoPayRequestVo(null, req.getAccountId(), req.getPartyId(), price, ServiceOrderEnum.OrderType.DIPOSAL_REQUIREMENT, disposalWxPayBody, payBusCode.toString());
        return getServiceOrderResp(doPayRequestVo);
    }

    @Override
    public ServiceOrderStatusResp queryStatus(ServiceOrderReq.QueryStatus req) {
        TServiceOrder  tServiceOrder  = serviceOrderService.selectById(req.getServiceOrderId());
        UnifiedPayResp unifiedPayResp = new UnifiedPayResp();
        if (tServiceOrder != null) {
            unifiedPayResp = doQuery(tServiceOrder.getPayType(), tServiceOrder.getOrderNum());
            // 对支付结果进行处理
            serviceOrderService.doProcess(req.getServiceOrderId(), unifiedPayResp.getCode(), unifiedPayResp.getDesc());
        }
        ServiceOrderStatusResp resp = new ServiceOrderStatusResp();
        resp.setPayStatus(unifiedPayResp.getCode());
        return resp;
    }

    private int getOrderFlag(Integer partyId, Integer assetId, Integer orderType) {
        if (partyId == null && assetId == null) {
            return 0;
        }
        List<TServiceBusinessRecord> businessRecordByType = serviceBusinessRecordService.getBusinessRecordByType(partyId, orderType, assetId);
        if (CollectionUtils.isNotEmpty(businessRecordByType)) {
            return 1;
        }
        return 0;
    }


    private ServiceOrderResp createBasisReport(ServiceOrderReq.AdjustedPay req, Asset asset) {
        try {
            TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.BASIS_REPORT_PRICE);
            DoPayRequestVo basisVo = new DoPayRequestVo(
                    req.getActivityId().toString(),
                    req.getAccountId(),
                    req.getPartyId(),
                    new BigDecimal(tServiceConfig.getConfigValue()),
                    ServiceOrderEnum.OrderType.REPORT_BASIS,
                    basisReportWxPayBody);
            ServiceOrderResp basisResp = getServiceOrderResp(basisVo);
            TDisposeSurvey   survey    = disposeSurveyService.getDisposeSurveyByAssetId(asset.getId());
            createBusinessRecord(asset, basisVo, basisResp.getOrderNum(), survey.getBasisSurvey());
            return basisResp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ServiceOrderResp createCompleteReport(ServiceOrderReq.AdjustedPay req, Asset asset) {
        try {
            TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(ServiceConfigEnum.COMPLETE_REPORT_PRICE);
            DoPayRequestVo completeVo = new DoPayRequestVo(
                    req.getActivityId().toString(),
                    req.getAccountId(),
                    req.getPartyId(),
                    new BigDecimal(tServiceConfig.getConfigValue()),
                    ServiceOrderEnum.OrderType.REPORT_COMPLETE,
                    CompleteReportWxPayBody);
            ServiceOrderResp completeResp = getServiceOrderResp(completeVo);
            TDisposeSurvey   survey       = disposeSurveyService.getDisposeSurveyByAssetId(asset.getId());
            createBusinessRecord(asset, completeVo, completeResp.getOrderNum(), survey.getCompleteSurvey());
            return completeResp;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void createBusinessRecord(Asset asset, DoPayRequestVo doPayRequestVo, String orderNum, String additional) {
        TServiceBusinessRecord record = new TServiceBusinessRecord();
        record.setAssetId(asset.getId());
        record.setOrderType(doPayRequestVo.getOrderType().getValue());
        record.setBuyerAccountId(doPayRequestVo.getAccountId());
        record.setBuyerPartyId(doPayRequestVo.getPartyId());
        record.setOrderNum(orderNum);
        record.setAssetName(asset.getName());
        record.setAdditional(getImageObject(additional));
        record.setAmount(doPayRequestVo.getAmount());
        record.setSellerAccountId(accountService.getAccountBaseByPartyId(asset.getPartyId()).getAccountId());
        record.setSellerPartyId(asset.getPartyId());
        record.setPayStatus("00");
        serviceBusinessRecordService.addBusinessRecord(record);
    }


    private String getImageObject( String additional) {

        if (StringUtils.isNotBlank(additional) ) {

            try {
                JSONObject jsonObject = JSON.parseObject(additional);
                JSONArray images =(JSONArray) jsonObject.get("images");
                if (images != null) {
                    return additional;
                }
            } catch (Exception e ) {
                JSONObject tmp = new JSONObject();
                tmp.put("images",new String[]{additional});
                return JSON.toJSONString(tmp);
            }
        }

        return additional;

    }

    private void createBusinessRecordMode2(Asset asset, DoPayRequestVo doPayRequestVo, String orderNum) {
        TServiceBusinessRecord record = new TServiceBusinessRecord();
        record.setAssetId(asset.getId());
        record.setAssetName(asset.getName());
        record.setOrderType(doPayRequestVo.getOrderType().getValue());
        record.setBuyerAccountId(doPayRequestVo.getAccountId());
        record.setBuyerPartyId(doPayRequestVo.getPartyId());
        record.setOrderNum(orderNum);
        record.setAmount(doPayRequestVo.getAmount());
        record.setPayStatus("00");
        serviceBusinessRecordService.addBusinessRecord(record);
    }

    @Data
    class DoPayRequestVo {
        private String                     busId;
        private Integer                    accountId;
        private Integer                    partyId;
        private BigDecimal                 amount;
        private ServiceOrderEnum.OrderType orderType;
        private String                     body;
        private String                     payBusCode;

        public DoPayRequestVo(String busId, Integer accountId, Integer partyId, BigDecimal amount, ServiceOrderEnum.OrderType orderType, String body) {
            this.busId = busId;
            this.accountId = accountId;
            this.partyId = partyId;
            this.amount = amount;
            this.orderType = orderType;
            this.body = body;
        }

        public DoPayRequestVo(String busId, Integer accountId, Integer partyId, BigDecimal amount, ServiceOrderEnum.OrderType orderType, String body, String payBusCode) {
            this.busId = busId;
            this.accountId = accountId;
            this.partyId = partyId;
            this.amount = amount;
            this.orderType = orderType;
            this.body = body;
            this.payBusCode = payBusCode;
        }
    }

    private ServiceOrderResp getServiceOrderResp(DoPayRequestVo doPayRequestVo) {

        TServiceOrder serviceOrder = new TServiceOrder();
        serviceOrder.setAmount(doPayRequestVo.getAmount());
        serviceOrder.setBusId(doPayRequestVo.getBusId());
        serviceOrder.setCreateTime(new Date());
        serviceOrder.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
        serviceOrder.setAccountId(doPayRequestVo.getAccountId().longValue());
        serviceOrder.setPartyId(doPayRequestVo.getPartyId().longValue());
        serviceOrder.setOrderType(doPayRequestVo.getOrderType().getValue());
        serviceOrder.setPayBusCode(doPayRequestVo.getPayBusCode());

        Integer serviceOrderId = serviceOrderService.insert(serviceOrder);

        UnifiedPayResp unifiedPayResp = doPay(serviceOrderId.toString(), doPayRequestVo.getAmount(), doPayRequestVo.getPartyId(), doPayRequestVo.getBody());
        serviceOrder.setOrderNum(unifiedPayResp.getPayOrder());
        serviceOrder.setPayStatus(unifiedPayResp.getCode());
        serviceOrder.setPayUrl(unifiedPayResp.getUrl());
        serviceOrder.setMsg(unifiedPayResp.getDesc());

        ServiceOrderResp resp  = new ServiceOrderResp();
        int              count = serviceOrderService.update(serviceOrder);
        if (count > 0) {
            resp.setPayUrl(unifiedPayResp.getUrl());
            resp.setServiceOrderId(serviceOrder.getId());
            resp.setAmount(serviceOrder.getAmount());
            resp.setBody(doPayRequestVo.getBody());
            resp.setOrderNum(unifiedPayResp.getPayOrder());
        }
        return resp;
    }


    /**
     * 发送 支付请求
     *
     * @param busId   业务id
     * @param amount  费用
     * @param partyId 账号id
     * @param body    支付显示title
     * @return UnifiedPayResp
     */
    private UnifiedPayResp doPay(String busId, BigDecimal amount, Integer partyId, String body) {
        UnifiedPayReq req = new UnifiedPayReq();
        WxScanPayReq  wx  = new WxScanPayReq();
        wx.setBody(body);
        req.setPartyId(partyId);
        req.setBusId(busId);
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.SERVICE_REQUIREMENT_PAY.getType());
        req.setAmount(amount);
        req.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
        req.setPayParam(wx);
        logger.debug("发送req: {}", JSONObject.toJSONString(req));
        UnifiedPayResp resp = payFacade.unifiedPay(req);
        logger.debug("支付resp: {}", JSONObject.toJSONString(resp));
        return resp;
    }

    /**
     * @return resp
     */
    private UnifiedPayResp doQuery(String payType, String payNum) {
        UnifiedQueryReq req = new UnifiedQueryReq();
        req.setPayOrder(payNum);
        req.setPayType(payType);
        logger.debug("支付查询req: {}", JSONObject.toJSONString(req));
        UnifiedPayResp resp = payFacade.unifiedPayQuery(req);
        logger.debug("支付查询resp: {}", JSONObject.toJSONString(resp));
        return resp;
    }

}
