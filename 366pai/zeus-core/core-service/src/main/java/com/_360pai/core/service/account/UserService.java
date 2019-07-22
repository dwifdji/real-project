package com._360pai.core.service.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.UserReq;
import com._360pai.core.facade.account.resp.UserResp;
import com._360pai.core.facade.account.vo.UserApplyRecordVo;
import com._360pai.core.facade.account.vo.UserVo;
import com._360pai.core.model.account.TUser;

import java.util.List;

public interface UserService{

    TUser findUserByNameAndIdCard(String name,String idCard);

    TUser findUserById(Integer userId);

    TUser findUserByMobile(String mobile);

    TUser findUserByAccountId(Integer accountId);

    List<TUser> findUser(TUser tUser);

    int updateUserById(TUser user);

    boolean saveUser(TUser user);

    PageInfoResp<UserApplyRecordVo> getUserApplyRecordListByPage(AccountReq.QueryReq req);

    UserApplyRecordVo getUserApplyRecordById(AccountReq.BaseReq req);

    PageInfoResp<UserVo> getUserListByPage(UserReq.QueryReq req);

    UserVo getUserById(AccountReq.BaseReq req);

    UserResp updateUser(UserReq.UpdateReq req);
}