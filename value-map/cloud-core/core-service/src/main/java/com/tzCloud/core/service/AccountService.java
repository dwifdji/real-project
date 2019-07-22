package com.tzCloud.core.service;

import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.core.model.account.TAccount;
import com.tzCloud.core.model.account.TAccountMembershipCard;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AccountService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 13:09
 */
public interface AccountService {
    TAccount findBy(String mobile);

    TAccount findBy(Integer id);

    TAccount register(PlatformAccountReq.RegisterReq req);

    boolean hasRegister(String mobile);

    int updateAccount(TAccount account);

    boolean modifyPassword(Integer id, String password);

    List<TAccountMembershipCard> findMembershipCard(Integer id);
}
