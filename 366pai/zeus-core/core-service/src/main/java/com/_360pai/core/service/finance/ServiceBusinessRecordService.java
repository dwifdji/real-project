package com._360pai.core.service.finance;

import com._360pai.core.model.order.TServiceBusinessRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-09-29 18:48
 */
public interface ServiceBusinessRecordService {

    int addBusinessRecord(TServiceBusinessRecord record);

    PageInfo<TServiceBusinessRecord> getBusinessRecordByBuyerPartyId(Integer buyerPartyId, int pageNum, int pageSize);

    List<TServiceBusinessRecord> getBusinessRecordByType(Integer buyerPartyId, Integer orderType, Integer assetId);
}
