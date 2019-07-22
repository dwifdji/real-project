package com.winback.core.service.account;

import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.resp.AppletAccountResp;
import com.winback.core.facade.account.vo.Account;
import com.winback.core.facade.account.vo.Party;
import com.winback.core.facade.account.vo.ProjectManager;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AccountService
 * @ProjectName winback
 * @Description:
 * @date 2019/1/16 15:55
 */
public interface AccountService {
    TAccount findByMobile(String mobile);

    TAccount findById(Integer id);

    TAccount register(AppAccountReq.RegisterReq req);

    boolean modifyPassword(Integer id, String password);

    boolean update(TAccount account);

    PageInfoResp<Account> getAccountListByPage(AdminAccountReq.AccountQueryReq req);

    PageInfoResp<Party> getPartyListByPage(AdminAccountReq.PartyQueryReq req);

    TAccountExtBind findAppletByOpenId(String openId);

    TAccountExtBind findAppletExtBind(Integer id);

    int saveAppletExtBind(TAccountExtBind accountExtBind);

    int updateAppletExtBind(TAccountExtBind accountExtBind);

    AppletAccountResp.AccountResp bindAccount(AppletAccountReq.BindAccountReq req);

    List<WorkBenchVO> getTodayRole(String searchDay);

    String getRealName(Integer accountId);

    String getAppRealName(Integer accountId);

    void uploadContacts(AppAccountReq.UploadContactsReq req);

    Integer updateAccount(AdminAccountReq.AccountUpdateReq req);

    PageInfoResp<ProjectManager> getProjectManagerListByPage(AdminAccountReq.AccountQueryReq req);

    ProjectManager getProjectManager(AdminAccountReq.AccountQueryReq req);
}
