
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model._case.TCase;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseLawyerOrderCondition;
import com.winback.core.dao._case.mapper.TCaseLawyerOrderMapper;
import com.winback.core.model._case.TCaseLawyerOrder;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseLawyerOrderDao;

import java.util.List;

@Service
public class TCaseLawyerOrderDaoImpl extends AbstractDaoImpl<TCaseLawyerOrder, TCaseLawyerOrderCondition, BaseMapper<TCaseLawyerOrder,TCaseLawyerOrderCondition>> implements TCaseLawyerOrderDao{
	
	@Resource
	private TCaseLawyerOrderMapper tCaseLawyerOrderMapper;
	
	@Override
	protected BaseMapper<TCaseLawyerOrder, TCaseLawyerOrderCondition> daoSupport() {
		return tCaseLawyerOrderMapper;
	}

	@Override
	protected TCaseLawyerOrderCondition blankCondition() {
		return new TCaseLawyerOrderCondition();
	}


	@Override
	public boolean updateAcceptOrderStatus(String caseId, Integer lawyerId,Integer operatorId) {
		return tCaseLawyerOrderMapper.updateAcceptOrderStatus(caseId,lawyerId,operatorId);
	}

	@Override
	public boolean updateRefusedOrderStatus(String caseId, Integer lawyerId,Integer operatorId) {
		return tCaseLawyerOrderMapper.updateRefusedOrderStatus(caseId,lawyerId,operatorId);
	}

	@Override
	public boolean updateSuccessOrderStatus(String caseId, Integer lawyerId,Integer operatorId) {
		return tCaseLawyerOrderMapper.updateSuccessOrderStatus(caseId,lawyerId,operatorId);
	}

	@Override
	public PageInfo<TCaseLawyerOrder> getApplyAcceptLawyers(int page, int perPage, String orderBy, TCaseLawyerOrderCondition condition) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCaseLawyerOrder> list = tCaseLawyerOrderMapper.selectByCondition(condition);
		return new PageInfo<>(list);
	}
}
