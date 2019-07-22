
package com.tzCloud.core.dao.view.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.view.ViewCourtNumCondition;
import com.tzCloud.core.dao.view.ViewCourtNumDao;
import com.tzCloud.core.dao.view.mapper.ViewCourtNumMapper;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.model.view.ViewCourtNum;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ViewCourtNumDaoImpl extends AbstractDaoImpl<ViewCourtNum, ViewCourtNumCondition, BaseMapper<ViewCourtNum,ViewCourtNumCondition>> implements ViewCourtNumDao {
	
	@Resource
	private ViewCourtNumMapper viewCourtNumMapper;
	
	@Override
	protected BaseMapper<ViewCourtNum, ViewCourtNumCondition> daoSupport() {
		return viewCourtNumMapper;
	}

	@Override
	protected ViewCourtNumCondition blankCondition() {
		return new ViewCourtNumCondition();
	}

	@Override
	public List<ViewCourtNumResp> findByCourtName(ViewCourtNum viewCourtNum) {
		return viewCourtNumMapper.findByCourtName(viewCourtNum);
	}
}
