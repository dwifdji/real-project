
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.condition._case.TLawyerCaseBriefMapCondition;
import com.winback.core.exception.BusinessException;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model._case.TLawyerCaseBriefMap;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TLawyerFirmCaseBriefMapCondition;
import com.winback.core.dao._case.mapper.TLawyerFirmCaseBriefMapMapper;
import com.winback.core.model._case.TLawyerFirmCaseBriefMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TLawyerFirmCaseBriefMapDao;

import java.util.*;

@Service
public class TLawyerFirmCaseBriefMapDaoImpl extends AbstractDaoImpl<TLawyerFirmCaseBriefMap, TLawyerFirmCaseBriefMapCondition, BaseMapper<TLawyerFirmCaseBriefMap,TLawyerFirmCaseBriefMapCondition>> implements TLawyerFirmCaseBriefMapDao{
	
	@Resource
	private TLawyerFirmCaseBriefMapMapper tLawyerFirmCaseBriefMapMapper;
	
	@Override
	protected BaseMapper<TLawyerFirmCaseBriefMap, TLawyerFirmCaseBriefMapCondition> daoSupport() {
		return tLawyerFirmCaseBriefMapMapper;
	}

	@Override
	protected TLawyerFirmCaseBriefMapCondition blankCondition() {
		return new TLawyerFirmCaseBriefMapCondition();
	}

	@Override
	public List<TCaseBrief> getCaseBriefListByLawyerFirmId(Integer lawFirmId) {
		return tLawyerFirmCaseBriefMapMapper.getCaseBriefListByLawyerFirmId(lawFirmId);
	}

	@Override
	public void saveCaseBriefMap(Integer lawFirmId, List<Integer> caseBriefIdList) {
		if (caseBriefIdList == null || caseBriefIdList.isEmpty()) {
			return;
		}
		int result;
		for (Integer caseBriefId : caseBriefIdList) {
			TLawyerFirmCaseBriefMap caseBriefMap = new TLawyerFirmCaseBriefMap();
			caseBriefMap.setLawFirmId(lawFirmId);
			caseBriefMap.setCaseBriefId(caseBriefId);
			result = tLawyerFirmCaseBriefMapMapper.insert(caseBriefMap);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	@Override
	public void syncCaseBriefMap(Integer lawFirmId, List<Integer> caseBriefIdList) {
		if (caseBriefIdList == null || caseBriefIdList.isEmpty()) {
			return;
		}
		TLawyerFirmCaseBriefMapCondition condition = new TLawyerFirmCaseBriefMapCondition();
		condition.setLawFirmId(lawFirmId);
		List<TLawyerFirmCaseBriefMap> dbList = tLawyerFirmCaseBriefMapMapper.selectByCondition(condition);
		Map<Integer, TLawyerFirmCaseBriefMap> dbMap = new HashMap<>();
		List<TLawyerFirmCaseBriefMap> updateList = new ArrayList<>();
		Set<Integer> insertList = new HashSet<>();
		for (TLawyerFirmCaseBriefMap item : dbList) {
			dbMap.put(item.getCaseBriefId(), item);
		}
		Set<Integer> curList = new HashSet<>();
		for (Integer caseBriefId : caseBriefIdList) {
			curList.add(caseBriefId);
			if (dbMap.containsKey(caseBriefId)) {
				TLawyerFirmCaseBriefMap caseBriefMap = dbMap.get(caseBriefId);
				if (caseBriefMap.getDeleteFlag()) {
					caseBriefMap.setDeleteFlag(false);
					updateList.add(caseBriefMap);
				}
			} else {
				insertList.add(caseBriefId);
			}
		}
		for (TLawyerFirmCaseBriefMap item : dbList) {
			if (!curList.contains(item.getCaseBriefId())) {
				if (!item.getDeleteFlag()) {
					item.setDeleteFlag(true);
					updateList.add(item);
				}
			}
		}
		int result;
		for (Integer caseBriefId : insertList) {
			TLawyerFirmCaseBriefMap caseBriefMap = new TLawyerFirmCaseBriefMap();
			caseBriefMap.setLawFirmId(lawFirmId);
			caseBriefMap.setCaseBriefId(caseBriefId);
			result = tLawyerFirmCaseBriefMapMapper.insert(caseBriefMap);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		for (TLawyerFirmCaseBriefMap item : updateList) {
			result = tLawyerFirmCaseBriefMapMapper.updateById(item);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	@Override
	public List<String> getCaseBriefIdListByLawyerFirmId(Integer lawFirmId) {
		TLawyerFirmCaseBriefMapCondition condition = new TLawyerFirmCaseBriefMapCondition();
		condition.setLawFirmId(lawFirmId);
		condition.setDeleteFlag(false);
		List<TLawyerFirmCaseBriefMap> list = tLawyerFirmCaseBriefMapMapper.selectByCondition(condition);
		List<String> resultList = new ArrayList<>();
		for (TLawyerFirmCaseBriefMap item : list) {
			resultList.add(item.getCaseBriefId() + "");
		}
		return resultList;
	}
}
