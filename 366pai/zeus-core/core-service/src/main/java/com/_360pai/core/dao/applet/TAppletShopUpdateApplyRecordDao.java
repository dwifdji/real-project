
package com._360pai.core.dao.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.applet.TAppletShopUpdateApplyRecordCondition;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TAppletShopUpdateApplyRecord的基础操作Dao</p>
 * @ClassName: TAppletShopUpdateApplyRecordDao
 * @Description: TAppletShopUpdateApplyRecord基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 16时52分10秒
 */
public interface TAppletShopUpdateApplyRecordDao extends BaseDao<TAppletShopUpdateApplyRecord,TAppletShopUpdateApplyRecordCondition>{
    boolean hasPendingApply(Integer shopId);

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
}
