
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListReq;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.enrolling.EnrollingActivityImportRealDataCondition;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>EnrollingActivityImportRealData数据层的基础操作</p>
 * @ClassName: EnrollingActivityImportRealDataMapper
 * @Description: EnrollingActivityImportRealData数据层的基础操作
 * @author Generator
 * @date 2019年05月20日 15时39分44秒
 */
@Mapper
public interface EnrollingActivityImportRealDataMapper extends BaseMapper<EnrollingActivityImportRealData, EnrollingActivityImportRealDataCondition> {

    void batchSaveRealImportData(List<EnrollingActivityImportRealDataVo> list);


    List<EnrollingActivityImportDataListVo> getRealImportDateList(EnrollingActivityImportDataListReq req);


    void batchUpdateRealImportData(@Param("activityIdList")List<String> activityIdList,  @Param("beginAt")String beginAt, @Param("endAt")String endAt,@Param("refuseReason")String  refuseReason);

}
