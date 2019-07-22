
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetRejectRecordCondition;
import com._360pai.core.model.asset.AssetRejectRecord;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetRejectRecord的基础操作Dao</p>
 * @ClassName: AssetRejectRecordDao
 * @Description: AssetRejectRecord基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetRejectRecordDao extends BaseDao<AssetRejectRecord,AssetRejectRecordCondition>{

    int save(Integer assetId, String reason);
}
