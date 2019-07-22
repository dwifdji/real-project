
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.EnrollingActivityImportDataCondition;
import com._360pai.core.facade.enrolling.req.*;
import com._360pai.core.facade.enrolling.resp.EnrollingActivityImportDataResp;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>EnrollingActivityImportData数据层的基础操作</p>
 * @ClassName: EnrollingActivityImportDataMapper
 * @Description: EnrollingActivityImportData数据层的基础操作
 * @author Generator
 * @date 2019年02月14日 13时27分22秒
 */
@Mapper
public interface EnrollingActivityImportDataMapper extends BaseMapper<EnrollingActivityImportData, EnrollingActivityImportDataCondition> {

    void batchSaveImportData(List<EnrollingActivityImportDataVo> list);


    void batchSaveEnrollingActivity(List<EnrollingActivity> list);


    List<EnrollingActivityImportUserVo> getApplyUserList(EnrollingActivityImportUserReq req);


    List<EnrollingActivityImportDataListVo> getImportDateList(EnrollingActivityImportDataListReq req);

    void batchAuditActivity(@Param("activityIdList")List<String> activityIdList,@Param("status")String status,@Param("beginAt")String beginAt,@Param("endAt")String endAt,@Param("refuseReason")String refuseReason,@Param("operatorId")Integer operatorId,@Param("operatorAt")String operatorAt);



    EnrollingActivityImportDataResp getImportDataDetail(@Param("activityId")String activityId);


}
