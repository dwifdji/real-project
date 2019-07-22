package com.tzCloud.core.service.impl;

import com.tzCloud.arch.common.constant.SystemConstant;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.utils.PasswordEncryption;
import com.tzCloud.arch.common.utils.RandomNumberGenerator;
import com.tzCloud.core.condition.account.TAccountCondition;
import com.tzCloud.core.constant.AccountEnum;
import com.tzCloud.core.dao.account.TAccountDao;
import com.tzCloud.core.dao.account.TAccountMembershipCardDao;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.core.model.account.TAccount;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.core.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * @author xdrodger
 * @Title: AccountServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 13:10
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TAccountMembershipCardDao tAccountMembershipCardDao;

    @Override
    public TAccount findBy(String mobile) {
        TAccountCondition condition = new TAccountCondition();
        condition.setMobile(mobile);
        return accountDao.selectFirst(condition);
    }

    @Override
    public TAccount findBy(Integer id) {
        TAccountCondition condition = new TAccountCondition();
        condition.setId(id);
        return accountDao.selectFirst(condition);
    }

    @Override
    public TAccount register(PlatformAccountReq.RegisterReq req) {
        TAccount account = new TAccount();
        String encryptedPassword = null;
        try {
            encryptedPassword = PasswordEncryption.getEncryptedPassword(req.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        //加密密码
        account.setPassword(encryptedPassword);
        account.setMobile(req.getMobile());
        if (StringUtils.isEmpty(req.getHeadImgUrl())) {
            account.setHeadImgUrl(SystemConstant.DEFAULT_HEAD_IMG_URL);
        } else {
            account.setHeadImgUrl(req.getHeadImgUrl());
        }
        if (StringUtils.isEmpty(req.getNickName())) {
            account.setNickName("tZCloud" + RandomNumberGenerator.numberGenerator(6));
        } else {
            account.setNickName(req.getNickName());
        }
        account.setInviteCode(req.getInviteCode());
        if (StringUtils.isNotBlank(req.getRegisterSource())) {
            account.setRegisterSource(req.getRegisterSource());
        } else {
            account.setRegisterSource(AccountEnum.RegisterSource.PLATFORM.getKey());
        }
        int result = accountDao.insert(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return account;
    }

    @Override
    public boolean hasRegister(String mobile) {
        TAccount account = findBy(mobile);
        if (account != null) {
            return true;
        }
        return false;
    }

    @Override
    public int updateAccount(TAccount account) {
        return accountDao.updateById(account);
    }

    @Override
    public boolean modifyPassword(Integer id, String password) {
        TAccount account = new TAccount();
        account.setId(id);
        String encryptedPassword = null;
        try {
            encryptedPassword = PasswordEncryption.getEncryptedPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        //加密密码
        account.setPassword(encryptedPassword);
        int result = accountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return true;
    }

    @Override
    public List<TAccountMembershipCard> findMembershipCard(Integer id) {
        return tAccountMembershipCardDao.findAvailableCard(id);
    }
}
