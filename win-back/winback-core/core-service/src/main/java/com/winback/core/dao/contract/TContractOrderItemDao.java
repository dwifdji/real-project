
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractOrderItemCondition;
import com.winback.core.model.contract.TContractOrderItem;
import com.winback.arch.core.abs.BaseDao;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>TContractOrderItem的基础操作Dao</p>
 * @ClassName: TContractOrderItemDao
 * @Description: TContractOrderItem基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractOrderItemDao extends BaseDao<TContractOrderItem,TContractOrderItemCondition>{
    TContractOrderItem createOrderItem(Integer orderId, Integer contractId, BigDecimal amount);

    List<TContractOrderItem> findBy(Integer orderId);
}
