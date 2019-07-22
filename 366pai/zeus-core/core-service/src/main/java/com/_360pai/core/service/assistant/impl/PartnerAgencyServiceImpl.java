package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.assistant.PartnerAgencyCondition;
import com._360pai.core.dao.assistant.PartnerAgencyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.assistant.PartnerAgency;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.assistant.PartnerAgencyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerAgencyServiceImpl implements PartnerAgencyService {

    @Autowired
    private PartnerAgencyDao partnerAgencyDao;
    @Autowired
    private AgencyService agencyService;


    @Override
    public PageInfo selectPartnerAgenciesList(int page, int perPage, String orderNum) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderNum)) {
            PageHelper.orderBy(orderNum);
        }
        List<PartnerAgency> partnerAgencies = partnerAgencyDao.selectAll();
        for (PartnerAgency agency : partnerAgencies) {
            TAgency byAgencyId = agencyService.findByAgencyId(agency.getAgencyId());
            agency.setAgencyName(byAgencyId.getName());
            agency.setCode(byAgencyId.getCode());
            agency.setLogoUrl(byAgencyId.getLogoUrl());
        }
        return new PageInfo<>(partnerAgencies);
    }

    @Override
    public int addPartnerAgency(PartnerAgency params) {
        PartnerAgencyCondition condition = new PartnerAgencyCondition();
        condition.setAgencyId(params.getAgencyId());
        List<PartnerAgency> partnerAgencies = partnerAgencyDao.selectList(condition);
        boolean empty = CollectionUtils.isEmpty(partnerAgencies);
        if (!empty) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "机构已存在");
        }
        return partnerAgencyDao.insert(params);
    }

    @Override
    public int editPartnerAgency(PartnerAgency params) {
        PartnerAgency partnerAgencyById = findPartnerAgencyById(params);
        if (partnerAgencyById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的合作机构不存在");
        }
        if (!params.getAgencyId().equals(partnerAgencyById.getAgencyId())) {
            PartnerAgencyCondition condition = new PartnerAgencyCondition();
            condition.setAgencyId(params.getAgencyId());
            List<PartnerAgency> partnerAgencies = partnerAgencyDao.selectList(condition);
            boolean empty = CollectionUtils.isEmpty(partnerAgencies);
            if (!empty) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "机构已存在");
            }
        }
        return partnerAgencyDao.updateById(params);
    }

    @Override
    public int deletePartnerAgency(PartnerAgency params) {
        PartnerAgency partnerAgencyById = findPartnerAgencyById(params);
        if (partnerAgencyById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除的合作机构不存在");
        }
        return partnerAgencyDao.deletePartnerAgency(params.getId());
    }

    private PartnerAgency findPartnerAgencyById(PartnerAgency params) {
        PartnerAgencyCondition condition = new PartnerAgencyCondition();
        condition.setId(params.getId());
        return partnerAgencyDao.selectOneResult(condition);
    }
}