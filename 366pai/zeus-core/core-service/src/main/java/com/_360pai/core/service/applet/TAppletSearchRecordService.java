package com._360pai.core.service.applet;

import com._360pai.core.facade.shop.dto.DeleteSearchRecordDto;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.SearchRecordListVO;
import com._360pai.core.facade.shop.vo.ShopMessageTypeVO;
import com._360pai.core.model.applet.TAppletSearchRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TAppletSearchRecordService {

    void saveSearchRecord(TAppletSearchRecord tAppletSearchRecord);

    PageInfo getSearchRecordList(SearchRecordListDto params);

    void deleteSearchRecord(Map<String, Object> params);

    TAppletSearchRecord selectTAppletSearchRecord(String query, String openId, Integer shopId);

    void updateSearchRecord(TAppletSearchRecord tAppletSearchRecord);
}
