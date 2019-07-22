package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.CompanyReq;
import com._360pai.core.facade.account.resp.CompanyResp;
import com._360pai.core.facade.account.vo.CompanyApplyRecordVo;
import com._360pai.core.facade.account.vo.CompanyMemberVo;
import com._360pai.core.facade.account.vo.CompanyVo;
import com._360pai.core.model.account.TCompany;

import java.util.List;

public interface CompanyService{

    TCompany findCompanyById(Integer companyId);

    TCompany findCompanyByLicence(String  licence);

    List<TCompany> findCompanyByAccountId(Integer accountId);

    List<TCompany> findCompanyByMobile(String mobile);

    int updateCompanyById(TCompany company);

    boolean saveCompany(TCompany company);

    PageInfoResp<CompanyApplyRecordVo> getCompanyApplyRecordListByPage(AccountReq.QueryReq req);

    CompanyApplyRecordVo getCompanyApplyRecordById(AccountReq.BaseReq req);

    PageInfoResp<CompanyVo> getCompanyListByPage(CompanyReq.QueryReq req);

    CompanyVo getCompanyById(AccountReq.BaseReq req);

    CompanyResp updateCompany(CompanyReq.UpdateReq req);

    CompanyResp createChannelPayCompany(CompanyReq.CreateChannelPayCompanyReq req);

    CompanyResp companySetChannelPay(CompanyReq.BaseReq req);

    PageInfoResp<CompanyMemberVo> getCompanyMemberList(CompanyReq.BaseReq req);

    CompanyResp addCompanyMember(CompanyReq.AddMemberReq req);

    CompanyResp deleteCompanyMember(CompanyReq.DeleteMemberReq req);

    int addAccountCompanyMap(Integer accountId, Integer companyId, String name);

    CompanyResp changeAdmin(CompanyReq.ChangeAdminReq req);
    TCompany findCompanyByMemCode(String memCode);}