package com._360pai.core.service.assistant;

import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: DataMigrationService
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/26 13:43
 */
public interface DataMigrationService {

    void migrateAgencyData();

    boolean doMigrateAgency(String license);

    void migrateAccountData();

    boolean doMigrateAccount(String mobile);

    void syncCompanyFadadaEmial();

    void syncAgencyFadadaEmial();

    List<String> getAppletAccountListNeedRepair(Map<String, Object> params);

    void syncProvinceId();

    void syncAuctionActivityBusTypeName();

    void syncAssetData();

    void syncEnrollingActivityData();

    void syncOldSubscribeMp360PaiUserToDb();


    void syncProvincePinyin();

    void syncAgencyPinyin();
}
