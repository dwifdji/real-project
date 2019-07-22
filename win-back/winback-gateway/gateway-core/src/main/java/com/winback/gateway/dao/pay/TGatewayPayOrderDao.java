
package com.winback.gateway.dao.pay;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.arch.core.abs.BaseDao;
import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;

import java.util.List;

/**
 * <p>TGatewayPayOrder的基础操作Dao</p>
 * @ClassName: TGatewayPayOrderDao
 * @Description: TGatewayPayOrder基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
public interface TGatewayPayOrderDao extends BaseDao<TGatewayPayOrder,TGatewayPayOrderCondition>{


    List<TGatewayPayOrder> getNotPayOrder();
}
