package com._360pai.core.service.finance.impl;

import com._360pai.arch.common.utils.OrderCodeGenerator;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.common.constants.FinanceEnum;
import com._360pai.core.dao.order.TServiceAdjustedRecordDao;
import com._360pai.core.dao.order.TServiceWithdrawRecordDao;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.core.model.order.TServiceWithdrawRecord;
import com._360pai.core.service.finance.ServiceWithdrawRecordService;
import com._360pai.core.utils.Constant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-10-07 14:26
 */
@Service
public class ServiceWithdrawRecordServiceImpl implements ServiceWithdrawRecordService {

    @Autowired
    private TServiceWithdrawRecordDao tServiceWithdrawRecordDao;
    @Autowired
    private TServiceAdjustedRecordDao tServiceAdjustedRecordDao;

    @Override
    public int addWithdrawRecord(Integer accountId, Integer partyId, Integer bankId, Integer[] adjustedId, String accountType, String accountName, String bankNo) {
        /*
            1.生成提现编号
            2.计算提现金额
            3.生成提现记录
            4.更新分润记录
         */
        String     withdrawNo     = getWithdrawNo();
        BigDecimal withdrawAmount = getWithdrawAmount(adjustedId);
        int        recordId       = createWithdrawRecord(accountId, partyId, withdrawNo, bankId, withdrawAmount, accountType, accountName, bankNo);
        updateAdjustedRecord(adjustedId, withdrawNo);
        return recordId;
    }

    @Override
    public PageInfo getWithdrawRecordPage(Map<String, Object> queryParam, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TServiceWithdrawRecord>     withdrawRecords = tServiceWithdrawRecordDao.selectWithdrawRecordByParam(queryParam);
        PageInfo<TServiceWithdrawRecord> pageInfo        = new PageInfo<>(withdrawRecords);
        return pageInfo;
    }

    @Override
    public int updateWithdrawRecord(TServiceWithdrawRecord record) {
        return tServiceWithdrawRecordDao.updateById(record);
    }

    @Override
    public PageInfo getAdminWithdrawRecordPage(Map<String, Object> queryParam, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TServiceWithdrawRecord>     withdrawRecords = tServiceWithdrawRecordDao.selectAdminWithdrawRecordByParam(queryParam);
        PageInfo<TServiceWithdrawRecord> pageInfo        = new PageInfo<>(withdrawRecords);
        return pageInfo;
    }

    private void updateAdjustedRecord(Integer[] adjustedId, String withdrawNo) {
        List<TServiceAdjustedRecord> recordList = tServiceAdjustedRecordDao.selectAdjustedRecordByIds(adjustedId);
        for (TServiceAdjustedRecord tmp : recordList) {
            tmp.setWithdrawNo(withdrawNo);
            tmp.setAdjustedStatus(Integer.valueOf(FinanceEnum.AdjustedStatusEnum.PENDING.getKey()));
            tServiceAdjustedRecordDao.updateById(tmp);
        }
    }

    private int createWithdrawRecord(Integer accountId, Integer partyId, String withdrawNo, Integer bankId, BigDecimal withdrawAmount, String accountType, String accountName, String bankNo) {
        TServiceWithdrawRecord record = new TServiceWithdrawRecord();
        record.setWithdrawNo(withdrawNo);
        record.setBankId(bankId);
        record.setBankNo(bankNo);
        record.setAccountName(accountName);
        record.setAccountId(accountId);
        record.setPartyId(partyId);
        record.setWithdrawAmount(withdrawAmount);
        record.setAccountType(accountType);
        tServiceWithdrawRecordDao.insert(record);
        return record.getId();
    }

    private BigDecimal getWithdrawAmount(Integer[] adjustedId) {
        BigDecimal                   amount     = BigDecimal.ZERO;
        List<TServiceAdjustedRecord> recordList = tServiceAdjustedRecordDao.selectAdjustedRecordByIds(adjustedId);
        for (TServiceAdjustedRecord tmp : recordList) {
            amount = amount.add(tmp.getAdjustedAmount());
        }
        return amount;
    }

    private String getWithdrawNo() {
        return OrderCodeGenerator.INSTANCE.getOrderCode(Constant.WithdrawCons.TX_PREFIX);
    }
}
