
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletSearchRecordCondition;
import com._360pai.core.facade.shop.dto.DeleteSearchRecordDto;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.SearchRecordListVO;
import com._360pai.core.model.applet.TAppletSearchRecord;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletSearchRecord的基础操作Dao</p>
 * @ClassName: TAppletSearchRecordDao
 * @Description: TAppletSearchRecord基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletSearchRecordDao extends BaseDao<TAppletSearchRecord,TAppletSearchRecordCondition>{

    List<SearchRecordListVO> getSearchRecordList(SearchRecordListDto params);

    void deleteSearchRecord(Map<String, Object> params);
}
