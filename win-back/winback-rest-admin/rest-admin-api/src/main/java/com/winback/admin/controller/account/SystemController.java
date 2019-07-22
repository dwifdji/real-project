package com.winback.admin.controller.account;

import com.winback.admin.controller.AbstractController;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.constant.RedisKeyConstant;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.arch.core.redis.RedisCachemanager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: SystemController
 * @ProjectName winback
 * @Description:
 * @date 2019/3/4 16:35
 */
@Slf4j
@RestController
public class SystemController extends AbstractController {
    @Resource
    private RedisCachemanager redisCacheManager;

    /**
     * 清除权限缓存
     */
    @PostMapping(value = "/system/clear/permission/cache")
    public ResponseModel clearPermissionCache(@RequestBody Map<String, Object> params) {
        Set<String> keys;
        String type = (String) params.get("type");
        if (type.equals("base")) {
            keys = new HashSet<>();
            keys.add(RedisKeyConstant.RBAC_ALL_MODULE);
            keys.add(RedisKeyConstant.RBAC_ALL_NORMAL_PERMISSION_CODE);
            keys.add(RedisKeyConstant.RBAC_ALL_SPECIAL_PERMISSION_CODE);

            keys.add(RedisKeyConstant.RBAC_ROLE_MODULE);
            keys.add(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE);
        } else if (type.equals("all")) {
            keys = new HashSet<>();
            keys.add(RedisKeyConstant.RBAC_ALL_MODULE);
            keys.add(RedisKeyConstant.RBAC_ALL_NORMAL_PERMISSION_CODE);
            keys.add(RedisKeyConstant.RBAC_ALL_SPECIAL_PERMISSION_CODE);

            keys.add(RedisKeyConstant.RBAC_ROLE_MODULE);
            keys.add(RedisKeyConstant.RBAC_ROLE_PERMISSION_CODE);

            keys.add(RedisKeyConstant.RBAC_STAFF_ROLE);
            keys.add(RedisKeyConstant.RBAC_STAFF_SPECIAL_PERMISSION);
        } else {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        for (String key : keys) {
            log.info("清除权限缓存,key={}", key);
            redisCacheManager.del(key);
        }
        return ResponseModel.succ();
    }
}
