package com._360pai.core.dao.account.mapper;

import com._360pai.core.model.activity.AuctionActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: DataMigrationMapper
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/26 13:47
 */
@Mapper
public interface DataMigrationMapper {

    List<String> getLicensesNeedToMigrate();

    List<String> getMobilesNeedToMigrate();

    List<Integer> getCompanyIdByFadadaEmailIsNull();

    List<Integer> getAgencyIdByFadadaEmailIsNull();

    List<AuctionActivity> getNeedToSyncBusTypeNameActivityId(Map<String, Object> params);
}
