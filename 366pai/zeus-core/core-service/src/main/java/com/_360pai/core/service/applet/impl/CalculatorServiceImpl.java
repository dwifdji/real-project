package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.CalculatorEnum;
import com._360pai.core.common.constants.ExceptionEmailEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.numberJump.TDebtCalculatorCondition;
import com._360pai.core.condition.numberJump.TPrincipalInterestCalculatorCondition;
import com._360pai.core.dao.account.TAccountExtBindDao;
import com._360pai.core.dao.numberJump.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.facade.applet.vo.CalculatorQueryConditionVo;
import com._360pai.core.model.account.TAccountExtBind;
import com._360pai.core.model.numberJump.*;
import com._360pai.core.service.applet.CalculatorService;
import com._360pai.core.service.applet.DebtCalculatorService;
import com._360pai.core.service.applet.PrincipalInterestCalculatorService;
import com._360pai.core.service.assistant.TServiceConfigService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayReq;
import com._360pai.gateway.controller.req.dfftpay.UnifiedPayResp;
import com._360pai.gateway.controller.req.dfftpay.UnifiedQueryReq;
import com._360pai.gateway.controller.req.dfftpay.WxScanPayReq;
import com._360pai.gateway.facade.PayFacade;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wx.OpenIdResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

import static com._360pai.core.common.CoreConstants.BIG_DECIMAL_SCALE;

/**
 * @author xdrodger
 * @Title: NumberJumpServiceImpl
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 15:03
 */
@Slf4j
@Service("calculatorService")
public class CalculatorServiceImpl implements CalculatorService {
    @Reference(version = "1.0.0")
    protected WxFacade                                 wxFacade;
    @Autowired
    protected TAccountExtBindDao                       accountExtBindDao;
    @Autowired
    protected TDebtCalculatorDao                       debtCalculatorDao;
    @Autowired
    protected TPrincipalInterestCalculatorDao          principalInterestCalculatorDao;
    @Autowired
    private   TCalculatorRequestRecordDao              requestRecordDao;
    @Autowired
    protected TPrincipalInterestCalculatorDetailDao    principalInterestCalculatorDetailDao;
    @Autowired
    protected TDebtCalculatorBroadcastDao              debtCalculatorBroadcastDao;
    @Autowired
    protected TPrincipalInterestCalculatorBroadcastDao principalInterestCalculatorBroadcastDao;
    @Autowired
    protected TBankBenchmarkInterestRateDao            benchmarkInterestRateDao;
    @Autowired
    protected TServiceConfigService                    serviceConfigService;
    @Reference(version = "1.0.0")
    protected PayFacade                                payFacade;
    @Autowired
    protected ExceptionEmailService                    exceptionEmailService;
    @Autowired
    protected GatewayProperties                        gatewayProperties;
    @Autowired
    protected PrincipalInterestCalculatorService       principalInterestCalculatorService;
    @Autowired
    protected DebtCalculatorService                    debtCalculatorService;

    @Override
    public CalculatorResp.LoginResp login(CalculatorReq.LoginReq req) {
        CalculatorResp.LoginResp resp = new CalculatorResp.LoginResp();
        log.info("get applet openid start, code={}", req.getCode());
        OpenIdResp openIdResp = wxFacade.getCalculatorOpenId(req.getCode());
        log.info("get applet openid end, code={}, resp={}", req.getCode(), JSON.toJSONString(openIdResp));
        if (openIdResp == null || !openIdResp.getCode().equals("000")) {
            log.error("获取小程序openId失败，入参={}，出参={}", req.getCode(), JSON.toJSONString(openIdResp));
            throw new BusinessException("获取小程序openId失败");
        }
        TAccountExtBind accountExtBind = accountExtBindDao.findCalculatorByOpenId(openIdResp.getOpenId());
        if (accountExtBind == null) {
            accountExtBind = new TAccountExtBind();
            accountExtBind.setExtType(AccountEnum.ExtType.CALCULATOR.getKey());
            accountExtBind.setExtUserId(openIdResp.getOpenId());
            accountExtBind.setUnionId(openIdResp.getUnionId());
            accountExtBind.setNickName(StringEscapeUtils.escapeJava(req.getNickName()));
            accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
            int result = accountExtBindDao.insert(accountExtBind);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else {
            boolean update = false;
            if (StringUtils.isNotEmpty(req.getNickName()) && !req.getNickName().equals(accountExtBind.getNickName())) {
                accountExtBind.setNickName(StringEscapeUtils.escapeJava(req.getNickName()));
                update = true;
            }
            if (StringUtils.isNotEmpty(req.getHeadImgUrl()) && !req.getHeadImgUrl().equals(accountExtBind.getHeadImgUrl())) {
                accountExtBind.setHeadImgUrl(req.getHeadImgUrl());
                update = true;
            }
            if (StringUtils.isBlank(accountExtBind.getUnionId()) && StringUtils.isNotBlank(openIdResp.getUnionId())) {
                accountExtBind.setUnionId(openIdResp.getUnionId());
                update = true;
            }
            if (update) {
                accountExtBindDao.updateById(accountExtBind);
            }
        }
        resp.setLoginId(accountExtBind.getId());
        resp.setOpenId(accountExtBind.getExtUserId());
        return resp;
    }


    @Override
    public ResponseModel calculatorBroadcastPay(CalculatorReq.CalculatorBroadcastPayReq req) {
        UnifiedPayResp payResp;
        String         amount = serviceConfigService.findByConfigType(ServiceConfigEnum.CALCULATOR_BROADCAST_AMOUNT);
        if (CalculatorEnum.Type.Debt.getKey().equals(req.getType())) {
            TDebtCalculator calculator = debtCalculatorDao.selectById(req.getCalculatorId());
            if (calculator == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (calculator.getPayFlag()) {
                throw new BusinessException("已支付，无需重复支付");
            }
            TAccountExtBind extBind = accountExtBindDao.selectById(calculator.getExtBindId());
            if (extBind == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            payResp = doCalculatorBroadcastPay(CalculatorEnum.Type.Debt.getKey() + "-" + req.getCalculatorId() + "", extBind.getExtUserId(), amount);
        } else if (CalculatorEnum.Type.PrincipalInterest.getKey().equals(req.getType())) {
            TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(req.getCalculatorId());
            if (calculator == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (calculator.getPayFlag()) {
                throw new BusinessException("已支付，无需重复支付");
            }
            TAccountExtBind extBind = accountExtBindDao.selectById(calculator.getExtBindId());
            if (extBind == null) {
                throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            payResp = doCalculatorBroadcastPay(CalculatorEnum.Type.PrincipalInterest.getKey() + "-" + req.getCalculatorId() + "", extBind.getExtUserId(), amount);
        } else {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!PayResultEnums.PAY_NOTICE.getCode().equals(payResp.getCode())) {
            return ResponseModel.fail(payResp.getDesc());
        }
        Map<String, String> data = new HashMap<>();
        data.put("amount", amount);
        data.put("nonceStr", RandomNumberGenerator.getUUID());
        data.put("timeStamp", DateUtil.format(new Date(), DateUtil.FORMAT_LONG_NO_SPLIT));
        data.put("signType", "MD5");
        data.put("payPackage", "prepay_id=" + payResp.getParam());
        data.put("paySign", getPaySign(data));
        data.put("orderId", payResp.getPayOrder());
        return ResponseModel.succ(data);
    }

    /**
     * 微信加密
     */
    private String getPaySign(Map<String, String> p) {
        Map<String, String> data = new HashMap<>();

        data.put("appId", gatewayProperties.getCalculatorAppId());
        data.put("nonceStr", p.get("nonceStr"));
        data.put("package", p.get("payPackage"));
        data.put("signType", p.get("signType"));
        data.put("timeStamp", p.get("timeStamp"));
        try {
            return WXPayUtil.generateSignature(data, gatewayProperties.getWxPayKey(), WXPayConstants.SignType.MD5);
        } catch (Exception e) {
            log.error("小程序支付二次签名异常，异常信息为：{}", e);
        }
        return null;
    }

    private UnifiedPayResp doCalculatorBroadcastPay(String busId, String openId, String amount) {
        UnifiedPayResp payResp = new UnifiedPayResp();
        UnifiedPayReq  payReq  = new UnifiedPayReq();
        payReq.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());
        payReq.setPayBusCode(PayEnums.PAY_BUS_CODE.APPLET_CALCULATOR_PAY.getType());
        payReq.setAmount(new BigDecimal(amount));
        payReq.setBusId(busId);
        WxScanPayReq wxScanPayReq = new WxScanPayReq();
        wxScanPayReq.setBody("实时播报费用");
        wxScanPayReq.setOpenId(openId);
        payReq.setPayParam(wxScanPayReq);
        log.info("实时播报请求微信支付接口，参数为：{}", JSON.toJSONString(payReq));
        try {
            payResp = payFacade.unifiedPay(payReq);
        } catch (Exception e) {
            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS, ExceptionEmailEnum.MODULE_TYPE.APPLET, "实时播报支付异常", JSON.toJSONString(payReq), exceptionEmailService.exceptionToStr(e));
            log.error("实时播报支付异常，异常信息为：{}", e);
            payResp.setCode(PayResultEnums.SYS_EXCEPTION.getCode());
            payResp.setDesc(PayResultEnums.SYS_EXCEPTION.getDesc());
        }
        return payResp;
    }

    @Override
    public ResponseModel calculatorBroadcastPayCallBack(CalculatorReq.CalculatorBroadcastPayCallBackReq req) {
        log.info("实时播报微信支付回调，参数为：{}", JSON.toJSONString(req));
        String   busId        = getCalculatorBroadcastPayBusId(req.getOrderId());
        String[] arrs         = busId.split("-");
        String   type         = arrs[0];
        String   calculatorId = arrs[1];

        if (CalculatorEnum.Type.Debt.getKey().equals(type)) {
            TDebtCalculator calculator = debtCalculatorDao.selectById(calculatorId);
            if (!calculator.getPayFlag()) {
                calculator.setPayFlag(true);
                calculator.setUpdateTime(new Date());
                int result = debtCalculatorDao.updateById(calculator);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                debtCalculatorService.processDebtCalculatorBroadcast(calculator);
            }
        } else if (CalculatorEnum.Type.PrincipalInterest.getKey().equals(type)) {
            TPrincipalInterestCalculator calculator = principalInterestCalculatorDao.selectById(calculatorId);
            if (!calculator.getPayFlag()) {
                calculator.setPayFlag(true);
                calculator.setUpdateTime(new Date());
                int result = principalInterestCalculatorDao.updateById(calculator);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                principalInterestCalculatorService.processPrincipalInterestCalculatorBroadcast(calculator);
            }
        } else {
            return ResponseModel.fail();
        }
        return ResponseModel.succ();
    }

    private String getCalculatorBroadcastPayBusId(String orderId) {
        UnifiedQueryReq queryReq = new UnifiedQueryReq();
        queryReq.setPayType(PayEnums.PAY_TYPE.APPLET_PAY.getType());
        queryReq.setPayOrder(orderId);
        UnifiedPayResp payResp = payFacade.unifiedPayQuery(queryReq);
        if (PayResultEnums.PAY_SUCCESS.getCode().equals(payResp.getCode())) {
            return payResp.getBusId();
        } else {
            throw new BusinessException(ApiCallResult.NO_PAY);
        }
    }

    @Override
    public Object getCalculatorHistory(CalculatorReq.QueryReq req) {
        PageInfoResp<CalculatorHistory> resp   = new PageInfoResp<>();
        Map<String, Object>             params = new HashMap<>();
        params.put("extBindId", req.getExtBindId());
        PageInfo<CalculatorHistory> pageInfo = debtCalculatorDao.getHistoryList(req.getPage(), req.getPerPage(), params);
        resp.setList(pageInfo.getList());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<CalculatorHistory> getCalculatorHistoryV2(CalculatorReq.QueryHistoryReq req) {
        PageInfoResp<CalculatorHistory> resp = new PageInfoResp<>();

        if ("1".equals(req.getType())) {
            PageInfo<CalculatorHistory> pageInfo = debtCalculatorDao.getHistoryListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
            resp.setList(pageInfo.getList());
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
        } else if ("0".equals(req.getType())) {
            PageInfo<CalculatorHistory> pageInfo = principalInterestCalculatorDao.getHistoryListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
            resp.setList(pageInfo.getList());
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
        }
        return resp;
    }

    @Override
    public PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastList(CalculatorReq.QueryReq req) {
        PageInfoResp<CalculatorBroadcast> resp       = new PageInfoResp<>();
        PageInfo<CalculatorBroadcast>     pageInfo   = debtCalculatorDao.getMyBroadcastList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        List<CalculatorBroadcast>         resultList = new ArrayList<>();
        for (CalculatorBroadcast item : pageInfo.getList()) {
            if (CalculatorEnum.Type.PrincipalInterest.getKey().equals(item.getType())) {
                TPrincipalInterestCalculatorBroadcast broadcast = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                if (broadcast == null) {
                    continue;
                }
                item.setTodayInterest(broadcast.getTodayInterest());
                item.setEstimatedIncome(broadcast.getIncome());
                item.setReadFlag(broadcast.getReadFlag());
            } else if (CalculatorEnum.Type.Debt.getKey().equals(item.getType())) {
                TDebtCalculatorBroadcast broadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                if (broadcast == null) {
                    continue;
                }
                item.setAnnualizedReturn(broadcast.getAnnualizedReturn());
                item.setRemainIncome(broadcast.getIncome());
                item.setReadFlag(broadcast.getReadFlag());
            }
            resultList.add(item);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastListV2(CalculatorReq.QueryBroadcastReq req) {
        PageInfoResp<CalculatorBroadcast> resp     = new PageInfoResp<>();
        PageInfo<CalculatorBroadcast>     pageInfo = new PageInfo<>();
        if ("1".equals(req.getType())) {
            pageInfo = debtCalculatorDao.getMyBroadcastListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        } else if ("0".equals(req.getType())) {
            pageInfo = principalInterestCalculatorDao.getMyBroadcastList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        }
        List<CalculatorBroadcast> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (CalculatorBroadcast item : pageInfo.getList()) {
                if (CalculatorEnum.Type.PrincipalInterest.getKey().equals(item.getType())) {
                    TPrincipalInterestCalculatorBroadcast broadcast = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                    if (broadcast == null) {
                        continue;
                    }
                    item.setTodayInterest(broadcast.getTodayInterest());
                    item.setEstimatedIncome(broadcast.getIncome());
                    item.setReadFlag(broadcast.getReadFlag());
                } else if (CalculatorEnum.Type.Debt.getKey().equals(item.getType())) {
                    TDebtCalculatorBroadcast broadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                    if (broadcast == null) {
                        continue;
                    }
                    item.setAnnualizedReturn(broadcast.getAnnualizedReturn());
                    item.setRemainIncome(broadcast.getIncome());
                    item.setReadFlag(broadcast.getReadFlag());
                }
                resultList.add(item);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public void saveCalculatorRecord(TCalculatorRequestRecord record) {
        try {
            requestRecordDao.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getUnreadBroadcastCount(Integer extBindId) {
        Integer count  = principalInterestCalculatorBroadcastDao.getUnreadBroadcastCount(extBindId);
        Integer count2 = debtCalculatorBroadcastDao.getUnreadBroadcastCount(extBindId);
        return count + count2;
    }

    @Override
    public ResponseModel getRelativeList(CalculatorReq.QueryRelativeListReq req) {
        PageInfoResp<CalculatorHistory> resp = new PageInfoResp<>();

        if ("1".equals(req.getType())) {
            PageInfo<CalculatorHistory> pageInfo = debtCalculatorDao.getHistoryListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
            resp.setList(pageInfo.getList());
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
        } else if ("0".equals(req.getType())) {
            PageInfo<CalculatorHistory> pageInfo = principalInterestCalculatorDao.getHistoryListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
            resp.setList(pageInfo.getList());
            resp.setHasNextPage(pageInfo.isHasNextPage());
            resp.setTotal(pageInfo.getTotal());
        }
        return ResponseModel.succ(resp);

//
//        List<CalculatorRelativeVo> resultList = new ArrayList<>();
//        CalculatorRelativeVo       calculatorRelativeVo;
//        if ("1".equals(req.getType())) {
//            TPrincipalInterestCalculatorCondition condition       = new TPrincipalInterestCalculatorCondition();
//            condition.setProjectName(req.getProjectName());
//            condition.setExtBindId(req.getExtBindId());
//            List<TPrincipalInterestCalculator> tPrincipalInterestCalculators = principalInterestCalculatorDao.selectList(condition);
//            for (TPrincipalInterestCalculator item : tPrincipalInterestCalculators) {
//                calculatorRelativeVo = new CalculatorRelativeVo();
//                calculatorRelativeVo.setId(item.getId());
//                calculatorRelativeVo.setProjectName(item.getProjectName());
//                calculatorRelativeVo.setProjectManager(item.getProjectManager());
//                calculatorRelativeVo.setTotalCost(item.getTotalCost());
//                calculatorRelativeVo.setAnnualizedReturn(BigDecimal.ZERO);
//                calculatorRelativeVo.setDisposalPeriod(BigDecimal.ZERO);
//                calculatorRelativeVo.setEstimatedIncome(BigDecimal.ZERO);
//                calculatorRelativeVo.setClosedFlag(item.getClosedFlag());
//                calculatorRelativeVo.setType("0");
//                resultList.add(calculatorRelativeVo);
//            }
//        } else if ("0".equals(req.getType())) {
//            TDebtCalculatorCondition     condition                    = new TDebtCalculatorCondition();
//            condition.setProjectName(req.getProjectName());
//            condition.setExtBindId(req.getExtBindId());
//            List<TDebtCalculator> tDebtCalculators = debtCalculatorDao.selectList(condition);
//            for (TDebtCalculator item : tDebtCalculators) {
//                calculatorRelativeVo = new CalculatorRelativeVo();
//                calculatorRelativeVo.setId(item.getId());
//                calculatorRelativeVo.setProjectName(item.getProjectName());
//                calculatorRelativeVo.setProjectManager(item.getProjectManager());
//                calculatorRelativeVo.setTotalCost(item.getTotalCost());
//                calculatorRelativeVo.setAnnualizedReturn(item.getAnnualizedReturn());
//                calculatorRelativeVo.setDisposalPeriod(item.getDisposalPeriod());
//                calculatorRelativeVo.setEstimatedIncome(item.getEstimatedIncome());
//                calculatorRelativeVo.setClosedFlag(item.getClosedFlag());
//                calculatorRelativeVo.setType("1");
//                resultList.add(calculatorRelativeVo);
//            }
//        }
//        return ResponseModel.succ(resultList);
    }

    @Override
    public ResponseModel getCalculatorQueryCondition(CalculatorReq.CalculatorQueryConditionReq req) {

        CalculatorQueryConditionVo calculatorQueryConditionVo = new CalculatorQueryConditionVo();
        if ("0".equals(req.getType())) {
            TPrincipalInterestCalculatorCondition condition = new TPrincipalInterestCalculatorCondition();
            condition.setExtBindId(req.getExtBindId());
            if (req.getBroadcastFlag() != null && req.getBroadcastFlag()) {
                condition.setPayFlag(true);
            }
            condition.setIsDelete(false);
            List<TPrincipalInterestCalculator> tPrincipalInterestCalculators = principalInterestCalculatorDao.selectList(condition);
            for (TPrincipalInterestCalculator item : tPrincipalInterestCalculators) {
                if (StringUtils.isNotBlank(item.getProjectManager())) {
                    calculatorQueryConditionVo.getProjectManagerList().add(item.getProjectManager());
                }
            }
        } else if ("1".equals(req.getType())) {
            TDebtCalculatorCondition condition = new TDebtCalculatorCondition();
            condition.setExtBindId(req.getExtBindId());
            if (req.getBroadcastFlag() != null && req.getBroadcastFlag()) {
                condition.setPayFlag(true);
            }
            condition.setIsDelete(false);
            List<TDebtCalculator> tDebtCalculators = debtCalculatorDao.selectList(condition);
            for (TDebtCalculator item : tDebtCalculators) {
                if (StringUtils.isNotBlank(item.getProjectManager())) {
                    calculatorQueryConditionVo.getProjectManagerList().add(item.getProjectManager());
                }
            }
        }
        return ResponseModel.succ(calculatorQueryConditionVo);
    }

    @Override
    public ResponseModel getRelativeBroadcastList(CalculatorReq.QueryRelativeListReq req) {
        PageInfoResp<CalculatorBroadcast> resp     = new PageInfoResp<>();
        PageInfo<CalculatorBroadcast>     pageInfo = new PageInfo<>();
        if ("1".equals(req.getType())) {
            pageInfo = debtCalculatorDao.getMyBroadcastListV2(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        } else if ("0".equals(req.getType())) {
            pageInfo = principalInterestCalculatorDao.getMyBroadcastList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req));
        }
        List<CalculatorBroadcast> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(pageInfo.getList())) {
            for (CalculatorBroadcast item : pageInfo.getList()) {
                if (CalculatorEnum.Type.PrincipalInterest.getKey().equals(item.getType())) {
                    TPrincipalInterestCalculatorBroadcast broadcast = principalInterestCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                    if (broadcast == null) {
                        continue;
                    }
                    item.setTodayInterest(broadcast.getTodayInterest());
                    item.setEstimatedIncome(broadcast.getIncome());
                    item.setReadFlag(broadcast.getReadFlag());
                } else if (CalculatorEnum.Type.Debt.getKey().equals(item.getType())) {
                    TDebtCalculatorBroadcast broadcast = debtCalculatorBroadcastDao.findLatestByCalculatorId(item.getId());
                    if (broadcast == null) {
                        continue;
                    }
                    item.setAnnualizedReturn(broadcast.getAnnualizedReturn());
                    item.setRemainIncome(broadcast.getIncome());
                    item.setReadFlag(broadcast.getReadFlag());
                }
                resultList.add(item);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return ResponseModel.succ(resp);
//        List<CalculatorRelativeVo> resultList = new ArrayList<>();
//        CalculatorRelativeVo       calculatorRelativeVo;
//        if ("1".equals(req.getType())) {
//            TPrincipalInterestCalculatorCondition condition       = new TPrincipalInterestCalculatorCondition();
//            condition.setExtBindId(req.getExtBindId());
//            condition.setPayFlag(true);
//            condition.setClosedFlag(false);
//            List<TPrincipalInterestCalculator> tPrincipalInterestCalculators = principalInterestCalculatorDao.selectList(condition);
//            for (TPrincipalInterestCalculator item : tPrincipalInterestCalculators) {
//                calculatorRelativeVo = new CalculatorRelativeVo();
//                calculatorRelativeVo.setId(item.getId());
//                calculatorRelativeVo.setProjectName(item.getProjectName());
//                calculatorRelativeVo.setProjectManager(item.getProjectManager());
//                calculatorRelativeVo.setTotalCost(item.getTotalCost());
//                calculatorRelativeVo.setAnnualizedReturn(BigDecimal.ZERO);
//                calculatorRelativeVo.setDisposalPeriod(BigDecimal.ZERO);
//                calculatorRelativeVo.setEstimatedIncome(BigDecimal.ZERO);
//                calculatorRelativeVo.setClosedFlag(item.getClosedFlag());
//                calculatorRelativeVo.setType("0");
//                resultList.add(calculatorRelativeVo);
//            }
//        } else if ("0".equals(req.getType())) {
//            TDebtCalculatorCondition     condition                    = new TDebtCalculatorCondition();
//            condition.setExtBindId(req.getExtBindId());
//            condition.setPayFlag(true);
//            condition.setClosedFlag(false);
//            List<TDebtCalculator> tDebtCalculators = debtCalculatorDao.selectList(condition);
//            for (TDebtCalculator item : tDebtCalculators) {
//                calculatorRelativeVo = new CalculatorRelativeVo();
//                calculatorRelativeVo.setId(item.getId());
//                calculatorRelativeVo.setProjectName(item.getProjectName());
//                calculatorRelativeVo.setProjectManager(item.getProjectManager());
//                calculatorRelativeVo.setTotalCost(item.getTotalCost());
//                calculatorRelativeVo.setAnnualizedReturn(item.getAnnualizedReturn());
//                calculatorRelativeVo.setDisposalPeriod(item.getDisposalPeriod());
//                calculatorRelativeVo.setEstimatedIncome(item.getEstimatedIncome());
//                calculatorRelativeVo.setClosedFlag(item.getClosedFlag());
//                calculatorRelativeVo.setType("1");
//                resultList.add(calculatorRelativeVo);
//            }
//        }
//        return ResponseModel.succ(resultList);

    }

    @Override
    public ResponseModel dataFix() {

        TPrincipalInterestCalculatorCondition condition1 = new TPrincipalInterestCalculatorCondition();
//        condition1.setPayFlag(true);
        condition1.setIsDelete(false);
        List<TPrincipalInterestCalculator> tPrincipalInterestCalculators = principalInterestCalculatorDao.selectList(condition1);
        for (TPrincipalInterestCalculator item : tPrincipalInterestCalculators) {
            processsPrincipalInterest(item);
        }

        TDebtCalculatorCondition condition2 = new TDebtCalculatorCondition();
//        condition2.setPayFlag(true);
        condition2.setIsDelete(false);
        List<TDebtCalculator> tDebtCalculators = debtCalculatorDao.selectList(condition2);
        for (TDebtCalculator item : tDebtCalculators) {
            processsDebt(item);
        }
        return ResponseModel.succ();
    }

    private void processsDebt(TDebtCalculator req) {
        try {
            //债权本金
            BigDecimal debtPrincipal = req.getDebtPrincipal();
            // 债权利息
            BigDecimal debtInterest = req.getDebtInterest();
            //债权最高额
            BigDecimal maximumDebt = req.getMaximumDebt();
            //清收目标金额
            BigDecimal liquidationAmount = req.getLiquidationAmount();
            //转让价格
            BigDecimal transferAmount = req.getTransferAmount();
            //自有资金GP
            BigDecimal gpAmount = req.getGpAmount();
            //需要配资LP金额
            BigDecimal lpAmount = req.getLpAmount();
            // 尽调成本
            BigDecimal dueDiligenceAmount = req.getDueDiligenceAmount();
            // 杂费
            BigDecimal miscAmount = req.getMiscAmount();

            //需求金额=转让价-自有金额
            //实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
            //资金成本=需求金额*配资成本费率（年）/12*配资周期（月）

            //  资金成本=(转让价-自有金额)*配资成本费率（年）/12*配资周期（月）

            BigDecimal withFundingAmount = transferAmount.subtract(gpAmount)
                    .multiply(req.getWithFundingAnnualizedRate())
                    .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .divide(new BigDecimal(12), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(req.getWithFundingPeriod());

            //处置成本=清收目标*处置成本费率
            BigDecimal disposalAmount = liquidationAmount
                    .multiply(req.getDisposalAnnualizedRate())
                    .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN);


            // 实际收益金额=清收目标-资金成本-处置成本-尽调成本-杂费-转让价
            BigDecimal estimatedIncome = liquidationAmount
                    .subtract(withFundingAmount)
                    .subtract(disposalAmount)
                    .subtract(dueDiligenceAmount)
                    .subtract(miscAmount)
                    .subtract(transferAmount);

            //劣后资金年收益率=实际收益金额/自有资金/处置周期（月）*12
            BigDecimal annualizedReturn = estimatedIncome
                    .divide(gpAmount.equals(BigDecimal.ZERO) ? BigDecimal.ONE : gpAmount, BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .divide(req.getDisposalPeriod().equals(BigDecimal.ZERO) ? BigDecimal.ONE : req.getDisposalPeriod(), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                    .multiply(new BigDecimal("12"));

            // 成本小计=资金成本+处置成本+尽调成本+杂费
            BigDecimal totalCost = withFundingAmount
                    .add(disposalAmount)
                    .add(dueDiligenceAmount)
                    .add(miscAmount);

            req.setWithFundingAmount(withFundingAmount);
            req.setDisposalAmount(disposalAmount);
            req.setAnnualizedReturn(annualizedReturn.multiply(new BigDecimal(100)));
            req.setEstimatedIncome(estimatedIncome);
            req.setTotalCost(totalCost);
            req.setDueDiligenceAmount(dueDiligenceAmount);
            req.setMiscAmount(miscAmount);
            int result = debtCalculatorDao.updateById(req);
        } catch (Exception e) {
            log.error("processsDebt报错:{}", req);
        }

    }

    private void processsPrincipalInterest(TPrincipalInterestCalculator req) {

        try {
            //贷款期限
            BigDecimal loanPeriod = req.getLoanPeriod();
            //罚息比例
            BigDecimal penaltyRate = req.getPenaltyRate();
            //浮动利率
            BigDecimal                       fluctuationRate = req.getFluctuationRate();
            List<TBankBenchmarkInterestRate> list            = benchmarkInterestRateDao.getByDateDuration(req.getOverdueStart(), req.getOverdueEnd());

            if (CollectionUtils.isNotEmpty(list)) {
                list.get(0).setStartDate(req.getOverdueStart());
                list.get(list.size() - 1).setEndDate(req.getOverdueEnd());
            }
            List<TPrincipalInterestCalculatorDetail> overdueDetails  = new ArrayList<>();
            BigDecimal                               overdueInterest = BigDecimal.ZERO;
            for (TBankBenchmarkInterestRate item : list) {
                TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
                detail.setStartDate(item.getStartDate());
                detail.setEndDate(item.getEndDate());
                BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
                detail.setType("0");
                // 逾息利息=剩余本金*基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）/360*逾期时间天数（终止日期-起始日期）
                int overdueDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
                // 基准利率年利率*（1+上浮/下降比率）*（1+加收的罚息比率）
                BigDecimal actualInterestRate = benchmarkInterestRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN).
                        multiply(new BigDecimal(1)
                                .add(fluctuationRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)))
                        .multiply(new BigDecimal(1)
                                .add(penaltyRate.divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)));

                BigDecimal interest = req.getRemainPrincipal()
                        .multiply(actualInterestRate)
                        .divide(new BigDecimal(360), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                        .multiply(new BigDecimal(overdueDays));
                detail.setActualInterestRate(actualInterestRate);
                detail.setInterest(interest);
                detail.setBenchmarkInterestRate(benchmarkInterestRate);
                overdueDetails.add(detail);

                overdueInterest = overdueInterest.add(interest);
            }

            List<TPrincipalInterestCalculatorDetail> delayDetails = new ArrayList<>();

            //延迟履行金
            BigDecimal delayPerformanceAmount = BigDecimal.ZERO;
            // 如果延迟履行期没有填写，则没有延迟履行金
            if (req.getDelayPerformanceStart() != null && req.getDelayPerformanceEnd() != null && req.getDelayPerformanceStart().compareTo(req.getDelayPerformanceEnd()) <= 0) {

                if (CalculatorEnum.BenchmarkInterestRateSource.Bank.getKey().equals(req.getBenchmarkInterestRateSource())) {
                    List<TBankBenchmarkInterestRate> delayHits = benchmarkInterestRateDao.getByDateDuration(req.getDelayPerformanceStart(), req.getDelayPerformanceEnd());
                    if (CollectionUtils.isNotEmpty(delayHits)) {
                        delayHits.get(0).setStartDate(req.getDelayPerformanceStart());
                        delayHits.get(delayHits.size() - 1).setEndDate(req.getDelayPerformanceEnd());
                    }
                    for (TBankBenchmarkInterestRate item : delayHits) {
                        TPrincipalInterestCalculatorDetail detail = new TPrincipalInterestCalculatorDetail();
                        detail.setStartDate(item.getStartDate());
                        detail.setEndDate(item.getEndDate());
                        BigDecimal benchmarkInterestRate = item.getRate(loanPeriod);
                        detail.setType("1");

                        int delayDays = DateUtil.getMargin(detail.getEndDate(), detail.getStartDate());
                        // 国家利率按2倍基准利率计算，迟延履行金=剩余本金*基准利率年利率x2/360*迟延履行期天数（终止日期-起始日期）
                        BigDecimal actualInterestRate = benchmarkInterestRate
                                .divide(new BigDecimal(100), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(new BigDecimal(2));

                        BigDecimal interest = req.getRemainPrincipal()
                                .multiply(actualInterestRate).divide(new BigDecimal("360"), BIG_DECIMAL_SCALE, BigDecimal.ROUND_HALF_DOWN)
                                .multiply(new BigDecimal(delayDays));

                        detail.setActualInterestRate(actualInterestRate);
                        detail.setInterest(interest);
                        detail.setBenchmarkInterestRate(benchmarkInterestRate);

                        delayDetails.add(detail);
                        delayPerformanceAmount = delayPerformanceAmount.add(interest);
                    }
                } else {
                    // 法院利率按0.175‰利率计算，迟延履行金=剩余本金*1.75/10000*迟延履行期天数（终止日期-起始日期）
                    int delayPerformanceDays = DateUtil.getMargin(req.getDelayPerformanceEnd(), req.getDelayPerformanceStart());
                    delayPerformanceAmount = req.getRemainPrincipal().multiply(new BigDecimal("1.75")).divide(new BigDecimal("10000")).multiply(new BigDecimal(delayPerformanceDays));
                }
            }
            BigDecimal totalCost = req.getDebtInterest().add(overdueInterest).add(delayPerformanceAmount);
            req.setDelayPerformanceAmount(delayPerformanceAmount);
            req.setTotalCost(totalCost);
            req.setOverdueInterest(overdueInterest);
            req.setPenaltyRate(penaltyRate);
            int result = principalInterestCalculatorDao.updateById(req);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }

            overdueDetails.addAll(delayDetails);
            overdueDetails.forEach(item -> {
                item.setCalculatorId(req.getId());
                item.setActualInterestRate(item.getActualInterestRate().multiply(new BigDecimal(100)));
                int insertResult = principalInterestCalculatorDetailDao.insert(item);
                if (insertResult == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
            });
        } catch (Exception e) {
            log.error("req报错:{}", req);
        }


    }
}
