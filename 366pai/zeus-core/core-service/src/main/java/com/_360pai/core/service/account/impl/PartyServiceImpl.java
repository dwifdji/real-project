package com._360pai.core.service.account.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.dao.account.TCompanyDao;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.account.TUserDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.PartyReq;
import com._360pai.core.facade.account.resp.PartyResp;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.account.TUser;
import com._360pai.core.service.account.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 10:36
 */
@Service
public class PartyServiceImpl implements PartyService {

	@Autowired
	private TPartyDao partyDao;
	@Autowired
	private TUserDao userDao;
	@Autowired
	private TCompanyDao companyDao;

	@Override
	public TParty findPartyById(Integer id) {
		return partyDao.selectById(id);
	}

	@Override
	public boolean saveParty(TParty party) {
		return partyDao.insert(party) == 1;
	}

	@Override
	public boolean updateById(TParty party) {
		return partyDao.updateById(party) == 1;
	}

	@Override
	public boolean operateOffline(PartyReq.OperateOfflineReq req) {
		TParty party = partyDao.selectById(req.getPartyId());
		if (party == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (PartyEnum.Type.company.name().equals(party.getType())) {
			TCompany company = companyDao.selectById(party.getId());
			if (company == null) {
				throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
			}
			company.setOperOffline(req.getOperOffline());
			company.setUpdateTime(new Date());
			int result = companyDao.updateById(company);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		} else if (PartyEnum.Type.user.name().equals(party.getType())) {
			TUser user = userDao.selectById(party.getId());
			if (user == null) {
				throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
			}
			user.setOperOffline(req.getOperOffline());
			user.setUpdateTime(new Date());
			int result = userDao.updateById(user);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		return true;
	}

}