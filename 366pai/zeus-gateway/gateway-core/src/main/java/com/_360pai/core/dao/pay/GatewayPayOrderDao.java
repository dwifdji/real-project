
package com._360pai.core.dao.pay;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.pay.GatewayPayOrderCondition;
import com._360pai.core.model.pay.GatewayPayOrder;

import java.util.List;

/**
 * <p>GatewayPayOrder的基础操作Dao</p>
 * @ClassName: GatewayPayOrderDao
 * @Description: GatewayPayOrder基础操作的Dao
 * @author Generator
 * @date 2018年09月15日 18时40分38秒
 */
public interface GatewayPayOrderDao extends BaseDao<GatewayPayOrder,GatewayPayOrderCondition>{

    /**
     *
     *获取等待回调的订单数据
     */
    List<GatewayPayOrder> getNoticeList();

}
