
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.EnrollingActivityImportDataCondition;
import com._360pai.core.facade.enrolling.req.*;
import com._360pai.core.facade.enrolling.resp.EnrollingActivityImportDataResp;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityImportData;

import java.util.List;

/**
 * <p>EnrollingActivityImportData的基础操作Dao</p>
 * @ClassName: EnrollingActivityImportDataDao
 * @Description: EnrollingActivityImportData基础操作的Dao
 * @author Generator
 * @date 2019年02月14日 13时27分22秒
 */
public interface EnrollingActivityImportDataDao extends BaseDao<EnrollingActivityImportData,EnrollingActivityImportDataCondition> {

    void batchSaveImportData(List<EnrollingActivityImportDataVo> list);


    void batchSaveEnrollingActivity(List<EnrollingActivity> list);


    List<EnrollingActivityImportUserVo>  getApplyUserList(EnrollingActivityImportUserReq req);



    List<EnrollingActivityImportDataListVo>  getImportDateList(EnrollingActivityImportDataListReq req);


    void batchAuditActivity(EnrollingImportReq.auditActivityReq req);



    EnrollingActivityImportDataResp getImportDataDetail(String activityId);

}
