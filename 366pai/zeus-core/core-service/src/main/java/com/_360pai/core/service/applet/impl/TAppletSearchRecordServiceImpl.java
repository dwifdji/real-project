package com._360pai.core.service.applet.impl;

import com._360pai.core.condition.applet.TAppletSearchRecordCondition;
import com._360pai.core.dao.applet.TAppletSearchRecordDao;
import com._360pai.core.facade.shop.dto.DeleteSearchRecordDto;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.vo.SearchRecordListVO;
import com._360pai.core.model.applet.TAppletSearchRecord;
import com._360pai.core.service.applet.TAppletSearchRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TAppletSearchRecordServiceImpl implements TAppletSearchRecordService {

    @Autowired
    private TAppletSearchRecordDao tAppletSearchRecordDao;

    @Override
    public void saveSearchRecord(TAppletSearchRecord tAppletSearchRecord) {

        tAppletSearchRecordDao.insert(tAppletSearchRecord);
    }

    @Override
    public PageInfo getSearchRecordList(SearchRecordListDto params) {
        PageHelper.startPage(1, 5);
        List<SearchRecordListVO> searchRecordListVOS = tAppletSearchRecordDao.getSearchRecordList(params);

        return new PageInfo<>(searchRecordListVOS);
    }

    @Override
    public void deleteSearchRecord(Map<String, Object> params) {
        tAppletSearchRecordDao.deleteSearchRecord(params);
    }

    @Override
    public TAppletSearchRecord selectTAppletSearchRecord(String query, String openId, Integer shopId) {
        TAppletSearchRecordCondition tAppletSearchRecordCondition = new TAppletSearchRecordCondition();
        tAppletSearchRecordCondition.setContext(query);
        tAppletSearchRecordCondition.setOpenId(openId);
        tAppletSearchRecordCondition.setShopId(shopId);
        return tAppletSearchRecordDao.selectFirst(tAppletSearchRecordCondition);
    }

    @Override
    public void updateSearchRecord(TAppletSearchRecord tAppletSearchRecord) {
        tAppletSearchRecordDao.updateById(tAppletSearchRecord);
    }
}
