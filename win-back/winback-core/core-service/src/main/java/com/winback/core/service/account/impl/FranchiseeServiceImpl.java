package com.winback.core.service.account.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.ToolUtil;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.CaseEnum;
import com.winback.core.condition.assistant.TComAreaCondition;
import com.winback.core.condition.assistant.TComCityCondition;
import com.winback.core.condition.assistant.TComProvinceCondition;
import com.winback.core.dao._case.TCaseBriefDao;
import com.winback.core.dao._case.TCaseTypeDao;
import com.winback.core.dao.account.*;
import com.winback.core.dao.assistant.TComAreaDao;
import com.winback.core.dao.assistant.TComCityDao;
import com.winback.core.dao.assistant.TComProvinceDao;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.resp.AppAccountResp;
import com.winback.core.facade.account.vo.Customer;
import com.winback.core.facade.account.vo.Franchisee;
import com.winback.core.facade.account.vo.FranchiseeApplyRecord;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TFranchisee;
import com.winback.core.model.account.TFranchiseeApplyRecord;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.assistant.TComArea;
import com.winback.core.model.assistant.TComCity;
import com.winback.core.model.assistant.TComProvince;
import com.winback.core.service.account.FranchiseeService;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.core.service.assistant.PushAppMessageService;
import com.winback.core.utils.ReqConvertUtil;
import com.winback.core.utils.RespConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: FranchiseeServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 15:08
 */
@Slf4j
@Service
public class FranchiseeServiceImpl implements FranchiseeService {
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TFranchiseeApplyRecordDao franchiseeApplyRecordDao;
    @Autowired
    private TFranchiseeDao franchiseeDao;
    @Autowired
    private TCaseBriefDao caseBriefDao;
    @Autowired
    private TCaseTypeDao caseTypeDao;
    @Autowired
    private TSysStaffDao staffDao;
    @Autowired
    private PushAppMessageService pushAppMessageService;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private TComProvinceDao comProvinceDao;
    @Autowired
    private TComCityDao comCityDao;
    @Autowired
    private TComAreaDao comAreaDao;
    @Autowired
    private TLawyerDao lawyerDao;

    @Override
    public AppAccountResp.ApplyResp franchiseeApply(AppAccountReq.FranchiseeApplyReq req) {
        TAccount account = accountDao.selectById(req.getLoginId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (franchiseeApplyRecordDao.hasPendingApply(account.getId())) {
            throw new BusinessException("有等待审核的申请，暂时无法重新申请");
        }
        TFranchisee lawyer = franchiseeDao.findByAccountId(account.getId());
        if (lawyer != null) {
            throw new BusinessException("您已成为加盟商，无需再次提交");
        }
        TFranchiseeApplyRecord applyRecord = ReqConvertUtil.convertToTFranchiseeApplyRecord(req);
        int result = franchiseeApplyRecordDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        pushAppMessageService.pushFranchiseeApplySuccessMsg(req.getLoginId());
        AppAccountResp.ApplyResp resp = new AppAccountResp.ApplyResp();
        resp.setApplyId(applyRecord.getId());
        return resp;
    }

    @Override
    public String getApplyStatus(Integer accountId) {
        TFranchisee franchisee = franchiseeDao.findByAccountId(accountId);
        if (franchisee != null) {
            return AccountEnum.ApplyStatus.APPROVED.getKey();
        }
        TFranchiseeApplyRecord applyRecord = franchiseeApplyRecordDao.findLatestByAccountId(accountId);
        if (applyRecord != null) {
            return applyRecord.getStatus();
        }
        return AccountEnum.ApplyStatus.NO_APPLY.getKey();
    }

    @Override
    public TFranchisee findByAccountId(Integer accountId) {
        return franchiseeDao.findByAccountId(accountId);
    }

    @Override
    public TFranchisee findById(Integer id) {
        return franchiseeDao.selectById(id);
    }

    @Override
    public PageInfoResp<FranchiseeApplyRecord> getApplyRecordListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        PageInfoResp<FranchiseeApplyRecord> resp = new PageInfoResp<>();
        List<FranchiseeApplyRecord> resultList = new ArrayList<>();
        PageInfo<TFranchiseeApplyRecord> pageInfo = franchiseeApplyRecordDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "r.id desc");
        for (TFranchiseeApplyRecord item : pageInfo.getList()) {
            try {
                FranchiseeApplyRecord vo = RespConvertUtil.convertToFranchiseeApplyRecord(item);
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
                vo.setOperator(staffDao.getName(item.getOperatorId()));
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
    public Integer franchiseeApplyApprove(AdminAccountReq.FranchiseeVerifyReq req) {
        TFranchiseeApplyRecord applyRecord = franchiseeApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TFranchisee lawyer = franchiseeDao.findByAccountId(applyRecord.getAccountId());
        if (lawyer != null) {
            throw new BusinessException("该账户已认证加盟商");
        }
        applyRecord = ReqConvertUtil.convertToTFranchiseeApplyRecord(req);
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        applyRecord.setOperatorId(req.getLoginId());
        int result = franchiseeApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        applyRecord = franchiseeApplyRecordDao.selectById(req.getId());
        lawyer = franchiseeDao.createFromApply(applyRecord);
        pushAppMessageService.pushFranchiseeApplyApproveMsg(applyRecord.getAccountId());
        return lawyer.getId();
    }

    @Override
    public void franchiseeApplyReject(AdminAccountReq.FranchiseeVerifyReq req) {
        TFranchiseeApplyRecord applyRecord = franchiseeApplyRecordDao.selectById(req.getId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord.setReason(req.getReason());
        applyRecord.setStatus(AccountEnum.ApplyStatus.REJECT.getKey());
        applyRecord.setOperatorId(req.getLoginId());
        int result = franchiseeApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
    }

    @Override
    public PageInfoResp<Franchisee> getFranchiseeListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        PageInfoResp<Franchisee> resp = new PageInfoResp<>();
        List<Franchisee> resultList = new ArrayList<>();
        PageInfo<TFranchisee> pageInfo = franchiseeDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "f.id desc");
        for (TFranchisee item : pageInfo.getList()) {
            try {
                Franchisee vo = RespConvertUtil.convertToFranchisee(item);
                vo.setMobile(accountDao.getMobile(item.getAccountId()));
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

    @Override
    public Integer franchiseeUpdate(AdminAccountReq.FranchiseeUpdateReq req) {
        TFranchisee franchisee = franchiseeDao.selectById(req.getId());
        if (franchisee == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        franchisee = ReqConvertUtil.convertToTFranchisee(req);
        int result = franchiseeDao.updateById(franchisee);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return franchisee.getId();
    }

    @Override
    public PageInfoResp<Customer> getInviteCustomerListByPage(AppAccountReq.FranchiseeQueryReq req) {
        TFranchisee franchisee = franchiseeDao.findByAccountId(req.getLoginId());
        if (franchisee == null) {
            return new PageInfoResp<>();
        }
        req.setFranchiseeId(franchisee.getId());
        PageInfoResp<Customer> resp = new PageInfoResp<>();
        List<Customer> itemList = new ArrayList<>();
        PageInfo<TAccount> pageInfo = franchiseeDao.getInviteCustomerList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "sa.id desc");
        for (TAccount account : pageInfo.getList()) {
            Customer vo = RespConvertUtil.convertToCustomer(account);
            vo.setMobile(ToolUtil.maskMobile(vo.getMobile()));
            TLawyer lawyer = lawyerDao.findByAccountId(account.getId());
            if (lawyer != null) {
                vo.setNickName(lawyer.getName());
                vo.setHeadImgUrl(lawyer.getHeadImgUrl());
            }
            itemList.add(vo);
        }
        resp.setList(itemList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<Customer> getInviteCustomerListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        PageInfoResp<Customer> resp = new PageInfoResp<>();
        List<Customer> itemList = new ArrayList<>();
        PageInfo<TAccount> pageInfo = franchiseeDao.getInviteCustomerList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "sa.id desc");
        for (TAccount account : pageInfo.getList()) {
            Customer vo = RespConvertUtil.convertToCustomer(account);
            TLawyer lawyer = lawyerDao.findByAccountId(account.getId());
            if (lawyer != null) {
                vo.setNickName(lawyer.getName());
                vo.setHeadImgUrl(lawyer.getHeadImgUrl());
            }
            itemList.add(vo);
        }
        resp.setList(itemList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<Case> getInviteCaseListByPage(AppAccountReq.FranchiseeQueryReq req) {
        TFranchisee franchisee = franchiseeDao.findByAccountId(req.getLoginId());
        if (franchisee == null) {
            return new PageInfoResp<>();
        }
        req.setFranchiseeId(franchisee.getId());
        PageInfoResp<Case> resp = new PageInfoResp<>();
        List<Case> itemList = new ArrayList<>();
        PageInfo<TCase> pageInfo = franchiseeDao.getInviteCaseList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "c.id desc");
        for (TCase item : pageInfo.getList()) {
            try {
                Case vo = RespConvertUtil.convertToCase(item);
                vo.setCaseTypeName(caseTypeDao.getName(item.getCaseTypeId()));
                vo.setCaseBriefName(caseBriefDao.getName(item.getCaseBriefId()));
                vo.setStatusDesc(CaseEnum.MainStatus.getValueByKey(item.getMainStatus()));
                vo.setCaseReason(caseBriefDao.getName(item.getCaseBriefId()));

                if(!StringUtils.isEmpty(item.getProvinceCode())){
                    TComProvinceCondition provinceCondition = new TComProvinceCondition();
                    provinceCondition.setCode(item.getProvinceCode());
                    List<TComProvince> provinceList = comProvinceDao.selectList(provinceCondition);
                    if(provinceList != null && !provinceList.isEmpty() && provinceList.get(0) != null){
                        vo.setProvinceName(provinceList.get(0).getName());
                    }
                }

                if(!StringUtils.isEmpty(item.getCityCode())){
                    TComCityCondition cityCondition = new TComCityCondition();
                    cityCondition.setCode(item.getCityCode());
                    List<TComCity> cityList = comCityDao.selectList(cityCondition);
                    if(cityList != null && !cityList.isEmpty() && cityList.get(0) != null){
                        vo.setCityName(cityList.get(0).getName());
                    }
                }

                if(!StringUtils.isEmpty(item.getAreaCode())){
                    TComAreaCondition areaCondition = new TComAreaCondition();
                    areaCondition.setCode(item.getAreaCode());
                    List<TComArea> areaList = comAreaDao.selectList(areaCondition);
                    if(areaList != null && !areaList.isEmpty() && areaList.get(0) != null){
                        vo.setAreaName(areaList.get(0).getName());
                    }
                }

                itemList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(itemList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }



    @Override
    public PageInfoResp<Case> getInviteCaseListByPage(AdminAccountReq.FranchiseeQueryReq req) {
        PageInfoResp<Case> resp = new PageInfoResp<>();
        List<Case> itemList = new ArrayList<>();
        PageInfo<TCase> pageInfo = franchiseeDao.getInviteCaseList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "c.id desc");
        for (TCase item : pageInfo.getList()) {
            try {
                Case vo = RespConvertUtil.convertToCase(item);
                vo.setCaseTypeName(caseTypeDao.getName(item.getCaseTypeId()));
                vo.setCaseBriefName(caseBriefDao.getName(item.getCaseBriefId()));
                itemList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(itemList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }
}
