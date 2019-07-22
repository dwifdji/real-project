
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.TAssetAuthorizationCondition;
import com._360pai.core.model.asset.TAssetAuthorization;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAssetAuthorization的基础操作Dao</p>
 * @ClassName: TAssetAuthorizationDao
 * @Description: TAssetAuthorization基础操作的Dao
 * @author Generator
 * @date 2018年11月05日 09时38分23秒
 */
public interface TAssetAuthorizationDao extends BaseDao<TAssetAuthorization,TAssetAuthorizationCondition>{

    TAssetAuthorization getJdReportSaleByActivityId(Integer activityId);

    TAssetAuthorization getJdReportCommissionByActivityId(Integer activityId);

    TAssetAuthorization getThirdProtocolByActivityId(Integer activityId);
}
