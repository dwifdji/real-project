package com._360pai.core.service.finance;

import com._360pai.core.model.order.TServiceWithdrawRecord;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-10-07 14:03
 */
public interface ServiceWithdrawRecordService {

    int addWithdrawRecord(Integer accountId,Integer partyId, Integer bankId, Integer[] adjustedId, String accountType, String accountName, String bankNo);
    PageInfo getWithdrawRecordPage(Map<String, Object> queryParam, int pageNum, int pageSize);
    int updateWithdrawRecord(TServiceWithdrawRecord record);
    PageInfo getAdminWithdrawRecordPage(Map<String, Object> queryParam, int pageNum, int pageSize);
}
