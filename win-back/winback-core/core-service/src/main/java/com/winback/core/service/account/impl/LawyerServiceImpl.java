package com.winback.core.service.account.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.enums.MessageTemplateEnum;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.dao._case.TLawyerCaseBriefMapDao;
import com.winback.core.dao._case.TLawyerFirmCaseBriefMapDao;
import com.winback.core.dao.account.*;
import com.winback.core.dao.assistant.TComAreaDao;
import com.winback.core.dao.assistant.TComCityDao;
import com.winback.core.dao.assistant.TComProvinceDao;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade._case.vo.CaseBrief;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.vo.LawFirm;
import com.winback.core.facade.account.vo.Lawyer;
import com.winback.core.facade.account.vo.LawyerApplyRecord;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TLawFirm;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.account.TLawyerApplyRecord;
import com.winback.core.model.assistant.TAppMessageTemplate;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.service.account.LawyerService;
import com.winback.core.service.assistant.*;
import com.winback.core.utils.ReqConvertUtil;
import com.winback.core.utils.RespConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author xdrodger
 * @Title: LawyerServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 14:33
 */
@Slf4j
@Service
public class LawyerServiceImpl implements LawyerService {
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TLawyerApplyRecordDao lawyerApplyRecordDao;
    @Autowired
    private TLawyerDao lawyerDao;
    @Autowired
    private TLawyerCaseBriefMapDao lawyerCaseBriefMapDao;
    @Autowired
    private TLawFirmDao lawFirmDao;
    @Autowired
    private TLawyerFirmCaseBriefMapDao lawyerFirmCaseBriefMapDao;
    @Autowired
    private CityService cityService;
    @Autowired
    private TComAreaDao areaDao;
    @Autowired
    private TComCityDao cityDao;
    @Autowired
    private TComProvinceDao provinceDao;
    @Autowired
    private TSysStaffDao staffDao;
    @Autowired
    private PushAppMessageService pushAppMessageService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private AppMessageTemplateService appMessageTemplateService;

    @Override
    public AppAccountResp.ApplyResp lawyerApply(AppAccountReq.LawyerApplyReq req) {
        TAccount account = accountDao.selectById(req.getLoginId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (lawyerApplyRecordDao.hasPendingApply(account.getId())) {
            throw new BusinessException("有等待审核的申请，暂时无法重新申请");
        }
        TLawyer lawyer = lawyerDao.findByAccountId(account.getId());
        if (lawyer != null) {
            throw new BusinessException("该账户已认证律师");
        }
        if (lawyerDao.isExistLawyerLicenseNumber(req.getLawyerLicenseNumber())) {
            throw new BusinessException("律师执业证号重复");
        }
        TLawyerApplyRecord applyRecord = ReqConvertUtil.convertToTLawyerApplyRecord(req);
        int result = lawyerApplyRecordDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        pushAppMessageService.pushLawyerApplySuccessMsg(req.getLoginId());
        List<String> mobiles = getMobiles();
        for (String mobile : mobiles) {
            smsService.sendLawyerApplySms(mobile);
        }
        AppAccountResp.ApplyResp resp = new AppAccountResp.ApplyResp();
        resp.setApplyId(applyRecord.getId());
        return resp;
    }

    private List<String> getMobiles() {
        TAppMessageTemplate template = appMessageTemplateService.findBy(MessageTemplateEnum.SEND_TYPE.SMS.getType(), MessageTemplateEnum.TYPE.TYPE_44.getType());
        if (template != null && StringUtils.isNotBlank(template.getPushIds())) {
            return Arrays.asList(template.getPushIds().split(","));
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public TLawyer findById(Integer id) {
        return lawyerDao.selectById(id);
    }

    @Override
    public TLawyer findByAccountId(Integer accountId) {
        return lawyerDao.findByAccountId(accountId);
    }

    @Override
    public String getApplyStatus(Integer accountId) {
        TLawyer lawyer = lawyerDao.findByAccountId(accountId);
        if (lawyer != null) {
            return AccountEnum.ApplyStatus.APPROVED.getKey();
        }
        TLawyerApplyRecord applyRecord = lawyerApplyRecordDao.findLatestByAccountId(accountId);
        if (applyRecord != null) {
            return applyRecord.getStatus();
        }
        return AccountEnum.ApplyStatus.NO_APPLY.getKey();
    }

    @Override
    public PageInfoResp<LawyerApplyRecord> getLawyerApplyRecordListByPage(AdminAccountReq.LawyerQueryReq req) {
        PageInfoResp<LawyerApplyRecord> resp = new PageInfoResp<>();
        List<LawyerApplyRecord> resultList = new ArrayList<>();
        PageInfo<TLawyerApplyRecord> pageInfo = lawyerApplyRecordDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "r.id desc");
        for (TLawyerApplyRecord item : pageInfo.getList()) {
            try {
                LawyerApplyRecord vo = RespConvertUtil.convertToLawyerApplyRecord(item);
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
                vo.setBusinessProvinceName(provinceDao.getName(item.getBusinessProvinceCode()));
                if (StringUtils.isNotEmpty(item.getBusinessCityCode())) {
                    vo.setBusinessCityName(cityDao.getName(item.getBusinessCityCode()));
                }
                if (StringUtils.isNotEmpty(item.getBusinessAreaCode())) {
                    vo.setBusinessAreaName(areaDao.getName(item.getBusinessAreaCode()));
                }
                String businessArea = vo.getBusinessProvinceName();
                if (StringUtils.isNotEmpty(vo.getBusinessCityName())) {
                    businessArea += "/" + vo.getBusinessCityName();
                }
                if (StringUtils.isNotEmpty(vo.getBusinessAreaName())) {
                    businessArea += "/" + vo.getBusinessAreaName();
                }
                vo.setBusinessArea(businessArea);
                vo.setOperator(staffDao.getName(item.getOperatorId()));

                if (AccountEnum.ApplyStatus.APPROVED.getKey().equals(item.getStatus())) {
                    TLawyer lawyer = lawyerDao.findByAccountId(item.getAccountId());
                    if (lawyer != null) {
                        vo.setCaseBriefList(getCaseBriefList(lawyer.getId()));
                        vo.setCaseBriefIdList(getCaseBriefIdList(lawyer.getId()));
                    }
                }
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer applyApprove(AdminAccountReq.LawyerVerifyReq req) {
        TLawyerApplyRecord applyRecord = lawyerApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TLawyer lawyer = lawyerDao.findByAccountId(applyRecord.getAccountId());
        if (lawyer != null) {
            throw new BusinessException("该账户已认证律师");
        }
        if (lawyerDao.isExistLawyerLicenseNumber(applyRecord.getLawyerLicenseNumber())) {
            throw new BusinessException("律师执业证号已存在");
        }
        applyRecord = ReqConvertUtil.convertToTLawyerApplyRecord(req);
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setOperatorId(req.getLoginId());
        int result = lawyerApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        applyRecord = lawyerApplyRecordDao.selectById(req.getId());
        lawyer = lawyerDao.createFromApply(applyRecord);
        // 保存擅长案由列表
        lawyerCaseBriefMapDao.saveCaseBriefMap(lawyer.getId(), req.getCaseBriefIdList());

        pushAppMessageService.pushLawyerApplyApproveMsg(applyRecord.getAccountId());
        return lawyer.getId();
    }

    @Override
    public void applyReject(AdminAccountReq.LawyerVerifyReq req) {
        TLawyerApplyRecord applyRecord = lawyerApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord.setReason(req.getReason());
        applyRecord.setStatus(AccountEnum.ApplyStatus.REJECT.getKey());
        applyRecord.setOperatorId(req.getLoginId());
        int result = lawyerApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        pushAppMessageService.pushLawyerApplyRejectMsg(applyRecord.getAccountId());
    }

    @Override
    public PageInfoResp<Lawyer> getLawyerListByPage(AdminAccountReq.LawyerQueryReq req) {
        PageInfoResp<Lawyer> resp = new PageInfoResp<>();
        List<Lawyer> resultList = new ArrayList<>();
        PageInfo<TLawyer> pageInfo = lawyerDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "l.id desc");
        for (TLawyer item : pageInfo.getList()) {
            try {
                Lawyer vo = RespConvertUtil.convertToLawyer(item);
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
                vo.setCaseBriefIdList(getCaseBriefIdList(item.getId()));
                vo.setCaseBriefs(getCaseBriefList(item.getId()));
                vo.setBusinessProvinceName(provinceDao.getName(item.getBusinessProvinceCode()));
                if (StringUtils.isNotEmpty(item.getBusinessCityCode())) {
                    vo.setBusinessCityName(cityDao.getName(item.getBusinessCityCode()));
                }
                if (StringUtils.isNotEmpty(item.getBusinessAreaCode())) {
                    vo.setBusinessAreaName(areaDao.getName(item.getBusinessAreaCode()));
                }
                String businessArea = vo.getBusinessProvinceName();
                if (StringUtils.isNotEmpty(vo.getBusinessCityName())) {
                    businessArea += "/" + vo.getBusinessCityName();
                }
                if (StringUtils.isNotEmpty(vo.getBusinessAreaName())) {
                    businessArea += "/" + vo.getBusinessAreaName();
                }
                vo.setBusinessArea(businessArea);
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Transactional
    @Override
    public Integer lawyerUpdate(AdminAccountReq.LawyerUpdateReq req) {
        TLawyer lawyer = lawyerDao.selectById(req.getId());
        if (lawyer == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        lawyer = ReqConvertUtil.convertToTLawyer(req);
        int result = lawyerDao.updateById(lawyer);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        lawyerCaseBriefMapDao.syncCaseBriefMap(lawyer.getId(), req.getCaseBriefIdList());
        return lawyer.getId();
    }

    @Override
    public PageInfoResp<LawFirm> getLawFirmListByPage(AdminAccountReq.LawFirmQueryReq req) {
        PageInfoResp<LawFirm> resp = new PageInfoResp<>();
        List<LawFirm> resultList = new ArrayList<>();
        PageInfo<TLawFirm> pageInfo = lawFirmDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "f.id desc");
        for (TLawFirm item : pageInfo.getList()) {
            try {
                LawFirm vo = RespConvertUtil.convertToLawFirm(item);
                vo.setCaseBriefIdList(lawyerFirmCaseBriefMapDao.getCaseBriefIdListByLawyerFirmId(item.getId()));
                vo.setCaseBriefList(getLawFirmCaseBriefList(item.getId()));
                vo.setBusinessProvinceName(provinceDao.getName(item.getBusinessProvinceCode()));
                if (StringUtils.isNotEmpty(item.getBusinessCityCode())) {
                    vo.setBusinessCityName(cityDao.getName(item.getBusinessCityCode()));
                }
                if (StringUtils.isNotEmpty(item.getBusinessAreaCode())) {
                    vo.setBusinessAreaName(areaDao.getName(item.getBusinessAreaCode()));
                }
                String businessArea = vo.getBusinessProvinceName();
                if (StringUtils.isNotEmpty(vo.getBusinessCityName())) {
                    businessArea += "/" + vo.getBusinessCityName();
                }
                if (StringUtils.isNotEmpty(vo.getBusinessAreaName())) {
                    businessArea += "/" + vo.getBusinessAreaName();
                }
                vo.setBusinessArea(businessArea);
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Transactional
    @Override
    public Integer lawFirmAdd(AdminAccountReq.LawFirmAddReq req) {
        TLawFirm lawFirm = ReqConvertUtil.convertToTLawFirm(req);
        if (lawFirmDao.isExistName(lawFirm.getName())) {
            throw new BusinessException("律所名称重复");
        }
        if (lawFirmDao.isExistLicenseNumber(lawFirm.getLicenseNumber())) {
            throw new BusinessException("律所营业执照号重复");
        }
        int result = lawFirmDao.insert(lawFirm);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        lawyerFirmCaseBriefMapDao.saveCaseBriefMap(lawFirm.getId(), req.getCaseBriefIdList());
        return lawFirm.getId();
    }

    @Transactional
    @Override
    public Integer lawFirmUpdate(AdminAccountReq.LawFirmUpdateReq req) {
        TLawFirm lawFirm = lawFirmDao.selectById(req.getId());
        if (lawFirm == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        lawFirm = ReqConvertUtil.convertToTLawFirm(req);
        int result = lawFirmDao.updateById(lawFirm);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        lawyerFirmCaseBriefMapDao.syncCaseBriefMap(lawFirm.getId(), req.getCaseBriefIdList());
        return req.getId();
    }

    private List<String> getCaseBriefIdList(Integer lawyerId) {
        List<TCaseBrief> list = lawyerCaseBriefMapDao.getCaseBriefListByLawyerId(lawyerId);
        List<String> resultList = new ArrayList<>();
        for (TCaseBrief item : list) {
            resultList.add(item.getId() + "");
        }
        return resultList;
    }

    private List<CaseBrief> getCaseBriefList(Integer lawyerId) {
        List<TCaseBrief> list = lawyerCaseBriefMapDao.getCaseBriefListByLawyerId(lawyerId);
        List<CaseBrief> resultList = new ArrayList<>();
        for (TCaseBrief item : list) {
            CaseBrief vo = RespConvertUtil.convertToCaseBrief(item);
            resultList.add(vo);
        }
        return resultList;
    }

    private List<CaseBrief> getLawFirmCaseBriefList(Integer lawFirmId) {
        List<TCaseBrief> list = lawyerFirmCaseBriefMapDao.getCaseBriefListByLawyerFirmId(lawFirmId);
        List<CaseBrief> resultList = new ArrayList<>();
        for (TCaseBrief item : list) {
            CaseBrief vo = RespConvertUtil.convertToCaseBrief(item);
            resultList.add(vo);
        }
        return resultList;
    }
}
