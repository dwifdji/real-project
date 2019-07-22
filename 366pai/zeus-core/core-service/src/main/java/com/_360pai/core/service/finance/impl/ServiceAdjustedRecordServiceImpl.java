package com._360pai.core.service.finance.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.condition.order.TServiceAdjustedRecordCondition;
import com._360pai.core.dao.order.TServiceAdjustedRecordDao;
import com._360pai.core.facade.finance.resp.AdjustedRecordDTO;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.core.service.finance.ServiceAdjustedRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-09-29 18:51
 */
@Service
public class ServiceAdjustedRecordServiceImpl implements ServiceAdjustedRecordService {

    @Autowired
    private TServiceAdjustedRecordDao tServiceAdjustedRecordDao;

    @Override
    public PageInfoResp<AdjustedRecordDTO> getAdjustedRecordByPartyIdAndStatus(Integer partyId, int adjustedStatus,
                                                                               int pageNum, int pageSize) {

        TServiceAdjustedRecordCondition condition = new TServiceAdjustedRecordCondition();
        condition.setPartyId(partyId);
        condition.setAdjustedStatus(adjustedStatus);
        PageHelper.startPage(pageNum, pageSize);
        List<TServiceAdjustedRecord> recordList = tServiceAdjustedRecordDao.selectList(condition);
        PageInfo<TServiceAdjustedRecord> pageInfo = new PageInfo<>(recordList);
        return getPageInfoResp(pageInfo, convertAdjustedRecordToDTO(recordList));
    }

    @Override
    public PageInfoResp<AdjustedRecordDTO> getAdjustedRecordByWithdrawNo(String withdrawNo, int pageNum, int pageSize) {
        TServiceAdjustedRecordCondition condition = new TServiceAdjustedRecordCondition();
        condition.setWithdrawNo(withdrawNo);
        PageHelper.startPage(pageNum, pageSize);
        List<TServiceAdjustedRecord> recordList = tServiceAdjustedRecordDao.selectList(condition);
        PageInfo<TServiceAdjustedRecord> pageInfo = new PageInfo<>(recordList);
        return getPageInfoResp(pageInfo, convertAdjustedRecordToDTO(recordList));
    }

    private List<AdjustedRecordDTO> convertAdjustedRecordToDTO(List<TServiceAdjustedRecord> recordList) {
        LinkedList<AdjustedRecordDTO> dtoList = new LinkedList<>();
        for (TServiceAdjustedRecord tmp : recordList) {
            AdjustedRecordDTO dto = new AdjustedRecordDTO();
            BeanUtils.copyProperties(tmp, dto);
            dto.setAdjustedId(tmp.getId());
            dto.setOrderTypeDesc(ServiceOrderEnum.OrderType.getKeyByValue(tmp.getOrderType()));
            dtoList.add(dto);
        }
        return dtoList;
    }

    private <T> PageInfoResp getPageInfoResp (PageInfo pageInfo, List<T> list) {
        PageInfoResp<T> pageInfoResp = new PageInfoResp<>();
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setList(list);
        pageInfoResp.setTotal(pageInfo.getTotal());
        return pageInfoResp;
    }

}