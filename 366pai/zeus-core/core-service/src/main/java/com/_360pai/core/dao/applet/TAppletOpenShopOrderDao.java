
package com._360pai.core.dao.applet;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.applet.TAppletOpenShopOrderCondition;
import com._360pai.core.model.applet.TAppletOpenShopOrder;

import java.util.List;

/**
 * <p>TAppletOpenShopOrder的基础操作Dao</p>
 * @ClassName: TAppletOpenShopOrderDao
 * @Description: TAppletOpenShopOrder基础操作的Dao
 * @author Generator
 * @date 2018年11月28日 13时30分57秒
 */
public interface TAppletOpenShopOrderDao extends BaseDao<TAppletOpenShopOrder,TAppletOpenShopOrderCondition> {


    List<TAppletOpenShopOrder> getAppletNotPayOrderList();

}
