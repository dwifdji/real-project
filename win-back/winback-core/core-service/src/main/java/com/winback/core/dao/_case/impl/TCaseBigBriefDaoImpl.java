
package com.winback.core.dao._case.impl;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition._case.TCaseBigBriefCondition;
import com.winback.core.dao._case.TCaseBigBriefDao;
import com.winback.core.dao._case.mapper.TCaseBigBriefMapper;
import com.winback.core.model._case.TCaseBigBrief;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TCaseBigBriefDaoImpl extends AbstractDaoImpl<TCaseBigBrief, TCaseBigBriefCondition, BaseMapper<TCaseBigBrief,TCaseBigBriefCondition>> implements TCaseBigBriefDao{
	
	@Resource
	private TCaseBigBriefMapper tCaseBigBriefMapper;
	
	@Override
	protected BaseMapper<TCaseBigBrief, TCaseBigBriefCondition> daoSupport() {
		return tCaseBigBriefMapper;
	}

	@Override
	protected TCaseBigBriefCondition blankCondition() {
		return new TCaseBigBriefCondition();
	}

	@Override
	public List<TCaseBigBrief> getList() {
		TCaseBigBriefCondition condition = new TCaseBigBriefCondition();
		condition.setDisplay(true);
		condition.setDeleteFlag(false);
		return tCaseBigBriefMapper.selectByCondition(condition);
	}

	@Override
	public List<TCaseBigBrief> getBackgroundList() {
		TCaseBigBriefCondition condition = new TCaseBigBriefCondition();
		condition.setDeleteFlag(false);
		return tCaseBigBriefMapper.selectByCondition(condition);
	}

	@Override
	public TCaseBigBrief findBy(String name) {
		TCaseBigBriefCondition condition = new TCaseBigBriefCondition();
		condition.setDeleteFlag(false);
		condition.setName(name);
		List<TCaseBigBrief> list = tCaseBigBriefMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
