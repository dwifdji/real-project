package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DepositEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.assistant.DepositDao;
import com._360pai.core.dao.assistant.DepositOfflineActionDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.assistant.req.DepositReq;
import com._360pai.core.facade.assistant.resp.DepositResp;
import com._360pai.core.facade.assistant.vo.DepositVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.Asset;
import com._360pai.core.model.asset.AssetData;
import com._360pai.core.model.assistant.Deposit;
import com._360pai.core.model.assistant.DepositOfflineAction;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.assistant.DepositService;
import com._360pai.core.service.assistant.SmsHelperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/16 14:31
 */
@Service
public class DepositServiceImpl	implements DepositService{

	@Autowired
	private DepositDao depositDao;
	@Autowired
	private DepositOfflineActionDao depositOfflineActionDao;
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TPartyDao partyDao;
	@Autowired
	private AuctionActivityDao auctionActivityDao;
	@Autowired
	private SmsHelperService smsHelperService;
	@Autowired
	private AssetDao assetDao;

	@Override
	public PageInfo getAllByActivityId(int page, int perPage, DepositCondition condition, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		PageInfo pageInfo = new PageInfo(depositDao.selectList(condition));
		return pageInfo;
	}

	@Override
	public boolean saveDeposit(Deposit deposit) {
		return depositDao.insert(deposit) == 1;
	}

	@Override
	public boolean yxUserHasBuy(Integer activityId) {
		DepositCondition  depositCondition = new DepositCondition();
		depositCondition.setActivityId(activityId);
		depositCondition.setAskResult("1");
		Deposit deposit = depositDao.selectFirst(depositCondition);
		if(deposit == null || deposit.getId() == null){
			return false;
		}else {
			return true;
		}
	}

	@Override
	public List<Deposit> findDeposit(DepositCondition depositCondition) {
		return depositDao.selectList(depositCondition);
	}

	@Override
	public boolean updateDepositById(Deposit deposit) {
		return depositDao.updateById(deposit) == 1;
	}


	@Override
	public Deposit getByActivityIdPartId(Integer activityId, Integer partyId) {
		return depositDao.getByActivityIdPartId(activityId,partyId);
	}

	@Override
	public PageInfoResp<PartyAccount> getParticipatingPartiesByPage(ActivityReq.ActivityId req) {
		PageInfoResp<PartyAccount> resp = new PageInfoResp<>();
		DepositCondition condition = new DepositCondition();
		condition.setActivityId(req.getActivityId());
		PageInfo pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, "");
		List<PartyAccount> partyVos = new ArrayList<>();
		List<Deposit> list = pageInfo.getList();
		for (Deposit item : list) {
			PartyAccount partyVo = accountService.getPartyAccountById(item.getPartyId());
			partyVo.setContact(partyVo.getName());
			partyVo.setRecordAt(item.getCreatedAt());
			partyVos.add(partyVo);
		}
		resp.setList(partyVos);
		resp.setHasNextPage(pageInfo.isHasNextPage());
		resp.setTotal(pageInfo.getTotal());
		return resp;
	}

	@Override
	public int countParticipantNumber(Integer activityId) {
		return depositDao.getDepositCount(activityId);
	}

	@Override
	public int countParticipantNumber(Integer activityId, Integer agencyId) {
		return depositDao.getDepositCount(activityId, agencyId);
	}

	@Override
	public PageInfoResp<PartyAccount> getAgencyParticipatingPartiesByPage(ActivityReq.ActivityId req) {
		PageInfoResp<PartyAccount> resp = new PageInfoResp<>();
		DepositCondition condition = new DepositCondition();
		condition.setActivityId(req.getActivityId());
		condition.setAgencyId(req.getAgencyId());
		PageInfo pageInfo = getAllByActivityId(req.getPage(), req.getPerPage(), condition, "id asc");
		List<PartyAccount> partyVos = new ArrayList<>();
		List<Deposit> list = pageInfo.getList();
		for (Deposit item : list) {
			TParty party = partyDao.selectById(item.getPartyId());
			PartyAccount vo = new PartyAccount();
			vo.setId(party.getId());
			vo.setType(party.getType());
			if (PartyEnum.Type.user.equals(party.getType())) {
				TUser user = userService.findUserById(party.getId());
				if (user != null) {
					vo.setName(user.getName());
					vo.setMobile(user.getMobile());
					vo.setContact(user.getName());
				}
			} else if (PartyEnum.Type.company.equals(party.getType())) {
				TCompany company = companyService.findCompanyById(party.getId());
				if (company != null) {
					TAccount account = accountService.selectByPrimaryKey(company.getAccountId());
					if (account != null) {
						TUser tUser = userService.findUserByAccountId(account.getId());
						if (tUser != null) {
							vo.setContact(tUser.getName());
						}
					}
					vo.setName(company.getName());
					vo.setMobile(account.getMobile());
				}
			}
			vo.setRecordAt(item.getCreatedAt());
			partyVos.add(vo);
		}
		resp.setList(partyVos);
		resp.setHasNextPage(pageInfo.isHasNextPage());
		resp.setTotal(pageInfo.getTotal());
		return resp;
	}


	@Override
	public List<Deposit> getDepositByActivityId(Integer activityId) {
		DepositCondition condition = new DepositCondition();
		condition.setActivityId(activityId);
		return depositDao.selectList(condition);
	}

	@Override
	public List<Deposit> selectNoDealYX(Integer activityId) {
		return depositDao.selectNoDealYX(activityId);
	}

	@Override
	public Deposit getDepositById(Long depositId) {
		DepositCondition condition = new DepositCondition();
		condition.setId(depositId);
		return depositDao.selectFirst(condition);
	}

    @Override
    public PageInfo myDepositList(int page, int perPage, Integer partyId,String categoryId,String name) {
		PageHelper.startPage(page,perPage);
		List<Map> deposits = depositDao.myDepositList(partyId,categoryId,name);
		for(Map map:deposits){
			if("-1".equals(map.get("categoryId"))){
				map.put("categoryName","租赁权拍卖");
			}
		}
		return new PageInfo<>(deposits);
    }

	@Override
	public PageInfoResp<DepositVo> getDepositOfflineListByPage(DepositReq.QueryReq req) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(req.getStatus())) {
			params.put("status", req.getStatus());
		}
		if (StringUtils.isNotBlank(req.getQ())) {
			params.put("q", req.getQ());
		}
		if (StringUtils.isNotEmpty(req.getCreatedAtBegin())&& StringUtils.isNotEmpty(req.getCreatedAtEnd())) {
			params.put("createdAtBegin", req.getCreatedAtBegin());
			params.put("createdAtEnd", req.getCreatedAtEnd());
		}
		if (req.getId() != null) {
			params.put("id", req.getId());
		}
		PageInfo pageInfo = depositOfflineActionDao.getList(req.getPage(), req.getPerPage(), params, "");
		List<DepositVo> list = pageInfo.getList();
		List<DepositVo> itemsList = new ArrayList<>();
		for (DepositVo depositVo : list) {
			try {
				Deposit deposit = depositDao.selectById(depositVo.getId());
				AuctionActivity activity = auctionActivityDao.getById(deposit.getActivityId());
				Asset asset = assetDao.selectById(activity.getAssetId());
				if (asset.getBankOfflineFlag()) {
					depositVo.setPayType("银行类全线下");
				} else {
					depositVo.setPayType("线下");
				}
				depositVo.setStatusDesc(DepositEnum.Status.getValueByKey(depositVo.getStatus()));

				if("-1".equals(depositVo.getCategoryId())){
					depositVo.setCategoryName("租赁权拍卖");

				}

				itemsList.add(depositVo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageInfoResp<DepositVo> resp = new PageInfoResp<>();
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	@Override
	public PageInfoResp<DepositVo> getDepositOfflineRefundListByPage(DepositReq.QueryReq req) {
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(req.getStatus())) {
			params.put("status", req.getStatus());
		}
		if (StringUtils.isNotBlank(req.getQ())) {
			params.put("q", req.getQ());
		}
		if (StringUtils.isNotEmpty(req.getCreatedAtBegin())&& StringUtils.isNotEmpty(req.getCreatedAtEnd())) {
			params.put("createdAtBegin", req.getCreatedAtBegin());
			params.put("createdAtEnd", req.getCreatedAtEnd());
		}
		if (req.getId() != null) {
			params.put("id", req.getId());
		}
		PageInfo pageInfo = depositOfflineActionDao.getRefundList(req.getPage(), req.getPerPage(), params, "");
		List<DepositVo> list = pageInfo.getList();
		List<DepositVo> itemsList = new ArrayList<>();
		for (DepositVo depositVo : list) {
			try {
				if (DepositEnum.Status.OFFLINE_RECEIVED.getKey().equals(depositVo.getStatus())) {
					depositVo.setStatusDesc("待退回保证金");
				} else {
					depositVo.setStatusDesc(DepositEnum.Status.getValueByKey(depositVo.getStatus()));
				}
				if (DepositEnum.Status.OFFLINE_FINISHED.getKey().equals(depositVo.getStatus())) {
					if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(depositVo.getComeFrom())) {
						AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(depositVo.getSellerId());
						if (accountBaseDto.isBank()) {
							depositVo.setBankAccountName(accountBaseDto.getBankAccountName());
							depositVo.setBankAccountNumber(accountBaseDto.getBankAccountNo());
							depositVo.setBankName(accountBaseDto.getBankName());
						} else {
							continue;
						}
					}
				}
				if (DepositEnum.Status.OFFLINE_TRANSFERRED.getKey().equals(depositVo.getStatus())) {
					if (AssetEnum.ComeFrom.PLATFORM.getKey().equals(depositVo.getComeFrom())) {
						AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(depositVo.getSellerId());
						if (accountBaseDto.isBank()) {
							depositVo.setBankAccountName(accountBaseDto.getBankAccountName());
							depositVo.setBankAccountNumber(accountBaseDto.getBankAccountNo());
							depositVo.setBankName(accountBaseDto.getBankName());
						}
					}
				}
				Deposit deposit = depositDao.selectById(depositVo.getId());
				AuctionActivity activity = auctionActivityDao.getById(deposit.getActivityId());
				Asset asset = assetDao.selectById(activity.getAssetId());
				if (asset.getBankOfflineFlag()) {
					depositVo.setPayType("银行类全线下");
				} else {
					depositVo.setPayType("线下");
				}
				depositVo.setStatusDesc(DepositEnum.Status.getValueByKey(depositVo.getStatus()));
				itemsList.add(depositVo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageInfoResp<DepositVo> resp = new PageInfoResp<>();
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	@Transactional
	@Override
	public DepositResp receiveDeposit(DepositReq.OfflineConfirmReq req) {
		DepositResp resp = new DepositResp();
		Deposit deposit = depositDao.selectById(req.getId());
		if (deposit == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (!DepositEnum.Status.INIT.getKey().equals(deposit.getStatus())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		deposit.setStatus(DepositEnum.Status.OFFLINE_RECEIVED.getKey());
		int result = depositDao.updateById(deposit);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		DepositOfflineAction depositOfflineAction = new DepositOfflineAction();
		depositOfflineAction.setAction(DepositEnum.OfflineAction.PAYMENT.getKey());
		depositOfflineAction.setDepositId(deposit.getId());
		depositOfflineAction.setRemark(req.getRemark());
		depositOfflineAction.setVoucher(req.getVoucher());
		depositOfflineAction.setCreateTime(new Date());
		depositOfflineAction.setOperatorId(req.getOperatorId());
		result = depositOfflineActionDao.insert(depositOfflineAction);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		result = auctionActivityDao.addParticipantNumber(deposit.getActivityId());
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		AuctionActivity activity = auctionActivityDao.selectById(deposit.getActivityId());
		if (activity != null) {
			smsHelperService.offlineDepositReceivedNotify(accountService.getNotifierMobile(deposit.getPartyId()), activity.getAssetName());
		}
		return resp;
	}

	@Transactional
	@Override
	public DepositResp refundDeposit(DepositReq.OfflineConfirmReq req) {
		DepositResp resp = new DepositResp();
		Deposit deposit = depositDao.selectById(req.getId());
		if (deposit == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (!DepositEnum.Status.OFFLINE_RECEIVED.getKey().equals(deposit.getStatus())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		deposit.setStatus(DepositEnum.Status.OFFLINE_RETURNED.getKey());
		int result = depositDao.updateById(deposit);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		DepositOfflineAction depositOfflineAction = new DepositOfflineAction();
		depositOfflineAction.setAction(DepositEnum.OfflineAction.REFUND.getKey());
		depositOfflineAction.setDepositId(deposit.getId());
		depositOfflineAction.setRemark(req.getRemark());
		depositOfflineAction.setVoucher(req.getVoucher());
		depositOfflineAction.setCreateTime(new Date());
		depositOfflineAction.setOperatorId(req.getOperatorId());
		result = depositOfflineActionDao.insert(depositOfflineAction);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Transactional
	@Override
	public DepositResp transferDeposit(DepositReq.OfflineConfirmReq req) {
		DepositResp resp = new DepositResp();
		Deposit deposit = depositDao.selectById(req.getId());
		if (deposit == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (!DepositEnum.Status.OFFLINE_FINISHED.getKey().equals(deposit.getStatus())) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		deposit.setStatus(DepositEnum.Status.OFFLINE_TRANSFERRED.getKey());
		int result = depositDao.updateById(deposit);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		DepositOfflineAction depositOfflineAction = new DepositOfflineAction();
		depositOfflineAction.setAction(DepositEnum.OfflineAction.TRANSFERRED.getKey());
		depositOfflineAction.setDepositId(deposit.getId());
		depositOfflineAction.setRemark(req.getRemark());
		depositOfflineAction.setVoucher(req.getVoucher());
		depositOfflineAction.setCreateTime(new Date());
		depositOfflineAction.setOperatorId(req.getOperatorId());
		result = depositOfflineActionDao.insert(depositOfflineAction);
		if (result ==0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}


	@Override
	public int updateDealYX(Integer level) {
		return depositDao.updateDealYX(level);
	}

	@Override
	public List<AuctionActivity> getBeginIn5MinuteList(Integer partyId) {
		return depositDao.getBeginIn5MinuteList(partyId);
	}

	@Override
	public List<AuctionActivity> getEndIn5MinuteList(Integer partyId) {
		return depositDao.getEndIn5MinuteList(partyId);
	}
}