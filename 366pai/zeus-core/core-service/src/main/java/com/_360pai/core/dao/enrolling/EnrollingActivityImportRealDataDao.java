
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.EnrollingActivityImportRealDataCondition;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListReq;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportDataListVo;
import com._360pai.core.facade.enrolling.req.EnrollingActivityImportRealDataVo;
import com._360pai.core.model.enrolling.EnrollingActivityImportRealData;

import java.util.Date;
import java.util.List;

/**
 * <p>EnrollingActivityImportRealData的基础操作Dao</p>
 * @ClassName: EnrollingActivityImportRealDataDao
 * @Description: EnrollingActivityImportRealData基础操作的Dao
 * @author Generator
 * @date 2019年05月20日 15时39分44秒
 */
public interface EnrollingActivityImportRealDataDao extends BaseDao<EnrollingActivityImportRealData,EnrollingActivityImportRealDataCondition> {


    void batchSaveRealImportData(List<EnrollingActivityImportRealDataVo> list);


    List<EnrollingActivityImportDataListVo>  getRealImportDateList(EnrollingActivityImportDataListReq req);



    void batchUpdateRealImportData(List<String> list,String beginAt, String endAt,String refuseReason);

}
