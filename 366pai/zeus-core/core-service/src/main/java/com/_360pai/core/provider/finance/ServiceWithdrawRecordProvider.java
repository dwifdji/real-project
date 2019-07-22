package com._360pai.core.provider.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.FinanceEnum;
import com._360pai.core.facade.finance.ServiceWithdrawRecordFacade;
import com._360pai.core.facade.finance.req.WithdrawRecordReq;
import com._360pai.core.facade.finance.resp.AdminWithdrawRecordDTO;
import com._360pai.core.facade.finance.resp.WithdrawRecordDTO;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.order.TServiceWithdrawRecord;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.service.finance.ServiceWithdrawRecordService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xiaolei
 * @create 2018-10-07 20:27
 */
@Component
@Service(version = "1.0.0")
public class ServiceWithdrawRecordProvider implements ServiceWithdrawRecordFacade {

    @Autowired
    private ServiceWithdrawRecordService serviceWithdrawRecordService;
    @Autowired
    private StaffService staffService;

    @Override
    public int addWithdrawRecord(WithdrawRecordReq.AddWithdrawRecord req) {
        return serviceWithdrawRecordService.addWithdrawRecord(req.getAccountId(), req.getPartyId(),req.getBankId(), req.getAdjustedId(),
                req.getAccountType(), req.getAccountName(), req.getBankNo());
    }

	@Override
    public PageInfoResp getWithdrawRecordPage(WithdrawRecordReq.GetWithdrawRecord req) {
        Map<String, Object> queryParam = createQueryParam(req);
        PageInfo pageInfo
                = serviceWithdrawRecordService.getWithdrawRecordPage(queryParam, req.getPage(), req.getPerPage());
        List convertToDto = convertToDto(pageInfo.getList());
        return getPageInfoResp(pageInfo, convertToDto);
    }

    @Override
    public int resubmitWithdrawRecord(WithdrawRecordReq.AddWithdrawRecord req) {
        TServiceWithdrawRecord record = new TServiceWithdrawRecord();
        record.setId(req.getWithdrawId());
        record.setBankId(req.getBankId());
        record.setVerifyStatus(FinanceEnum.WithdrawStatusEnum.PENDING.getKey());
        record.setUpdateTime(new Date());
        int i = serviceWithdrawRecordService.updateWithdrawRecord(record);
        return i;
    }

    @Override
    public PageInfoResp getAdminWithdrawRecordPage(WithdrawRecordReq.GetAdminWithdrawRecord req) {
        Map<String, Object> queryParam = createQueryParam2(req);
        PageInfo pageInfo
                = serviceWithdrawRecordService.getAdminWithdrawRecordPage(queryParam, req.getPage(), req.getPerPage());
        List convertToDto = convertToDto2(pageInfo.getList());
        return getPageInfoResp(pageInfo, convertToDto);
    }

    private List<WithdrawRecordDTO> convertToDto(List<TServiceWithdrawRecord> list) {
        List<WithdrawRecordDTO> dtoList = new ArrayList<>();
        for (TServiceWithdrawRecord tmp : list ) {
            WithdrawRecordDTO dto = new WithdrawRecordDTO();
            BeanUtils.copyProperties(tmp, dto);
            dto.setWithdrawId(tmp.getId());
            dto.setVerifyStatusDesc(FinanceEnum.WithdrawStatusEnum.getValueByKey(tmp.getVerifyStatus()));
            dtoList.add(dto);
        }
        return dtoList;
    }

    private List<AdminWithdrawRecordDTO> convertToDto2(List<TServiceWithdrawRecord> list) {
        List<AdminWithdrawRecordDTO> dtoList = new ArrayList<>();
        for (TServiceWithdrawRecord tmp : list ) {
            AdminWithdrawRecordDTO dto = new AdminWithdrawRecordDTO();
            BeanUtils.copyProperties(tmp, dto);
            dto.setWithdrawId(tmp.getId());
            dto.setVerifyStatusDesc(FinanceEnum.WithdrawStatusEnum.getValueByKey(tmp.getVerifyStatus()));
            if (tmp.getVerifyOperator() != null) {
                Staff staff = staffService.getById(tmp.getVerifyOperator());
                dto.setVerifyOperatorName(staff.getName());
            }
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

    private Map<String, Object> createQueryParam(WithdrawRecordReq.GetWithdrawRecord req) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("partyId", req.getPartyId());
        queryParam.put("withdrawNo", req.getWithdrawNo());
        queryParam.put("timeBy", req.getTimeBy());
        queryParam.put("beginDate", req.getBeginDate());
        queryParam.put("endDate", req.getEndDate());
        return queryParam;
    }

    private Map<String, Object> createQueryParam2(WithdrawRecordReq.GetAdminWithdrawRecord req) {
        Map<String, Object> queryParam = new HashMap<>();
        queryParam.put("accountType", req.getAccountType());
        queryParam.put("searchStr", req.getSearchStr());
        queryParam.put("timeBy", req.getTimeBy());
        queryParam.put("beginDate", req.getBeginDate());
        queryParam.put("endDate", req.getEndDate());
        queryParam.put("verifyStatus", req.getVerifyStatus());
        return queryParam;
    }
}
