package com.tzCloud.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.assistant.AssistantFacade;
import com.tzCloud.core.facade.assistant.req.MigrationDataReq;
import com.tzCloud.core.facade.legalEngine.CourtFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: AssistantController
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-22 14:57
 */
@Slf4j
@RestController
@RequestMapping(value = "/open/assistant", produces = "application/json;charset=UTF-8")
public class AssistantController {
    @Reference(version = "1.0.0")
    private AssistantFacade assistantFacade;
    @Reference(version = "1.0.0")
    private CourtFacade courtFacade;

    /**
     * 缓存数据接口
     */
    @GetMapping(value = "/cacheData")
    public ResponseModel cacheData(@RequestParam(value = "key", required = false, defaultValue = "") String key) {
        assistantFacade.cacheData(key);
        return ResponseModel.succ();
    }

    /**
     * 迁移案件到t_case表接口
     */
    @GetMapping(value = "/migrationCaseFromCpwswItem")
    public ResponseModel migrationFromCpwswItem(@RequestParam(value = "limit", required = false, defaultValue = "10000") Integer limit) {
        assistantFacade.migrationCaseFromCpwswItem(limit);
        return ResponseModel.succ();
    }

    /**
     * 迁移案件到ElasticSearch接口
     */
    @GetMapping(value = "/migrationCaseToElasticSearch")
    public ResponseModel migrationCaseToElasticSearch(MigrationDataReq.CaseToElasticSearchReq req) {
        assistantFacade.migrationCaseToElasticSearch(req);
        return ResponseModel.succ();
    }

    /**
     * 迁移案件额外数据到ElasticSearch接口
     */
    @GetMapping(value = "/migrationCaseExtraDataToElasticSearch")
    public ResponseModel migrationCaseExtraDataToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req) {
        assistantFacade.migrationCaseExtraDataToElasticSearch(req);
        return ResponseModel.succ();
    }

    /**
     * 缓存表数据到ElasticSearch接口
     */
    @GetMapping(value = "/migrationTableDataToElasticSearch")
    public ResponseModel migrationTableDataToElasticSearch(@RequestParam(value = "table", required = false, defaultValue = "") String table) {
        assistantFacade.migrationTableDataToElasticSearch(table);
        return ResponseModel.succ();
    }


    /**
     * 迁移律师到ElasticSearch接口
     */
    @GetMapping(value = "/migrationLawyerToElasticSearch")
    public ResponseModel migrationLawyerToElasticSearch() {
        assistantFacade.migrationLawyerToElasticSearch();
        return ResponseModel.succ();
    }

    /**
     * 迁移律所到ElasticSearch接口
     */
    @GetMapping(value = "/migrationLawFirmToElasticSearch")
    public ResponseModel migrationLawFirmToElasticSearch() {
        assistantFacade.migrationLawFirmToElasticSearch();
        return ResponseModel.succ();
    }

    /**
     * 迁移案例当事人到ElasticSearch接口
     */
    @GetMapping(value = "/migrationCaseDsrxxToElasticSearch")
    public ResponseModel migrationCaseDsrxxToElasticSearch() {
        assistantFacade.migrationCaseDsrxxToElasticSearch();
        return ResponseModel.succ();
    }

    /**
     * 重置法院名称接口
     */
    @GetMapping(value = "/resetCourtName")
    public ResponseModel resetCourtName() {
        assistantFacade.resetCourtName();
        return ResponseModel.succ();
    }

    /**
     * 修复法院省市接口
     */
    @GetMapping(value = "/repairCourtProvinceCity")
    public ResponseModel repairCourtProvinceCity() {
        assistantFacade.repairCourtProvinceCity();
        return ResponseModel.succ();
    }


    /**
     * 迁移案件到ElasticSearch接口
     */
    @GetMapping(value = "/open/court/migrationCourtToElasticSearch")
    public ResponseModel migrationCourtToElasticSearch() {
        courtFacade.migrationCourtToElasticSearch();
        return ResponseModel.succ();
    }

    /**
     * 功能描述: 首页属性加载
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/29 16:27
     */
    @GetMapping(value = "/nav")
    public ResponseModel nav() {
        Map<String, Object> data = new HashMap<>();

        data.putAll(assistantFacade.getDynamicNav());
        return ResponseModel.succ(data);
    }

    /**
     * 获取七牛token信息
     */
    @GetMapping(value = "/getQiniuToken")
    public ResponseModel getQiniuToken(@RequestParam(value = "fileType", required = false, defaultValue = "") String fileType) {
        return assistantFacade.getQiNiuToken(fileType);
    }
}
