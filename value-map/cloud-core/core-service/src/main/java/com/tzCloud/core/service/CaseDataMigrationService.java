package com.tzCloud.core.service;

import com.tzCloud.core.facade.assistant.req.MigrationDataReq;

/**
 * @author xdrodger
 * @Title: CaseDataMigrationService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/16 18:17
 */
public interface CaseDataMigrationService {
    int migrationCaseFromCpwswItem(Integer limit);

    int migrationCaseToElasticSearch(MigrationDataReq.CaseToElasticSearchReq req);

    int migrationCaseExtraDataToElasticSearch(MigrationDataReq.CaseExtraDataToElasticSearchReq req);
}
