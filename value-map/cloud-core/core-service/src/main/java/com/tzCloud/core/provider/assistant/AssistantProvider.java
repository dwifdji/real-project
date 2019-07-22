package com.tzCloud.core.provider.assistant;

import com.alibaba.dubbo.config.annotation.Service;
import com.qiniu.util.Auth;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.arch.core.redis.RedisCachemanager;
import com.tzCloud.arch.core.sysconfig.properties.GatewayProperties;
import com.tzCloud.core.constant.SysConstant;
import com.tzCloud.core.facade.assistant.AssistantFacade;
import com.tzCloud.core.facade.assistant.req.MigrationDataReq;
import com.tzCloud.core.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author xdrodger
 * @Title: AssistantProvider
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-22 09:18
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class AssistantProvider implements AssistantFacade {

    @Autowired
    private DataMigrationService dataMigrationService;
    @Autowired
    private CaseDataMigrationService caseMigrationService;
    @Autowired
    private CourtMigrationService courtMigrationService;
    @Autowired
    private CaseService caseService;
    @Autowired
    private AssistantService assistantService;
    @Autowired
    private RedisCachemanager redisCachemanager;
    @Autowired
    private GatewayProperties gatewayProperties;
    private static SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

    @Override
    public void cacheData(String key) {
        dataMigrationService.cacheData(key);
    }

    @Override
    public int migrationCaseFromCpwswItem(Integer limit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                caseMigrationService.migrationCaseFromCpwswItem(limit);
            }
        }).start();
        return 0;
    }

    @Override
    public int migrationCaseToElasticSearch(MigrationDataReq.CaseToElasticSearchReq req) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                caseMigrationService.migrationCaseToElasticSearch(req);
            }
        }).start();
        return 0;
    }

    @Override
    public int migrationTableDataToElasticSearch(String table) {
        return dataMigrationService.migrationTableDataToElasticSearch(table);
    }

    @Override
    public int migrationLawyerToElasticSearch() {
        return dataMigrationService.migrationLawyerToElasticSearch();
    }

    @Override
    public int migrationLawFirmToElasticSearch() {
        return dataMigrationService.migrationLawFirmToElasticSearchPage();
    }

    @Override
    public int migrationCaseDsrxxToElasticSearch() {
        return dataMigrationService.migrationCaseDsrxxToElasticSearch();
    }

    @Override
    public int resetCourtName() {
        return dataMigrationService.resetCourtName();
    }

    @Override
    public int migrationCaseExtraDataToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                caseMigrationService.migrationCaseExtraDataToElasticSearch(req);
            }
        }).start();
        return 0;
    }

    @Override
    public int repairCourtProvinceCity() {
        return courtMigrationService.repairCourtProvinceCity();
    }

    @Override
    public Map<String, Object> getDynamicNav() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalCaseCount", caseService.getTotalCaseCount());
        int todayCaseCount = 0;
        String cache = (String) redisCachemanager.get(SysConstant.TODAY_CASE_COUNT_KEY);
        if (StringUtils.isBlank(cache)) {
            todayCaseCount = RandomUtils.nextInt(8000, 12000);
            Date now = new Date();
            long timeout = (DateUtil.getEndDate(now).getTime() - now.getTime()) / 1000;
            redisCachemanager.set(SysConstant.TODAY_CASE_COUNT_KEY, todayCaseCount + "", timeout > 0 ? timeout : 1);
        } else {
            todayCaseCount = Integer.parseInt(cache);
        }
        data.put("todayCaseCount", todayCaseCount);
        data.put("hotWords", assistantService.getHotWords());
        data.put("guidedCases", assistantService.getGuidedCases());
        return data;
    }

    @Override
    public ResponseModel getQiNiuToken(String fileType) {
        Auth auth = Auth.create(gatewayProperties.getAccessKey(), gatewayProperties.getSecretKey());
        long expireSeconds = 3600;
        String key = getKey(fileType);
        String upToken = auth.uploadToken(gatewayProperties.getBucket(), key, expireSeconds,null);
        Map<String, Object> data = new HashMap<>();
        data.put("upToken", upToken);
        data.put("expires", expireSeconds);
        data.put("domain", gatewayProperties.getDomain());
        if (org.apache.commons.lang.StringUtils.isNotBlank(key)) {
            data.put("fileUrl", "https://" + gatewayProperties.getDomain() + "/" + key);
            data.put("key", key);
        }
        return ResponseModel.succ(data);
    }

    private String getKey(String fileType) {
        if (org.apache.commons.lang.StringUtils.isBlank(fileType)) {
            return null;
        }
        return getTimeStamp() + UUID.randomUUID().toString().toUpperCase().replaceAll("-", "") + fileType;
    }

    private String getTimeStamp() {
        return df.format(new Date());
    }
}
