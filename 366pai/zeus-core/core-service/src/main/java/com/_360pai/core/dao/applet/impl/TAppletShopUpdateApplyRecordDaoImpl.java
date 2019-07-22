
package com._360pai.core.dao.applet.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.model.account.TUser;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.applet.TAppletShopUpdateApplyRecordCondition;
import com._360pai.core.dao.applet.mapper.TAppletShopUpdateApplyRecordMapper;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.applet.TAppletShopUpdateApplyRecordDao;

import java.util.List;
import java.util.Map;

@Service
public class TAppletShopUpdateApplyRecordDaoImpl extends AbstractDaoImpl<TAppletShopUpdateApplyRecord, TAppletShopUpdateApplyRecordCondition, BaseMapper<TAppletShopUpdateApplyRecord,TAppletShopUpdateApplyRecordCondition>> implements TAppletShopUpdateApplyRecordDao{
	
	@Resource
	private TAppletShopUpdateApplyRecordMapper tAppletShopUpdateApplyRecordMapper;
	
	@Override
	protected BaseMapper<TAppletShopUpdateApplyRecord, TAppletShopUpdateApplyRecordCondition> daoSupport() {
		return tAppletShopUpdateApplyRecordMapper;
	}

	@Override
	protected TAppletShopUpdateApplyRecordCondition blankCondition() {
		return new TAppletShopUpdateApplyRecordCondition();
	}

	@Override
	public boolean hasPendingApply(Integer shopId) {
		TAppletShopUpdateApplyRecordCondition condition = new TAppletShopUpdateApplyRecordCondition();
		condition.setShopId(shopId);
		condition.setStatus(AccountEnum.ApplyStatus.PENDING.getKey());
		List<TAppletShopUpdateApplyRecord> list = tAppletShopUpdateApplyRecordMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAppletShopUpdateApplyRecord> list = tAppletShopUpdateApplyRecordMapper.getList(params);
		return new PageInfo<>(list);
	}
}
