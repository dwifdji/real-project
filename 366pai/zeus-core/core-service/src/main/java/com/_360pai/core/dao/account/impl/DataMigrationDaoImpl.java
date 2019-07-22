package com._360pai.core.dao.account.impl;

import com._360pai.core.dao.account.DataMigrationDao;
import com._360pai.core.dao.account.mapper.DataMigrationMapper;
import com._360pai.core.model.account.TUser;
import com._360pai.core.model.activity.AuctionActivity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: DataMigrationDaoImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/26 13:48
 */
@Service
public class DataMigrationDaoImpl implements DataMigrationDao {
    @Autowired
    private DataMigrationMapper dataMigrationMapper;

    @Override
    public List<String> getLicensesNeedToMigrate() {
        return dataMigrationMapper.getLicensesNeedToMigrate();
    }

    @Override
    public List<String> getMobilesNeedToMigrate() {
        return dataMigrationMapper.getMobilesNeedToMigrate();
    }

    @Override
    public List<Integer> getCompanyIdByFadadaEmailIsNull() {
        return dataMigrationMapper.getCompanyIdByFadadaEmailIsNull();
    }

    @Override
    public List<Integer> getAgencyIdByFadadaEmailIsNull() {
        return dataMigrationMapper.getAgencyIdByFadadaEmailIsNull();
    }

    @Override
    public PageInfo<AuctionActivity> getNeedToSyncBusTypeNameActivityId(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        List<AuctionActivity> list = dataMigrationMapper.getNeedToSyncBusTypeNameActivityId(params);
        return new PageInfo<>(list);
    }
}
