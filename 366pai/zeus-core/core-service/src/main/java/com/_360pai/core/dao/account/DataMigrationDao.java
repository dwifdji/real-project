package com._360pai.core.dao.account;

import com._360pai.core.model.activity.AuctionActivity;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: DataMigrationDao
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/26 13:48
 */
public interface DataMigrationDao {

    List<String> getLicensesNeedToMigrate();

    List<String> getMobilesNeedToMigrate();

    List<Integer> getCompanyIdByFadadaEmailIsNull();

    List<Integer> getAgencyIdByFadadaEmailIsNull();

    PageInfo<AuctionActivity> getNeedToSyncBusTypeNameActivityId(int page, int perPage, Map<String, Object> params, String orderBy);
}
