package com._360pai.admin.shiro;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.vo.MenuVo;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.facade.account.vo.PermissionVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xdrodger
 * @Title: PermissionService
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 14:39
 */
@Service
public class PermissionService {

    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;
    @Resource
    private RedisCachemanager redisCacheManager;

    private static long expire = 3600;

    public JSONObject getPermission(String staffId, Boolean isAdmin) {
        JSONObject data = new JSONObject();
        data.put("modules", getStaffModuleList(staffId, isAdmin));
        data.put("permissionCodes", getStaffSPermissionCodes(staffId, isAdmin));
        data.put("specialPermissionCodes", getStaffSpecialPermissionCodes(staffId, isAdmin));
        return data;
    }

    private List<ModuleVo> getStaffModuleList(String staffId, Boolean isAdmin) {
        Set<String> roleIds = getRoleIds(staffId);
        if (roleIds.isEmpty() || isAdmin) {
            return getAllModuleList(isAdmin);
        }
        Map<String, ModuleVo> moduleMap = new HashMap<>();
        Map<String, Map<String, MenuVo>> menuMap = new HashMap<>();
        for (String roleId : roleIds) {
            List<ModuleVo> roleModules = getRoleModuleList(roleId);
            for (ModuleVo moduleVo : roleModules) {
                // 合并模块
                if (!moduleMap.containsKey(moduleVo.getModuleCode())) {
                    moduleMap.put(moduleVo.getModuleCode(), moduleVo);
                    Map<String, MenuVo> map = new HashMap<>();
                    for (MenuVo menuVo : moduleVo.getMenus()) {
                        map.put(menuVo.getMenuCode(), menuVo);
                    }
                    menuMap.put(moduleVo.getModuleCode(), map);
                } else {
                    // 合并模块下菜单
                    Map<String, MenuVo> map = menuMap.get(moduleVo.getModuleCode());
                    for (MenuVo menuVo : moduleVo.getMenus()) {
                        if (!map.containsKey(menuVo.getMenuCode())) {
                            map.put(menuVo.getMenuCode(), menuVo);
                        }
                    }
                }
            }
        }
        List<ModuleVo> modules = new ArrayList<>();
        for (String key : moduleMap.keySet()) {
            List<MenuVo> menuVos = new ArrayList<>();
            ModuleVo moduleVo = moduleMap.get(key);
            Map<String, MenuVo> map = menuMap.get(moduleVo.getModuleCode());
            for (String menuCode : map.keySet()) {
                menuVos.add(map.get(menuCode));
            }
            Collections.sort(menuVos, new Comparator<MenuVo>() {
                @Override
                public int compare(MenuVo o1, MenuVo o2) {
                    return o1.getMenuOrder().compareTo(o2.getMenuOrder());
                }
            });
            moduleVo.setMenus(menuVos);
            modules.add(moduleVo);
        }
        Collections.sort(modules, new Comparator<ModuleVo>() {
            @Override
            public int compare(ModuleVo o1, ModuleVo o2) {
                return o1.getModuleOrder().compareTo(o2.getModuleOrder());
            }
        });
        return modules;
    }

    private List<ModuleVo> getAllModuleList(Boolean isAdmin) {
        String cache = (String) redisCacheManager.get(RedisKeyConstant.RBAC_ALL_MODULE);
        List<ModuleVo> modules;
        if (StringUtils.isEmpty(cache)) {
            modules = staffFacade.getAllModuleList();
            redisCacheManager.set(RedisKeyConstant.RBAC_ALL_MODULE, JSON.toJSONString(modules), expire);
        } else {
            modules = JSON.parseArray(cache, ModuleVo.class);
        }
        if (!isAdmin) {
            Iterator<ModuleVo> itr = modules.iterator();
            while (itr.hasNext()) {
                ModuleVo moduleVo = itr.next();
                if ("nbgl".equals(moduleVo.getModuleCode())) {
                    itr.remove();
                }
            }
        }
        return modules;
    }

    private List<ModuleVo> getRoleModuleList(String roleId) {
        String cache = (String) redisCacheManager.hGet(RedisKeyConstant.RBAC_ROLE_MODULE, roleId);
        List<ModuleVo> modules;
        if (StringUtils.isEmpty(cache)) {
            modules = staffFacade.getRoleModuleList(Integer.parseInt(roleId));
            redisCacheManager.hSet(RedisKeyConstant.RBAC_ROLE_MODULE, roleId, JSON.toJSONString(modules));
        } else {
            modules = JSON.parseArray(cache, ModuleVo.class);
        }
        return modules;
    }

    public Set<String> getPermissionCodeList(String loginId) {
        String staffId = getStaffId(loginId);
        Boolean isAdmin = isAdmin(loginId);
        Set<String> permissionCodes = getStaffSPermissionCodes(staffId, isAdmin);
        permissionCodes.addAll(getStaffSpecialPermissionCodes(staffId, isAdmin));
        return permissionCodes;
    }

    private Boolean isAdmin(String loginId) {
        String cache = (String) redisCacheManager.hGet(SystemConstant.ADMIN_MOBILE_AUTHEN_SESSION_CACHE_KEY, loginId);
        if (StringUtils.isNotEmpty(cache)) {
            JSONObject cacheObj = (JSONObject) JSONObject.parse(cache);
            if (cacheObj.containsKey("isAdmin")) {
                return cacheObj.getBoolean("isAdmin");
            }
        }
        return false;
    }

    private String getStaffId(String loginId) {
        return loginId.split("_")[1];
    }

    private Set<String> getAllNormalPermissionCodes() {
        String cache = (String) redisCacheManager.get(RedisKeyConstant.RBAC_ALL_NORMAL_PERMISSION_CODE);
        Set<String> permissionCodes;
        if (StringUtils.isEmpty(cache)) {
            permissionCodes = staffFacade.getAllNormalPermissionCodeList();
            redisCacheManager.set(RedisKeyConstant.RBAC_ALL_NORMAL_PERMISSION_CODE, JSON.toJSONString(permissionCodes), expire);
        } else {
            permissionCodes = JSON.parseObject(cache, Set.class);
        }
        return permissionCodes;
    }

    private Set<String> getRoleIds(String staffId) {
        String cache = (String) redisCacheManager.hGet(RedisKeyConstant.RBAC_STAFF_ROLE, staffId);
        Set<String> roleIds;
        if (StringUtils.isEmpty(cache)) {
            roleIds = staffFacade.getRoleIdList(Integer.parseInt(staffId));
            redisCacheManager.hSet(RedisKeyConstant.RBAC_STAFF_ROLE, staffId, JSON.toJSONString(roleIds));
        } else {
            roleIds = JSON.parseObject(cache, Set.class);
        }
        return roleIds;
    }

    private Set<String> getRolePermissionCodes(String roleId) {
        String cache = (String) redisCacheManager.hGet(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE, roleId);
        Set<String> permissionCodes;
        if (StringUtils.isEmpty(cache)) {
            permissionCodes = staffFacade.getRolePermissionCodeList(Integer.parseInt(roleId));
            redisCacheManager.hSet(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE, roleId, JSON.toJSONString(permissionCodes));
        } else {
            permissionCodes = JSON.parseObject(cache, Set.class);
        }
        return permissionCodes;
    }

    private Set<String> getStaffSPermissionCodes(String staffId, Boolean isAdmin) {
        if (isAdmin) {
            return getAllNormalPermissionCodes();
        }
        Set<String> roleIds = getRoleIds(staffId);
        if (roleIds.isEmpty()) {
            return getAllNormalPermissionCodes();
        }
        Set<String> permissionCodes = new HashSet<>();
        for (String roleId : roleIds) {
            permissionCodes.addAll(getRolePermissionCodes(roleId));
        }
        return permissionCodes;
    }

    private Set<String> getStaffSpecialPermissionCodes(String staffId, Boolean isAdmin) {
        if (isAdmin) {
            return getAllSpecialPermissionCodes();
        }
        String cache = (String) redisCacheManager.hGet(RedisKeyConstant.RBAC_STAFF_SPECIAL_PERMISSION, staffId);
        Set<String> permissionCodes;
        if (StringUtils.isEmpty(cache)) {
            permissionCodes = staffFacade.getStaffSpecialPermissionCodeList(Integer.parseInt(staffId));
            redisCacheManager.hSet(RedisKeyConstant.RBAC_STAFF_SPECIAL_PERMISSION, staffId, JSON.toJSONString(permissionCodes));
        } else {
            permissionCodes = JSON.parseObject(cache, Set.class);
        }
        return permissionCodes;
    }

    private Set<String> getAllSpecialPermissionCodes() {
        String cache = (String) redisCacheManager.get(RedisKeyConstant.RBAC_ALL_SPECIAL_PERMISSION_CODE);
        Set<String> permissionCodes;
        if (StringUtils.isEmpty(cache)) {
            permissionCodes = new HashSet<>();
            ListResp<PermissionVo> resp = staffFacade.getSpecialPermissionList();
            for (PermissionVo permissionVo : resp.getList()) {
                permissionCodes.add(permissionVo.getPermissionCode());
            }
            redisCacheManager.set(RedisKeyConstant.RBAC_ALL_SPECIAL_PERMISSION_CODE, JSON.toJSONString(permissionCodes), expire);
        } else {
            permissionCodes = JSON.parseObject(cache, Set.class);
        }
        return permissionCodes;
    }
}
