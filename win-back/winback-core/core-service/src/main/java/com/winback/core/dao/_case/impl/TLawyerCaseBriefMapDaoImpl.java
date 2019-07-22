
package com.winback.core.dao._case.impl;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition._case.TLawyerCaseBriefMapCondition;
import com.winback.core.dao._case.TLawyerCaseBriefMapDao;
import com.winback.core.dao._case.mapper.TLawyerCaseBriefMapMapper;
import com.winback.core.exception.BusinessException;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model._case.TLawyerCaseBriefMap;
import com.winback.core.model.account.TSysStaffRoleMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class TLawyerCaseBriefMapDaoImpl extends AbstractDaoImpl<TLawyerCaseBriefMap, TLawyerCaseBriefMapCondition, BaseMapper<TLawyerCaseBriefMap,TLawyerCaseBriefMapCondition>> implements TLawyerCaseBriefMapDao{
	
	@Resource
	private TLawyerCaseBriefMapMapper tLawyerCaseBriefMapMapper;
	
	@Override
	protected BaseMapper<TLawyerCaseBriefMap, TLawyerCaseBriefMapCondition> daoSupport() {
		return tLawyerCaseBriefMapMapper;
	}

	@Override
	protected TLawyerCaseBriefMapCondition blankCondition() {
		return new TLawyerCaseBriefMapCondition();
	}

	@Override
	public List<TCaseBrief> getCaseBriefListByLawyerId(Integer lawyerId) {
		return tLawyerCaseBriefMapMapper.getCaseBriefListByLawyerId(lawyerId);
	}

	@Override
	public void saveCaseBriefMap(Integer lawyerId, List<Integer> caseBriefIdList) {
		if (caseBriefIdList == null || caseBriefIdList.isEmpty()) {
			return;
		}
		int result;
		for (Integer caseBriefId : caseBriefIdList) {
			TLawyerCaseBriefMap caseBriefMap = new TLawyerCaseBriefMap();
			caseBriefMap.setLawyerId(lawyerId);
			caseBriefMap.setCaseBriefId(caseBriefId);
			result = tLawyerCaseBriefMapMapper.insert(caseBriefMap);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	@Override
	public void syncCaseBriefMap(Integer lawyerId, List<Integer> caseBriefIdList) {
		if (caseBriefIdList == null || caseBriefIdList.isEmpty()) {
			return;
		}
		TLawyerCaseBriefMapCondition condition = new TLawyerCaseBriefMapCondition();
		condition.setLawyerId(lawyerId);
		List<TLawyerCaseBriefMap> dbList = tLawyerCaseBriefMapMapper.selectByCondition(condition);
		Map<Integer, TLawyerCaseBriefMap> dbMap = new HashMap<>();
		List<TLawyerCaseBriefMap> updateList = new ArrayList<>();
		Set<Integer> insertList = new HashSet<>();
		for (TLawyerCaseBriefMap item : dbList) {
			dbMap.put(item.getCaseBriefId(), item);
		}
		Set<Integer> curList = new HashSet<>();
		for (Integer caseBriefId : caseBriefIdList) {
			curList.add(caseBriefId);
			if (dbMap.containsKey(caseBriefId)) {
				TLawyerCaseBriefMap caseBriefMap = dbMap.get(caseBriefId);
				if (caseBriefMap.getDeleteFlag()) {
					caseBriefMap.setDeleteFlag(false);
					updateList.add(caseBriefMap);
				}
			} else {
				insertList.add(caseBriefId);
			}
		}
		for (TLawyerCaseBriefMap item : dbList) {
			if (!curList.contains(item.getCaseBriefId())) {
				if (!item.getDeleteFlag()) {
					item.setDeleteFlag(true);
					updateList.add(item);
				}
			}
		}
		int result;
		for (Integer caseBriefId : insertList) {
			TLawyerCaseBriefMap caseBriefMap = new TLawyerCaseBriefMap();
			caseBriefMap.setLawyerId(lawyerId);
			caseBriefMap.setCaseBriefId(caseBriefId);
			result = tLawyerCaseBriefMapMapper.insert(caseBriefMap);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		for (TLawyerCaseBriefMap item : updateList) {
			result = tLawyerCaseBriefMapMapper.updateById(item);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}
}
