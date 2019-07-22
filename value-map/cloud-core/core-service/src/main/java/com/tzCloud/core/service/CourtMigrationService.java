package com.tzCloud.core.service;

public interface CourtMigrationService {

    int migrationCourtToElasticSearch();

    Object getAggs();

    int repairCourtProvinceCity();
}
