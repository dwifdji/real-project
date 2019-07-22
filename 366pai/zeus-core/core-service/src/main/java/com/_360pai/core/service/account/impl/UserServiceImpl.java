package com._360pai.core.service.account.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.account.TUserApplyRecordCondition;
import com._360pai.core.condition.account.TUserCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.AreaDao;
import com._360pai.core.dao.assistant.CityDao;
import com._360pai.core.dao.assistant.ProvinceDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.UserReq;
import com._360pai.core.facade.account.resp.UserResp;
import com._360pai.core.facade.account.vo.PartyAccount;
import com._360pai.core.facade.account.vo.UserApplyRecordVo;
import com._360pai.core.facade.account.vo.UserVo;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.City;
import com._360pai.core.service.account.AccountBusinessService;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/17 11:02
 */
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private TUserDao tUserDao;
	@Autowired
	private TUserApplyRecordDao tUserApplyRecordDao;
	@Autowired
	private StaffDao staffDao;
	@Autowired
	private AgencyService agencyService;
	@Autowired
	private PartyChannelAgentDao partyChannelAgentDao;
	@Autowired
	private AccountService accountService;
	@Autowired
	private TPartyDao partyDao;
	@Autowired
	private PartyBlackListActionDao partyBlackListActionDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AccountBusinessService accountBusinessService;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private AreaDao areaDao;

	@Override
	public TUser findUserByNameAndIdCard(String name, String idCard) {
		TUserCondition userCondition = new TUserCondition();
		userCondition.setName(name);
		userCondition.setCertificateNumber(idCard);
		return tUserDao.selectFirst(userCondition);
	}

	@Override
	public TUser findUserById(Integer userId) {
		return tUserDao.selectById(userId);
	}

	@Override
	public TUser findUserByMobile(String mobile) {
		TUserCondition condition = new TUserCondition();
		condition.setMobile(mobile);
		return tUserDao.selectFirst(condition);
	}

	@Override
	public TUser findUserByAccountId(Integer accountId) {
		TUserCondition condition = new TUserCondition();
		condition.setAccountId(accountId);
		condition.setDeleteFlag(false);
		return tUserDao.selectFirst(condition);
	}

	@Override
	public int updateUserById(TUser user) {
		return tUserDao.updateById(user);
	}


	@Override
	public List<TUser> findUser(TUser tUser) {
		TUserCondition condition = new TUserCondition();
		BeanUtils.copyProperties(tUser,condition);
		return tUserDao.selectList(condition);
	}

    @Override
    public boolean saveUser(TUser user) {
        return tUserDao.insert(user) == 1;
    }

	@Override
	public PageInfoResp<UserApplyRecordVo> getUserApplyRecordListByPage(AccountReq.QueryReq req) {
		PageInfoResp<UserApplyRecordVo> resp = new PageInfoResp<>();
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(req.getQ())) {
			params.put("q", req.getQ());
		}
		if (StringUtils.isNotBlank(req.getStatus())) {
			params.put("status", req.getStatus());
		}
		if (StringUtils.isNotBlank(req.getApplySource())) {
			params.put("applySource", req.getApplySource());
		}
		PageInfo pageInfo = tUserApplyRecordDao.getListByPage(req.getPage(), req.getPerPage(), params, "r.id desc");
		List<UserApplyRecordVo> itemsList = new ArrayList<>();
		List<TUserApplyRecord> applyRecords = pageInfo.getList();
		for (TUserApplyRecord applyRecord : applyRecords) {
			try {
				itemsList.add(processUserApplyRecord(applyRecord));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	@Override
	public UserApplyRecordVo getUserApplyRecordById(AccountReq.BaseReq req) {
		TUserApplyRecordCondition condition = new TUserApplyRecordCondition();
		condition.setId(Long.valueOf(req.getId()));
		TUserApplyRecord applyRecord = tUserApplyRecordDao.selectFirst(condition);
		if (applyRecord == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		return processUserApplyRecord(applyRecord);
	}

	@Override
	public PageInfoResp<UserVo> getUserListByPage(UserReq.QueryReq req) {
		PageInfoResp<UserVo> resp = new PageInfoResp<>();
		Map<String, Object> params = (Map<String, Object>) JSON.toJSON(req);
		PageInfo pageInfo = tUserDao.getListByPage(req.getPage(), req.getPerPage(), params, "t.id desc");
		List<UserVo> itemsList = new ArrayList<>();
		List<TUser> users = pageInfo.getList();
		for (TUser user : users) {
			try {
				itemsList.add(processUser(user));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	@Override
	public UserVo getUserById(AccountReq.BaseReq req) {
		TUser user = tUserDao.selectById(req.getPartyId());
		if (user == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		return processUser(user);
	}

	@Override
	public UserResp updateUser(UserReq.UpdateReq req) {
		UserResp resp = new UserResp();
		TUser user = tUserDao.selectById(req.getId());
		if (user == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		user = ReqConvertUtil.convertToUser(req);
		boolean success = tUserDao.updateById(user) > 0 ? true : false;
		if (!success) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		accountBusinessService.updateBusinessInfo(user.getId(), JSONObject.parseObject(JSONObject.toJSONString(user)) , AccountBusinessService.BusinessType.user	);
		return resp;
	}

	private UserApplyRecordVo processUserApplyRecord(TUserApplyRecord applyRecord) {
		UserApplyRecordVo vo = RespConvertUtil.convertToUserApplyRecordVo(applyRecord);
		vo.setDefaultAgency(RespConvertUtil.convertToAgencyVo(agencyService.findByAgencyId(applyRecord.getDefaultAgencyId())));
		if (applyRecord.getOperatorId() != null) {
			vo.setOperator(RespConvertUtil.convertToStaff(staffDao.selectById(applyRecord.getOperatorId())));
		}
		if (applyRecord.getOpenAccountOperatorId() != null) {
			vo.setOpenAccountOperator(staffDao.getName(applyRecord.getOpenAccountOperatorId()));
		}
		if (applyRecord.getBusinessOperatorId() != null) {
			vo.setBusinessOperator(staffDao.getName(applyRecord.getBusinessOperatorId()));
		}
		if (StringUtils.isNotBlank(vo.getCityId())) {
			vo.setCityName(cityDao.getName(vo.getCityId()));
		}
		if (StringUtils.isNotBlank(vo.getProvinceId())) {
			vo.setProvinceName(provinceDao.getName(vo.getProvinceId()));
		}
		if (StringUtils.isNotBlank(vo.getAreaId())) {
			vo.setAreaName(areaDao.getName(vo.getAreaId()));
		}
		return vo;
	}

	private UserVo processUser(TUser user) {
		UserVo vo = RespConvertUtil.convertToUserVo(user);
		TParty party = partyDao.selectById(user.getId());
		if (party != null) {
			if (party.getIsChannelAgent() != null && party.getIsChannelAgent()) {
				vo.setIsChannel(1);
			} else {
				vo.setIsChannel(0);
			}
			if (party.getIsInBlackList() != null && party.getIsInBlackList()) {
				vo.setIsInBlackList("1");
				PartyBlackListAction partyBlackListAction = partyBlackListActionDao.getLatestByPartyId(party.getId());
				if (partyBlackListAction != null) {
					vo.setLatestInBlackListAt(partyBlackListAction.getCreatedAt());
				}
			} else {
				vo.setIsInBlackList("0");
			}
		}
		vo.setDefaultAgency(RespConvertUtil.convertToAgencyVo(agencyService.findByAgencyId(user.getDefaultAgencyId())));
		PartyChannelAgent partyChannelAgent = partyChannelAgentDao.getByPartyId(user.getId());
		if (partyChannelAgent != null) {
			vo.setCommissionPercentChannelAgent(partyChannelAgent.getCommissionPercentChannelAgent());
			vo.setMyChannelAgentId(partyChannelAgent.getChannelAgentPartyId());
			PartyAccount channelAgent = accountService.getPartyAccountById(partyChannelAgent.getChannelAgentPartyId());
			if (channelAgent != null) {
				vo.setMyChannelAgentName(channelAgent.getName());
			}
		}
		if (StringUtils.isNotBlank(vo.getCityId())) {
			vo.setCityName(cityDao.getName(vo.getCityId()));
		}
		if (StringUtils.isNotBlank(vo.getProvinceId())) {
			vo.setProvinceName(provinceDao.getName(vo.getProvinceId()));
		}
		if (StringUtils.isNotBlank(vo.getAreaId())) {
			vo.setAreaName(areaDao.getName(vo.getAreaId()));
		}
		if (user.getOpenAccountOperatorId() != null) {
			vo.setOpenAccountOperator(staffDao.getName(user.getOpenAccountOperatorId()));
		}
		if (user.getBusinessOperatorId() != null) {
			vo.setBusinessOperator(staffDao.getName(user.getBusinessOperatorId()));
		}
		TUserApplyRecord applyRecord = tUserApplyRecordDao.getApprovedByCertificateNumber(user.getCertificateNumber());
		if (applyRecord != null) {
			vo.setApplySource(applyRecord.getApplySource());
			vo.setApplySourceDesc(PartyEnum.ApplySource.getValueByKey(applyRecord.getApplySource()));
		}
		return vo;
	}
}