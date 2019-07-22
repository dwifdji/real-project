
package com._360pai.core.dao.fdd;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.fdd.GatewayFddCallBackRecordCondition;
import com._360pai.core.model.fdd.GatewayFddCallBackRecord;

import java.util.List;

/**
 * <p>GatewayFddCallBackRecord的基础操作Dao</p>
 * @ClassName: GatewayFddCallBackRecordDao
 * @Description: GatewayFddCallBackRecord基础操作的Dao
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
public interface GatewayFddCallBackRecordDao extends BaseDao<GatewayFddCallBackRecord,GatewayFddCallBackRecordCondition>{


    List<GatewayFddCallBackRecord>   notifyFailureList();
}
