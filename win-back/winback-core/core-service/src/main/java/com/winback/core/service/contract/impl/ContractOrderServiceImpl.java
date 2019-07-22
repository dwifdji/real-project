package com.winback.core.service.contract.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.DateUtil;
import com.winback.core.aspact.GatewayMqSender;
import com.winback.core.commons.constants.ContractEnum;
import com.winback.core.dao.account.TAccountDao;
import com.winback.core.dao.account.TAccountExtBindDao;
import com.winback.core.dao.contract.*;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade.contract.req.AdminContractOrderReq;
import com.winback.core.facade.contract.req.AppContractOrderReq;
import com.winback.core.facade.contract.req.AppletContractOrderReq;
import com.winback.core.facade.contract.resp.AppContractOrderResp;
import com.winback.core.facade.contract.resp.AppletContractOrderResp;
import com.winback.core.facade.contract.vo.ContractOrder;
import com.winback.core.facade.contract.vo.ContractOrderItem;
import com.winback.core.facade.contract.vo.InvoiceContractOrder;
import com.winback.core.facade.contract.vo.InvoiceContractOrderDetail;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.model.contract.*;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.service.assistant.PushAppletMessageService;
import com.winback.core.service.contract.ContractOrderService;
import com.winback.core.utils.RespConvertUtil;
import com.winback.gateway.common.constants.PayEnum;
import com.winback.gateway.common.constants.PayResultEnums;
import com.winback.gateway.controller.req.pay.PayReq;
import com.winback.gateway.controller.req.pay.PayResp;
import com.winback.gateway.controller.req.pay.QueryReq;
import com.winback.gateway.facade.PayFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xdrodger
 * @Title: ContractOrderServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/2/11 13:23
 */
@Slf4j
@Service
public class ContractOrderServiceImpl implements ContractOrderService {
    @Autowired
    private TContractDao contractDao;
    @Autowired
    private TContractShoppingCartDao shoppingCartDao;
    @Autowired
    private TContractShoppingCartItemDao shoppingCartItemDao;
    @Autowired
    private TContractOrderDao contractOrderDao;
    @Autowired
    private TContractOrderItemDao contractOrderItemDao;
    @Autowired
    private TAccountContractMapDao accountContractMapDao;
    @Reference(version = "1.0.0")
    private PayFacade payFacade;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Autowired
    private GatewayMqSender mqSender;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private PushAppMessageService pushAppMessageService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private PushAppletMessageService pushAppletMessageService;

    @Transactional
    @Override
    public Integer buyNow(AppContractOrderReq.BuyNowReq req) {
        TContract contract = contractDao.selectById(req.getContractId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (accountContractMapDao.hasBuy(req.getLoginId(), req.getContractId())) {
            throw new BusinessException("已购买此合同，无需再次购买");
        }
        List<Integer> contractIdList = new ArrayList<>();
        contractIdList.add(req.getContractId());
        TContractOrder order = createOrder(req.getLoginId(), ContractEnum.OrderSource.APP, contractIdList);
        return order.getId();
    }

    @Transactional
    @Override
    public Integer shoppingCartBuy(AppContractOrderReq.ShoppingCartBuyReq req) {
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(req.getLoginId());
        if (shoppingCart == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<TContractShoppingCartItem> shoppingCartItemList = shoppingCartItemDao.findBy(shoppingCart.getId());
        if (shoppingCartItemList.isEmpty()) {
            throw new BusinessException("购物车为空，请先添加合同");
        }
        List<Integer> contractIdList = new ArrayList<>();
        for (TContractShoppingCartItem shoppingCartItem : shoppingCartItemList) {
            shoppingCartItem.setDeleteFlag(true);
            int result = shoppingCartItemDao.updateById(shoppingCartItem);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            if (!accountContractMapDao.hasBuy(req.getLoginId(), shoppingCartItem.getContractId())) {
                contractIdList.add(shoppingCartItem.getContractId());
            }

        }
        TContractOrder order = createOrder(req.getLoginId(), ContractEnum.OrderSource.APP, contractIdList);
        return order.getId();
    }

    @Transactional
    @Override
    public AppContractOrderResp.OrderBuyResp orderBuy(AppContractOrderReq.OrderBuyReq req) {
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!order.getAccountId().equals(req.getLoginId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus()) && !ContractEnum.PayStatus.CANCEL.getKey().equals(order.getPayStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<TContractOrderItem> list = contractOrderItemDao.findBy(req.getOrderId());
        List<Integer> contractIdList = new ArrayList<>();
        for (TContractOrderItem item : list) {
            if (!accountContractMapDao.hasBuy(req.getLoginId(), item.getContractId())) {
                contractIdList.add(item.getContractId());
            }
        }
        TContractOrder newOrder = createOrder(req.getLoginId(), ContractEnum.OrderSource.APP, contractIdList);
        AppContractOrderResp.OrderBuyResp resp = new AppContractOrderResp.OrderBuyResp();
        resp.setOrderId(newOrder.getId());
        resp.setAmount(newOrder.getAmount());
        return resp;
    }

    @Override
    public AppContractOrderResp.PrepayResp prepay(AppContractOrderReq.PrepayReq req) {
        AppContractOrderResp.PrepayResp resp = new AppContractOrderResp.PrepayResp();
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (order.getPayDeadline().compareTo(new Date()) < 0) {
            throw new BusinessException(ApiCallResult.CONTRACT_ORDER_PAY_FAIL);
        }
        PayReq payReq = new PayReq();
        payReq.setAccountId(req.getLoginId());
        payReq.setAmount(order.getAmount());
        payReq.setOrderDesc("合同购买");
        payReq.setBusinessId(order.getOrderNo());
        payReq.setBusinessType(PayEnum.BUSINESS_TYPE.APP_PAY);
        if (ContractEnum.PayType.ALI_PAY.getKey().equals(req.getPayType())) {
            payReq.setType(PayEnum.PAY_TYPE.ALI_PAY);
        } else  if (ContractEnum.PayType.WX_PAY.getKey().equals(req.getPayType())) {
            payReq.setType(PayEnum.PAY_TYPE.WX_PAY);
        }
        PayResp payResp = payFacade.unifyPay(payReq);
        if (payResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (ContractEnum.PayType.ALI_PAY.getKey().equals(req.getPayType())) {
            order.setPayType(ContractEnum.PayType.ALI_PAY.getKey());
        } else  if (ContractEnum.PayType.WX_PAY.getKey().equals(req.getPayType())) {
            order.setPayType(ContractEnum.PayType.WX_PAY.getKey());
        }
        order.setUpdateTime(new Date());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setPayOrderId(payResp.getOrderId());
        resp.setPrepayId(payResp.getPrepayId());
        resp.setParam(payResp.getParam());
        return resp;
    }

    @Override
    public PageInfoResp<ContractOrder> getOrderList(AppContractOrderReq.QueryReq req) {
        PageInfoResp<ContractOrder> resp = new PageInfoResp<>();
        List<ContractOrder> resultList = new ArrayList<>();
        PageInfo<TContractOrder> pageInfo = contractOrderDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "o.id desc");
        for (TContractOrder item : pageInfo.getList()) {
            try {
                ContractOrder vo = reCalculateContractOrder(item);
                filterRepurchaseOrderItem(vo);
                if (vo.getItemList().isEmpty()) {
                    continue;
                }
                vo.setInvoiceFlag(contractOrderDao.hasInvoice(item.getId()));
                vo.setPayStatusDesc(getAppPayStatusDesc(vo));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    private String getAppPayStatusDesc(ContractOrder order) {
        if (ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            return "未支付";
        } else if (ContractEnum.PayStatus.SUCCESS.getKey().equals(order.getPayStatus())) {
            return "已支付";
        } else if (ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus())) {
            return "已失效";
        } else if (ContractEnum.PayStatus.CANCEL.getKey().equals(order.getPayStatus())) {
            return "已取消";
        } else {
            return "未支付";
        }
    }

    private String getAppletPayStatusDesc(ContractOrder order) {
        if (ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            return "未支付";
        } else if (ContractEnum.PayStatus.SUCCESS.getKey().equals(order.getPayStatus())) {
            return "已支付";
        } else if (ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus())) {
            return "已失效";
        } else if (ContractEnum.PayStatus.CANCEL.getKey().equals(order.getPayStatus())) {
            return "已取消";
        } else {
            return "未支付";
        }
    }

    @Override
    public ContractOrder getOrder(AppContractOrderReq.QueryReq req) {
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        ContractOrder vo = reCalculateContractOrder(order);
        filterRepurchaseOrderItem(vo);
        vo.setPayStatusDesc(getAppPayStatusDesc(vo));
        return vo;
    }

    @Override
    public Integer buyNow(AppletContractOrderReq.BuyNowReq req) {
        TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
        if (!extBind.hasBindAccount()) {
            throw new BusinessException("请先绑定账户");
        }
        TContract contract = contractDao.selectById(req.getContractId());
        if (contract == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (accountContractMapDao.hasBuy(extBind.getAccountId(), req.getContractId())) {
            throw new BusinessException("已购买此合同，无需再次购买");
        }
        List<Integer> contractIdList = new ArrayList<>();
        contractIdList.add(req.getContractId());
        TContractOrder order = createOrder(extBind.getAccountId(), ContractEnum.OrderSource.APPLET, contractIdList);
        return order.getId();
    }

    @Override
    public Integer shoppingCartBuy(AppletContractOrderReq.ShoppingCartBuyReq req) {
        TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
        if (!extBind.hasBindAccount()) {
            throw new BusinessException("请先绑定账户");
        }
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(extBind.getAccountId());
        if (shoppingCart == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<TContractShoppingCartItem> shoppingCartItemList = shoppingCartItemDao.findBy(shoppingCart.getId());
        if (shoppingCartItemList.isEmpty()) {
            throw new BusinessException("购物车为空，请先添加合同");
        }
        List<Integer> contractIdList = new ArrayList<>();
        for (TContractShoppingCartItem shoppingCartItem : shoppingCartItemList) {
            shoppingCartItem.setDeleteFlag(true);
            int result = shoppingCartItemDao.updateById(shoppingCartItem);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            if (!accountContractMapDao.hasBuy(extBind.getAccountId(), shoppingCartItem.getContractId())) {
                contractIdList.add(shoppingCartItem.getContractId());
            }

        }
        TContractOrder order = createOrder(extBind.getAccountId(), ContractEnum.OrderSource.APPLET, contractIdList);
        return order.getId();
    }

    @Override
    public AppletContractOrderResp.OrderBuyResp orderBuy(AppletContractOrderReq.OrderBuyReq req) {
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAccountExtBind extBind = accountExtBindDao.selectById(req.getLoginId());
        if (!order.getAccountId().equals(extBind.getAccountId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus()) && !ContractEnum.PayStatus.CANCEL.getKey().equals(order.getPayStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        List<TContractOrderItem> list = contractOrderItemDao.findBy(req.getOrderId());
        List<Integer> contractIdList = new ArrayList<>();
        for (TContractOrderItem item : list) {
            if (!accountContractMapDao.hasBuy(req.getLoginId(), item.getContractId())) {
                contractIdList.add(item.getContractId());
            }
        }
        TContractOrder newOrder = createOrder(extBind.getAccountId(), ContractEnum.OrderSource.APPLET, contractIdList);
        AppletContractOrderResp.OrderBuyResp resp = new AppletContractOrderResp.OrderBuyResp();
        resp.setOrderId(newOrder.getId());
        resp.setAmount(newOrder.getAmount());
        return resp;
    }

    @Override
    public AppletContractOrderResp.PrepayResp orderPrepay(AppletContractOrderReq.PrepayReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        AppletContractOrderResp.PrepayResp resp = new AppletContractOrderResp.PrepayResp();
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (order.getPayDeadline().compareTo(new Date()) < 0) {
            throw new BusinessException(ApiCallResult.CONTRACT_ORDER_PAY_FAIL);
        }
        PayReq payReq = new PayReq();
        payReq.setAccountId(accountExtBind.getAccountId());
        payReq.setOpenId(accountExtBind.getExtUserId());
        payReq.setAmount(order.getAmount());
        payReq.setOrderDesc("合同购买");
        payReq.setBusinessId(order.getOrderNo());
        payReq.setBusinessType(PayEnum.BUSINESS_TYPE.APPLET_PAY);
        if (ContractEnum.PayType.ALI_PAY.getKey().equals(req.getPayType())) {
            payReq.setType(PayEnum.PAY_TYPE.ALI_PAY);
        } else  if (ContractEnum.PayType.WX_PAY.getKey().equals(req.getPayType())) {
            payReq.setType(PayEnum.PAY_TYPE.WX_PAY);
        }
        PayResp payResp = payFacade.unifyPay(payReq);
        if (payResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (ContractEnum.PayType.ALI_PAY.getKey().equals(req.getPayType())) {
            order.setPayType(ContractEnum.PayType.ALI_PAY.getKey());
        } else  if (ContractEnum.PayType.WX_PAY.getKey().equals(req.getPayType())) {
            order.setPayType(ContractEnum.PayType.WX_PAY.getKey());
        }
        order.setUpdateTime(new Date());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        resp.setPayOrderId(payResp.getOrderId());
        resp.setPrepayId(payResp.getPrepayId());
        resp.setParam(payResp.getParam());
        return resp;
    }

    @Override
    public PageInfoResp<ContractOrder> getOrderList(AppletContractOrderReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        req.setLoginId(accountExtBind.getAccountId());
        PageInfoResp<ContractOrder> resp = new PageInfoResp<>();
        List<ContractOrder> resultList = new ArrayList<>();
        PageInfo<TContractOrder> pageInfo = contractOrderDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "o.id desc");
        for (TContractOrder item : pageInfo.getList()) {
            try {
                ContractOrder vo = reCalculateContractOrder(item);
                vo.setInvoiceFlag(contractOrderDao.hasInvoice(item.getId()));
                vo.setPayStatusDesc(getAppletPayStatusDesc(vo));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public ContractOrder getOrder(AppletContractOrderReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!accountExtBind.getAccountId().equals(order.getAccountId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        ContractOrder vo = reCalculateContractOrder(order);
        vo.setPayStatusDesc(getAppletPayStatusDesc(vo));
        return vo;
    }

    @Override
    public Integer cancelOrder(AppContractOrderReq.QueryReq req) {
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!req.getLoginId().equals(order.getAccountId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            order.setPayStatus(ContractEnum.PayStatus.CANCEL.getKey());
            int result = contractOrderDao.updateById(order);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return req.getOrderId();
    }

    @Override
    public Integer cancelOrder(AppletContractOrderReq.QueryReq req) {
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind.getAccountId() == null || !accountExtBind.getAccountId().equals(order.getAccountId())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            order.setPayStatus(ContractEnum.PayStatus.CANCEL.getKey());
            int result = contractOrderDao.updateById(order);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return req.getOrderId();
    }

    @Override
    public ResponseModel getOrderList(AdminContractOrderReq.QueryReq req) {
        Map<String, Object> data = new HashMap<>();
        List<ContractOrder> resultList = new ArrayList<>();
        PageInfo<TContractOrder> pageInfo = contractOrderDao.getBackgroundList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "o.id desc");
        for (TContractOrder item : pageInfo.getList()) {
            try {
                ContractOrder vo = reCalculateContractOrder(item);
                if (ContractEnum.PayStatus.SUCCESS.getKey().equals(item.getPayStatus())) {
                    vo.setPayTime(item.getUpdateTime());
                }
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        data.put("list", resultList);
        data.put("hasNextPage", pageInfo.isHasNextPage());
        data.put("total", pageInfo.getTotal());
        data.putAll(contractOrderDao.getSummaryInfo((Map<String, Object>) JSON.toJSON(req)));
        return ResponseModel.succ(data);
    }

    @Override
    public Integer payTimeOut(Integer orderId) {
        TContractOrder order = contractOrderDao.selectById(orderId);
        if (order == null || !ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            return orderId;
        }
        order.setPayStatus(ContractEnum.PayStatus.FAIL.getKey());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (ContractEnum.OrderSource.APPLET.getKey().equals(order.getOrderSource())) {
            TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(order.getAccountId());
            if (extBind != null) {
                pushAppletMessageService.pushContractOrderPayTimeOutMsg(extBind.getId(), order.getId(), order.getOrderNo());
            }
        }
        return orderId;
    }

    @Override
    public Integer beAboutToPayTimeOut(Integer orderId) {
        TContractOrder order = contractOrderDao.selectById(orderId);
        if (order == null || !ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            return orderId;
        }
        if (ContractEnum.OrderSource.APPLET.getKey().equals(order.getOrderSource())) {
            TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(order.getAccountId());
            if (extBind != null) {
                pushAppletMessageService.pushContractOrderBeAboutToPayTimeOutMsg(extBind.getId(), order.getId(), order.getOrderNo());
            }
        }
        return orderId;
    }

    @Transactional
    @Override
    public String payCallBack(AppContractOrderReq.PayCallBackReq req) {
        log.info("APP合同购买订单支付回调={}", JSON.toJSONString(req));
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            if (ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus())) {
                throw new BusinessException(ApiCallResult.CONTRACT_ORDER_PAY_FAIL);
            }
            return req.getPayOrderId();
        }
        if (BigDecimal.ZERO.compareTo(order.getAmount()) < 0) {
            if (StringUtils.isBlank(req.getPayOrderId())) {
                throw new BusinessException(ApiCallResult.EMPTY);
            }
            QueryReq queryReq = new QueryReq();
            queryReq.setOrderId(req.getPayOrderId());
            PayResp payResp = payFacade.query(queryReq);
            if (payResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
                log.info("APP合同购买订单={}，未支付成功");
                throw new BusinessException("订单支付失败");
            }
        }
        order.setPayStatus(ContractEnum.PayStatus.SUCCESS.getKey());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        List<TContractOrderItem> list = contractOrderItemDao.findBy(order.getId());
        for (TContractOrderItem item : list) {
            TAccountContractMap contractMap = new TAccountContractMap();
            contractMap.setAccountId(order.getAccountId());
            contractMap.setContractId(item.getContractId());
            result = accountContractMapDao.insert(contractMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            contractDao.addPurchaseCount(item.getContractId());
        }
        pushAppMessageService.pushContractOrderPaySuccessMsg(order.getAccountId());
        return req.getPayOrderId();
    }

    @Transactional
    @Override
    public String payCallBack(AppletContractOrderReq.PayCallBackReq req) {
        log.info("APPLET合同购买订单支付回调={}", JSON.toJSONString(req));
        TContractOrder order = contractOrderDao.selectById(req.getOrderId());
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            if (ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus())) {
                throw new BusinessException(ApiCallResult.CONTRACT_ORDER_PAY_FAIL);
            }
            return req.getPayOrderId();
        }
        if (BigDecimal.ZERO.compareTo(order.getAmount()) < 0) {
            if (StringUtils.isBlank(req.getPayOrderId())) {
                throw new BusinessException(ApiCallResult.EMPTY);
            }
            QueryReq queryReq = new QueryReq();
            queryReq.setOrderId(req.getPayOrderId());
            PayResp payResp = payFacade.query(queryReq);
            if (payResp == null || !PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
                log.info("APPLET合同购买订单={}，未支付成功");
                throw new BusinessException("订单支付失败");
            }
        }
        order.setPayStatus(ContractEnum.PayStatus.SUCCESS.getKey());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        List<TContractOrderItem> list = contractOrderItemDao.findBy(order.getId());
        for (TContractOrderItem item : list) {
            TAccountContractMap contractMap = new TAccountContractMap();
            contractMap.setAccountId(order.getAccountId());
            contractMap.setContractId(item.getContractId());
            result = accountContractMapDao.insert(contractMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            contractDao.addPurchaseCount(item.getContractId());
        }
        TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(order.getAccountId());
        if (extBind != null) {
            pushAppletMessageService.pushContractOrderPaySuccessMsg(extBind.getId());
        }
        return req.getPayOrderId();
    }

    @Transactional
    @Override
    public String payCallBack(String orderNo) {
        log.info("gateway合同购买订单支付回调={}", orderNo);
        TContractOrder order = contractOrderDao.findBy(orderNo);
        if (order == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus())) {
            return orderNo;
        }
        order.setPayStatus(ContractEnum.PayStatus.SUCCESS.getKey());
        int result = contractOrderDao.updateById(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        List<TContractOrderItem> list = contractOrderItemDao.findBy(order.getId());
        for (TContractOrderItem item : list) {
            TAccountContractMap contractMap = new TAccountContractMap();
            contractMap.setAccountId(order.getAccountId());
            contractMap.setContractId(item.getContractId());
            result = accountContractMapDao.insert(contractMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            contractDao.addPurchaseCount(item.getContractId());
        }
        if (ContractEnum.OrderSource.APPLET.getKey().equals(order.getOrderSource())) {
            TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(order.getAccountId());
            if (extBind != null) {
                pushAppletMessageService.pushContractOrderPaySuccessMsg(extBind.getAccountId());
            }
        }
        return orderNo;
    }

    @Override
    public ListResp<InvoiceContractOrder> getInvoiceOrderList(AppContractOrderReq.QueryReq req) {
        ListResp<InvoiceContractOrder> resp = new ListResp<>();
        List<TContractOrder> itemList = contractOrderDao.getInvoiceOrderList((Map<String, Object>) JSON.toJSON(req));
        Map<String, List<InvoiceContractOrderDetail>> map = new LinkedHashMap<>();
        for (TContractOrder item : itemList) {
            String month = DateUtil.formatChinaMonth(item.getCreateTime());
            InvoiceContractOrderDetail order = new InvoiceContractOrderDetail();
            order.setOrderId(item.getId());
            order.setOrderNo(item.getOrderNo());
            order.setAmount(item.getAmount());
            order.setCreateTime(item.getCreateTime());
            if (map.containsKey(month)) {
                List<InvoiceContractOrderDetail> monthOrders = map.get(month);
                monthOrders.add(order);
            } else {
                List<InvoiceContractOrderDetail> monthOrders = new ArrayList<>();
                monthOrders.add(order);
                map.put(month, monthOrders);
            }
        }
        List<InvoiceContractOrder> list = new LinkedList<>();
        for (Map.Entry<String, List<InvoiceContractOrderDetail>> entry : map.entrySet()) {
            InvoiceContractOrder vo = new InvoiceContractOrder();
            vo.setMonth(entry.getKey());
            vo.setOrderList(entry.getValue());
            list.add(vo);
        }
        resp.setList(list);
        return resp;
    }

    @Override
    public ListResp<InvoiceContractOrder> getInvoiceOrderList(AppletContractOrderReq.QueryReq req) {
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        AppContractOrderReq.QueryReq queryReq = new AppContractOrderReq.QueryReq();
        queryReq.setLoginId(accountExtBind.getAccountId());
        queryReq.setPage(req.getPage());
        queryReq.setPerPage(req.getPerPage());
        return getInvoiceOrderList(queryReq);
    }

    private ContractOrder reCalculateContractOrder(TContractOrder order) {
        ContractOrder result = RespConvertUtil.convertToContractOrder(order);
        List<ContractOrderItem> itemList = new ArrayList<>();
        List<TContractOrderItem> list = contractOrderItemDao.findBy(order.getId());
        for (TContractOrderItem item : list) {
            if ((ContractEnum.PayStatus.INIT.getKey().equals(order.getPayStatus()) || ContractEnum.PayStatus.FAIL.getKey().equals(order.getPayStatus())) && !item.getDeleteFlag()) {
                if (accountContractMapDao.hasBuy(order.getAccountId(), item.getContractId())) {
                    item.setDeleteFlag(true);
                    contractOrderItemDao.updateById(item);
                }
            }
            TContract contract = contractDao.selectById(item.getContractId());
            if (contract == null) {
                item.setDeleteFlag(true);
                contractOrderItemDao.updateById(item);
            }
            ContractOrderItem vo = new ContractOrderItem();
            vo.setId(item.getId());
            vo.setAmount(item.getAmount());
            vo.setName(contract.getName());
            vo.setContractId(item.getContractId());
            vo.setDeleteFlag(item.getDeleteFlag());
            itemList.add(vo);
        }
        BigDecimal amount = reCalculateOrderAmount(itemList);
        if (order.getAmount().compareTo(amount) != 0) {
            order.setAmount(amount);
            contractOrderDao.updateById(order);
        }
        result.setItemList(itemList);
        if (isAllRePurchase(result) && !ContractEnum.PayStatus.CANCEL.getKey().equals(order.getPayStatus())) {
            order.setPayStatus(ContractEnum.PayStatus.CANCEL.getKey());
            contractOrderDao.updateById(order);
        }
        result.setAmount(order.getAmount());
        result.setPayStatus(order.getPayStatus());
        return result;
    }

    private void filterRepurchaseOrderItem(ContractOrder order) {
        List<ContractOrderItem> itemList = order.getItemList();
        Iterator<ContractOrderItem> itr = itemList.iterator();
        while (itr.hasNext()) {
            ContractOrderItem item = itr.next();
            if (item.getDeleteFlag()) {
                itr.remove();
            }
        }
    }

    private BigDecimal reCalculateOrderAmount(List<ContractOrderItem> itemList) {
        BigDecimal amount = BigDecimal.ZERO;
        for (ContractOrderItem item : itemList) {
            if (item.getDeleteFlag()) {
                continue;
            }
            amount = amount.add(item.getAmount());
        }
        return amount;
    }

    private boolean isAllRePurchase(ContractOrder order) {
        boolean flag = true;
        for (ContractOrderItem item : order.getItemList()) {
            if (!item.getDeleteFlag()) {
                flag = false;
            }
        }
        return flag;
    }

    private TContractOrder createOrder(Integer accountId, ContractEnum.OrderSource orderSource, List<Integer> contractIdList) {
        if (contractIdList.isEmpty()) {
            throw new BusinessException("暂无有效合同，请重新选择");
        }
        BigDecimal amount = BigDecimal.ZERO;
        List<TContract> contractList = new ArrayList<>();
        for (Integer contractId : contractIdList) {
            TContract contract = contractDao.selectById(contractId);
            if (contract == null) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            amount = amount.add(contract.getAmount());
            contractList.add(contract);
        }
        TContractOrder order = contractOrderDao.createOrder(accountId, orderSource, amount);
        for (TContract contract : contractList) {
            contractOrderItemDao.createOrderItem(order.getId(), contract.getId(), contract.getAmount());
        }
        if (ContractEnum.OrderSource.APPLET.equals(orderSource)) {
            mqSender.contractOrderBeAboutToPayTimeOut(order.getId() + "", 1505);
        }
        mqSender.contractOrderPayTimeOut(order.getId() + "", 1805);
        return order;
    }
}
