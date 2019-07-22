
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model._case.TCaseType;
import com.winback.core.model.account.TAccount;
import com.winback.core.vo.operate.CaseBriefVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseBriefCondition;
import com.winback.core.dao._case.mapper.TCaseBriefMapper;
import com.winback.core.model._case.TCaseBrief;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseBriefDao;

import java.util.List;
import java.util.Map;

@Service
public class TCaseBriefDaoImpl extends AbstractDaoImpl<TCaseBrief, TCaseBriefCondition, BaseMapper<TCaseBrief,TCaseBriefCondition>> implements TCaseBriefDao{
	
	@Resource
	private TCaseBriefMapper tCaseBriefMapper;
	
	@Override
	protected BaseMapper<TCaseBrief, TCaseBriefCondition> daoSupport() {
		return tCaseBriefMapper;
	}

	@Override
	protected TCaseBriefCondition blankCondition() {
		return new TCaseBriefCondition();
	}

	@Override
	public String getName(Integer id) {
		if (id == null) {
			return "";
		}
		TCaseBrief model = super.selectById(id);
		if (model != null) {
			return model.getName();
		}
		return "";
	}

	@Override
	public PageInfo<TCaseBrief> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCaseBrief> list = tCaseBriefMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<TCaseBrief> getList(Map<String, Object> params) {
		return tCaseBriefMapper.getList(params);
	}

	@Override
	public List<CaseBriefVO> getCaseBriefList() {

		return tCaseBriefMapper.getCaseBriefList();
	}

	@Override
	public List<TCaseBrief> findBy(Integer bigBriefId) {
		TCaseBriefCondition condition = new TCaseBriefCondition();
		condition.setDeleteFlag(false);
		condition.setBigBriefId(bigBriefId);
		return tCaseBriefMapper.selectByCondition(condition);
	}

	@Override
	public List<TCaseBrief> findBy(Integer bigBriefId, Boolean display) {
		TCaseBriefCondition condition = new TCaseBriefCondition();
		condition.setDisplay(display);
		condition.setDeleteFlag(false);
		condition.setBigBriefId(bigBriefId);
		return tCaseBriefMapper.selectByCondition(condition);
	}

	@Override
	public TCaseBrief findBy(String name) {
		TCaseBriefCondition condition = new TCaseBriefCondition();
		condition.setDeleteFlag(false);
		condition.setName(name);
		List<TCaseBrief> list = tCaseBriefMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TCaseBrief findBy(Integer bigBriefId, String name) {
		TCaseBriefCondition condition = new TCaseBriefCondition();
		condition.setDeleteFlag(false);
		condition.setBigBriefId(bigBriefId);
		condition.setName(name);
		List<TCaseBrief> list = tCaseBriefMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
