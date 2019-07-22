
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.common.constants.ProtocolTypeConstants;
import com._360pai.core.condition.asset.TAssetAuthorizationCondition;
import com._360pai.core.dao.asset.TAssetAuthorizationDao;
import com._360pai.core.dao.asset.mapper.TAssetAuthorizationMapper;
import com._360pai.core.model.asset.TAssetAuthorization;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TAssetAuthorizationDaoImpl extends AbstractDaoImpl<TAssetAuthorization, TAssetAuthorizationCondition, BaseMapper<TAssetAuthorization,TAssetAuthorizationCondition>> implements TAssetAuthorizationDao{
	
	@Resource
	private TAssetAuthorizationMapper tAssetAuthorizationMapper;
	
	@Override
	protected BaseMapper<TAssetAuthorization, TAssetAuthorizationCondition> daoSupport() {
		return tAssetAuthorizationMapper;
	}

	@Override
	protected TAssetAuthorizationCondition blankCondition() {
		return new TAssetAuthorizationCondition();
	}

	@Override
	public TAssetAuthorization getJdReportSaleByActivityId(Integer activityId) {
		TAssetAuthorizationCondition condition = new TAssetAuthorizationCondition();
		condition.setActivityId(activityId);
		condition.setProtocolType(ProtocolTypeConstants.SURVEY_REPORT_SALE);
		condition.setProtocolSubtype(ProtocolTypeConstants.JD_REPORT_SALE);
		List<TAssetAuthorization> list = tAssetAuthorizationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAssetAuthorization getJdReportCommissionByActivityId(Integer activityId) {
		TAssetAuthorizationCondition condition = new TAssetAuthorizationCondition();
		condition.setActivityId(activityId);
		condition.setProtocolType(ProtocolTypeConstants.THIRD_AUTH);
		condition.setProtocolSubtype(ProtocolTypeConstants.JD_REPORT_COMMISSION);
		List<TAssetAuthorization> list = tAssetAuthorizationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAssetAuthorization getThirdProtocolByActivityId(Integer activityId) {
		TAssetAuthorizationCondition condition = new TAssetAuthorizationCondition();
		condition.setActivityId(activityId);
		condition.setProtocolType(ProtocolTypeConstants.THIRD_AUTH);
		condition.setProtocolSubtype(ProtocolTypeConstants.THIRD_PROTOCOL);
		List<TAssetAuthorization> list = tAssetAuthorizationMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
