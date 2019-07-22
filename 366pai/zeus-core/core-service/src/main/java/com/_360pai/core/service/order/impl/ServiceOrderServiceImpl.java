package com._360pai.core.service.order.impl;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.order.TServiceBusinessRecordCondition;
import com._360pai.core.condition.order.TServiceOrderCondition;
import com._360pai.core.dao.assistant.TActivityServiceProviderDao;
import com._360pai.core.dao.comprador.TCompradorRequirementDao;
import com._360pai.core.dao.disposal.TDisposalRequirementDao;
import com._360pai.core.dao.disposal.TDisposeShowDao;
import com._360pai.core.dao.order.TServiceAdjustedRecordDao;
import com._360pai.core.dao.order.TServiceBusinessRecordDao;
import com._360pai.core.dao.order.TServiceOrderDao;
import com._360pai.core.dao.withfudig.TWithfudigRequirementDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.core.model.order.TServiceOrder;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.core.service.account.DisposeService;
import com._360pai.core.service.disposal.DisposalRequirementService;
import com._360pai.core.service.finance.ServiceAccountMoneyService;
import com._360pai.core.service.order.ServiceOrderService;
import com._360pai.core.utils.ServiceMessageUtils;
import com._360pai.gateway.common.dfftpay.PayEnums;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/11 16:50
 */
@Service
public class ServiceOrderServiceImpl implements ServiceOrderService {
    @Autowired
    private TServiceOrderDao           tServiceOrderDao;
    @Autowired
    private TCompradorRequirementDao   tCompradorRequirementDao;
    @Autowired
    private TWithfudigRequirementDao   tWithfudigRequirementDao;
    @Autowired
    private TDisposalRequirementDao    tDisposalRequirementDao;
    @Autowired
    private TServiceAdjustedRecordDao  tServiceAdjustedRecordDao;
    @Autowired
    private TServiceBusinessRecordDao  tServiceBusinessRecordDao;
    @Autowired
    private ServiceAccountMoneyService serviceAccountMoneyService;
    @Autowired
    private ServiceMessageUtils        serviceMessageUtils;
    @Autowired
    private DisposalRequirementService disposalRequirementService;
    @Autowired
    private TDisposeShowDao            disposeShowDao;
    @Autowired
    private DisposeService             disposeService;
    @Autowired
    private GatewayMqSender            mqSender;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private TActivityServiceProviderDao activityServiceProviderDao;

    @Override
    public boolean adjustedPayStatus(Long accountId, Integer activityId) {
        if (accountId == null || activityId == null) {
            return false;
        }
        TServiceOrderCondition condition = new TServiceOrderCondition();
        condition.setAccountId(accountId);
        condition.setBusId(activityId.toString());
        condition.setOrderType(ServiceOrderEnum.OrderType.ADJUSTED.getValue());
        condition.setPayStatus(PayEnums.PAY_QUERY_STATUS.SUCCESS.getTypeName());
        return CollectionUtils.isNotEmpty(tServiceOrderDao.selectList(condition));
    }

    @Override
    public Integer insert(TServiceOrder tServiceOrder) {
        tServiceOrderDao.insert(tServiceOrder);
        return tServiceOrder.getId();
    }

    @Override
    public Integer update(TServiceOrder serviceOrder) {
        return tServiceOrderDao.updateById(serviceOrder);
    }

    @Override
    public TServiceOrder selectById(Integer serviceOrderId) {
        return tServiceOrderDao.selectById(serviceOrderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int doProcess(Integer serviceOrderId, String code, String msg) {
        TServiceOrder tServiceOrder = tServiceOrderDao.selectById(serviceOrderId);
        // "code":"500001","desc":"未支付"

        int count = 0;
        // 如果是支付成功，则处理成功逻辑
        if (PayResultEnums.PAY_SUCCESS.getCode().equals(code)) {
            // 如果之前未处理过，则运行成功逻辑
            if (!PayResultEnums.PAY_SUCCESS.getCode().equals(tServiceOrder.getPayStatus())) {
                count += doSuccess(tServiceOrder);
                tServiceOrder.setPayStatus(code);
                tServiceOrder.setMsg(msg);
                count += tServiceOrderDao.updateById(tServiceOrder);
            }
        } else {
            count += tServiceOrderDao.updateById(tServiceOrder);
        }
        return count;
    }

    private int doSuccess(TServiceOrder tServiceOrder) {
        int count = 0;
        if (ServiceOrderEnum.OrderType.COMPRADOR_REQUIREMENT.getValue().equals(tServiceOrder.getOrderType())) {
            TCompradorRequirement tCompradorRequirement = tCompradorRequirementDao.selectByIdWithoutPay(Integer.valueOf(tServiceOrder.getBusId()));
            tCompradorRequirement.setPayId(tServiceOrder.getId());
            tCompradorRequirement.setPayTime(new Date());
            tCompradorRequirement.setRequirementStatus(CompradorEnum.RequirementStatus.PLACEMENT.getValue().toString());
            count += tCompradorRequirementDao.updateById(tCompradorRequirement);
            // 通知前置 发送通知
//            serviceMessageUtils.compradorAdd(tCompradorRequirement.getId());
        } else if (ServiceOrderEnum.OrderType.WITHFUDIG_REQUIREMENT.getValue().equals(tServiceOrder.getOrderType())) {
            TWithfudigRequirement tWithfudigRequirement = tWithfudigRequirementDao.selectByIdWithoutPay(Integer.valueOf(tServiceOrder.getBusId()));
            tWithfudigRequirement.setPayId(tServiceOrder.getId().toString());
            tWithfudigRequirement.setPayTime(new Date());

            // 如果是平台的，直接是配资中状态
            tWithfudigRequirement.setRequirementStatus(tWithfudigRequirement.getIsPlatform() ?
                    WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getValue().toString() :
                    WithfudigEnum.RequirementStatus.BEGINNING.getValue().toString()
            );

            count += tWithfudigRequirementDao.updateById(tWithfudigRequirement);
            // 通知前置 发送通知
//            serviceMessageUtils.withfudigAdd(tWithfudigRequirement.getId());
        } else if (ServiceOrderEnum.OrderType.DIPOSAL_REQUIREMENT.getValue().equals(tServiceOrder.getOrderType())) {
            String   payBusCode = tServiceOrder.getPayBusCode();
            String[] ids        = payBusCode.split(",");
            for (String id : ids) {
                TDisposalRequirement tDisposalRequirement = tDisposalRequirementDao.selectByIdWithoutPay(id);
                tDisposalRequirement.setPayOrder(tServiceOrder.getId().toString());
                tDisposalRequirement.setDisposalStatus(DisposalEnum.RequirementStatus.BEGINNING.getValue());
                count += tDisposalRequirementDao.updateById(tDisposalRequirement);
                // 通知前置，在提交时候提醒
//                addDisposalSendMessage(Integer.valueOf(id));
                // 支付成功，设置截至日期
                setDisposalDeadline(Integer.valueOf(id));
            }
        } else if (ServiceOrderEnum.OrderType.ADJUSTED.getValue().equals(tServiceOrder.getOrderType())) {
            TServiceAdjustedRecord adjustedRecord = createAdjustedRecord(tServiceOrder);
            count += tServiceAdjustedRecordDao.insert(adjustedRecord);
            serviceAccountMoneyService.addAvailableAmount(adjustedRecord.getPartyId(), adjustedRecord.getAdjustedAmount());
        } else if (ServiceOrderEnum.OrderType.REPORT_BASIS.getValue().equals(tServiceOrder.getOrderType())) {
            TServiceAdjustedRecord basisReportRecord = createReportRecord(tServiceOrder, ServiceOrderEnum.OrderType.REPORT_BASIS);
            count += tServiceAdjustedRecordDao.insert(basisReportRecord);
            serviceAccountMoneyService.addAvailableAmount(basisReportRecord.getPartyId(), basisReportRecord.getAdjustedAmount());
        } else if (ServiceOrderEnum.OrderType.REPORT_COMPLETE.getValue().equals(tServiceOrder.getOrderType())) {
            TServiceAdjustedRecord completeReportRecord = createReportRecord(tServiceOrder, ServiceOrderEnum.OrderType.REPORT_COMPLETE);
            count += tServiceAdjustedRecordDao.insert(completeReportRecord);
            serviceAccountMoneyService.addAvailableAmount(completeReportRecord.getPartyId(), completeReportRecord.getAdjustedAmount());
        } else if (ServiceOrderEnum.OrderType.DISPOSAL_WANT_SHOW.getValue().equals(tServiceOrder.getOrderType())) {
            /*
                修改购买记录状态
                生成我要处置记录
             */
            updateBusinessStatus(tServiceOrder);
            count += createDisposeShow(tServiceOrder);
        }

        return count;
    }

//    private void addDisposalSendMessage(Integer id) {
//        TDisposalRequirement requirementDetail = disposalRequirementService.findRequirementDetail(id);
//        // 需求单生成发信息
//        serviceMessageUtils.disposalRequirementAdd(id);
//        // 设置过期时间处置key
//        long timeout = (requirementDetail.getDeadline().getTime() - System.currentTimeMillis()) / 1000;
//        mqSender.disposalDeadlineEnqueue(id + "", timeout);
//    }

    private void setDisposalDeadline(Integer id) {
        TDisposalRequirement requirementDetail = disposalRequirementService.findRequirementDetail(id);
        // 设置过期时间处置key
        long timeout = (requirementDetail.getDeadline().getTime() - System.currentTimeMillis()) / 1000;
        //mqSender.disposalDeadlineEnqueue(id + "", timeout);
        if (timeout > 0) {
            redisCachemanager.set(SystemConstant.DISPOSAL_DEADLINE_SMS + id + "", id + "", timeout);
        }
    }

    private TServiceAdjustedRecord createAdjustedRecord(TServiceOrder tServiceOrder) {
        TServiceBusinessRecordCondition condition = new TServiceBusinessRecordCondition();
        condition.setOrderNum(tServiceOrder.getOrderNum());
        // 获取订单业务记录
        TServiceBusinessRecord record = tServiceBusinessRecordDao.selectOneResult(condition);
        record.setPayStatus("10");
        tServiceBusinessRecordDao.updateById(record);
        TServiceAdjustedRecord adjustedRecord = new TServiceAdjustedRecord();
        adjustedRecord.setAccountId(record.getSellerAccountId());
        adjustedRecord.setPartyId(record.getSellerPartyId());
        adjustedRecord.setAdjustedRate(ServiceOrderEnum.ADJUSTED_RATE);
        adjustedRecord.setAmount(tServiceOrder.getAmount());
        adjustedRecord.setOrderNum(tServiceOrder.getOrderNum());
        adjustedRecord.setOrderType(ServiceOrderEnum.OrderType.ADJUSTED.getValue());
        adjustedRecord.setOrderTime(tServiceOrder.getCreateTime());
        adjustedRecord.setAdjustedAmount(tServiceOrder.getAmount().multiply(new BigDecimal(ServiceOrderEnum.ADJUSTED_RATE))
                .setScale(4, RoundingMode.HALF_UP));
        return adjustedRecord;
    }

    private TServiceAdjustedRecord createReportRecord(TServiceOrder tServiceOrder, ServiceOrderEnum.OrderType orderType) {
        TServiceBusinessRecordCondition condition = new TServiceBusinessRecordCondition();
        condition.setOrderNum(tServiceOrder.getOrderNum());
        // 获取订单业务记录
        TServiceBusinessRecord record = tServiceBusinessRecordDao.selectOneResult(condition);
        record.setPayStatus("10");
        tServiceBusinessRecordDao.updateById(record);
        TServiceAdjustedRecord adjustedRecord = new TServiceAdjustedRecord();
        adjustedRecord.setAccountId(record.getSellerAccountId());
        adjustedRecord.setPartyId(record.getSellerPartyId());
        adjustedRecord.setAmount(tServiceOrder.getAmount());
        adjustedRecord.setOrderNum(tServiceOrder.getOrderNum());
        adjustedRecord.setOrderTime(tServiceOrder.getCreateTime());
        if (ServiceOrderEnum.OrderType.REPORT_BASIS.equals(orderType)) {
            adjustedRecord.setAdjustedRate(ServiceOrderEnum.BASIS_REPORT_RATE);
            adjustedRecord.setOrderType(ServiceOrderEnum.OrderType.REPORT_BASIS.getValue());
            adjustedRecord.setAdjustedAmount(tServiceOrder.getAmount().multiply(new BigDecimal(ServiceOrderEnum.BASIS_REPORT_RATE))
                    .setScale(4, RoundingMode.HALF_UP));
        }

        if (ServiceOrderEnum.OrderType.REPORT_COMPLETE.equals(orderType)) {
            adjustedRecord.setAdjustedRate(ServiceOrderEnum.COMPLETE_REPORT_RATE);
            adjustedRecord.setOrderType(ServiceOrderEnum.OrderType.REPORT_COMPLETE.getValue());
            adjustedRecord.setAdjustedAmount(tServiceOrder.getAmount().multiply(new BigDecimal(ServiceOrderEnum.COMPLETE_REPORT_RATE))
                    .setScale(4, RoundingMode.HALF_UP));
        }
        return adjustedRecord;
    }

    private void updateBusinessStatus(TServiceOrder tServiceOrder) {
        TServiceBusinessRecordCondition condition = new TServiceBusinessRecordCondition();
        condition.setOrderNum(tServiceOrder.getOrderNum());
        // 获取订单业务记录
        TServiceBusinessRecord record = tServiceBusinessRecordDao.selectOneResult(condition);
        record.setPayStatus("10");
        tServiceBusinessRecordDao.updateById(record);
    }

    @Transactional
    private int createDisposeShow(TServiceOrder tServiceOrder) {
        TDisposeShow     disposeShow = new TDisposeShow();
        TDisposeProvider provider    = disposeService.getDisposeProviderByPartyId(tServiceOrder.getPartyId().intValue());
        if (StringUtils.isNotBlank(provider.getRegion())) {
            disposeShow.setCityId(Integer.valueOf(provider.getRegion()));
        }
        disposeShow.setActivityId(Integer.valueOf(tServiceOrder.getBusId()));
        disposeShow.setProviderId(provider.getId());
        int insert = disposeShowDao.insert(disposeShow);
        if (insert == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TActivityServiceProvider activityServiceProvider = new TActivityServiceProvider();
        activityServiceProvider.setActivityId(Integer.valueOf(tServiceOrder.getBusId()));
        activityServiceProvider.setActivityType(ActivityServiceProviderEnum.ActivityType.Auction.getKey());
        activityServiceProvider.setProviderType(ActivityServiceProviderEnum.ProviderType.Dispose.getKey());
        activityServiceProvider.setProviderId(provider.getId());
        activityServiceProvider.setSource(ActivityServiceProviderEnum.Source.Web.getKey());
        insert = activityServiceProviderDao.insert(activityServiceProvider);
        if (insert == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return insert;
    }

}
