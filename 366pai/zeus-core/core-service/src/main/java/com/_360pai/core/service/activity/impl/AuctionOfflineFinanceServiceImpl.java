package com._360pai.core.service.activity.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.exception.ExceptionEnumImpl;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.AuctionOfflineEnum;
import com._360pai.core.condition.activity.TAuctionOfflineFinanceCondition;
import com._360pai.core.condition.activity.TOfflineEnterAccountCondition;
import com._360pai.core.dao.account.TBankAccountDao;
import com._360pai.core.dao.activity.TAuctionOfflineFinanceDao;
import com._360pai.core.dao.activity.TOfflineEnterAccountDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.dto.AuctionOfflineFinaceDto;
import com._360pai.core.dto.CommissionDto;
import com._360pai.core.dto.OfflineEnterAccountDto;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.req.AuctionOfflineEnterAccountReq;
import com._360pai.core.facade.activity.req.AuctionOfflineFinanceReq;
import com._360pai.core.facade.activity.vo.AuctionOfflineFinaceVo;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TBankAccount;
import com._360pai.core.model.activity.TAuctionOfflineFinance;
import com._360pai.core.model.activity.TAuctionPayOrder;
import com._360pai.core.model.activity.TOfflineEnterAccount;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.PartyChannelAgentService;
import com._360pai.core.service.activity.AuctionOfflineFinanceService;
import com._360pai.core.service.applet.AppletShopService;
import com._360pai.core.service.payment.AuctionOrderService;
import com._360pai.core.service.payment.AuctionPayOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RuQ
 * @Title: AuctionOfflineFinanceServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/4/28 16:14
 */
@Service
public class AuctionOfflineFinanceServiceImpl implements AuctionOfflineFinanceService {

    @Autowired
    private TAuctionOfflineFinanceDao auctionOfflineFinanceDao;

    @Autowired
    private TOfflineEnterAccountDao offlineEnterAccountDao;

    @Autowired
    private TBankAccountDao bankAccountDao;

    @Autowired
    private StaffDao staffDao;

    @Autowired
    private AuctionPayOrderService auctionPayOrderService;

    @Autowired
    private AuctionOrderService auctionOrderService;

    @Autowired
    private AgencyService agencyService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PartyChannelAgentService partyChannelAgentService;

    @Autowired
    private AcctService acctService;

    @Autowired
    private AppletShopService appletShopService;

    @Override
    public PageInfoResp<AuctionOfflineFinaceDto> searchOfflineFinanceList(AuctionOfflineFinanceReq req) {

        PageInfoResp<AuctionOfflineFinaceDto> pageInfoResp = new PageInfoResp<AuctionOfflineFinaceDto>();
        List<AuctionOfflineFinaceDto> dtoList = new ArrayList<AuctionOfflineFinaceDto>();

        TAuctionOfflineFinanceCondition condition = new TAuctionOfflineFinanceCondition();
        BeanUtils.copyProperties(req,condition);
        condition.setDeleteFlag(false);
        PageHelper.startPage(req.getPage(), req.getPerPage());
        //PageHelper.orderBy("id desc");

        List<TAuctionOfflineFinance> financeList = auctionOfflineFinanceDao.selectList(condition);
        PageInfo<TAuctionOfflineFinance> pageInfo = new PageInfo<>(financeList);
        if(pageInfo != null && pageInfo.getList()!= null){
            pageInfoResp.setTotal(pageInfo.getTotal());
            pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
            for(TAuctionOfflineFinance finance : pageInfo.getList()){
                AuctionOfflineFinaceDto dto = new AuctionOfflineFinaceDto();
                BeanUtils.copyProperties(finance,dto);
                dto.setOrderId(finance.getOrderId()+"");
                dto.setCreateTime(DateUtil.getNormDateStr(finance.getCreateTime()));
                dto.setOperatorName(staffDao.getName(finance.getOperatorId()));
                dto.setRoleType(AuctionOfflineEnum.RoleType.getValueByKey(finance.getRoleType()));
                dto.setFinanceType(AuctionOfflineEnum.FinanceType.getValueByKey(finance.getFinanceType()));
                dto.setStaus(AuctionOfflineEnum.ConfirmStatus.getValueByKey(finance.getStaus()));
                dtoList.add(dto);
            }
            pageInfoResp.setList(dtoList);
        }
        return pageInfoResp;
    }

    @Override
    public List<AuctionOfflineFinaceVo> searchAllOfflineFinanceList(AuctionOfflineFinanceReq req) {
        List<AuctionOfflineFinaceVo> dtoList = new ArrayList<>();
        //List<AuctionOfflineFinaceDto> dtoList = new ArrayList<>();

        TAuctionOfflineFinanceCondition condition = new TAuctionOfflineFinanceCondition();
        BeanUtils.copyProperties(req,condition);
        condition.setDeleteFlag(false);
        List<TAuctionOfflineFinance> financeList = auctionOfflineFinanceDao.selectList(condition);
        if(financeList != null && !financeList.isEmpty()){
            for(TAuctionOfflineFinance finance : financeList){
                AuctionOfflineFinaceVo dto = new AuctionOfflineFinaceVo();
                BeanUtils.copyProperties(finance,dto);
                dto.setCreateTime(DateUtil.getNormDateStr(finance.getCreateTime()));
                dto.setRoleType(AuctionOfflineEnum.RoleType.getValueByKey(finance.getRoleType()));
                dto.setFinanceType(AuctionOfflineEnum.FinanceType.getValueByKey(finance.getFinanceType()));
                dto.setStaus(AuctionOfflineEnum.ConfirmStatus.getValueByKey(finance.getStaus()));
                dtoList.add(dto);
            }
        }
        return dtoList;
    }

    @Override
    public AuctionOfflineFinaceDto getDetailById(Integer financeId) {
        AuctionOfflineFinaceDto dto = new AuctionOfflineFinaceDto();
        TAuctionOfflineFinance finance = auctionOfflineFinanceDao.selectById(financeId);
        if(finance != null){
            BeanUtils.copyProperties(finance,dto);
        }
        dto.setOrderId(finance.getOrderId()+"");
        TOfflineEnterAccountCondition accountCondition = new TOfflineEnterAccountCondition();
        accountCondition.setFinanceId(financeId);
        accountCondition.setDeleteFlag(false);
        List<TOfflineEnterAccount> accountList = offlineEnterAccountDao.selectList(accountCondition);
        List<OfflineEnterAccountDto> accountDtoList = new ArrayList<>();
        if(accountList != null){
            for (TOfflineEnterAccount account : accountList){
                OfflineEnterAccountDto accountDto = new OfflineEnterAccountDto();
                BeanUtils.copyProperties(account,accountDto);
                accountDto.setBankAccountNo(getBankAccountNoById(account.getBankAccountId()));
                accountDtoList.add(accountDto);
            }
        }
        dto.setEnterAccountList(accountDtoList);

        List<CommissionDto>  commissionList = new ArrayList<>();
        if(AuctionOfflineEnum.ConfirmStatus.CONFIRM.getKey().equals(finance.getStaus())){
            TAuctionPayOrder payOrder = auctionPayOrderService.findAuctionPayOrderByOrderId(finance.getOrderId());
            AuctionOrder order = auctionOrderService.getById(finance.getOrderId());
            if(payOrder != null && !StringUtils.isEmpty(finance.getActualReceiveCommissionAmount())){

                BigDecimal totalCommission = payOrder.getBuyerCommissionPayAmount().add(payOrder.getSellerCommissionPayAmount());
                BigDecimal rate = new BigDecimal(finance.getActualReceiveCommissionAmount()).divide(totalCommission,2,BigDecimal.ROUND_HALF_UP);
                if(payOrder.getBuyerAgencyCommissionPayAmount() != null){
                    //联拍机构佣金
                    TAgency agency = agencyService.findByAgencyId(order.getBuyerAgencyId());
                    if(agency != null){
                        commissionList.add(transCommissionDto(agency.getName(),"联拍机构",payOrder.getBuyerAgencyCommissionPayAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                    }
                }
                if(payOrder.getSellerAgencyCommissionPayAmount() != null){
                    //送拍机构佣金
                    TAgency agency = agencyService.findByAgencyId(order.getSellerAgencyId());
                    if(agency != null){
                        commissionList.add(transCommissionDto(agency.getName(),"送拍机构",payOrder.getSellerAgencyCommissionPayAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                    }

                }
                if(payOrder.getPlatformCommissionPayAmount() != null){
                    //平台佣金
                    commissionList.add(transCommissionDto("360PAI平台","360PAI平台",payOrder.getPlatformCommissionPayAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                }
                if(payOrder.getBuyerChannelCommissionPayAmount() != null){
                    //买家渠道代理佣金
                    PartyChannelAgent channelAgent = partyChannelAgentService.findChannelByPartyId(order.getBuyerId());
                    if(channelAgent != null){
                        AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(channelAgent.getChannelAgentPartyId());
                        if(accountBaseDto != null){
                            commissionList.add(transCommissionDto(accountBaseDto.getName(),"买受人渠道代理商",payOrder.getBuyerChannelCommissionPayAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                        }
                    }
                }
                if(payOrder.getSellerChannelCommissionPayAmount() != null){
                    //卖家渠道代理佣金
                    PartyChannelAgent channelAgent = partyChannelAgentService.findChannelByPartyId(order.getSellerId());
                    if(channelAgent != null){
                        AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(channelAgent.getChannelAgentPartyId());
                        if(accountBaseDto != null){
                            commissionList.add(transCommissionDto(accountBaseDto.getName(),"委托人渠道代理商",payOrder.getSellerChannelCommissionPayAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                        }
                    }
                }
                if(payOrder.getBelongShopCommission() != null){
                    //所属店铺佣金
                    commissionList.add(transCommissionDto(getShopCommissionParentName(payOrder.getBelongAcctId()),"买受人上级",payOrder.getBelongShopCommission().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                }
                if(payOrder.getParentCommission() != null){
                    //所属店铺父级佣金
                    commissionList.add(transCommissionDto(getShopCommissionParentName(payOrder.getParentAcctId()),"买受人上上级",payOrder.getParentCommission().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP)+""));
                }
            }
        }
        dto.setCommissionList(commissionList);

        return dto;
    }

    private String getShopCommissionParentName(Integer acctId){
        String userName = "";
        TAcct acct = acctService.findAcctById(acctId);
        if(acct != null){
            if(acct.getType().equals(AccountEnum.AcctType.USER.getKey())
                    || acct.getType().equals(AccountEnum.AcctType.COMPANY.getKey())){
                AccountBaseDto dto = accountService.getAccountBaseByPartyId(acct.getPartyId());
                if(dto != null){
                    userName = dto.getName();
                }
            }else if(AccountEnum.AcctType.AGENCY.getKey().equals(acct.getType())){
                TAgency agency = agencyService.findByAgencyId(acct.getPartyId());
                if(agency != null){
                    userName = agency.getName();
                }
            }else if(AccountEnum.AcctType.SHOP.getKey().equals(acct.getType())){
                TAppletShop shop = appletShopService.getAppletShopById(acct.getPartyId());
                if(shop != null){
                    userName = shop.getName();
                }
            }
        }
        return userName;
    }

    private CommissionDto transCommissionDto(String userName,String roleType,String commissionAmount){
        CommissionDto dto = new CommissionDto();
        dto.setCommissionAmount(commissionAmount);
        dto.setUserName(userName);
        dto.setRoleType(roleType);
        return dto;
    }

    private String getBankAccountNoById(Integer bankAccountId){
        TBankAccount bankAccount = bankAccountDao.selectById(bankAccountId);
        if(bankAccount != null){
            return bankAccount.getBankAccountName()+bankAccount.getBankAccountNo();
        }
        return "";
    }

    @Transactional
    @Override
    public boolean updateFinanceInfo(AuctionOfflineFinanceReq req) {
        TAuctionOfflineFinance offlineFinance = auctionOfflineFinanceDao.selectById(req.getId());
        if(offlineFinance == null || AuctionOfflineEnum.ConfirmStatus.CONFIRM.getKey().equals(offlineFinance.getStaus())){
            throw new BusinessException(ExceptionEnumImpl.SYSTEM_ERROR);
        }
        TAuctionOfflineFinance finance = new TAuctionOfflineFinance();
        BeanUtils.copyProperties(req,finance);
        finance.setStaus(AuctionOfflineEnum.ConfirmStatus.CONFIRM.getKey());
        auctionOfflineFinanceDao.updateById(finance);
        if(req.getEnterAccountList() != null && !req.getEnterAccountList().isEmpty()){
            for(AuctionOfflineEnterAccountReq accountReq : req.getEnterAccountList()){
                TOfflineEnterAccount account = new TOfflineEnterAccount();
                BeanUtils.copyProperties(accountReq,account);
                account.setFinanceId(req.getId());
                offlineEnterAccountDao.insert(account);
            }
        }
        return true;
    }

    @Override
    public boolean saveFinanceInfo(TAuctionOfflineFinance finance) {
        return auctionOfflineFinanceDao.insert(finance) == 1;
    }
}
