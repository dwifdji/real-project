package com._360pai.core.service.account.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.account.PartyChannelAgentCondition;
import com._360pai.core.dao.account.PartyChannelAgentDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.PartyChannelAgentReq;
import com._360pai.core.facade.account.resp.PartyChannelAgentResp;
import com._360pai.core.model.account.PartyChannelAgent;
import com._360pai.core.model.account.TParty;
import com._360pai.core.service.account.PartyChannelAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PartyChannelAgentServiceImpl	implements PartyChannelAgentService{

	@Autowired
	private PartyChannelAgentDao partyChannelAgentDao;
	@Autowired
	private TPartyDao partyDao;


	@Override
	public PartyChannelAgentResp partySetChannelAgent(PartyChannelAgentReq.BaseReq req) {
		PartyChannelAgentResp resp = new PartyChannelAgentResp();
		TParty party = partyDao.selectById(req.getPartyId());
		if (party == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (!req.getIsChannelAgent()) { // 取消成为渠道代理商
			List<PartyChannelAgent> list = partyChannelAgentDao.getByChannelAgentPartyId(req.getPartyId());
			if (list.size() > 0) {
				throw new BusinessException(ApiCallResult.FAILURE, "渠道下有下级用户，无法取消");
			}
		}
		party.setIsChannelAgent(req.getIsChannelAgent());
		int result = partyDao.updateById(party);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public PartyChannelAgentResp partySelectChannelAgent(PartyChannelAgentReq.BaseReq req) {
		PartyChannelAgentResp resp = new PartyChannelAgentResp();
		TParty channelAgentParty = partyDao.selectById(req.getChannelAgentPartyId());
		if (channelAgentParty == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (!channelAgentParty.getIsChannelAgent()) {
			throw new BusinessException(ApiCallResult.FAILURE, "该用户非渠道代理商，请先设置该用户为渠道代理商");
		}
		TParty party = partyDao.selectById(req.getPartyId());
		if (party == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (channelAgentParty.getId().equals(party.getId())) {
			throw new BusinessException(ApiCallResult.FAILURE, "不可以设置自己为自己的代理商");
		}
		PartyChannelAgent partyChannelAgent = partyChannelAgentDao.getByPartyId(party.getId());
		int result;
		if (partyChannelAgent == null) {
			partyChannelAgent = new PartyChannelAgent();
			partyChannelAgent.setChannelAgentPartyId(req.getChannelAgentPartyId());
			partyChannelAgent.setCommissionPercentChannelAgent(req.getChannelAgentCommissionPercent());
			partyChannelAgent.setPartyId(req.getPartyId());
			partyChannelAgent.setCreatedAt(new Date());
			result = partyChannelAgentDao.insert(partyChannelAgent);
		} else {
			partyChannelAgent.setChannelAgentPartyId(req.getChannelAgentPartyId());
			partyChannelAgent.setCommissionPercentChannelAgent(req.getChannelAgentCommissionPercent());
			result = partyChannelAgentDao.updateById(partyChannelAgent);
		}
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public PartyChannelAgentResp partyCancelSelectChannelAgent(PartyChannelAgentReq.BaseReq req) {
		PartyChannelAgentResp resp = new PartyChannelAgentResp();
		PartyChannelAgent partyChannelAgent = partyChannelAgentDao.getByPartyId(req.getPartyId());
		if (partyChannelAgent == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		int result = partyChannelAgentDao.deleteById(partyChannelAgent.getId());
		if (0 == result) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public PartyChannelAgent findChannelByPartyId(Integer partyId) {
		PartyChannelAgentCondition condition = new PartyChannelAgentCondition();
		condition.setPartyId(partyId);
		return partyChannelAgentDao.selectFirst(condition);
	}
}