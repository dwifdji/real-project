package com.tzCloud.core.facade.assistant;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.facade.assistant.req.MigrationDataReq;

import java.util.Map;

/**
 * @author xdrodger
 * @Title: AssistantFacade
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-22 09:17
 */
public interface AssistantFacade {
    void cacheData(String key);

    int migrationCaseFromCpwswItem(Integer limit);

    int migrationCaseToElasticSearch(MigrationDataReq.CaseToElasticSearchReq req);

    int migrationTableDataToElasticSearch(String table);

    int migrationLawyerToElasticSearch();

    int migrationLawFirmToElasticSearch();

    int migrationCaseDsrxxToElasticSearch();

    int resetCourtName();
    int migrationCaseExtraDataToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req);

    int repairCourtProvinceCity();

    Map<String, Object> getDynamicNav();

    ResponseModel getQiNiuToken(String fileType);
}
