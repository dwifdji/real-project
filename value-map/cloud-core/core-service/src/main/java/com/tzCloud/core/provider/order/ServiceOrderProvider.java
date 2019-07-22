package com.tzCloud.core.provider.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.tzCloud.core.common.constants.ServiceConfigEnum;
import com.tzCloud.core.common.constants.ServiceOrderEnum;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.core.facade.order.ServiceOrderFacade;
import com.tzCloud.core.facade.order.req.ServiceOrderReq;
import com.tzCloud.core.facade.order.resp.ServiceConfigResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderResp;
import com.tzCloud.core.facade.order.resp.ServiceOrderStatusResp;
import com.tzCloud.core.model.order.TServiceConfig;
import com.tzCloud.core.model.order.TServiceOrder;
import com.tzCloud.core.service.ServiceOrderService;
import com.tzCloud.core.service.TServiceConfigService;
import com.tzCloud.gateway.common.constants.PayEnums;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayReq;
import com.tzCloud.gateway.controller.req.pay.UnifiedPayResp;
import com.tzCloud.gateway.controller.req.pay.UnifiedQueryReq;
import com.tzCloud.gateway.controller.req.pay.WxScanPayReq;
import com.tzCloud.gateway.facade.PayFacade;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @Autowired
    private ServiceOrderService serviceOrderService;
    @Autowired
    private TServiceConfigService tServiceConfigService;
    @Reference(version = "1.0.0")
    private PayFacade payFacade;

    private static String MEMBERSHIP_PAY_BODY = "会员费用支付";

    @Override
    public ServiceOrderStatusResp queryStatus(ServiceOrderReq.QueryStatus req) {
        TServiceOrder tServiceOrder  = serviceOrderService.selectById(req.getServiceOrderId());
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

    @Override
    public ServiceOrderResp membershipPay(ServiceOrderReq.MembershipOpenReq req) {
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(req.getMembershipFeeType());
        if (tServiceConfig == null) {
            throw new RuntimeException("支付类型错误");
        }
        BigDecimal price          = new BigDecimal(tServiceConfig.getConfigValue());
        //如果价格小于或等于0 或者 配置该人不需付钱
        if (price.compareTo(BigDecimal.ZERO) <= 0) {
            // 发送通知
            return new ServiceOrderResp();
        }
        ServiceOrderEnum.OrderType membershipFee ;
        if (req.getMembershipFeeType().equals(ServiceConfigEnum.MEMBERSHIP_MONTHLY_FEE.getKey())) {
            membershipFee = ServiceOrderEnum.OrderType.MEMBERSHIP_MONTH_FEE;
        } else if (req.getMembershipFeeType().equals(ServiceConfigEnum.MEMBERSHIP_ANNUAL_FEE.getKey())) {
            membershipFee = ServiceOrderEnum.OrderType.MEMBERSHIP_YEAR_FEE;
        } else {
            throw new BusinessException("订单类型异常");
        }
        DoPayRequestVo doPayRequestVo = new DoPayRequestVo(null, req.getAccountId(), price, membershipFee, MEMBERSHIP_PAY_BODY, PayEnums.PAY_BUS_CODE.MEMBERSHIP_FEE_PAY.getType());
        return getServiceOrderResp(doPayRequestVo);
    }

    @Override
    public List<ServiceConfigResp> getMembershipConfig() {
        List<TServiceConfig> membershipConfig = tServiceConfigService.selectByLike("membership_");
        List<ServiceConfigResp> resp = new LinkedList<>();
        for (TServiceConfig config : membershipConfig) {
            ServiceConfigResp serviceConfigResp = new ServiceConfigResp();
            serviceConfigResp.setConfigType(config.getConfigType());
            serviceConfigResp.setConfigValue(config.getConfigValue());
            resp.add(serviceConfigResp);
        }
        return resp;
    }

    @Data
    class DoPayRequestVo {
        private String                     busId;
        private Integer                    accountId;
        private BigDecimal                 amount;
        private ServiceOrderEnum.OrderType orderType;
        private String                     body;
        private String                     payBusCode;

        public DoPayRequestVo(String busId, Integer accountId, BigDecimal amount, ServiceOrderEnum.OrderType orderType, String body) {
            this.busId = busId;
            this.accountId = accountId;
            this.amount = amount;
            this.orderType = orderType;
            this.body = body;
        }

        public DoPayRequestVo(String busId, Integer accountId, BigDecimal amount, ServiceOrderEnum.OrderType orderType, String body, String payBusCode) {
            this.busId = busId;
            this.accountId = accountId;
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
        serviceOrder.setAccountId(doPayRequestVo.getAccountId());
        serviceOrder.setOrderType(doPayRequestVo.getOrderType().getValue());
        serviceOrder.setPayBusCode(doPayRequestVo.getPayBusCode());

        Integer serviceOrderId = serviceOrderService.insert(serviceOrder);

        UnifiedPayResp unifiedPayResp = doPay(serviceOrderId.toString(), doPayRequestVo.getAmount(), doPayRequestVo.getAccountId(), doPayRequestVo.getBody());
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
     * @param body    支付显示title
     * @return UnifiedPayResp
     */
    private UnifiedPayResp doPay(String busId, BigDecimal amount,Integer accountId, String body) {
        UnifiedPayReq req = new UnifiedPayReq();
        WxScanPayReq  wx  = new WxScanPayReq();
        wx.setBody(body);
        req.setAccountId(accountId);
        req.setBusId(busId);
        req.setPayBusCode(PayEnums.PAY_BUS_CODE.MEMBERSHIP_FEE_PAY.getType());
        req.setAmount(amount);
        req.setPayType(PayEnums.PAY_TYPE.SCAN_PAY.getType());
        req.setPayParam(wx);
        log.debug("发送req: {}", JSONObject.toJSONString(req));
        UnifiedPayResp resp = payFacade.unifiedPay(req);
        log.debug("支付resp: {}", JSONObject.toJSONString(resp));
        return resp;
    }


    private UnifiedPayResp doQuery(String payType, String payNum) {
        UnifiedQueryReq req = new UnifiedQueryReq();
        req.setPayOrder(payNum);
        req.setPayType(payType);
        log.debug("支付查询req: {}", JSONObject.toJSONString(req));
        UnifiedPayResp resp = payFacade.unifiedPayQuery(req);
        log.debug("支付查询resp: {}", JSONObject.toJSONString(resp));
        return resp;
    }
}
