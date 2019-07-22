package com._360pai.core.provider.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.disposal.req.City;
import com._360pai.core.facade.fastway.FundApplyAdminFacade;
import com._360pai.core.facade.fastway.req.FundApplyReq;
import com._360pai.core.facade.fastway.resp.FundApplyListVO;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.vo.FundBasisVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.service.fastway.FundApplyAdminService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-12-07 11:21
 */
@Component
@Service(version = "1.0.0")
public class FundApplyAdminProvider implements FundApplyAdminFacade {

    @Autowired
    private FundApplyAdminService fundApplyAdminService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private CompanyService companyService;

    @Override
    public PageInfoResp findFundApplyByParam(FundApplyReq.FundFindReq req) {
        Map<String, Object> query = new HashMap<>(2);
        if (StringUtils.isNotBlank(req.getApplyStatus())) {
            query.put("applyStatus", req.getApplyStatus());
        }
        if (StringUtils.isNotBlank(req.getQuery())) {
            query.put("query", req.getQuery());
        }
        PageInfo pageInfo = fundApplyAdminService.findByParam(query, req.getPage(), req.getPerPage());
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public UserFundDetailVO findUserFundDetailById(Integer applyId) {
        TFastwayFundApply apply = fundApplyAdminService.findById(applyId);
        if (apply == null) {
            throw new BusinessException("该记录不存在");
        }
        UserFundDetailVO userFundDetailVO = convertToFundVO(apply, UserFundDetailVO.class);
        userFundDetailVO.setPartyId(apply.getPartyId());
        userFundDetailVO.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(apply.getApplyStatus()));
        if (userFundDetailVO.getCityId() != null) {
            userFundDetailVO.setCityBean(JSONObject.parseObject(userFundDetailVO.getCityId(), City.class));
        }
        userFundDetailVO.setDefaultAgency(defaultAgency());
        if (apply.getOpenAccountOperatorId() != null) {
            userFundDetailVO.setOpenAccountOperator(staffDao.getName(apply.getOpenAccountOperatorId()));
        }
        if (apply.getBusinessOperatorId() != null) {
            userFundDetailVO.setBusinessOperator(staffDao.getName(apply.getBusinessOperatorId()));
        }
        return userFundDetailVO;
    }

    @Override
    public CompanyFundDetailVO findCompanyFundDetailById(Integer applyId) {
        TFastwayFundApply apply = fundApplyAdminService.findById(applyId);
        if (apply == null) {
            throw new BusinessException("该记录不存在");
        }
        CompanyFundDetailVO companyFundVO = convertToFundVO(apply, CompanyFundDetailVO.class);
        if (!apply.getApplyStatus().equals(FastwayEnum.DisposeStatusEnum.access) && apply.getPartyId() != null) {
            TCompany companyById = companyService.findCompanyById(apply.getPartyId());
            companyFundVO.setLicense(companyById.getLicense());
            companyFundVO.setAuditNum(companyById.getLeaseNum());
        }
        companyFundVO.setPartyId(apply.getPartyId());
        companyFundVO.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(apply.getApplyStatus()));
        if (companyFundVO.getCityId() != null) {
            companyFundVO.setCityBean(JSONObject.parseObject(companyFundVO.getCityId(), City.class));
        }
        companyFundVO.setRegisterMobile(getRegisterMobile(apply.getAccountId()));
        if (apply.getOpenAccountOperatorId() != null) {
            companyFundVO.setOpenAccountOperator(staffDao.getName(apply.getOpenAccountOperatorId()));
        }
        if (apply.getBusinessOperatorId() != null) {
            companyFundVO.setBusinessOperator(staffDao.getName(apply.getBusinessOperatorId()));
        }
        return companyFundVO;
    }

    @Override
    public int userUpdate(FundApplyReq.UserUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.fundUpdate(req.getUserFundDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int companyUpdate(FundApplyReq.CompanyUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.fundUpdate(req.getCompanyFundDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int userVerifyAccess(FundApplyReq.UserUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.userVerifyAccess(req.getUserFundDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int userVerifyDeny(FundApplyReq.UserUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.fundVerifyDeny(req.getRefuseReason(), req.getApplyId(), operatorId);
    }

    @Override
    public int companyVerifyAccess(FundApplyReq.CompanyUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.companyVerifyAccess(req.getCompanyFundDetailVO(), req.getApplyId(), operatorId);
    }

    @Override
    public int companyVerifyDeny(FundApplyReq.CompanyUpdateReq req, Integer operatorId) {
        return fundApplyAdminService.fundVerifyDeny(req.getRefuseReason(), req.getApplyId(), operatorId);
    }

    private UserFundDetailVO convertToUserFundDetailVO(TFastwayFundApply apply) {
        UserFundDetailVO vo = apply.getApplyFiled()
                .getJSONObject(apply.getApplyType())
                .toJavaObject(UserFundDetailVO.class);
        BeanUtils.copyProperties(apply, vo);
        vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(apply.getApplyStatus()));
        if (vo.getCityId() != null) {
            vo.setCityBean(JSONObject.parseObject(vo.getCityId(), City.class));
        }
        return vo;
    }

    private CompanyFundDetailVO convertToCompanyFundDetailVO(TFastwayFundApply apply) {
        CompanyFundDetailVO vo = apply.getApplyFiled()
                .getJSONObject(apply.getApplyType())
                .toJavaObject(CompanyFundDetailVO.class);
        BeanUtils.copyProperties(apply, vo);
        vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(apply.getApplyStatus()));
        if (vo.getCityId() != null) {
            vo.setCityBean(JSONObject.parseObject(vo.getCityId(), City.class));
        }
        return vo;
    }

    private <T extends FundBasisVO> T convertToFundVO(TFastwayFundApply apply, Class<T> clazz) {
        T vo = apply.getApplyFiled()
                .getJSONObject(apply.getApplyType())
                .toJavaObject(clazz);
        BeanUtils.copyProperties(apply, vo);
        return vo;
    }

    public List<FundApplyListVO> convertModel(List<TFastwayFundApply> source) {
        List<FundApplyListVO> target = new LinkedList<>();
        TFastwayFundApply tmp ;
        for(int i = 0, size = source.size(); i < size; i++) {
            tmp  = source.get(i);
            target.add(jsonToVO(tmp));
        }
        return target;
    }

    private FundApplyListVO jsonToVO(TFastwayFundApply tmp) {
        if (FastwayEnum.FundType.User.equals(tmp.getApplyType()))
            return jsonToUser(tmp.getApplyFiled().getJSONObject(tmp.getApplyType()), tmp);
        else if (FastwayEnum.FundType.Company.equals(tmp.getApplyType()))
            return jsonToCompany(tmp.getApplyFiled().getJSONObject(tmp.getApplyType()), tmp);
        return FundApplyListVO.empty();
    }

    private FundApplyListVO jsonToUser(JSONObject json, TFastwayFundApply tmp) {
        FundApplyListVO vo = new FundApplyListVO();
        BeanUtils.copyProperties(tmp, vo);
        vo.setApplyId(tmp.getId());
        vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(tmp.getApplyStatus()));
        vo.setName(json.getString("name"));
        vo.setApplyType("个人");
        vo.setWorkCity(JSONObject.parseObject(json.getString("cityId"), City.class));
        vo.setOperatorName(getOperatorName(tmp.getOperatorId()));
        return vo;
    }

    private FundApplyListVO jsonToCompany(JSONObject json, TFastwayFundApply tmp) {
        FundApplyListVO vo = new FundApplyListVO();
        BeanUtils.copyProperties(tmp, vo);
        vo.setApplyId(tmp.getId());
        vo.setApplyStatusDesc(FastwayEnum.DisposeStatusEnum.getDescByKey(tmp.getApplyStatus()));
        vo.setName(json.getString("name"));
        vo.setApplyType("企业");
        vo.setWorkCity(JSONObject.parseObject(json.getString("cityId"), City.class));
        vo.setOperatorName(getOperatorName(tmp.getOperatorId()));
        return vo;
    }

    private String getOperatorName(Integer operatorId) {
        if (operatorId != null) {
            Staff staff = staffService.getById(operatorId);
            if (staff != null) {
                return staff.getName();
            }
        }
        return null;
    }

    private String getRegisterMobile(Integer accountId) {
        TAccount tAccount = accountService.selectByPrimaryKey(accountId);
        return tAccount.getMobile();
    }

    private String defaultAgency() {
        TAgency agency = agencyService.findByAgencyCode(SystemConstant.DEFAULT_AGENCY_CODE);
        if (agency != null) {
            return agency.getName();
        }
        return null;
    }
}
