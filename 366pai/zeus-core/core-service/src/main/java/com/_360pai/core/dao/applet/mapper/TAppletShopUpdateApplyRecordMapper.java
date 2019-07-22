
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.model.account.TUser;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletShopUpdateApplyRecordCondition;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletShopUpdateApplyRecord数据层的基础操作</p>
 * @ClassName: TAppletShopUpdateApplyRecordMapper
 * @Description: TAppletShopUpdateApplyRecord数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 16时52分10秒
 */
@Mapper
public interface TAppletShopUpdateApplyRecordMapper extends BaseMapper<TAppletShopUpdateApplyRecord, TAppletShopUpdateApplyRecordCondition>{
    List<TAppletShopUpdateApplyRecord> getList(Map<String, Object> params);
}
