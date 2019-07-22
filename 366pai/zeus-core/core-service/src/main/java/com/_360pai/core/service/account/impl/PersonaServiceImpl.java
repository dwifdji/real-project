package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.exception.BaseBusinessException;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.PersonaEnum;
import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.facade.account.req.PersonaReq;
import com._360pai.core.facade.account.resp.PersonaDetailResp;
import com._360pai.core.facade.account.resp.PersonaListResp;
import com._360pai.core.facade.account.resp.PersonaResp;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.service.account.PersonaService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author xdrodger
 * @Title: PersonaServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 29/08/2018 13:30
 */
@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private TPersonaDao personaDao;
    @Autowired
    private TPersonaAssetPartyDao personaAssetPartyDao;
    @Autowired
    private TPersonaBidderDao personaBidderDao;
    @Autowired
    private TPersonaDisposalPartyDao personaDisposalPartyDao;
    @Autowired
    private TPersonaFundingAgencyDao personaFundingAgencyDao;
    @Autowired
    private TPersonaIntermediaryOrganDao personaIntermediaryOrganDao;
    @Autowired
    private TPersonaUnionAuctionAgencyDao personaUnionAuctionAgencyDao;
    @Autowired
    private StaffDao staffDao;

    @Autowired
    private RedisCachemanager redisCachemanager;

    private static final String key = "persona_login_";

    private void validateUId(String uId) {
        if (StringUtils.isEmpty(uId)) {
            throw new BaseBusinessException(ApiCallResult.EMPTY);
        }
        String cacheStr = (String) redisCachemanager.hGet(key, uId);
        if (StringUtils.isEmpty(cacheStr)) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
    }

    private Staff getStaff(String uId) {
        if (StringUtils.isEmpty(uId)) {
            throw new BaseBusinessException(ApiCallResult.EMPTY);
        }
        String cacheStr = (String) redisCachemanager.hGet(key, uId);
        if (StringUtils.isEmpty(cacheStr)) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        return JSON.parseObject(cacheStr, Staff.class);
    }

    @Transactional
    @Override
    public PersonaResp createPersona(PersonaReq req) {
        Staff staff = getStaff(req.getuId());
        req.setDataEntryStaff(staff.getMobile());
        PersonaResp resp = new PersonaResp();
        Date now = new Date();
        TPersona persona = ReqConvertUtil.convertToTPersona(req);
        persona.setUpdateTime(now);
        persona.setCreateTime(now);
        int result = personaDao.insert(persona);
        if (result == 0) {
            throw new BaseBusinessException(ApiCallResult.DBERROR);
        }
        for (String customerType : req.getCustomerTypes()) {
            if (PersonaEnum.CustomerType.BIDDER.getCode().equals(customerType)) {
                if (req.getPersonaBidder() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaBidder().setPersonaId(persona.getId());
                TPersonaBidder personaBidder = ReqConvertUtil.convertToTPersonaBidder(req.getPersonaBidder());
                personaBidder.setCreateTime(now);
                personaBidder.setUpdateTime(now);
                result = personaBidderDao.insert(personaBidder);
            } else if (PersonaEnum.CustomerType.ASSET_PARTY.getCode().equals(customerType)) {
                if (req.getPersonaAssetParty() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaAssetParty().setPersonaId(persona.getId());
                TPersonaAssetParty personaAssetParty = ReqConvertUtil.convertToTPersonaAssetParty(req.getPersonaAssetParty());
                personaAssetParty.setCreateTime(now);
                personaAssetParty.setUpdateTime(now);
                result = personaAssetPartyDao.insert(personaAssetParty);
            } else if (PersonaEnum.CustomerType.DISPOSAL_PARTY.getCode().equals(customerType)) {
                if (req.getPersonaDisposalParty() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaDisposalParty().setPersonaId(persona.getId());
                TPersonaDisposalParty personaDisposalParty = ReqConvertUtil.convertToTPersonaDisposalParty(req.getPersonaDisposalParty());
                personaDisposalParty.setCreateTime(now);
                personaDisposalParty.setUpdateTime(now);
                result = personaDisposalPartyDao.insert(personaDisposalParty);
            } else if (PersonaEnum.CustomerType.INTERMEDIARY_ORGAN.getCode().equals(customerType)) {
                if (req.getPersonaIntermediaryOrgan() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaIntermediaryOrgan().setPersonaId(persona.getId());
                TPersonaIntermediaryOrgan personaIntermediaryOrgan = ReqConvertUtil.convertToTPersonaIntermediaryOrgan(req.getPersonaIntermediaryOrgan());
                personaIntermediaryOrgan.setCreateTime(now);
                personaIntermediaryOrgan.setUpdateTime(now);
                result = personaIntermediaryOrganDao.insert(personaIntermediaryOrgan);
            } else if (PersonaEnum.CustomerType.UNION_AUCTION_AGENCy.getCode().equals(customerType)) {
                if (req.getPersonaUnionAuctionAgency() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaUnionAuctionAgency().setPersonaId(persona.getId());
                TPersonaUnionAuctionAgency personaUnionAuctionAgency = ReqConvertUtil.convertToTPersonaUnionAuctionAgency(req.getPersonaUnionAuctionAgency());
                personaUnionAuctionAgency.setCreateTime(now);
                personaUnionAuctionAgency.setUpdateTime(now);
                result = personaUnionAuctionAgencyDao.insert(personaUnionAuctionAgency);
            } else if (PersonaEnum.CustomerType.FUNDING_AGENCY.getCode().equals(customerType)) {
                if (req.getPersonaFundingAgency() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                req.getPersonaFundingAgency().setPersonaId(persona.getId());
                TPersonaFundingAgency personaFundingAgency = ReqConvertUtil.convertToTPersonaFundingAgency(req.getPersonaFundingAgency());
                personaFundingAgency.setCreateTime(now);
                personaFundingAgency.setUpdateTime(now);
                result = personaFundingAgencyDao.insert(personaFundingAgency);
            } else {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (result == 0) {
                throw new BaseBusinessException(ApiCallResult.DBERROR);
            }

        }
        resp.setPersonaId(persona.getId());
        return resp;
    }

    @Transactional
    @Override
    public PersonaResp updatePersona(PersonaReq req) {
        validateUId(req.getuId());
        PersonaResp resp = new PersonaResp();
        Integer personaId = req.getPersonaId();
        TPersona persona = personaDao.selectById(personaId);
        if (persona == null) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        Date now = new Date();
        persona = ReqConvertUtil.convertToTPersona(req);
        persona.setId(personaId);
        persona.setUpdateTime(now);
        persona.setDataEntryStaff(null); // 不允许修改录入人
        int result = personaDao.updateById(persona);
        if (result == 0) {
            throw new BaseBusinessException(ApiCallResult.DBERROR);
        }
        for (String customerType : req.getCustomerTypes()) {
            if (PersonaEnum.CustomerType.BIDDER.getCode().equals(customerType)) {
                if (req.getPersonaBidder() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaBidder personaBidder = personaBidderDao.getByPersonaId(personaId);
                if (personaBidder != null) {
                    req.getPersonaBidder().setId(personaBidder.getId());
                }
                req.getPersonaBidder().setPersonaId(req.getPersonaId());
                personaBidder = ReqConvertUtil.convertToTPersonaBidder(req.getPersonaBidder());
                if (personaBidder.getId() == null) {
                    personaBidder.setCreateTime(now);
                    personaBidder.setUpdateTime(now);
                    result = personaBidderDao.insert(personaBidder);
                } else {
                    personaBidder.setUpdateTime(now);
                    result = personaBidderDao.updateById(personaBidder);
                }
            } else if (PersonaEnum.CustomerType.ASSET_PARTY.getCode().equals(customerType)) {
                if (req.getPersonaAssetParty() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaAssetParty personaAssetParty = personaAssetPartyDao.getByPersonaId(personaId);
                if (personaAssetParty != null) {
                    req.getPersonaAssetParty().setId(personaAssetParty.getId());
                }
                req.getPersonaAssetParty().setPersonaId(req.getPersonaId());
                personaAssetParty = ReqConvertUtil.convertToTPersonaAssetParty(req.getPersonaAssetParty());
                if (personaAssetParty.getId() == null) {
                    personaAssetParty.setCreateTime(now);
                    personaAssetParty.setUpdateTime(now);
                    result = personaAssetPartyDao.insert(personaAssetParty);
                } else {
                    personaAssetParty.setUpdateTime(now);
                    result = personaAssetPartyDao.updateById(personaAssetParty);
                }

            } else if (PersonaEnum.CustomerType.DISPOSAL_PARTY.getCode().equals(customerType)) {
                if (req.getPersonaDisposalParty() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaDisposalParty personaDisposalParty = personaDisposalPartyDao.getByPersonaId(personaId);
                if (personaDisposalParty != null) {
                    req.getPersonaDisposalParty().setId(personaDisposalParty.getId());
                }
                req.getPersonaDisposalParty().setPersonaId(req.getPersonaId());
                personaDisposalParty = ReqConvertUtil.convertToTPersonaDisposalParty(req.getPersonaDisposalParty());
                if (personaDisposalParty.getId() == null) {
                    personaDisposalParty.setCreateTime(now);
                    personaDisposalParty.setUpdateTime(now);
                    result = personaDisposalPartyDao.insert(personaDisposalParty);
                } else {
                    personaDisposalParty.setUpdateTime(now);
                    result = personaDisposalPartyDao.updateById(personaDisposalParty);
                }

            } else if (PersonaEnum.CustomerType.INTERMEDIARY_ORGAN.getCode().equals(customerType)) {
                if (req.getPersonaIntermediaryOrgan() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaIntermediaryOrgan personaIntermediaryOrgan = personaIntermediaryOrganDao.getByPersonaId(personaId);
                if (personaIntermediaryOrgan != null) {
                    req.getPersonaIntermediaryOrgan().setId(personaIntermediaryOrgan.getId());
                }
                req.getPersonaIntermediaryOrgan().setPersonaId(req.getPersonaId());
                personaIntermediaryOrgan = ReqConvertUtil.convertToTPersonaIntermediaryOrgan(req.getPersonaIntermediaryOrgan());
                if (personaIntermediaryOrgan.getId() == null) {
                    personaIntermediaryOrgan.setCreateTime(now);
                    personaIntermediaryOrgan.setUpdateTime(now);
                    result = personaIntermediaryOrganDao.insert(personaIntermediaryOrgan);
                } else {
                    personaIntermediaryOrgan.setUpdateTime(now);
                    result = personaIntermediaryOrganDao.updateById(personaIntermediaryOrgan);
                }
            } else if (PersonaEnum.CustomerType.UNION_AUCTION_AGENCy.getCode().equals(customerType)) {
                if (req.getPersonaUnionAuctionAgency() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaUnionAuctionAgency personaUnionAuctionAgency = personaUnionAuctionAgencyDao.getByPersonaId(personaId);
                if (personaUnionAuctionAgency != null) {
                    req.getPersonaUnionAuctionAgency().setId(personaUnionAuctionAgency.getId());
                }
                req.getPersonaUnionAuctionAgency().setPersonaId(req.getPersonaId());
                personaUnionAuctionAgency = ReqConvertUtil.convertToTPersonaUnionAuctionAgency(req.getPersonaUnionAuctionAgency());
                if (personaUnionAuctionAgency.getId() == null) {
                    personaUnionAuctionAgency.setCreateTime(now);
                    personaUnionAuctionAgency.setUpdateTime(now);
                    result = personaUnionAuctionAgencyDao.insert(personaUnionAuctionAgency);
                } else {
                    personaUnionAuctionAgency.setUpdateTime(now);
                    result = personaUnionAuctionAgencyDao.updateById(personaUnionAuctionAgency);
                }
            } else if (PersonaEnum.CustomerType.FUNDING_AGENCY.getCode().equals(customerType)) {
                if (req.getPersonaFundingAgency() == null) {
                    throw new BaseBusinessException(ApiCallResult.EMPTY);
                }
                TPersonaFundingAgency personaFundingAgency = personaFundingAgencyDao.getByPersonaId(personaId);
                if (personaFundingAgency != null) {
                    req.getPersonaFundingAgency().setId(personaFundingAgency.getId());
                }
                req.getPersonaFundingAgency().setPersonaId(req.getPersonaId());
                personaFundingAgency = ReqConvertUtil.convertToTPersonaFundingAgency(req.getPersonaFundingAgency());
                if (personaFundingAgency.getId() == null) {
                    personaFundingAgency.setCreateTime(now);
                    personaFundingAgency.setUpdateTime(now);
                    result = personaFundingAgencyDao.insert(personaFundingAgency);
                } else {
                    personaFundingAgency.setUpdateTime(now);
                    result = personaFundingAgencyDao.updateById(personaFundingAgency);
                }
            } else {
                throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
            }
            if (result == 0) {
                throw new BaseBusinessException(ApiCallResult.DBERROR);
            }

        }
        resp.setPersonaId(persona.getId());
        return resp;
    }

    @Override
    public PersonaDetailResp getPersonaById(PersonaReq req) {
        validateUId(req.getuId());
        Integer personaId = req.getPersonaId();
        TPersona persona = personaDao.selectById(personaId);
        if (persona == null) {
            throw new BaseBusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        PersonaDetailResp resp = RespConvertUtil.convertToPersonaResp(persona);
        for (String customerType : resp.getCustomerTypes()) {
            TPersonaBidder personaBidder = personaBidderDao.getByPersonaId(personaId);
            if (personaBidder != null) {
                resp.setPersonaBidder(RespConvertUtil.convertToPersonaBidderResp(personaBidder));
            }
            TPersonaAssetParty personaAssetParty = personaAssetPartyDao.getByPersonaId(personaId);
            if (personaAssetParty != null) {
                resp.setPersonaAssetParty(RespConvertUtil.convertToPersonaAssetPartyResp(personaAssetParty));
            }
            TPersonaDisposalParty personaDisposalParty = personaDisposalPartyDao.getByPersonaId(personaId);
            if (personaDisposalParty != null) {
                resp.setPersonaDisposalParty(RespConvertUtil.convertToPersonaDisposalPartyResp(personaDisposalParty));
            }
            TPersonaIntermediaryOrgan personaIntermediaryOrgan = personaIntermediaryOrganDao.getByPersonaId(personaId);
            if (personaIntermediaryOrgan != null) {
                resp.setPersonaIntermediaryOrgan(RespConvertUtil.convertToPersonaIntermediaryOrganResp(personaIntermediaryOrgan));
            }
            TPersonaUnionAuctionAgency personaUnionAuctionAgency = personaUnionAuctionAgencyDao.getByPersonaId(personaId);
            if (personaUnionAuctionAgency != null) {
                resp.setPersonaUnionAuctionAgency(RespConvertUtil.convertToPersonaUnionAuctionAgencyResp(personaUnionAuctionAgency));
            }
            TPersonaFundingAgency personaFundingAgency = personaFundingAgencyDao.getByPersonaId(personaId);
            if (personaFundingAgency != null) {
                resp.setPersonaFundingAgency(RespConvertUtil.convertToPersonaFundingAgencyResp(personaFundingAgency));
            }
        }
        return resp;
    }

    @Override
    public PageInfoResp<PersonaListResp> getPersonaListByPage(PersonaReq req) {
        PageInfoResp<PersonaListResp> resp = new PageInfoResp();
        Staff staff = getStaff(req.getuId());
        Map<String, Object> params = new HashMap<>();
        if (staff.getPersonaAdmin()) {
            if (StringUtils.isNotEmpty(req.getQ())) {
                params.put("q", req.getQ());
            }
        } else {
            params.put("dataEntryStaff", staff.getMobile());

        }
        if (StringUtils.isNotBlank(req.getContactPhone())) {
            params.put("contactPhone", req.getContactPhone());
        }
        if (StringUtils.isNotBlank(req.getContactName())) {
            params.put("contactName", req.getContactName());
        }
        if (StringUtils.isNotBlank(req.getCompanyName())) {
            params.put("companyName", req.getCompanyName());
        }
        PageInfo pageInfo = personaDao.getListByPage(req.getPage(), req.getPerPage(), params, "p.id desc");
        List<PersonaListResp> itemsList = new ArrayList<>();
        List<TPersona> personas = pageInfo.getList();
        for (TPersona persona : personas) {
            PersonaListResp vo = RespConvertUtil.convertToPersonaListResp(persona);
            if (StringUtils.isNotEmpty(vo.getDataEntryStaff())) {
                StaffCondition staffCondition = new StaffCondition();
                staffCondition.setMobile(vo.getDataEntryStaff());
                Staff s = staffDao.selectFirst(staffCondition);
                if (s != null && StringUtils.isNotEmpty(s.getName())) {
                    vo.setDataEntryStaff(vo.getDataEntryStaff() + "(" + s.getName() + ")");
                }
            }
            itemsList.add(vo);
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }
}
