
package com.winback.core.dao.assistant.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.assistant.TAppMessageCondition;
import com.winback.core.dao.assistant.TAppMessageDao;
import com.winback.core.dao.assistant.mapper.TAppMessageMapper;
import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.model.assistant.TAppMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAppMessageDaoImpl extends AbstractDaoImpl<TAppMessage, TAppMessageCondition, BaseMapper<TAppMessage,TAppMessageCondition>> implements TAppMessageDao{
	
	@Resource
	private TAppMessageMapper tAppMessageMapper;
	
	@Override
	protected BaseMapper<TAppMessage, TAppMessageCondition> daoSupport() {
		return tAppMessageMapper;
	}

	@Override
	protected TAppMessageCondition blankCondition() {
		return new TAppMessageCondition();
	}

	@Override
	public PageInfo<AppMessage> getAppMessageList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AppMessage> list = tAppMessageMapper.getAppMessageList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int unreadMessageCount(Integer accountId) {
		return tAppMessageMapper.unreadMessageCount(accountId);
	}


	@Override
	public int unreadConnectCount(Integer accountId, String type) {
		return tAppMessageMapper.unreadConnectCount(accountId,type);
	}
}
