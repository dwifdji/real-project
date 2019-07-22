package com.winback.admin.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.constant.SystemConstant;
import com.winback.arch.core.redis.RedisCachemanager;
import com.winback.core.facade.account.SysFacade;
import com.winback.core.facade.account.vo.Menu;
import com.winback.core.facade.account.vo.Module;
import com.winback.core.facade.account.vo.Permission;
import com.winback.core.facade.account.vo.SubModule;
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
    private SysFacade sysFacade;
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

    private List<Module> getStaffModuleList(String staffId, Boolean isAdmin) {
        if (isAdmin) {
            return getAllModuleList();
        }
        Set<String> roleIds = getRoleIds(staffId);
        if (roleIds.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        Map<String, Module> moduleMap = new HashMap<>();
        Map<String, Map<String, SubModule>> subModuleMap = new HashMap<>();
        Map<String, Map<String, Menu>> menuMap = new HashMap<>();
        for (String roleId : roleIds) {
            List<Module> roleModules = getRoleModuleList(roleId);
            for (Module module : roleModules) {
                // 合并模块
                if (!moduleMap.containsKey(module.getCode())) {
                    moduleMap.put(module.getCode(), module);
                    processSubModule(module, subModuleMap, menuMap);
                } else {
                    processSubModule(module, subModuleMap, menuMap);
                }
            }
        }
        List<Module> modules = new ArrayList<>();
        for (String key : moduleMap.keySet()) {
            List<SubModule> subModules = new ArrayList<>();
            Module module = moduleMap.get(key);
            Map<String, SubModule> subMap = subModuleMap.get(module.getCode());
            for (String subModuleCode : subMap.keySet()) {
                SubModule subModule = subMap.get(subModuleCode);
                List<Menu> menus = new ArrayList<>();
                Map<String, Menu> map = menuMap.get(subModule.getCode());
                for (String menuCode : map.keySet()) {
                    menus.add(map.get(menuCode));
                }
                Collections.sort(menus, new Comparator<Menu>() {
                    @Override
                    public int compare(Menu o1, Menu o2) {
                        return o1.getOrderNum().compareTo(o2.getOrderNum());
                    }
                });
                subModule.setMenus(menus);
                subModules.add(subModule);
            }
            Collections.sort(subModules, new Comparator<SubModule>() {
                @Override
                public int compare(SubModule o1, SubModule o2) {
                    return o1.getOrderNum().compareTo(o2.getOrderNum());
                }
            });
            module.setSubModules(subModules);
            modules.add(module);
        }
        Collections.sort(modules, new Comparator<Module>() {
            @Override
            public int compare(Module o1, Module o2) {
                return o1.getOrderNum().compareTo(o2.getOrderNum());
            }
        });
        return modules;
    }

    private void processSubModule(Module module, Map<String, Map<String, SubModule>> subModuleMap, Map<String, Map<String, Menu>> menuMap) {
        if (!subModuleMap.containsKey(module.getCode())) {
            Map<String, SubModule> subMap = new HashMap<>();
            for (SubModule subModule : module.getSubModules()) {
                if (!subMap.containsKey(subModule.getCode())) {
                    subMap.put(subModule.getCode(), subModule);
                }
                processMenu(subModule, menuMap);
            }
            subModuleMap.put(module.getCode(), subMap);
        } else {
            Map<String, SubModule> subMap = subModuleMap.get(module.getCode());
            for (SubModule subModule : module.getSubModules()) {
                if (!subMap.containsKey(subModule.getCode())) {
                    subMap.put(subModule.getCode(), subModule);
                }
                processMenu(subModule, menuMap);
            }
        }
    }

    private void processMenu(SubModule subModule, Map<String, Map<String, Menu>> menuMap) {
        if (!menuMap.containsKey(subModule.getCode())) {
            Map<String, Menu> map = new HashMap<>();
            for (Menu menu : subModule.getMenus()) {
                if (!map.containsKey(menu.getCode())) {
                    map.put(menu.getCode(), menu);
                }
            }
            menuMap.put(subModule.getCode(), map);
        } else {
            Map<String, Menu> map = menuMap.get(subModule.getCode());
            for (Menu menu : subModule.getMenus()) {
                if (!map.containsKey(menu.getCode())) {
                    map.put(menu.getCode(), menu);
                }
            }
        }
    }

    private List<Module> getAllModuleList() {
        String cache = (String) redisCacheManager.get(RedisKeyConstant.RBAC_ALL_MODULE);
        List<Module> modules;
        if (StringUtils.isEmpty(cache)) {
            modules = sysFacade.getModuleList().getList();
            redisCacheManager.set(RedisKeyConstant.RBAC_ALL_MODULE, JSON.toJSONString(modules), expire);
        } else {
            modules = JSON.parseArray(cache, Module.class);
        }
        return modules;
    }

    private List<Module> getRoleModuleList(String roleId) {
        String cache = (String) redisCacheManager.hGet(RedisKeyConstant.RBAC_ROLE_MODULE, roleId);
        List<Module> modules;
        if (StringUtils.isEmpty(cache)) {
            modules = sysFacade.getModuleList(Integer.parseInt(roleId));
            redisCacheManager.hSet(RedisKeyConstant.RBAC_ROLE_MODULE, roleId, JSON.toJSONString(modules));
        } else {
            modules = JSON.parseArray(cache, Module.class);
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
        String cache = (String) redisCacheManager.hGet(SystemConstant.ADMIN_SESSION_KEY, loginId);
        if (StringUtils.isNotEmpty(cache)) {
            JSONObject cacheObj = (JSONObject) JSONObject.parse(cache);
            if (cacheObj.containsKey("adminFlag")) {
                return cacheObj.getBoolean("adminFlag");
            }
        }
        return false;
    }

    private String getStaffId(String loginId) {
        return loginId;
    }

    private Set<String> getAllNormalPermissionCodes() {
        String cache = (String) redisCacheManager.get(RedisKeyConstant.RBAC_ALL_NORMAL_PERMISSION_CODE);
        Set<String> permissionCodes;
        if (StringUtils.isEmpty(cache)) {
            permissionCodes = sysFacade.getAllNormalPermissionCodeList();
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
            roleIds = sysFacade.getRoleIdList(Integer.parseInt(staffId));
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
            permissionCodes = sysFacade.getRolePermissionCodeList(Integer.parseInt(roleId));
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
            return Collections.EMPTY_SET;
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
            permissionCodes = sysFacade.getStaffSpecialPermissionCodeList(Integer.parseInt(staffId));
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
            ListResp<Permission> resp = sysFacade.getSpecialPermissionList();
            for (Permission permissionVo : resp.getList()) {
                permissionCodes.add(permissionVo.getPermissionCode());
            }
            redisCacheManager.set(RedisKeyConstant.RBAC_ALL_SPECIAL_PERMISSION_CODE, JSON.toJSONString(permissionCodes), expire);
        } else {
            permissionCodes = JSON.parseObject(cache, Set.class);
        }
        return permissionCodes;
    }
}
