package com._360pai.core.service.fastway;

import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.TAccountDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.model.account.*;
import com._360pai.core.service.account.*;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.utils.BusinessUtil;
import com._360pai.core.utils.ServiceMessageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-11-30 11:04
 */
public abstract class FastwayService {

    abstract public Long insertCompanyApplyRecord(TAccount tAccount, Object object, Integer operatorId, String source);

    @Autowired
    protected UserService userService;
    @Autowired
    protected CompanyService companyService;
    @Autowired
    protected CompanyVerifyApplicationService companyVerifyApplicationService;
    @Autowired
    protected PartyService partyService;
    @Autowired
    protected UserVerifyApplicationService userVerifyApplicationService;
    @Autowired
    protected AccountService accountService;
    @Autowired
    protected ServiceMessageUtils serviceMessageUtils;
    @Autowired
    protected TAccountDao accountDao;
    @Autowired
    protected AgencyService agencyService;
    @Autowired
    protected FundService fundService;
    @Autowired
    protected AcctService acctService;
    @Autowired
    protected DisposeService disposeService;
    @Autowired
    protected CityService cityService;

    @Autowired
    protected AccountBusinessService accountBusinessService;


    protected TAccount getAccountById(Integer accountId) {
        return accountService.selectByPrimaryKey(accountId);
    }

    protected void checkUserIDCardUnique(String idCard, Integer partyId) {
        //判断身份证是否被认证过
        TUser param = new TUser();
        param.setCertificateNumber(idCard);
        List<TUser> list = userService.findUser(param);
        if (list != null && !list.isEmpty() && (partyId == null || partyId == 0)) {
            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);
        }
    }

    protected void checkUserIDCardUnique(String idCard) {
        //判断身份证是否被认证过
        TUser param = new TUser();
        param.setCertificateNumber(idCard);
        List<TUser> list = userService.findUser(param);
        if (list != null && !list.isEmpty()) {
            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);
        }
    }

    protected void checkCompanyIDCardUnique(String idCard) {
        if (StringUtils.isNotBlank(idCard)) {
            TCompany company = companyService.findCompanyByLicence(idCard);
            if (company != null) {
                throw new BusinessException(ExceptionEnumImpl.COMPANY_HAS_AUTH);
            }
        }
    }

    protected void insertCompany(Long applyId, Integer companyId, String category, Date qualifiedBegin, Date qualifiedEnd) {
        TCompany tCompany = new TCompany();
        TCompanyApplyRecord tCompanyApplyRecord = companyVerifyApplicationService.findCompanyApplyRecordById(applyId);
        BeanUtils.copyProperties(tCompanyApplyRecord, tCompany);
        tCompany.setId(companyId);
        tCompany.setDfftId(BusinessUtil.genDfftId());
        tCompany.setCategory(category);
        tCompany.setQualifiedBegin(qualifiedBegin);
        tCompany.setQualifiedEnd(qualifiedEnd);
        tCompany.setFadadaEmail(RandomNumberGenerator.wordGenerator(10) + "@360pai.com");
        companyService.saveCompany(tCompany);

        TAcct acct = new TAcct();
        acct.setPartyId(companyId);
        acct.setType(AccountEnum.AcctType.COMPANY.getKey());
        acctService.saveTAcct(acct);
    }

    protected Integer insertParty(String type, String category) {
        TParty party = new TParty();
        party.setType(type);
        party.setCategory(category);
        partyService.saveParty(party);
        return party.getId();
    }

    protected void insertUser(Long applyId, Integer userId) {
        TUser tUser = new TUser();
        TUserApplyRecord tUserApplyRecord = userVerifyApplicationService.getUserApplyRecordById(applyId);
        BeanUtils.copyProperties(tUserApplyRecord, tUser);
        tUser.setId(userId);
        tUser.setDfftId(BusinessUtil.genDfftId());
        userService.saveUser(tUser);

        TAcct acct = new TAcct();
        acct.setPartyId(userId);
        acct.setType(AccountEnum.AcctType.USER.getKey());
        acctService.saveTAcct(acct);
    }

    protected String applySourceConvert(String source) {
        if ("10".equals(source)) {
            return PartyEnum.ApplySource.FASTWAY.getKey();
        } else {
            return PartyEnum.ApplySource.PLATFORM.getKey();
        }
    }

    protected CityVo getCityVo(TUser user) {
        CityVo city = new CityVo();
        if (StringUtils.isNotBlank(user.getAreaId())) {
            city.setAreaId(Integer.parseInt(user.getAreaId()));
            city.setAreaName(cityService.getAreaName(Integer.parseInt(user.getAreaId())));
        }
        if (StringUtils.isNotBlank(user.getCityId())) {
            city.setId(Integer.parseInt(user.getCityId()));
            city.setName(cityService.getCityName(Integer.parseInt(user.getCityId())));
        }

        if (StringUtils.isNotBlank(user.getProvinceId())) {
            city.setProvinceId(Integer.parseInt(user.getProvinceId()));
            city.setProvinceName(cityService.getProvinceName(Integer.parseInt(user.getProvinceId())));
        }
        return city;
    }

    protected CityVo getCityVo(TCompany company) {
        CityVo city = new CityVo();
        if (StringUtils.isNotBlank(company.getAreaId())) {
            city.setAreaId(Integer.parseInt(company.getAreaId()));
            city.setAreaName(cityService.getAreaName(Integer.parseInt(company.getAreaId())));
        }
        if (StringUtils.isNotBlank(company.getCityId())) {
            city.setId(Integer.parseInt(company.getCityId()));
            city.setName(cityService.getCityName(Integer.parseInt(company.getCityId())));
        }

        if (StringUtils.isNotBlank(company.getProvinceId())) {
            city.setProvinceId(Integer.parseInt(company.getProvinceId()));
            city.setProvinceName(cityService.getProvinceName(Integer.parseInt(company.getProvinceId())));
        }
        return city;
    }

    protected CityVo getCityVo(Integer cityId) {
        if (cityId != null) {
            CityVo cityVo = new CityVo();
            cityVo.setId(cityId);
            cityVo.setName(cityService.getCityName(cityId));
            return cityVo;
        }
        return null;
    }



}
