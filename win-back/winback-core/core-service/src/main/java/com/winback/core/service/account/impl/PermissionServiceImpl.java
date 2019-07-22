package com.winback.core.service.account.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.common.utils.PasswordEncryption;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.dao.account.*;
import com.winback.core.exception.BusinessException;
import com.winback.core.facade.account.req.AdminSysReq;
import com.winback.core.facade.account.vo.*;
import com.winback.core.model.account.*;
import com.winback.core.service.account.PermissionService;
import com.winback.core.utils.ReqConvertUtil;
import com.winback.core.utils.RespConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

/**
 * @author xdrodger
 * @Title: PermissionServiceImpl
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 15:08
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private TSysModuleDao moduleDao;
    @Autowired
    private TSysSubModuleDao subModuleDao;
    @Autowired
    private TSysMenuDao menuDao;
    @Autowired
    private TSysPermissionDao permissionDao;
    @Autowired
    private TSysStaffDao staffDao;
    @Autowired
    private TSysRoleDao roleDao;
    @Autowired
    private TSysStaffRoleMapDao staffRoleMapDao;
    @Autowired
    private TSysStaffPermissionMapDao staffPermissionMapDao;
    @Autowired
    private TSysRoleMenuMapDao roleMenuMapDao;
    @Autowired
    private TSysMenuPermissionMapDao menuPermissionMapDao;
    @Resource
    private RedisCachemanager redisCacheManager;

    @Override
    public TSysStaff getStaff(Integer staffId) {
        return staffDao.selectById(staffId);
    }

    @Override
    public ListResp<Module> getModuleList() {
        ListResp<Module> resp = new ListResp<>();
        List<Module> resultList = getAllModuleList();
        resp.setList(resultList);
        return resp;
    }

    private List<Module> getAllModuleList() {
        List<Module> resultList = new LinkedList<>();
        List<TSysModule> modules = moduleDao.findAll();
        for (TSysModule item : modules) {
            Module module = new Module();
            BeanUtils.copyProperties(item, module);
            module.setSubModules(getSubModuleList(item.getId()));
            resultList.add(module);
        }
        return resultList;
    }

    private List<SubModule> getSubModuleList(Integer moduleId) {
        List<TSysSubModule> subModules = subModuleDao.findBy(moduleId);
        List<SubModule> resultList = new LinkedList<>();
        for (TSysSubModule item : subModules) {
            SubModule subModule = new SubModule();
            BeanUtils.copyProperties(item, subModule);
            subModule.setMenus(getMenuList(item.getId()));
            resultList.add(subModule);
        }
        return resultList;
    }

    private  List<Menu> getMenuList(Integer moduleId) {
        List<Menu> resultList = new LinkedList<>();
        List<TSysMenu> menus = menuDao.findByModuleId(moduleId);
        for (TSysMenu item : menus) {
            Menu menu = new Menu();
            BeanUtils.copyProperties(item, menu);
            resultList.add(menu);
        }
        return resultList;
    }

    @Override
    public ListResp<Permission> getSpecialPermissionList() {
        ListResp<Permission> resp = new ListResp<>();
        List<Permission> resultList = new LinkedList<>();
        List<TSysPermission> list = permissionDao.getSpecialPermissionList();
        for (TSysPermission item : list) {
            Permission vo = new Permission();
            BeanUtils.copyProperties(item, vo);
            resultList.add(vo);
        }
        resp.setList(resultList);
        return resp;
    }

    @Override
    public PageInfoResp<Staff> getStaffList(AdminSysReq.QueryReq req) {
        PageInfoResp<Staff> resp = new PageInfoResp<>();
        List<Staff> resultList = new ArrayList<>();
        PageInfo<TSysStaff> pageInfo = staffDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "s.id desc");
        for (TSysStaff item : pageInfo.getList()) {
            Staff vo = RespConvertUtil.convertToStaff(item);
            vo.setRoles(getRoles(item.getId()));
            vo.setSpecialPermissions(getSpecialPermissions(item.getId()));
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    private List<Role> getRoles(Integer staffId) {
        List<Role> resultList = new ArrayList<>();
        List<TSysRole> list = staffRoleMapDao.findRoleBy(staffId);
        for (TSysRole item : list) {
            Role vo = RespConvertUtil.convertToRole(item);
            resultList.add(vo);
        }
        return resultList;
    }

    private List<Permission> getSpecialPermissions(Integer staffId) {
        List<Permission> resultList = new ArrayList<>();
        List<TSysPermission> list = staffPermissionMapDao.findPermissionBy(staffId);
        for (TSysPermission item : list) {
            Permission vo = RespConvertUtil.convertToPermission(item);
            resultList.add(vo);
        }
        return resultList;
    }

    @Transactional
    @Override
    public Integer staffAdd(AdminSysReq.StaffAddReq req) {
        TSysStaff staff = staffDao.findByMobile(req.getMobile());
        if (staff != null) {
            throw new BusinessException("手机号已存在");
        }
        if (req.getRoleIds() == null || req.getRoleIds().isEmpty()) {
            throw new BusinessException("角色必选");
        }
        staff = ReqConvertUtil.convertToTSysStaff(req);
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
        staff.setPassword(encryptedPassword);
        int result = staffDao.insert(staff);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        syncStaffRole(staff.getId(), req.getRoleIds());
        syncStaffSpecialPermission(staff.getId(), req.getSpecialPermissionIds());
        return staff.getId();
    }

    @Override
    public Integer staffUpdate(AdminSysReq.StaffUpdateReq req) {
        TSysStaff staff = staffDao.selectById(req.getId());
        if (staff == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        staff = ReqConvertUtil.convertToTSysStaff(req);
        int result = staffDao.updateById(staff);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        syncStaffRole(staff.getId(), req.getRoleIds());
        syncStaffSpecialPermission(staff.getId(), req.getSpecialPermissionIds());
        return staff.getId();
    }

    @Override
    public Integer staffResetPassword(AdminSysReq.StaffResetPasswordReq req) {
        TSysStaff staff = staffDao.selectById(req.getId());
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
        staff.setPassword(encryptedPassword);
        int result = staffDao.updateById(staff);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return staff.getId();
    }

    @Override
    public PageInfoResp<Role> getRoleList(AdminSysReq.QueryReq req) {
        PageInfoResp<Role> resp = new PageInfoResp<>();
        List<Role> resultList = new ArrayList<>();
        PageInfo<TSysRole> pageInfo = roleDao.getList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "r.id desc");
        for (TSysRole item : pageInfo.getList()) {
            Role vo = RespConvertUtil.convertToRole(item);
            vo.setModules(getAllModuleList(item.getId()));
            resultList.add(vo);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    private List<Module> getAllModuleList(Integer roleId) {
        List<Module> moduleList = getAllModuleList();
        List<TSysRoleMenuMap> roleMenuMapList = roleMenuMapDao.findBy(roleId);
        Set<Integer> selectedMenus = new HashSet<>();
        for (TSysRoleMenuMap item : roleMenuMapList) {
            if (item.getDeleteFlag()) {
                continue;
            }
            selectedMenus.add(item.getMenuId());
        }
        for (Module module : moduleList) {
            for (SubModule subModule : module.getSubModules()) {
                for (Menu menu : subModule.getMenus()) {
                    if (selectedMenus.contains(menu.getId())) {
                        menu.setSelected(true);
                        subModule.setSelected(true);
                        module.setSelected(true);
                    }
                }
            }
        }
        return moduleList;
    }

    @Override
    public List<Module> getModuleList(Integer roleId) {
        List<Module> list = getAllModuleList(roleId);
        List<Module> resultList = new ArrayList<>();
        for (Module item : list) {
            if (!item.getSelected()) {
                continue;
            }
            Module module = new Module();
            module.setCode(item.getCode());
            module.setName(item.getName());
            module.setSelected(item.getSelected());
            module.setOrderNum(item.getOrderNum());
            List<SubModule> subModules = new ArrayList<>();
            for (SubModule subModule : item.getSubModules()) {
                if (!subModule.getSelected()) {
                    continue;
                }
                SubModule subM = new SubModule();
                subM.setCode(subModule.getCode());
                subM.setName(subModule.getName());
                subM.setSelected(subModule.getSelected());
                subM.setOrderNum(subModule.getOrderNum());
                List<Menu> menuList = new ArrayList<>();
                for (Menu menu : subModule.getMenus()) {
                    if (menu.getSelected()) {
                        menuList.add(menu);
                    }
                }
                subM.setMenus(menuList);
                subModules.add(subM);
            }
            module.setSubModules(subModules);
            resultList.add(module);
        }
        return resultList;
    }

    @Override
    public Set<String> getAllNormalPermissionCodeList() {
        Set<String> result = new HashSet<>();
        List<TSysPermission> list = permissionDao.getNormalPermissionList();
        for (TSysPermission item : list) {
            result.add(item.getPermissionCode());
        }
        return result;
    }

    @Override
    public Set<String> getRoleIdList(Integer staffId) {
        List<TSysRole> list = staffRoleMapDao.findRoleBy(staffId);
        Set<String> resultList = new HashSet<>();
        for (TSysRole item : list) {
            resultList.add(item.getId() + "");
        }
        return resultList;
    }

    @Override
    public Set<String> getRolePermissionCodeList(Integer roleId) {
        List<TSysPermission> list = roleMenuMapDao.findPermissionBy(roleId);
        Set<String> resultList = new HashSet<>();
        for (TSysPermission item : list) {
            resultList.add(item.getPermissionCode() + "");
        }
        return resultList;
    }

    @Override
    public Set<String> getStaffSpecialPermissionCodeList(Integer staffId) {
        List<TSysPermission> list = staffPermissionMapDao.findPermissionBy(staffId);
        Set<String> resultList = new HashSet<>();
        for (TSysPermission item : list) {
            resultList.add(item.getPermissionCode() + "");
        }
        return resultList;
    }


    @Override
    public Integer roleAdd(AdminSysReq.RoleAddReq req) {
        TSysRole role = roleDao.findBy(req.getName());
        if (role != null) {
            throw new BusinessException("角色已存在");
        }
        if (req.getMenuIds() == null || req.getMenuIds().isEmpty()) {
            throw new BusinessException("菜单必选");
        }
        role = ReqConvertUtil.convertToTSysRole(req);
        int result = roleDao.insert(role);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        syncRoleMenu(role.getId(), req.getMenuIds());
        return role.getId();
    }

    @Override
    public Integer roleUpdate(AdminSysReq.RoleUpdateReq req) {
        TSysRole role = roleDao.selectById(req.getId());
        if (role == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        role = ReqConvertUtil.convertToTSysRole(req);
        int result = roleDao.updateById(role);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        if (req.getMenuIds().size() > 0) {
            syncRoleMenu(role.getId(), req.getMenuIds());
        }
        redisCacheManager.del(RedisKeyConstant.RBAC_STAFF_ROLE);
        return role.getId();
    }

    @Override
    public Integer roleDelete(AdminSysReq.RoleDeleteReq req) {
        TSysRole role = roleDao.selectById(req.getId());
        if (role == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        role.setDeleteFlag(true);
        int result = roleDao.updateById(role);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return role.getId();
    }

    private void syncRoleMenu(Integer roleId, List<Integer> menuIds) {
        List<TSysRoleMenuMap> dbList = roleMenuMapDao.findBy(roleId);
        Map<Integer, TSysRoleMenuMap> dbMap = new HashMap<>();
        for (TSysRoleMenuMap item : dbList) {
            dbMap.put(item.getMenuId(), item);
        }
        Set<Integer> insertList = new HashSet<>();
        List<TSysRoleMenuMap> updateList = new ArrayList<>();
        Set<Integer> curList = new HashSet<>();
        for (Integer menuId : menuIds) {
            curList.add(menuId);
            if (dbMap.containsKey(menuId)) {
                TSysRoleMenuMap staffRoleMap = dbMap.get(menuId);
                if (staffRoleMap.getDeleteFlag()) {
                    staffRoleMap.setDeleteFlag(false);
                    updateList.add(staffRoleMap);
                }
            } else {
                insertList.add(menuId);
            }
        }
        for (TSysRoleMenuMap item : dbList) {
            if (!curList.contains(item.getMenuId())) {
                if (!item.getDeleteFlag()) {
                    item.setDeleteFlag(true);
                    updateList.add(item);
                }
            }
        }
        int result;
        for (Integer menuId : insertList) {
            TSysRoleMenuMap roleMenuMap = new TSysRoleMenuMap();
            roleMenuMap.setRoleId(roleId);
            roleMenuMap.setMenuId(menuId);
            result = roleMenuMapDao.insert(roleMenuMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        for (TSysRoleMenuMap item : updateList) {
            result = roleMenuMapDao.updateById(item);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        redisCacheManager.del(RedisKeyConstant.RBAC_ROLE_MODULE);
        redisCacheManager.del(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE);
    }

    private void syncStaffRole(Integer staffId, List<Integer> roleIds) {
        List<TSysStaffRoleMap> dbList = staffRoleMapDao.findAllBy(staffId);
        Map<Integer, TSysStaffRoleMap> dbMap = new HashMap<>();
        for (TSysStaffRoleMap item : dbList) {
            dbMap.put(item.getRoleId(), item);
        }
        Set<Integer> insertList = new HashSet<>();
        List<TSysStaffRoleMap> updateList = new ArrayList<>();
        Set<Integer> curList = new HashSet<>();
        for (Integer roleId : roleIds) {
            curList.add(roleId);
            if (dbMap.containsKey(roleId)) {
                TSysStaffRoleMap staffRoleMap = dbMap.get(roleId);
                if (staffRoleMap.getDeleteFlag()) {
                    staffRoleMap.setDeleteFlag(false);
                    updateList.add(staffRoleMap);
                }
            } else {
                insertList.add(roleId);
            }
        }
        for (TSysStaffRoleMap item : dbList) {
            if (!curList.contains(item.getRoleId())) {
                if (!item.getDeleteFlag()) {
                    item.setDeleteFlag(true);
                    updateList.add(item);
                }
            }
        }
        int result;
        for (Integer roleId : insertList) {
            TSysStaffRoleMap staffRoleMap = new TSysStaffRoleMap();
            staffRoleMap.setStaffId(staffId);
            staffRoleMap.setRoleId(roleId);
            result = staffRoleMapDao.insert(staffRoleMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        for (TSysStaffRoleMap item : updateList) {
            result = staffRoleMapDao.updateById(item);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        redisCacheManager.del(RedisKeyConstant.RBAC_STAFF_ROLE);
        redisCacheManager.del(RedisKeyConstant.RBAC_STAFF_SPECIAL_PERMISSION);
    }

    private void syncStaffSpecialPermission(Integer staffId, List<Integer> specialPermissionIds) {
        List<TSysStaffPermissionMap> dbList = staffPermissionMapDao.findBy(staffId);
        Map<Integer, TSysStaffPermissionMap> dbMap = new HashMap<>();
        for (TSysStaffPermissionMap item : dbList) {
            dbMap.put(item.getPermissionId(), item);
        }
        Set<Integer> insertList = new HashSet<>();
        List<TSysStaffPermissionMap> updateList = new ArrayList<>();
        Set<Integer> curList = new HashSet<>();
        for (Integer permissionId : specialPermissionIds) {
            curList.add(permissionId);
            if (dbMap.containsKey(permissionId)) {
                TSysStaffPermissionMap staffPermissionMap = dbMap.get(permissionId);
                if (staffPermissionMap.getDeleteFlag()) {
                    staffPermissionMap.setDeleteFlag(false);
                    updateList.add(staffPermissionMap);
                }
            } else {
                insertList.add(permissionId);
            }
        }
        for (TSysStaffPermissionMap item : dbList) {
            if (!curList.contains(item.getPermissionId())) {
                if (!item.getDeleteFlag()) {
                    item.setDeleteFlag(true);
                    updateList.add(item);
                }
            }
        }
        int result;
        for (Integer permissionId : insertList) {
            TSysStaffPermissionMap staffPermissionMap = new TSysStaffPermissionMap();
            staffPermissionMap.setStaffId(staffId);
            staffPermissionMap.setPermissionId(permissionId);
            result = staffPermissionMapDao.insert(staffPermissionMap);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        for (TSysStaffPermissionMap item : updateList) {
            result = staffPermissionMapDao.updateById(item);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
    }
}
