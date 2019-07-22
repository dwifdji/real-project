package com._360pai.web.controller;

import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.OSUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.resp.*;
import com._360pai.core.facade.lease.LeaseFacade;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com._360pai.web.controller.account.resp.AccountSimpleVo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by RuQ on 2018/8/19 18:16
 */
@Controller
public class AbstractController {

    @Resource
    private RedisCachemanager redisCachemanager;


    /**
     * 日志
     */

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);


    /**
     * 用户信息服务
     */
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;


    @Reference(version = "1.0.0")
    private LeaseFacade leaseFacade;

    /**
     * 当前的HttpServletRequest
     */
    @Autowired
    private HttpServletRequest curRequest;

    /**
     * 获取curRequest
     *
     * @return the curRequest
     */
    protected HttpServletRequest getCurRequest() {
        return curRequest;
    }

    /**
     * 获取当前请求者Ip
     *
     * @return loginUserIp
     */
    protected String getCurRequestIp() {
        return OSUtil.getIpAddress(curRequest);
    }

    protected String getSelfIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 获取当前登录的User 找不到user会抛出用户不存在的JJJR2BusinessException
     *
     * @return loadCurLoginUser
     */


    protected final AccountBaseInfo loadCurLoginAccountInfo() {
        Cookie[] cookies = curRequest.getCookies();
        AccountBaseInfo accountBaseInfo = new AccountBaseInfo();
        int cookieId = -1;
        String type = "";
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (SystemConstant.COOKIE_ACCOUNT_ID_NAME.equals(cookie.getName())) {
                    cookieId = Integer.parseInt(cookie.getValue());
                }
            }
            if (cookieId == -1) {
                return accountBaseInfo;
            }

            Integer accountId = cookieId;
            //仅是  account 用户
            AccountResp resp = accountFacade.getAccount((long) cookieId);

            //租赁权信息
            accountBaseInfo = setLeaseInfo(accountId,accountBaseInfo,resp);
            accountBaseInfo.setAgencyId(resp.getAgencyId());
            accountBaseInfo.setShopId(resp.getShopId());
            accountBaseInfo.setAgencyStatus(accountFacade.getAgencyApplyStatus(accountId, null, resp.getAgencyId()));
            accountBaseInfo.setFundStatus(accountFacade.getFundApplyStatus(accountId, null, FastwayEnum.FundType.User));
            if (resp.getCurrentPartyId() == null || resp.getCurrentPartyId() <= 0) {
                accountBaseInfo.setAccountId(cookieId);
                accountBaseInfo.setDefaultAgencyId(resp.getDefaultAgencyId());
                accountBaseInfo.setMobile(resp.getMobile());
                accountBaseInfo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
                accountBaseInfo.setName(resp.getMobile());
                accountBaseInfo.setAccountAuth(false);
                accountBaseInfo.setBank(false);
                accountBaseInfo.setOperWithoutFadada(false);
                accountBaseInfo.setOperOffline(false);
                accountBaseInfo.setDisposerStatus(accountFacade.getDisposerApplyStatusByAccountId(cookieId,"accountId"));
            } else {
                AccountBaseDto dto = accountFacade.getAccoutBaseByPartyId(resp.getCurrentPartyId());
                BeanUtils.copyProperties(dto, accountBaseInfo);
                accountBaseInfo.setAccountId(cookieId);
                accountBaseInfo.setIs_pay_bind(dto.isPayBind());
                accountBaseInfo.setIs_channel(dto.isChannel());
                if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(dto.getType())
                        && String.valueOf(dto.getAccountId()).equals(String.valueOf(cookieId))){
                    accountBaseInfo.setAdmin(true);
                }
                List<AccountSimpleVo> voList =new ArrayList<AccountSimpleVo>();
                UserResp userResp = accountFacade.getUserByAccountId(cookieId);
                if(userResp != null && userResp.getId() != null){
                    AccountSimpleVo vo = new AccountSimpleVo();
                    vo.setType(SystemConstant.ACCOUNT_USER_TYPE);
                    vo.setName(userResp.getName());
                    vo.setPartyId(String.valueOf(userResp.getId()));
                    if(String.valueOf(userResp.getId()).equals(accountBaseInfo.getPartyPrimaryId()+"")){
                        vo.setDefault(true);
                    }
                    vo.setDisposerStatus(accountFacade.getDisposerApplyStatus(userResp.getId()));
                    vo.setFundStatus(accountFacade.getFundApplyStatus(accountId, userResp.getId(), FastwayEnum.FundType.User));
                    vo.setAddress(userResp.getAddress());
                    vo.setCityVo(userResp.getCityVo());
                    voList.add(vo);
                    accountBaseInfo.setIsUserAuth(true);
                } else {
                    accountBaseInfo.setIsUserAuth(false);
                }
                List<CompanyResp> employCompanyList = accountFacade.getCompanyListByEmployAccountId(accountBaseInfo.getAccountId());
                for(CompanyResp companyResp : employCompanyList){
                    AccountSimpleVo vo = new AccountSimpleVo();
                    vo.setName(companyResp.getName());
                    vo.setPartyId(String.valueOf(companyResp.getId()));
                    vo.setType(SystemConstant.ACCOUNT_COMPANY_TYPE);
                    if(String.valueOf(companyResp.getId()).equals(accountBaseInfo.getPartyPrimaryId()+"")){
                        vo.setDefault(true);
                    }
                    vo.setDisposerStatus(accountFacade.getDisposerApplyStatus(companyResp.getId()));
                    vo.setFundStatus(accountFacade.getFundApplyStatus(accountId, companyResp.getId(), FastwayEnum.FundType.Company));
                    voList.add(vo);
                }
                if(voList.isEmpty()){
                    AccountSimpleVo vo = new AccountSimpleVo();
                    vo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
                    vo.setName(accountBaseInfo.getMobile());
                    vo.setDefault(true);
                    voList.add(vo);
                }
                accountBaseInfo.setAccountList(voList);
            }
            accountBaseInfo.setDefaultAgencyName(accountFacade.getAgencyById(accountBaseInfo.getDefaultAgencyId()).getName());
            return accountBaseInfo;
        }

        LOGGER.warn("未登录或者没有获取到Cookie,不允许调用此方法");
        return new AccountBaseInfo();
    }

    private AccountBaseInfo setLeaseInfo(Integer accountId, AccountBaseInfo accountBaseInfo,AccountResp accountResp) {

        accountBaseInfo.setLeaseAuditFlag(false);
        accountBaseInfo.setLeaseReleaseFlag(false);

        //根据accoutId 查询租赁权信息
        List<LeaseStaffResp>  respList = leaseFacade.getLeaseStaffInfoByAccountId(accountId);

        for(LeaseStaffResp resp : respList){
            accountBaseInfo.setLeaseAuditFlag(resp.getFinalFlag()||resp.getTrialFlag());

            if(accountResp.getCurrentPartyId()!=null&&accountResp.getCurrentPartyId().equals(resp.getPartId())){
                accountBaseInfo.setLeaseReleaseFlag(resp.getAgentFlag());
                accountBaseInfo.setLeaseAuditFlag(resp.getFinalFlag()||resp.getTrialFlag());
            }

        }

        if(respList.size()>0){
            CompanyResp companyResp = accountFacade.getCompanyByCompanyId(respList.get(0).getComId());
            accountBaseInfo.setLeaseComId(respList.get(0).getComId());
            accountBaseInfo.setLeaseStaffId(respList.get(0).getId());
            if(companyResp!=null){
                accountBaseInfo.setLeaseComName(companyResp.getName());
                accountBaseInfo.setLeaseComAddress(companyResp.getAddress());
            }

        }


        return accountBaseInfo;

    }

    protected String cacheSendSmskey(String pre, String mobile, String type) {
        return pre + mobile + "_" + type;
    }

    /**
     * 校验验证码
     */
    protected boolean checkSmsCode(String mobile, String smsType, String smsCode) {
        Object code = redisCachemanager.get(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, smsType));
        String redisSmsCode = String.valueOf(code);
        return smsCode.equals(redisSmsCode);
    }

    /**
     * 清除验证码
     */
    protected void removeSmsCode(String mobile, String smsType) {
        redisCachemanager.del(cacheSendSmskey(SystemConstant.CACHE_KEY_PREFIX_PHONE_VERIFY_CODE, mobile, ""));
    }


    protected void isAuth() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        boolean accountAuth = accountBaseInfo.isAccountAuth();
        if (!accountAuth) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请前往个人中心认证");
        }
    }


}
