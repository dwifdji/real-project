
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import com._360pai.core.facade.account.vo.MenuVo;
import com._360pai.core.facade.account.vo.ModuleVo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.SysPermissionCondition;
import com._360pai.core.dao.account.mapper.SysPermissionMapper;
import com._360pai.core.model.account.SysPermission;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.SysPermissionDao;

import java.util.*;

@Service
public class SysPermissionDaoImpl extends AbstractDaoImpl<SysPermission, SysPermissionCondition, BaseMapper<SysPermission,SysPermissionCondition>> implements SysPermissionDao{
	
	@Resource
	private SysPermissionMapper sysPermissionMapper;
	
	@Override
	protected BaseMapper<SysPermission, SysPermissionCondition> daoSupport() {
		return sysPermissionMapper;
	}

	@Override
	protected SysPermissionCondition blankCondition() {
		return new SysPermissionCondition();
	}

	@Override
	public List<ModuleVo> getAllModuleList() {
		List<ModuleVo> modules = convertToModuleList(sysPermissionMapper.getAllModuleList());
		for (ModuleVo moduleVo : modules) {
			moduleVo.setSelected("0");
			for (MenuVo menuVo : moduleVo.getMenus()) {
				menuVo.setSelected("0");
			}
		}
		return modules;
	}

	@Override
	public List<ModuleVo> getStaffModuleList(Integer staffId) {
		List<ModuleVo> list = convertToModuleList(sysPermissionMapper.getStaffModuleList(staffId));
		for (ModuleVo moduleVo : list) {
			moduleVo.setSelected("1");
			for (MenuVo menuVo : moduleVo.getMenus()) {
				menuVo.setSelected("1");
			}
		}
		return list;
	}

	@Override
	public List<ModuleVo> getRoleModuleList(Integer roleId) {
		List<ModuleVo> list = convertToModuleList(sysPermissionMapper.getRoleModuleList(roleId));
		for (ModuleVo moduleVo : list) {
			moduleVo.setSelected("1");
			for (MenuVo menuVo : moduleVo.getMenus()) {
				menuVo.setSelected("1");
			}
		}
		return list;
	}

	private List<ModuleVo> convertToModuleList(List<SysPermission> permissions) {
		List<ModuleVo> moduleVos = new ArrayList<>();
		Map<String, ModuleVo> map = new HashMap<>();
		for (SysPermission permission : permissions) {
			if (map.containsKey(permission.getModuleCode())) {
				ModuleVo moduleVo = map.get(permission.getModuleCode());
				List<MenuVo> menuVos = moduleVo.getMenus();
				boolean exist = false;
				for (MenuVo menuVo : menuVos) {
					if (menuVo.getMenuCode().equals(permission.getMenuCode())) {
						exist = true;
						break;
					}
				}
				if (!exist) {
					MenuVo menuVo = new MenuVo();
					menuVo.setMenuCode(permission.getMenuCode());
					menuVo.setMenuName(permission.getMenuName());
					menuVo.setMenuOrder(permission.getMenuOrder());
					menuVos.add(menuVo);
				}
			} else {
				ModuleVo moduleVo = new ModuleVo();
				List<MenuVo> menuVos = new ArrayList<>();
				MenuVo menuVo = new MenuVo();
				menuVo.setMenuCode(permission.getMenuCode());
				menuVo.setMenuName(permission.getMenuName());
				menuVo.setMenuOrder(permission.getMenuOrder());
				menuVos.add(menuVo);
				moduleVo.setModuleCode(permission.getModuleCode());
				moduleVo.setModuleName(permission.getModuleName());
				moduleVo.setModuleOrder(permission.getModuleOrder());
				moduleVo.setMenus(menuVos);
				map.put(permission.getModuleCode(), moduleVo);
			}
		}
		for (String key : map.keySet()) {
			ModuleVo moduleVo = map.get(key);
			Collections.sort(moduleVo.getMenus(), new Comparator<MenuVo>() {
				@Override
				public int compare(MenuVo o1, MenuVo o2) {
					return o1.getMenuOrder().compareTo(o2.getMenuOrder());
				}
			});
			moduleVos.add(moduleVo);
		}
		Collections.sort(moduleVos, new Comparator<ModuleVo>() {
			@Override
			public int compare(ModuleVo o1, ModuleVo o2) {
				return o1.getModuleOrder().compareTo(o2.getModuleOrder());
			}
		});
		return moduleVos;
	}

	@Override
	public Set<String> getStaffPermissionCodeList(Integer staffId) {
		return sysPermissionMapper.getStaffPermissionCodeList(staffId);
	}

	@Override
	public List<SysPermission> getByMenuCode(String menuCode) {
		SysPermissionCondition condition = new SysPermissionCondition();
		condition.setMenuCode(menuCode);
		condition.setPermissionType("0");
		return sysPermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<SysPermission> getByModuleCodeMenuCode(String moduleCode, String menuCode) {
		SysPermissionCondition condition = new SysPermissionCondition();
		condition.setModuleCode(moduleCode);
		condition.setMenuCode(menuCode);
		condition.setPermissionType("0");
		return sysPermissionMapper.selectByCondition(condition);
	}

	@Override
	public List<SysPermission> getSpecialPermissionList() {
		SysPermissionCondition condition = new SysPermissionCondition();
		condition.setPermissionType("1");
		return sysPermissionMapper.selectByCondition(condition);
	}
}
