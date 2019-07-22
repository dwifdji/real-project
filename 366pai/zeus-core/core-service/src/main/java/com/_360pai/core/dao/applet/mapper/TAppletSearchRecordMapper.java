
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.shop.dto.DeleteSearchRecordDto;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.SearchRecordListVO;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletSearchRecordCondition;
import com._360pai.core.model.applet.TAppletSearchRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletSearchRecord数据层的基础操作</p>
 * @ClassName: TAppletSearchRecordMapper
 * @Description: TAppletSearchRecord数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletSearchRecordMapper extends BaseMapper<TAppletSearchRecord, TAppletSearchRecordCondition>{

    List<SearchRecordListVO> getSearchRecordList(SearchRecordListDto params);

    void deleteSearchRecord(Map<String, Object> params);
}
