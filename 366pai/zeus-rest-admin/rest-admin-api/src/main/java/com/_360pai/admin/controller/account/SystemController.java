package com._360pai.admin.controller.account;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.shiro.PermissionService;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.StaffVo;
import com._360pai.core.facade.assistant.CommonFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xdrodger
 * @Title: SystemController
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/18 14:10
 */
@RestController
@RequestMapping(value = "/admin/system", produces = "application/json;charset=UTF-8")
public class SystemController extends AbstractController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private PermissionService permissionService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Reference(version = "1.0.0")
    private CommonFacade commonFacade;

    /**
     * 清除表缓存
     */
    @PostMapping(value = "/clear/table/cache")
    public ResponseModel clearTableCache(@RequestBody Map<String, Object> params) {
        //StaffReq.BaseReq req = new StaffReq.BaseReq();
        //Integer operatorId = loadCurLoginId();
        //req.setStaffId(operatorId);
        //StaffResp.DetailResp resp = staffFacade.getStaff(req);
        //StaffVo staffVo = resp.getStaff();
        //if (!staffVo.getIsAdmin()) {
        //    return ResponseModel.fail(ApiCallResult.PERMISSION_DENIED);
        //}
        String key = (String) params.get("key");
        if (!key.startsWith(RedisKeyConstant.TABLE)) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        //LOGGER.info("清除表缓存，operatorId={},key={}", operatorId, key);
        LOGGER.info("清除表缓存,key={}", key);
        redisCachemanager.del(key);
        return ResponseModel.succ();
    }

    /**
     * 清除权限缓存
     */
    @PostMapping(value = "/clear/permission/cache")
    public ResponseModel clearPermissionCache(@RequestBody Map<String, Object> params) {
        //StaffReq.BaseReq req = new StaffReq.BaseReq();
        //Integer operatorId = loadCurLoginId();
        //req.setStaffId(operatorId);
        //StaffResp.DetailResp resp = staffFacade.getStaff(req);
        //StaffVo staffVo = resp.getStaff();
        //if (!staffVo.getIsAdmin()) {
        //    return ResponseModel.fail(ApiCallResult.PERMISSION_DENIED);
        //}
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
            //LOGGER.info("清除权限缓存，operatorId={},key={}", operatorId, key);
            LOGGER.info("清除权限缓存,key={}", key);
            redisCachemanager.del(key);
        }
        return ResponseModel.succ();
    }


    /**
     * 迁移机构数据
     */
    @GetMapping(value = "/migrate/agency/data")
    public ResponseModel migrateAgencyData() {
        commonFacade.migrateAgencyData();
        return ResponseModel.succ();
    }

    /**
     * 迁移用户数据
     */
    @GetMapping(value = "/migrate/account/data")
    public ResponseModel migrateAccountData() {
        commonFacade.migrateAccountData();
        return ResponseModel.succ();
    }

    /**
     * 同步机构法大大email
     */
    @GetMapping(value = "/sync/agency/fadadaemail")
    public ResponseModel syncAgencyFadadaEmial() {
        commonFacade.syncAgencyFadadaEmial();
        return ResponseModel.succ();
    }

    /**
     * 同步企业法大大email
     */
    @GetMapping(value = "/sync/company/fadadaemail")
    public ResponseModel syncCompanyFadadaEmial() {
        commonFacade.syncCompanyFadadaEmial();
        return ResponseModel.succ();
    }

    /**
     * 同步拍品商品类型
     */
    @GetMapping(value = "/sync/auctionActivityBusTypeName")
    public ResponseModel syncAuctionActivityBusTypeName() {
        commonFacade.syncAuctionActivityBusTypeName();
        return ResponseModel.succ();
    }

    /**
     * 同步AssetData省份信息
     */
    @GetMapping(value = "/sync/syncAssetData")
    public ResponseModel syncAssetData() {
        commonFacade.syncAssetData();
        return ResponseModel.succ();
    }

    /**
     * 同步预招商EnrollingActivityData省份信息
     */
    @GetMapping(value = "/sync/syncEnrollingActivityData")
    public ResponseModel syncEnrollingActivityData() {
        commonFacade.syncEnrollingActivityData();
        return ResponseModel.succ();
    }

    /**
     * 测试延迟队列
     */
    @PostMapping(value = "/testDelayQueue")
    public ResponseModel testDelayQueue(@RequestBody Map<String, Object> params) {
        long delay = 5;
        if (params.containsKey("delay")) {
            delay = ((Integer) params.get("delay")).longValue();
        }
        commonFacade.testDelayQueue(delay);
        return ResponseModel.succ();
    }

    /**
     * 测试队列
     */
    @PostMapping(value = "/testQueue")
    public ResponseModel testQueue(@RequestBody Map<String, Object> params) {
        //commonFacade.testQueue((String) params.get("message"));
        //Integer loop = 2000;
        //if (params.containsKey("loop")) {
        //    loop = (Integer) params.get("loop");
        //}
        //
        //for (int i = 0; i < loop ; i ++) {
        //    LOGGER.info("i={}", i);
        //    String suffix = i + "b" + RandomUtils.nextInt(10000,100000) + "";
        //    redisCachemanager.set("TEST_EX_" + suffix, suffix, RandomUtils.nextInt(3600 * 45, 3600 * 90));
        //}
        //Set<String> set = stringRedisTemplate.keys("TEST_EX_*");
        //for (String key : set) {
        //    LOGGER.info("key={}", key);
        //    stringRedisTemplate.delete(key);
        //}
        //String key = (String) params.get("key");
        //LOGGER.info("ttl={}", stringRedisTemplate.getExpire(key));
        String[] list = new String[] {
                "2019-07-04 00:00:00",
                "2019-07-04 16:20:39",
                "2019-07-04 23:59:59",
                "2019-07-05 00:00:00",
                "2019-07-05 23:59:59",
                "2019-07-06 00:00:00",
                "2019-07-08 00:00:00",
                "2019-07-08 23:59:59"

        };
        Date now = new Date();
        for (String item : list) {
            LOGGER.info("endAt={}, days={}", item, DateUtil.differentDaysByDate(now, DateUtil.strToDateLong(item)));
        }
        return ResponseModel.succ();
    }

    /**
     * 测试异常邮件
     */
    @PostMapping(value = "/testExceptionEmail")
    public ResponseModel testExceptionEmail(@RequestBody Map<String, Object> params) {
        String type = (String) params.get("type");
        if ("1".equals(type)) {
            throw new BusinessException("测试异常邮件，请忽略");
        }
        if ("2".equals(type)) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    /**
     * 迁移用户数据
     */
    @PostMapping(value = "/getAppletAccountListNeedRepair")
    public ResponseModel getAppletAccountListNeedRepair(@RequestBody Map<String, Object> params) {
        return ResponseModel.succ(commonFacade.getAppletAccountListNeedRepair(params));
    }


    /**
     * 同步省id
     */
    @GetMapping(value = "/sync/provinceId")
    public ResponseModel syncProvinceId() {
        commonFacade.syncProvinceId();
        return ResponseModel.succ();
    }

    /**
     * 迁移老360拍卖公众号用户到数据库接口
     */
    @PostMapping(value = "/syncOldSubscribeMp360PaiUserToDb")
    public ResponseModel syncOldSubscribeMp360PaiUserToDb(@RequestBody Map<String, Object> params) {
        commonFacade.syncOldSubscribeMp360PaiUserToDb();
        return ResponseModel.succ();
    }

    /**
     * 同步省名称拼音
     */
    @GetMapping(value = "/sync/province/pinyin")
    public ResponseModel syncProvincePinyin() {
        commonFacade.syncProvincePinyin();
        return ResponseModel.succ();
    }

    /**
     * 同步机构名称拼音
     */
    @GetMapping(value = "/sync/agency/pinyin")
    public ResponseModel syncAgencyPinyin() {
        commonFacade.syncAgencyPinyin();
        return ResponseModel.succ();
    }

    /**
     * 从redis中删除过期时间超过两天的活动
     */
    @GetMapping(value = "/removeActivityExpireKeyInRedisEndAtOver2Days")
    public ResponseModel removeActivityExpireKeyInRedisEndAtOver2Days() {
        commonFacade.removeActivityExpireKeyInRedisEndAtOver2Days();
        return ResponseModel.succ();
    }
}
