package com._360pai.core.provider.lease;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.condition.lease.TLeaseStaffCondition;
import com._360pai.core.facade.account.resp.LeaseStaffResp;
import com._360pai.core.facade.lease.LeaseFacade;
import com._360pai.core.facade.lease.req.LeaseReq;
import com._360pai.core.facade.lease.vo.LeaseStaffVO;
import com._360pai.core.facade.order.ServiceOrderCallBackFacade;
import com._360pai.core.model.account.TAccount;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.lease.TLeaseStaff;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.PartyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.lease.LeaseStaffService;
import com._360pai.core.service.order.ServiceOrderService;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.facade.FddSignatureFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 租赁权拍卖
 *
 */
@Component
@Service(version = "1.0.0")
public class LeaseProvider implements LeaseFacade {

    @Autowired
    private LeaseStaffService leaseStaffService;


    @Autowired
    private AccountService accountService;

    @Autowired
    private CompanyService companyService;

    @Resource
    private PartyService partyService;

    @Autowired
    private UserService userService;



    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    @Override
    @Transactional
    public ResponseModel saveLeaseStaff(LeaseReq.LeaseStaff req) {

        //根据手机号获取注册信息
        TAccount account = accountService.findAccountByMobile(req.getMobile());

        //校验手机号是否重复了
        if(checkMobile(req)){
            return ResponseModel.fail("手机号重复！");
        }

        if(account==null){
            return ResponseModel.fail("该手机号未注册平台账号！");
        }
        //获取公司信息
        TCompany company = companyService.findCompanyById(Integer.valueOf(req.getComId()));
        if(company==null){
            return ResponseModel.fail("未查询到公司信息！");
        }

        Integer partyId = -1;
        TUser userInfo = userService.findUserByAccountId(account.getId());

        //经办人必须认证 并且开通了东方富通信息
        if(req.isAgentFlag()&&(userInfo==null||StringUtils.isEmpty(userInfo.getFadadaId()))){

            return ResponseModel.fail("经办人必须个人认证并且开通法大大！");

        }

        if(userInfo!=null){
            partyId = userInfo.getId();
         }

        TLeaseStaff leaseStaff = new TLeaseStaff();
        leaseStaff.setName(req.getName());
        leaseStaff.setMobile(req.getMobile());
        leaseStaff.setAgentFlag(req.isAgentFlag());
        leaseStaff.setTrialFlag(req.isTrialFlag());
        leaseStaff.setFinalFlag(req.isFinalFlag());
        leaseStaff.setForbidFlag(req.isForbidFlag());
        leaseStaff.setAccountId(account.getId());
        leaseStaff.setPartId(partyId);
        leaseStaff.setComId(company.getId());
        leaseStaff.setFadadaId(fddId(company,leaseStaff));
        if(leaseStaff.getAgentFlag() && StringUtils.isEmpty(leaseStaff.getFadadaId())) {
            return ResponseModel.fail("开通法大大失败！");
        }

        leaseStaffService.insert(leaseStaff);


        return ResponseModel.succ();
    }

    private Integer saveUser(LeaseReq.LeaseStaff req,Integer accountId) {

        //添加t_user 表
        TParty party = new TParty();
        party.setType("user");
        party.setCategory("NORMAL_USER");
        party.setApplySource("LEASE");
        partyService.saveParty(party);

        TUser user = new TUser();
        user.setAccountId(accountId);
        user.setId(party.getId());
        user.setName(req.getName());
        user.setMobile(req.getMobile());
        userService.saveUser(user);

        return  party.getId();

    }

    private boolean checkMobile(LeaseReq.LeaseStaff req) {

        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setMobile(req.getMobile());
        condition.setIsDelete(false);
        //condition.setComId(Integer.valueOf(req.getComId()));
        return  leaseStaffService.getLeaseStaffByCondition(condition) != null;

    }


    /**
     *
     *开通法大大id
     */
    private String fddId(TCompany company, TLeaseStaff leaseStaff) {

        //经办人开通法大大
        if(leaseStaff.getAgentFlag()){
            FOpenMemberReq fOpenMemberReq = new FOpenMemberReq();

            fOpenMemberReq.setCustomer_type("2");
            fOpenMemberReq.setId_card(company.getLicense());
            fOpenMemberReq.setCustomer_name(company.getName());
            fOpenMemberReq.setMobile(leaseStaff.getMobile());

            FOpenMemberResp resp = fddSignatureFacade.openMember(fOpenMemberReq);

            if(ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
                return resp.getCustomer_id();
            }
        }
        return null;
    }

    @Override
    public ResponseModel getLeaseStaffInfo(LeaseReq.LeaseStaffInfo req) {
        return ResponseModel.succ(leaseStaffService.getLeaseStaffById(Integer.valueOf(req.getId())));
    }

    @Override
    public ResponseModel getLeaseStaffList(LeaseReq.LeaseStaffInfo req) {


        PageInfo pageInfo = leaseStaffService.getLeaseStaffList(req);


        PageSerializable page = new PageSerializable();


        List<LeaseStaffVO>  list = getLeaseStaffVoList(pageInfo.getList());
        page.setTotal(pageInfo.getTotal());
        page.setList(list);

        return ResponseModel.succ(page);
    }

    private List<LeaseStaffVO> getLeaseStaffVoList(List<TLeaseStaff> list) {

        List<LeaseStaffVO> LeaseStaffList = new ArrayList<>();

        for(TLeaseStaff staff :list){
            LeaseStaffVO vo = new LeaseStaffVO();
            vo.setId(staff.getId());
            vo.setMobile(staff.getMobile());
            vo.setCreateTime(DateUtil.formatDate2Str(staff.getCreateTime(),DateUtil.NORM_DATETIME_PATTERN));
            vo.setName(staff.getName());
            vo.setStatusDesc(staff.getForbidFlag()?"启用":"禁用");
            vo.setRoleDesc(getRoleInfo(staff));
            LeaseStaffList.add(vo);

        }

        return LeaseStaffList;
    }

    private String getRoleInfo(TLeaseStaff staff) {

        StringBuffer sb = new StringBuffer();

        if(staff.getAgentFlag()){
            sb.append("经办人、");
        }
        if(staff.getTrialFlag()){
            sb.append("初审、");
        }

        if(staff.getFinalFlag()){
            sb.append("终审、");
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }

    @Override
    public ResponseModel updateAuditInfo(LeaseReq.LeaseStaffInfo req) {

        TCompany company = new TCompany();
        company.setId(Integer.valueOf(req.getComId()));
        company.setLeaseNum(Integer.valueOf(req.getAuditNum()));
        company.setUpdateTime(DateUtil.getSysTime());
        companyService.updateCompanyById(company);

        return ResponseModel.succ();
    }


    @Override
    public ResponseModel updateLeaseStaff(LeaseReq.LeaseStaff req) {


        TLeaseStaff staff =  leaseStaffService.getLeaseStaffById(Integer.valueOf(req.getId()));


        if(staff==null){
            return ResponseModel.fail("未获取该员工信息");
        }

        staff.setName(req.getName());
        staff.setTrialFlag(req.isTrialFlag());
        staff.setFinalFlag(req.isFinalFlag());
        staff.setForbidFlag(req.isForbidFlag());
        staff.setAgentFlag(req.isAgentFlag());

        //手机号改变 或者赋予经办人角色
        if(StringUtils.isNotEmpty(req.getMobile())&&!req.getMobile().equals(staff.getMobile())||(req.isAgentFlag()&&!staff.getAgentFlag())){

            //根据手机号获取注册信息
            TAccount account = accountService.findAccountByMobile(req.getMobile());

            //校验手机号是否重复了
            if(checkMobile(req)){
                return ResponseModel.fail("手机号重复！");

            }

            if(account==null){
                return ResponseModel.fail("该手机号未注册平台账号！");
            }


            Integer partyId = -1;
            TUser userInfo = userService.findUserByAccountId(account.getId());

            //经办人必须认证 并且开通了东方富通信息
            if(req.isAgentFlag()&&(userInfo==null||StringUtils.isEmpty(userInfo.getFadadaId()))){

                return ResponseModel.fail("经办人必须个人认证并且开通法大大！");
            }
            if(userInfo!=null){
                partyId = userInfo.getId();
            }

            TCompany company = companyService.findCompanyById(Integer.valueOf(req.getComId()));
            staff.setMobile(req.getMobile());
            staff.setAgentFlag(req.isAgentFlag());
            staff.setFadadaId(fddId(company,staff));
            staff.setPartId(partyId);

        }


        leaseStaffService.update(staff);

        return ResponseModel.succ();
    }

    @Override
    public List<LeaseStaffResp> getLeaseStaffInfoByAccountId(Integer accountId) {
        TLeaseStaffCondition condition = new TLeaseStaffCondition();
        condition.setAccountId(accountId);
        condition.setForbidFlag(true);
        condition.setIsDelete(false);
        List<TLeaseStaff>  staffList = leaseStaffService.getLeaseStaffList(condition);
        List<LeaseStaffResp> respList = new ArrayList<>();

        for(TLeaseStaff staff: staffList){
            LeaseStaffResp resp = new LeaseStaffResp();
            resp.setId(staff.getId());
            resp.setAgentFlag(staff.getAgentFlag());
            resp.setFinalFlag(staff.getFinalFlag());
            resp.setTrialFlag(staff.getTrialFlag());
            resp.setPartId(staff.getPartId());
            resp.setComId(staff.getComId());
            respList.add(resp);


        }


        return respList;
    }

    @Override
    public ResponseModel myLeaseAuditList(LeaseReq.leaseAsset req) {


        return ResponseModel.succ();
    }
}