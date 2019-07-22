package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.MD5Helper;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.account.SysPermissionCondition;
import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.dao.assistant.TBackstageOperationRecordDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.PersonaReq;
import com._360pai.core.facade.account.req.RoleReq;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.PersonaResp;
import com._360pai.core.facade.account.resp.RoleResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.*;
import com._360pai.core.model.account.*;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.assistant.TBackstageOperationRecord;
import com._360pai.core.service.assistant.StaffService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;
	@Autowired
	private SysPermissionDao sysPermissionDao;
	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysStaffRoleDao sysStaffRoleDao;
	@Autowired
	private SysRolePermissionDao sysRolePermissionDao;
	@Autowired
	private SysStaffPermissionDao sysStaffPermissionDao;
	@Resource
	private RedisCachemanager redisCacheManager;
	@Autowired
	private TBackstageOperationRecordDao backstageOperationRecordDao;

	private static final String key = "persona_login_";

	@Override
	public Staff getById(Integer id) {
		return staffDao.selectById(id);
	}

	@Override
	public StaffResp.BasicResp getStaffBasic(StaffReq.BaseReq req) {
		StaffResp.BasicResp resp = new StaffResp.BasicResp();
		Staff staff;
		if (req.getStaffId() != null) {
			staff = staffDao.selectById(req.getStaffId());

		} else if (StringUtils.isNotEmpty(req.getMobile())) {
			staff = staffDao.getByMobile(req.getMobile());
		} else {
			throw new BusinessException(ApiCallResult.EMPTY);
		}
		if (staff != null) {
			BeanUtils.copyProperties(staff, resp);
		}
		return resp;
	}


	@Override
	public StaffResp.DetailResp getStaff(StaffReq.BaseReq req) {
		StaffResp.DetailResp resp = new StaffResp.DetailResp();
		Staff staff = staffDao.selectById(req.getStaffId());
		if (staff == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		StaffVo staffVo = RespConvertUtil.convertToStaff(staff);
		staffVo.setRoleDesc(getRoleDesc(req.getStaffId()));
		List<PermissionVo> specialPermissionList = sysStaffPermissionDao.getStaffSpecialPermissionList(req.getStaffId());
		staffVo.setSpecialPermissions(specialPermissionList);
		staffVo.setHasSpecialPermission(specialPermissionList.size() > 0);
		List<SysRole> list = sysStaffRoleDao.getRoleList(req.getStaffId());
		List<RoleVo> roleVos = new ArrayList<>();
		for (SysRole role : list) {
			RoleVo roleVo = new RoleVo();
			roleVo.setId(role.getId());
			roleVo.setName(role.getName());
			roleVos.add(roleVo);
		}
		staffVo.setRoles(roleVos);
		resp.setStaff(staffVo);
		return resp;
	}

	private String getRoleDesc(Integer staffId) {
		StringBuffer roleDesc = new StringBuffer();
		Set<String> roleIds = getRoleIdList(staffId);
		Iterator<String> itr = roleIds.iterator();
		while (itr.hasNext()) {
			Integer roleId = Integer.parseInt(itr.next());
			SysRole role = sysRoleDao.selectById(roleId);
			roleDesc.append(role.getName());
			if (itr.hasNext()) {
				roleDesc.append(",");
			}
		}
		return roleDesc.toString();
	}

	@Override
	public PageInfoResp<StaffVo> getStaffListByPage(StaffReq.QueryReq req) {
		PageInfoResp<StaffVo> resp = new PageInfoResp<>();
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(req.getQ())) {
			params.put("q", req.getQ());
		}
		if (StringUtils.isNotEmpty(req.getStatus())) {
			params.put("status", req.getStatus());
		}
		PageInfo pageInfo = staffDao.getListByPage(req.getPage(), req.getPerPage(), params, "s.id desc");
		List<StaffVo> itemsList = new ArrayList<>();
		List<Staff> staffList = pageInfo.getList();
		for (Staff staff : staffList) {
			StaffVo vo = new StaffVo();
			RespConvertUtil.convert(staff, vo);
			if (!req.isBasicInfo()) {
				vo.setRoleDesc(getRoleDesc(staff.getId()));
				List<PermissionVo> specialPermissionList = sysStaffPermissionDao.getStaffSpecialPermissionList(staff.getId());
				vo.setSpecialPermissions(specialPermissionList);
				vo.setHasSpecialPermission(specialPermissionList.size() > 0);
			}
			itemsList.add(vo);
		}
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	@Transactional
	@Override
	public StaffResp createStaff(StaffReq.CreateReq req) {
		StaffResp resp = new StaffResp();
		Staff operator = staffDao.selectById(req.getOperatorId());
		if (!operator.getIsAdmin()) {
			throw new BusinessException(ApiCallResult.FAILURE, "只有管理员可以进行操作");
		}
		Staff staff = staffDao.getByMobile(req.getMobile());
		if (staff != null) {
			throw new BusinessException(ApiCallResult.FAILURE, "手机号已存在");
		}
		staff = ReqConvertUtil.convertToStaff(req);
		String encryptedPassword = null;
		try {
			encryptedPassword = PasswordEncryption.getEncryptedPassword(req.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(encryptedPassword)) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		staff.setPasswordHash(encryptedPassword);
		int result = staffDao.insert(staff);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		syncStaffRole(staff.getId(), req.getRoles());
		syncStaffPermission(staff.getId(), req.getSpecialPermissions());
		updateStaffCache(staff.getId());
		return resp;
	}

	@Transactional
	@Override
	public StaffResp updateStaff(StaffReq.UpdateReq req) {
		StaffResp resp = new StaffResp();
		Staff operator = staffDao.selectById(req.getOperatorId());
		if (!operator.getIsAdmin()) {
			throw new BusinessException(ApiCallResult.FAILURE, "只有管理员可以进行操作");
		}
		Staff staff = staffDao.selectById(req.getStaffId());
		if (staff == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		staff = ReqConvertUtil.convertToStaff(req);
		int result = staffDao.updateById(staff);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		syncStaffRole(req.getStaffId(), req.getRoles());
		syncStaffPermission(req.getStaffId(), req.getSpecialPermissions());
		updateStaffCache(req.getStaffId());
		return resp;
	}

	private void syncStaffRole(Integer staffId, List<RoleVo> roleVos) {
		List<SysStaffRole> dbList = sysStaffRoleDao.getAllByStaffId(staffId);
		Set<String> curList = new HashSet<>();
		Map<String, SysStaffRole> dbMap = new HashMap<>();
		for (SysStaffRole staffRole : dbList) {
			dbMap.put(staffRole.getRoleId() + "", staffRole);
		}
		Set<String> insertList = new HashSet<>();
		List<SysStaffRole> updateList = new ArrayList<>();
		for (RoleVo roleVo : roleVos) {
			curList.add(roleVo.getId() + "");
			if (dbMap.containsKey(roleVo.getId() + "")) {
				SysStaffRole staffRole = dbMap.get(roleVo.getId() + "");
				if (staffRole.getIsDelete()) {
					staffRole.setIsDelete(false);
					updateList.add(staffRole);
				}
			} else {
				insertList.add(roleVo.getId() + "");
			}
		}
		for (SysStaffRole staffRole : dbList) {
			if (!curList.contains(staffRole.getRoleId() + "")) {
				if (!staffRole.getIsDelete()) {
					staffRole.setIsDelete(true);
					updateList.add(staffRole);
				}
			}
		}
		int result;
		for (String roleId : insertList) {
			SysStaffRole staffRole = new SysStaffRole();
			staffRole.setRoleId(Integer.parseInt(roleId));
			staffRole.setStaffId(staffId);
			staffRole.setCreateTime(new Date());
			staffRole.setUpdateTime(new Date());
			result = sysStaffRoleDao.insert(staffRole);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		for (SysStaffRole staffRole : updateList) {
			staffRole.setUpdateTime(new Date());
			result = sysStaffRoleDao.updateById(staffRole);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	private void syncStaffPermission(Integer staffId , List<PermissionVo> permissionVos) {
		List<SysStaffPermission> dbList = sysStaffPermissionDao.getAllStaffPermissionList(staffId);
		Map<String, SysStaffPermission> dbMap = new HashMap<>();
		for (SysStaffPermission sysStaffPermission : dbList) {
			dbMap.put(sysStaffPermission.getPermissionId() + "", sysStaffPermission);
		}
		Set<String> insertList = new HashSet<>();
		List<SysStaffPermission> updateList = new ArrayList<>();
		Set<String> curList = new HashSet<>();
		for (PermissionVo vo : permissionVos) {
			curList.add(vo.getPermissionId() + "");
			if (dbMap.containsKey(vo.getPermissionId() + "")) {
				SysStaffPermission sysStaffPermission = dbMap.get(vo.getPermissionId() + "");
				if (sysStaffPermission.getIsDelete()) {
					sysStaffPermission.setIsDelete(false);
					updateList.add(sysStaffPermission);
				}
			} else {
				insertList.add(vo.getPermissionId() + "");
			}
		}
		for (SysStaffPermission staffPermission : dbList) {
			if (!curList.contains(staffPermission.getPermissionId() + "")) {
				if (!staffPermission.getIsDelete()) {
					staffPermission.setIsDelete(true);
					updateList.add(staffPermission);
				}
			}
		}
		int result;
		for (String permissionId : insertList) {
			SysStaffPermission staffPermission = new SysStaffPermission();
			staffPermission.setPermissionId(Integer.parseInt(permissionId));
			staffPermission.setStaffId(staffId);
			result = sysStaffPermissionDao.insert(staffPermission);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		for (SysStaffPermission staffPermission : updateList) {
			staffPermission.setUpdateTime(new Date());
			result = sysStaffPermissionDao.updateById(staffPermission);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	private void updateStaffCache(Integer staffId) {
		Set<String> roleIds = getRoleIdList(staffId);
		redisCacheManager.hSet(RedisKeyConstant.RBAC_STAFF_ROLE, staffId + "", JSON.toJSONString(roleIds));
		Set<String> permissionCodes = getStaffSpecialPermissionCodeList(staffId);
		redisCacheManager.hSet(RedisKeyConstant.RBAC_STAFF_SPECIAL_PERMISSION, staffId + "", JSON.toJSONString(permissionCodes));
	}

	@Override
	public StaffResp resetStaffPassword(StaffReq.BaseReq req) {
		StaffResp resp = new StaffResp();
		Staff operator = staffDao.selectById(req.getOperatorId());
		if (!operator.getIsAdmin()) {
			throw new BusinessException(ApiCallResult.FAILURE, "只有管理员可以进行操作");
		}
		Staff staff = staffDao.selectById(req.getStaffId());
		if (staff == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		String encryptedPassword = null;
		try {
			encryptedPassword = PasswordEncryption.getEncryptedPassword(req.getPassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(encryptedPassword)) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		staff.setPasswordHash(encryptedPassword);
		staff.setUpdateTime(new Date());
		int result = staffDao.updateById(staff);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public StaffResp staffModifyPassword(StaffReq.ModifyPasswordReq req) {
		StaffResp resp = new StaffResp();
		Staff staff = staffDao.selectById(req.getStaffId());
		if (staff == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		try {
			if (!PasswordEncryption.authenticate(req.getOldPassword(), staff.getPasswordHash())) {
				throw new BusinessException("旧密码错误");
			}
			if (!req.getNewPassword().equals(req.getConfirmPassword())) {
				throw new BusinessException("新密码和确认密码不一致");
			}
			staff.setPasswordHash(PasswordEncryption.getEncryptedPassword(req.getConfirmPassword()));
			staff.setUpdateTime(new Date());
			int result = staffDao.updateById(staff);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return resp;
	}

	@Override
	public PageInfoResp<RoleVo> getRoleListByPage(RoleReq.QueryReq req) {
		PageInfoResp<RoleVo> resp = new PageInfoResp<>();
		Map<String, Object> params = new HashMap<>();
		if (StringUtils.isNotBlank(req.getQ())) {
			params.put("q", req.getQ());
		}
		PageInfo pageInfo = sysRoleDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
		List<RoleVo> itemsList = new ArrayList<>();
		List<SysRole> roles = pageInfo.getList();
		for (SysRole role : roles) {
			RoleVo vo = new RoleVo();
			RespConvertUtil.convert(role, vo);
			vo.setPermissionDesc(getRolePermissionDesc(role.getId()));
			itemsList.add(vo);
		}
		resp.setList(itemsList);
		resp.setTotal(pageInfo.getTotal());
		resp.setHasNextPage(pageInfo.isHasNextPage());
		return resp;
	}

	private String getRolePermissionDesc(Integer roleId) {
		StringBuffer sb = new StringBuffer();
		List<ModuleVo> allModuleList = getAllModuleList();
		List<ModuleVo> roleModuleList = getRoleModuleList(roleId);
		if (allModuleList.size() == roleModuleList.size()) {
			return "全部";
		}
		Iterator<ModuleVo> itr = roleModuleList.iterator();
		while (itr.hasNext()) {
			ModuleVo moduleVo = itr.next();
			sb.append(moduleVo.getModuleName());
			if (itr.hasNext()) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	@Override
	public RoleResp.DetailResp getRole(RoleReq.BaseReq req) {
		RoleResp.DetailResp resp = new RoleResp.DetailResp();
		SysRole role = sysRoleDao.selectById(req.getRoleId());
		if (role == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		RoleVo roleVo = new RoleVo();
		BeanUtils.copyProperties(role, roleVo);
		List<ModuleVo> roleModuleList = sysPermissionDao.getRoleModuleList(req.getRoleId());
		Map<String, MenuVo> map = new HashMap<>();
		for (ModuleVo moduleVo : roleModuleList) {
			for (MenuVo menuVo : moduleVo.getMenus()) {
				map.put(menuVo.getMenuCode(), menuVo);
			}
		}
		List<ModuleVo> moduleVoList = sysPermissionDao.getAllModuleList();
		Iterator<ModuleVo> itr = moduleVoList.iterator();
		while (itr.hasNext()) {
			ModuleVo moduleVo = itr.next();
			if ("nbgl".equals(moduleVo.getModuleCode())) {
				itr.remove();
			}
		}
		for (ModuleVo moduleVo : moduleVoList) {
			for (MenuVo menuVo : moduleVo.getMenus()) {
				if (map.containsKey(menuVo.getMenuCode())) {
					menuVo.setSelected("1");
				} else {
					menuVo.setSelected("0");
				}
			}
		}
		roleVo.setModuleList(moduleVoList);
		resp.setRole(roleVo);
		return resp;
	}

	@Transactional
	@Override
	public RoleResp createRole(RoleReq.CreateReq req) {
		RoleResp resp = new RoleResp();
		if (sysRoleDao.isExistName(req.getName())) {
			throw new BusinessException(ApiCallResult.FAILURE, "角色已存在");
		}
		SysRole role = new SysRole();
		role.setName(req.getName());
		role.setDescription(req.getDescription());
		int result = sysRoleDao.insert(role);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		Set<String> allPermissionIds = new HashSet<>();
		for (ModuleVo moduleVo : req.getModules()) {
			for (MenuVo menuVo : moduleVo.getMenus()) {
				if (!"1".equals(menuVo.getSelected())) {
					continue;
				}
				List<SysPermission> permissions = sysPermissionDao.getByModuleCodeMenuCode(moduleVo.getModuleCode(), menuVo.getMenuCode());
				for (SysPermission permission : permissions) {
					allPermissionIds.add(permission.getId() + "");
				}
			}
		}
		for (String permissionId : allPermissionIds) {
			SysRolePermission rolePermission = new SysRolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(Integer.parseInt(permissionId));
			result = sysRolePermissionDao.insert(rolePermission);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		updateRoleCache(role.getId());
		return resp;
	}

	@Transactional
	@Override
	public RoleResp updateRole(RoleReq.UpdateReq req) {
		RoleResp resp = new RoleResp();
		SysRole role = sysRoleDao.selectById(req.getId());
		if (role == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (StringUtils.isNotEmpty(req.getName())) {
			role.setName(req.getName());
		}
		if (StringUtils.isNotEmpty(req.getDescription())) {
			role.setDescription(req.getDescription());
		}
		role.setUpdateTime(new Date());
		int result = sysRoleDao.updateById(role);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		syncRole(role, req);
		updateRoleCache(role.getId());
		return resp;
	}

	private void syncRole(SysRole role, RoleReq.UpdateReq req) {
		List<SysRolePermission> dbList = sysRolePermissionDao.getAllByRoleId(role.getId());
		Map<String, SysRolePermission> dbMap = new HashMap<>();
		for (SysRolePermission sysRolePermission : dbList) {
			dbMap.put(sysRolePermission.getPermissionId() + "", sysRolePermission);
		}
		List<String> curList = new ArrayList<>();
		for (ModuleVo moduleVo : req.getModules()) {
			for (MenuVo menuVo : moduleVo.getMenus()) {
				if ("1".equals(menuVo.getSelected())) {
					List<SysPermission> permissions = sysPermissionDao.getByModuleCodeMenuCode(moduleVo.getModuleCode(), menuVo.getMenuCode());
					for (SysPermission sysPermission : permissions) {
						curList.add(sysPermission.getId() + "");
					}
				}
			}
		}
		Set<String> insertList = new HashSet<>();
		List<SysRolePermission> updateList = new ArrayList<>();
		for (String permissionId : curList) {
			if (dbMap.containsKey(permissionId)) {
				SysRolePermission sysRolePermission = dbMap.get(permissionId);
				if (sysRolePermission.getIsDelete()) {
					sysRolePermission.setIsDelete(false);
					updateList.add(sysRolePermission);
				}
			} else {
				insertList.add(permissionId);
			}
		}
		for (SysRolePermission rolePermission : dbList) {
			if (!curList.contains(rolePermission.getPermissionId() + "")) {
				if (!rolePermission.getIsDelete()) {
					rolePermission.setIsDelete(true);
					updateList.add(rolePermission);
				}
			}
		}
		int result;
		for (String permissionId : insertList) {
			SysRolePermission rolePermission = new SysRolePermission();
			rolePermission.setRoleId(role.getId());
			rolePermission.setPermissionId(Integer.parseInt(permissionId));
			result = sysRolePermissionDao.insert(rolePermission);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
		for (SysRolePermission sysRolePermission : updateList) {
			sysRolePermission.setUpdateTime(new Date());
			result = sysRolePermissionDao.updateById(sysRolePermission);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	private void updateRoleCache(Integer roleId) {
		Set<String> permissionCodes = getRolePermissionCodeList(roleId);
		redisCacheManager.hSet(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE, roleId + "", JSON.toJSONString(permissionCodes));
		List<ModuleVo> modules = getRoleModuleList(roleId);
		redisCacheManager.hSet(RedisKeyConstant.RBAC_ROLE_MODULE, roleId + "", JSON.toJSONString(modules));
	}

	@Override
	public StaffResp.PermissionResp getStaffPermissionInfo(StaffReq.BaseReq req) {
		StaffResp.PermissionResp resp = new StaffResp.PermissionResp();
		resp.setId(req.getStaffId());
		resp.setModules(sysPermissionDao.getStaffModuleList(req.getStaffId()));
		resp.setPermissions(sysPermissionDao.getStaffPermissionCodeList(req.getStaffId()));
		return resp;
	}

	@Override
	public Set<String> getPermissionCodeList(Integer staffId) {
		return null;
	}

	@Override
	public Set<String> getRoleIdList(Integer staffId) {
		return sysStaffRoleDao.getRoleIdList(staffId);
	}

	@Override
	public Set<String> getRolePermissionCodeList(Integer roleId) {
		return sysRolePermissionDao.getRolePermissionCodeList(roleId);
	}

	@Override
	public Set<String> getStaffSpecialPermissionCodeList(Integer staffId) {
		List<PermissionVo> list = sysStaffPermissionDao.getStaffSpecialPermissionList(staffId);
		if (list.isEmpty()) {
			return Collections.EMPTY_SET;
		}
		Set<String> result = new HashSet<>();
		for (PermissionVo item : list) {
			result.add(item.getPermissionCode());
		}
		return result;
	}

	@Override
	public Set<String> getAllNormalPermissionCodeList() {
		SysPermissionCondition condition = new SysPermissionCondition();
		condition.setPermissionType("0");
		List<SysPermission> list = sysPermissionDao.selectList(condition);
		if (list.size() > 0) {
			Set<String> result = new HashSet<>();
			for (SysPermission item : list) {
				result.add(item.getPermissionCode());
			}
			return result;
		}
		return Collections.EMPTY_SET;
	}

	@Override
	public List<ModuleVo> getAllModuleList() {
		return sysPermissionDao.getAllModuleList();
	}

	@Override
	public List<ModuleVo> getRoleModuleList(Integer roleId) {
		return sysPermissionDao.getRoleModuleList(roleId);
	}

	@Override
	public ListResp<PermissionVo> getSpecialPermissionList() {
		ListResp<PermissionVo> resp = new ListResp<>();
		List<SysPermission> list = sysPermissionDao.getSpecialPermissionList();
		List<PermissionVo> result = new ArrayList<>();
		for (SysPermission item : list) {
			PermissionVo vo = new PermissionVo();
			vo.setPermissionId(item.getId());
			vo.setPermissionCode(item.getPermissionCode());
			vo.setPermissionName(item.getPermissionName());
			vo.setPermissionType(item.getPermissionType());
			result.add(vo);
		}
		resp.setList(result);
		return resp;
	}

	@Override
	public PersonaResp login(PersonaReq req) throws Exception {
		StaffCondition condition = new StaffCondition();
		condition.setMobile(req.getUsername());
		List<Staff> list = staffDao.selectList(condition);
		if (list.isEmpty()) {
			throw new BusinessException(ApiCallResult.LOGIN_FAIL);
		}
		Staff staff = list.get(0);
		if (!PasswordEncryption.authenticate(req.getPassword(), staff.getPasswordHash())) {
			throw new BusinessException(ApiCallResult.LOGIN_FAIL);
		}
		String uId = MD5Helper.encrypt32(staff.getMobile() + staff.getId() + "pai");
		redisCacheManager.hSet(key, uId, JSON.toJSONString(staff));
		PersonaResp resp = new PersonaResp();
		resp.setIsAdmin(staff.getPersonaAdmin());
		resp.setMobile(staff.getMobile());
		resp.setuId(uId);
		return resp;
	}

	@Override
	public StaffResp toggleStatus(StaffReq.ToggleStatusReq req) {
		StaffResp resp = new StaffResp();
		Staff operator = staffDao.selectById(req.getOperatorId());
		if (!operator.getIsAdmin()) {
			throw new BusinessException(ApiCallResult.FAILURE, "只有管理员可以进行操作");
		}
		Staff staff = staffDao.selectById(req.getStaffId());
		if (staff == null) {
			throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
		}
		if (StringUtils.isEmpty(staff.getStatus())) {
			staff.setStatus("1");
		} else if ("1".equals(staff.getStatus())) {
			staff.setStatus("0");
		} else {
			staff.setStatus("1");
		}
		staff.setUpdateTime(new Date());
		int result = staffDao.updateById(staff);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return resp;
	}

	@Override
	public boolean saveOperationRecord(TBackstageOperationRecord record) {
		return backstageOperationRecordDao.insert(record) > 0 ? true : false;
	}
}