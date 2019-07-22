package com._360pai.core.provider.fastway;

import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.fastway.DisposeApplyFacade;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeCompanyVO;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.fastway.DisposeApplyService;
import com._360pai.core.utils.ServiceMessageUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-26 12:38
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class DisposeApplyProvider implements DisposeApplyFacade {

    @Autowired
    private DisposeApplyService disposeLawyerService;
    @Autowired
    private ServiceMessageUtils serviceMessageUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;

    @Override
    public int lawyerApplyDispose(DisposeApplyReq.LawyerApplyReq req, Integer accountId) {
        req.setPartyId(getUserPartyId(accountId));
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.DisposeType.LAWYER, req);
        TUser param = new TUser();
        param.setCertificateNumber(req.getCardNo());
        checkAccountIdApply(accountId, FastwayEnum.DisposeType.LAWYER);
//        List<TUser> list = userService.findUser(param);
//        if (list != null && !list.isEmpty()) {
//            throw new BusinessException(ExceptionEnumImpl.IDCARD_HAS_AUTH);
//        }
        if (StringUtils.isNotBlank(req.getWorkedYear())) {
            try {
                new BigDecimal(req.getWorkedYear());
            } catch (NumberFormatException e) {
                throw new BusinessException("请正确填写工作年限");
            }
        }
        int id = disposeLawyerService.lawyerApply(accountId, json, req.getSource(), req.getPartyId());
        if (id > 0) {
            serviceMessageUtils.fastwayDisposeApply(id);
        }
        return id;
    }

    @Override
    public int lawOfficeApplyDispose(DisposeApplyReq.LawOfficeApplyReq req, Integer accountId) {
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.DisposeType.LAW_OFFICE, req);
//        checkAccountIdApply(accountId, FastwayEnum.DisposeType.LAW_OFFICE, req.getPartyId());
//        TCompany company = companyService.findCompanyByLicence(req.getSocialCreditCode());
//        if (company != null) {
//            throw new BusinessException(ExceptionEnumImpl.COMPANY_HAS_AUTH);
//        }
        int id = disposeLawyerService.lawOfficeApply(accountId, json, req.getSource(), req.getPartyId());
        return id;
    }

    @Override
    public int sendDocByEmail(String[] emailAddress) {
        return disposeLawyerService.sendDocByEmail(emailAddress);
    }

    @Override
    public Map<String, Object> getLawyerAuthInfoByMobile(String mobile) {
        return disposeLawyerService.getLawyerAuthInfoByMobile(mobile);
    }

    @Override
    public List<DisposeCompanyVO> getLawOfficeAuthInfoByMobile(String mobile) {
        return disposeLawyerService.getLawOfficeAuthInfoByMobile(mobile);
    }

    private void checkAccountIdApply(Integer accountId, String disposeType, Integer partyId) {
        TFastwayDisposeApplyCondition condition = new TFastwayDisposeApplyCondition();
        condition.setAccountId(accountId);
        condition.setApplyStatus(FastwayEnum.DisposeStatusEnum.waitting.getKey());
        condition.setPartyId(partyId);
        condition.setApplyType(disposeType);
        condition.setIsDel(false);
        List<TFastwayDisposeApply> byCondition = disposeLawyerService.findByCondition(condition);
        if (CollectionUtils.isNotEmpty(byCondition)) {
            throw new BusinessException("您的申请已提交，请等待审核");
        }
    }

    private void checkAccountIdApply(Integer accountId, String disposeType) {
        checkAccountIdApply(accountId, disposeType, null);
    }

    private Integer getUserPartyId(Integer accountId) {
        TUser userByAccountId = userService.findUserByAccountId(accountId);
        return userByAccountId == null ? null : userByAccountId.getId();
    }
}
