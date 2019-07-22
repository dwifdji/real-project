package com.tzCloud.core.service.impl;


import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.core.common.constants.ServiceOrderEnum;
import com.tzCloud.core.dao.account.TAccountMembershipCardDao;
import com.tzCloud.core.dao.order.TServiceOrderDao;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.core.model.order.TServiceOrder;
import com.tzCloud.core.service.ServiceOrderService;
import com.tzCloud.gateway.common.constants.PayResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
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
    private TServiceOrderDao tServiceOrderDao;
    @Autowired
    private TAccountMembershipCardDao tAccountMembershipCardDao;

    private static SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORM_DATETIME_PATTERN);

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
            try {
                // 如果之前未处理过，则运行成功逻辑
                if (!PayResultEnums.PAY_SUCCESS.getCode().equals(tServiceOrder.getPayStatus())) {
                    count += doSuccess(tServiceOrder);
                    tServiceOrder.setPayStatus(code);
                    tServiceOrder.setMsg(msg);
                    count += tServiceOrderDao.updateById(tServiceOrder);
                }
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
        return count;
    }

    private int doSuccess(TServiceOrder tServiceOrder) throws Exception{
        int count = 0;
        if (tServiceOrder.getOrderType().equals(ServiceOrderEnum.OrderType.MEMBERSHIP_MONTH_FEE.getValue())) {
            count += addMonthMemberShip(tServiceOrder.getAccountId());
        }

        if (tServiceOrder.getOrderType().equals(ServiceOrderEnum.OrderType.MEMBERSHIP_YEAR_FEE.getValue())) {
            count += addYearMemberShip(tServiceOrder.getAccountId());
        }
        return count;
    }


    private int addMonthMemberShip(Integer accountId) {
        TAccountMembershipCard card = new TAccountMembershipCard();
        card.setAccountId(accountId);
        card.setType("M");
        card.setStartTime(new Date());
        card.setEndTime(DateUtil.nextMouth(1));
        return tAccountMembershipCardDao.insert(card);
    }

    private int addYearMemberShip(Integer accountId) {
        TAccountMembershipCard card = new TAccountMembershipCard();
        card.setAccountId(accountId);
        card.setType("Y");
        card.setStartTime(new Date());
        card.setEndTime(DateUtil.nextYear(1));
        return tAccountMembershipCardDao.insert(card);
    }

}
