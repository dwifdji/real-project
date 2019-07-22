package com._360pai.core.facade.fastway;

import com._360pai.core.facade.fastway.req.FundApplyReq;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-12-07 09:45
 */
public interface FundApplyFacade {

    /**
     * 个人用户认证资金服务商
     * @param req
     * @param accountId
     * @return
     */
    int userApplyFund(FundApplyReq.UserApplyReq req, Integer accountId);

    /**
     * 企业用户认证资金服务商
     * @param req
     * @param accountId
     * @return
     */
    int companyApplyFund(FundApplyReq.CompanyApplyReq req, Integer accountId);

    /**
     * 检测资金服务商认证信息
     * @param accountId
     * @return
     */
    Map<String, Object> fundAuthInfo(Integer accountId, String type);
}
