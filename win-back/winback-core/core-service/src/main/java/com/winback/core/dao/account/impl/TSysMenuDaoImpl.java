
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TSysMenuCondition;
import com.winback.core.dao.account.mapper.TSysMenuMapper;
import com.winback.core.model.account.TSysMenu;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TSysMenuDao;

import java.util.List;

@Service
public class TSysMenuDaoImpl extends AbstractDaoImpl<TSysMenu, TSysMenuCondition, BaseMapper<TSysMenu,TSysMenuCondition>> implements TSysMenuDao{
	
	@Resource
	private TSysMenuMapper tSysMenuMapper;
	
	@Override
	protected BaseMapper<TSysMenu, TSysMenuCondition> daoSupport() {
		return tSysMenuMapper;
	}

	@Override
	protected TSysMenuCondition blankCondition() {
		return new TSysMenuCondition();
	}

	@Override
	public List<TSysMenu> findByModuleId(Integer moduleId) {
		TSysMenuCondition condition = new TSysMenuCondition();
		condition.setModuleId(moduleId);
		condition.setDeleteFlag(false);
		return tSysMenuMapper.selectByCondition(condition);
	}
}
