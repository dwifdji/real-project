
package com.winback.core.dao.applet.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.applet.TAppletMessageCondition;
import com.winback.core.dao.applet.TAppletMessageDao;
import com.winback.core.dao.applet.mapper.TAppletMessageMapper;
import com.winback.core.facade.account.vo.AppletMessage;
import com.winback.core.model.applet.TAppletMessage;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAppletMessageDaoImpl extends AbstractDaoImpl<TAppletMessage, TAppletMessageCondition, BaseMapper<TAppletMessage,TAppletMessageCondition>> implements TAppletMessageDao {

	@Resource
	private TAppletMessageMapper tAppletMessageMapper;

	@Override
	protected BaseMapper<TAppletMessage, TAppletMessageCondition> daoSupport() {
		return tAppletMessageMapper;
	}

	@Override
	protected TAppletMessageCondition blankCondition() {
		return new TAppletMessageCondition();
	}

	@Override
	public PageInfo<AppletMessage> getAppletMessageList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AppletMessage> list = tAppletMessageMapper.getAppletMessageList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int unreadMessageCount(Integer extBindId) {
		return tAppletMessageMapper.unreadMessageCount(extBindId);
	}
}
