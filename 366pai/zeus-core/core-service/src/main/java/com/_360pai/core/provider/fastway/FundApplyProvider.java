package com._360pai.core.provider.fastway;

import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.facade.fastway.FundApplyFacade;
import com._360pai.core.facade.fastway.req.FundApplyReq;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.service.fastway.FundApplyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-12-07 09:49
 */
@Component
@Service(version = "1.0.0")
public class FundApplyProvider implements FundApplyFacade {

    @Autowired
    private FundApplyService fundApplyService;

    @Override
    public int userApplyFund(FundApplyReq.UserApplyReq req, Integer accountId) {
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.FundType.User, req.getUserFundVO());
        return fundApplyService.userApply(json, accountId, req.getSource(), req.getPartyId());
    }

    @Override
    public int companyApplyFund(FundApplyReq.CompanyApplyReq req, Integer accountId) {
        JSONObject json = new JSONObject();
        CompanyFundDetailVO companyFundVO = req.getCompanyFundVO();
        companyFundVO.setContactPerson(companyFundVO.getContactName());
        companyFundVO.setContactPhone(companyFundVO.getMobile());
        req.setCompanyFundVO(companyFundVO);
        json.put(FastwayEnum.FundType.Company, req.getCompanyFundVO());
        return fundApplyService.companyApply(json, accountId, req.getSource(), req.getPartyId());
    }

    @Override
    public Map<String, Object> fundAuthInfo(Integer accountId, String type) {
        // 个人
        // 企业
        Map<String, Object> result = new HashMap<>(2);

        if ("user".equals(type)) {
            result = fundApplyService.userFundAuthInfo(accountId);
        } else if ("company".equals(type)) {
            result = fundApplyService.companyFundAuthInfo(accountId);
        }

        return result;
    }


}
