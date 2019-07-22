package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.PartyBlackListActionEnum;
import com._360pai.core.condition.account.PartyBlackListActionCondition;
import com._360pai.core.dao.account.PartyBlackListActionDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.PartyBlackListActionReq;
import com._360pai.core.facade.account.resp.PartyBlackListActionResp;
import com._360pai.core.facade.account.vo.PartyBlackListActionVo;
import com._360pai.core.model.account.PartyBlackListAction;
import com._360pai.core.model.account.TParty;
import com._360pai.core.service.account.PartyBlackListActionService;
import com._360pai.core.utils.RespConvertUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PartyBlackListActionServiceImpl	implements PartyBlackListActionService{

	@Autowired
	private PartyBlackListActionDao partyBlackListActionDao;
	@Autowired
	private TPartyDao partyDao;
	@Autowired
	private StaffDao staffDao;


	@Transactional
	@Override
	public PartyBlackListActionResp partyLockInBlackList(PartyBlackListActionReq.BaseReq req) {
		PartyBlackListActionResp resp = new PartyBlackListActionResp();
		TParty party = partyDao.selectById(req.getPartyId());
		if (party == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		PartyBlackListAction partyBlackListAction = new PartyBlackListAction();
		partyBlackListAction.setAction(PartyBlackListActionEnum.LOCK.getKey());
		partyBlackListAction.setPartyId(party.getId());
		partyBlackListAction.setReason(req.getReason());
		partyBlackListAction.setStaffId(req.getOperatorId());
		partyBlackListAction.setCreatedAt(new Date());
		int result = partyBlackListActionDao.insert(partyBlackListAction);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		party.setIsInBlackList(true);
		result = partyDao.updateById(party);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Transactional
	@Override
	public PartyBlackListActionResp partyReleaseFromBlackList(PartyBlackListActionReq.BaseReq req) {
		PartyBlackListActionResp resp = new PartyBlackListActionResp();
		TParty party = partyDao.selectById(req.getPartyId());
		if (party == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		PartyBlackListAction partyBlackListAction = new PartyBlackListAction();
		partyBlackListAction.setAction(PartyBlackListActionEnum.RELEASE.getKey());
		partyBlackListAction.setPartyId(party.getId());
		partyBlackListAction.setReason(req.getReason());
		partyBlackListAction.setStaffId(req.getOperatorId());
		partyBlackListAction.setCreatedAt(new Date());
		int result = partyBlackListActionDao.insert(partyBlackListAction);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		party.setIsInBlackList(false);
		result = partyDao.updateById(party);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public PageInfoResp<PartyBlackListActionVo> getPartyBlackListActionListByPage(PartyBlackListActionReq.BaseReq req) {
		PageInfoResp<PartyBlackListActionVo> resp = new PageInfoResp<>();
		PartyBlackListActionCondition condition = new PartyBlackListActionCondition();
		condition.setPartyId(req.getPartyId());
		PageInfo pageInfo = partyBlackListActionDao.getListByPage(req.getPage(), req.getPerPage(), condition, "");
		List<PartyBlackListActionVo> itemsList = new ArrayList<>();
		List<PartyBlackListAction> list = pageInfo.getList();
		for (PartyBlackListAction partyBlackListAction : list) {
			PartyBlackListActionVo vo = new PartyBlackListActionVo();
			BeanUtils.copyProperties(partyBlackListAction, vo);
			if (partyBlackListAction.getStaffId() != null) {
				vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(partyBlackListAction.getStaffId())));
			}
			itemsList.add(vo);
		}
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}
}