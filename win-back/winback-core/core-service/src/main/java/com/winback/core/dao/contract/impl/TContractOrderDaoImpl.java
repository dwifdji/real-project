
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.DateUtil;
import com.winback.arch.common.utils.RandomNumberGenerator;
import com.winback.core.commons.constants.ContractEnum;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.contract.TContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractOrderCondition;
import com.winback.core.dao.contract.mapper.TContractOrderMapper;
import com.winback.core.model.contract.TContractOrder;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractOrderDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class TContractOrderDaoImpl extends AbstractDaoImpl<TContractOrder, TContractOrderCondition, BaseMapper<TContractOrder, TContractOrderCondition>> implements TContractOrderDao {

    @Resource
    private TContractOrderMapper tContractOrderMapper;

    @Override
    protected BaseMapper<TContractOrder, TContractOrderCondition> daoSupport() {
        return tContractOrderMapper;
    }

    @Override
    protected TContractOrderCondition blankCondition() {
        return new TContractOrderCondition();
    }

    @Override
    public TContractOrder createOrder(Integer accountId, ContractEnum.OrderSource orderSource, BigDecimal amount) {
        TContractOrder order = new TContractOrder();
        order.setAccountId(accountId);
        order.setAmount(amount);
        order.setOrderNo(RandomNumberGenerator.generatorContractOrderNum(7));
        order.setPayDeadline(DateUtil.nextMinute(30));
        order.setPayStatus(ContractEnum.PayStatus.INIT.getKey());
        order.setOrderSource(orderSource.getKey());
        int result = tContractOrderMapper.insert(order);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return order;
    }

    @Override
    public PageInfo<TContractOrder> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<TContractOrder> list = tContractOrderMapper.getFrontList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<TContractOrder> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<TContractOrder> list = tContractOrderMapper.getBackgroundList(params);
        return new PageInfo<>(list);
    }

    @Override
    public boolean hasInvoice(Integer orderId) {
        return tContractOrderMapper.hasInvoice(orderId);
    }

    @Override
    public TContractOrder findBy(String orderNo) {
        TContractOrderCondition condition = new TContractOrderCondition();
        condition.setOrderNo(orderNo);
        List<TContractOrder> list = tContractOrderMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> getSummaryInfo(Map<String, Object> params) {
        return tContractOrderMapper.getSummaryInfo(params);
    }

    @Override
    public List<TContract> getContractList(Integer orderId) {
        return tContractOrderMapper.getContractList(orderId);
    }

    @Override
    public List<TContractOrder> getInvoiceOrderList(Map<String, Object> params) {
        return tContractOrderMapper.getInvoiceOrderList(params);
    }
}
