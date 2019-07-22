package com._360pai.core.facade.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.fastway.req.FundApplyReq;
import com._360pai.core.facade.fastway.resp.CompanyFundDetailVO;
import com._360pai.core.facade.fastway.resp.UserFundDetailVO;

/**
 * @author xiaolei
 * @create 2018-12-07 11:20
 */
public interface FundApplyAdminFacade {
    /**
     * admin获取资金服务商快速申请列表
     * @param req
     * @return
     */
    PageInfoResp findFundApplyByParam(FundApplyReq.FundFindReq req);

    /**
     *  admin 根据id 获取用户资金服务商详情
     * @param applyId
     * @return
     */
    UserFundDetailVO findUserFundDetailById(Integer applyId);

    /**
     *  admin 根据id 获取企业资金服务商详情
     * @param applyId
     * @return
     */
    CompanyFundDetailVO findCompanyFundDetailById(Integer applyId);

    /**
     *  admin 个人保存更新
     * @param req
     * @return
     */
    int userUpdate(FundApplyReq.UserUpdateReq req, Integer operatorId);

    /**
     *  admin 企业保存更新
     * @param req
     * @return
     */
    int companyUpdate(FundApplyReq.CompanyUpdateReq req, Integer operatorId);

    /**
     * user 审核通过
     * @param req
     * @param operatorId
     * @return
     */
    int userVerifyAccess(FundApplyReq.UserUpdateReq req, Integer operatorId);

    /**
     *  user 审核拒绝
     * @param req
     * @param operatorId
     * @return
     */
    int userVerifyDeny(FundApplyReq.UserUpdateReq req, Integer operatorId);

    /**
     * company 审核通过
     * @param req
     * @param operatorId
     * @return
     */
    int companyVerifyAccess(FundApplyReq.CompanyUpdateReq req, Integer operatorId);

    /**
     *  company 审核拒绝
     * @param req
     * @param operatorId
     * @return
     */
    int companyVerifyDeny(FundApplyReq.CompanyUpdateReq req, Integer operatorId);
}
