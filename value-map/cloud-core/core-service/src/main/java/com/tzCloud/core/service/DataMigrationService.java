package com.tzCloud.core.service;

import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;

import java.util.List;

/**
 * @author xdrodger
 * @Title: DataMigrationService
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-22 09:21
 */
public interface DataMigrationService {
    void cacheData(String key);

    int migrationTableDataToElasticSearch(String table);

    int migrationLawyerToElasticSearch();

    /**
     * 删除了在律师中关联的docId
     * @return
     */
    @Deprecated
    int migrationLawyerToElasticSearchWithoutDocId();

    int migrationLawFirmToElasticSearch();
    int migrationLawFirmToElasticSearchPage();

    /**
     * 删除了在律所中关联的docId
     * @return
     */
    @Deprecated
    int migrationLawFirmToElasticSearchWithoutDocId();

    int migrationCaseDsrxxToElasticSearch();

    int resetCourtName();

    List<TLawyerFirmInfo> getLawyerFirmInfosByCache(String shortName);
}
