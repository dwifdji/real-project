package com.winback.core.service.account.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.PasswordEncryption;
import com.winback.arch.common.utils.RandomNumberGenerator;
import com.winback.arch.core.sysconfig.properties.SystemProperties;
import com.winback.core.commons.constants.AccountEnum;
import com.winback.core.commons.constants.PushEnum;
import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.dao._case.TCaseDao;
import com.winback.core.dao._case.TCaseProjectManagerMapDao;
import com.winback.core.dao.account.*;
import com.winback.core.dao.connect.TConnectRoomPersonDao;
import com.winback.core.dao.contract.TAppletContractShoppingCartDao;
import com.winback.core.dao.contract.TContractShoppingCartDao;
import com.winback.core.dao.contract.TFavoriteContractDao;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.account.resp.AppletAccountResp;
import com.winback.core.facade.account.vo.Account;
import com.winback.core.facade.account.vo.Contacts;
import com.winback.core.facade.account.vo.Party;
import com.winback.core.facade.account.vo.ProjectManager;
import com.winback.core.facade.contract.vo.Contract;
import com.winback.core.model._case.TCase;
import com.winback.core.model._case.TCaseProjectManagerMap;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TAccountContacts;
import com.winback.core.model.account.TAccountExtBind;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.core.model.contract.TAppletContractShoppingCart;
import com.winback.core.model.contract.TContractShoppingCart;
import com.winback.core.service.account.AccountService;
import com.winback.core.service.assistant.ExceptionService;
import com.winback.core.service.assistant.SmsService;
import com.winback.core.utils.RespConvertUtil;import com.winback.core.vo.finance.WorkBenchVO;import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * @author xdrodger
 * @Title: AccountServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/16 15:55
 */
@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TFranchiseeDao franchiseeDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private TFavoriteContractDao favoriteContractDao;
    @Autowired
    private TContractShoppingCartDao shoppingCartDao;
    @Autowired
    private TAppletContractShoppingCartDao appletShoppingCartDao;
    @Autowired
    private TLawyerDao lawyerDao;
    @Autowired
    private TCaseDao caseDao;
    @Autowired
    private ExceptionService exceptionService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private TAccountContactsDao accountContactsDao;
    @Autowired
    private TCaseProjectManagerMapDao caseProjectManagerMapDao;
    @Autowired
    private TConnectRoomPersonDao connectRoomPersonDao;

    @Override
    public TAccount findByMobile(String mobile) {
        return accountDao.findByMobile(mobile);
    }

    @Override
    public TAccount findById(Integer id) {
        return accountDao.selectById(id);
    }

    @Override
    public TAccount register(AppAccountReq.RegisterReq req) {
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
            account.setNickName("WIN" + RandomNumberGenerator.numberGenerator(6));
        } else {
            account.setNickName(req.getNickName());
        }
        account.setInviteCode(req.getInviteCode());
        if (StringUtils.isNotBlank(req.getRegisterSource())) {
            account.setRegisterSource(req.getRegisterSource());
        } else {
            account.setRegisterSource(AccountEnum.RegisterSource.APP.getKey());
        }
        //account.setSource();
        int result = accountDao.insert(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return account;
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
    public boolean update(TAccount account) {
        int result = accountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return true;
    }

    @Override
    public PageInfoResp<Account> getAccountListByPage(AdminAccountReq.AccountQueryReq req) {
        PageInfoResp<Account> resp = new PageInfoResp<>();
        List<Account> resultList = new ArrayList<>();
        PageInfo<TAccount> pageInfo = accountDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "a.id desc");
        for (TAccount item : pageInfo.getList()) {
            try {
                Account vo = RespConvertUtil.convertToAccount(item);
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<Party> getPartyListByPage(AdminAccountReq.PartyQueryReq req) {
        PageInfoResp<Party> resp = new PageInfoResp<>();
        List<Party> resultList = new ArrayList<>();
        PageInfo<Party> pageInfo = accountDao.getPartyList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "a.id desc");
        for (Party item : pageInfo.getList()) {
            try {
                Party vo = new Party();
                BeanUtils.copyProperties(item, vo);
                vo.setRegisterSourceDesc(AccountEnum.RegisterSource.getValueByKey(vo.getRegisterSource()));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public TAccountExtBind findAppletByOpenId(String openId) {
        return accountExtBindDao.findAppletByOpenId(openId);
    }

    @Override
    public TAccountExtBind findAppletExtBind(Integer id) {
        return accountExtBindDao.selectById(id);
    }

    @Override
    public int saveAppletExtBind(TAccountExtBind accountExtBind) {
        return accountExtBindDao.insert(accountExtBind);
    }

    @Override
    public int updateAppletExtBind(TAccountExtBind accountExtBind) {
        return accountExtBindDao.updateById(accountExtBind);
    }

    @Transactional
    @Override
    public AppletAccountResp.AccountResp bindAccount(AppletAccountReq.BindAccountReq req) {
        AppletAccountResp.AccountResp resp = new AppletAccountResp.AccountResp();
        TAccountExtBind accountExtBind = accountExtBindDao.selectById(req.getLoginId());
        if (accountExtBind == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAccount account = accountDao.findByMobile(req.getMobile());
        if (account == null) {
            AppAccountReq.RegisterReq registerReq = new AppAccountReq.RegisterReq();
            registerReq.setMobile(req.getMobile());
            registerReq.setPassword(systemProperties.getDefaultRegisterPassword());
            registerReq.setNickName(accountExtBind.getNickName());
            registerReq.setHeadImgUrl(accountExtBind.getHeadImgUrl());
            registerReq.setRegisterSource(AccountEnum.RegisterSource.APPLET.getKey());
            account = register(registerReq);
            smsService.sendDefaultPasswordSms(req.getMobile(), systemProperties.getDefaultRegisterPassword());
        }
        if (accountExtBind.getAccountId() != null) {
            if (accountExtBind.getAccountId().equals(account.getId())) {
                throw new BusinessException("请不要重复绑定");
            }
            throw new BusinessException("已绑定手机号");
        }
        TAccountExtBind accountExtBind2 = accountExtBindDao.findAppletByAccountId(account.getId());
        if (accountExtBind2 != null) {
            if (accountExtBind2.getId().equals(accountExtBind.getId())) {
                throw new BusinessException("请不要重复绑定");
            }
            throw new BusinessException("该手机号已绑定");
        }
        accountExtBind.setAccountId(account.getId());
        int result = accountExtBindDao.updateById(accountExtBind);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        favoriteContractDao.migrateFromAppletToApp(accountExtBind.getId(), accountExtBind.getAccountId());
        TAppletContractShoppingCart appletShoppingCart = appletShoppingCartDao.findBy(accountExtBind.getId());
        if (appletShoppingCart == null) {
            appletShoppingCart = appletShoppingCartDao.create(accountExtBind.getId());
        }
        TContractShoppingCart shoppingCart = shoppingCartDao.findBy(accountExtBind.getAccountId());
        if (shoppingCart == null) {
            shoppingCart = shoppingCartDao.create(accountExtBind.getAccountId());
        }
        shoppingCartDao.migrateFromAppletToApp(appletShoppingCart.getId(), shoppingCart.getId());
        resp.setLoginId(req.getLoginId());
        return resp;
    }

    @Override
    public List<WorkBenchVO> getTodayRole(String searchDay) {

        return accountDao.getTodayRole(searchDay);
    }

    @Override
    public String getRealName(Integer accountId) {
        TLawyer lawyer = lawyerDao.findByAccountId(accountId);
        if (lawyer != null) {
            return lawyer.getName();
        }
        TAccount account = accountDao.selectById(accountId);
        if (account != null) {
            if(!StringUtils.isEmpty(account.getNickName())){
                return account.getNickName();
            }else{
                return account.getMobile();
            }
        }
        return "";
    }

    @Override
    public String getAppRealName(Integer accountId) {
        TLawyer lawyerUSser = lawyerDao.findByAccountId(accountId);
        if (lawyerUSser != null) {
            return lawyerUSser.getName()+"律师";
        }
        TAccount account = accountDao.selectById(accountId);
        if (account != null) {
            return account.getNickName();
        }
        return account.getMobile();
    }

    @Override
    public void uploadContacts(AppAccountReq.UploadContactsReq req) {
        List<Contacts> list = req.getList();
        Set<String> dbSets = new HashSet<>();
        Set<String> reqSets = new HashSet<>();
        List<TAccountContacts> dbList = accountContactsDao.find(req.getLoginId());
        for (TAccountContacts item : dbList) {
            dbSets.add(item.getTelephone());
        }
        List<TAccountContacts> insertList = new ArrayList<>();

        for (Contacts item : list) {
            if (StringUtils.isBlank(item.getName()) || StringUtils.isBlank(item.getTelephone())) {
                continue;
            }
            if (dbSets.contains(item.getTelephone())) {
                continue;
            }
            if (reqSets.contains(item.getTelephone())) {
                continue;
            }
            reqSets.add(item.getTelephone());
            TAccountContacts accountContacts = new TAccountContacts();
            accountContacts.setAccountId(req.getLoginId());
            accountContacts.setName(item.getName());
            accountContacts.setTelephone(item.getTelephone());
            insertList.add(accountContacts);
        }
        if (insertList.size() > 0) {
            accountContactsDao.batchSave(insertList);
        }
    }

    @Transactional
    @Override
    public Integer updateAccount(AdminAccountReq.AccountUpdateReq req) {
        TAccount account = accountDao.selectById(req.getId());
        if (account == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAccount updateAccount = new TAccount();
        updateAccount.setId(req.getId());
        updateAccount.setProjectManagerFlag(req.getProjectManagerFlag());
        updateAccount.setUpdateTime(new Date());
        int result = accountDao.updateById(updateAccount);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (account.getProjectManagerFlag() && !req.getProjectManagerFlag()) {
            // 取消项目经理
            List<TCaseProjectManagerMap> list = caseProjectManagerMapDao.findBy(req.getId());
            for (TCaseProjectManagerMap item : list) {
                item.setDeleteFlag(true);
                item.setUpdateTime(new Date());
                result = caseProjectManagerMapDao.updateById(item);
                if (result == 0) {
                    throw new BusinessException(ApiCallResult.FAILURE);
                }
                TConnectRoomPersonCondition condition = new TConnectRoomPersonCondition();
                TCase tCase = caseDao.getCaseByCaseId(item.getCaseId());
                condition.setCaseId(tCase.getId());
                condition.setRelevanceId(item.getAccountId());
                condition.setType(PushEnum.PUSH_PERSON_TYPE.MANAGE.getType());
                TConnectRoomPerson roomPerson = connectRoomPersonDao.selectFirst(condition);
                //不为空时 直接
                if(roomPerson !=null){
                    roomPerson.setDeleteFlag(true);
                    roomPerson.setUpdateTime(new Date());
                    connectRoomPersonDao.updateById(roomPerson);
                }
            }
        }
        return req.getId();
    }

    @Override
    public PageInfoResp<ProjectManager> getProjectManagerListByPage(AdminAccountReq.AccountQueryReq req) {
        req.setProjectManagerFlag(true);
        PageInfoResp<ProjectManager> resp = new PageInfoResp<>();
        List<ProjectManager> resultList = new ArrayList<>();
        PageInfo<TAccount> pageInfo = accountDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "a.id desc");
        for (TAccount item : pageInfo.getList()) {
            try {
                ProjectManager vo = RespConvertUtil.convertToProjectManager(item);
                vo.setAllocatedCaseNum(caseProjectManagerMapDao.countCaseNum(item.getId()));
                vo.setSuccessfulCaseNum(caseProjectManagerMapDao.countSuccessCaseNum(item.getId()));
                resultList.add(vo);
            } catch (Exception e) {
                e.printStackTrace();
                exceptionService.processTryCatchException(item.getId(), e);
            }
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public ProjectManager getProjectManager(AdminAccountReq.AccountQueryReq req) {
        TAccount account = accountDao.selectById(req.getId());
        ProjectManager projectManager = RespConvertUtil.convertToProjectManager(account);

        return projectManager;
    }
}
