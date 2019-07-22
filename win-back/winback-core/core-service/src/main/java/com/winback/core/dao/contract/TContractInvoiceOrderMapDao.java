
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractInvoiceOrderMapCondition;
import com.winback.core.model.contract.TContractInvoiceOrderMap;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model.contract.TContractOrder;

import java.util.List;

/**
 * <p>TContractInvoiceOrderMap的基础操作Dao</p>
 * @ClassName: TContractInvoiceOrderMapDao
 * @Description: TContractInvoiceOrderMap基础操作的Dao
 * @author Generator
 * @date 2019年02月18日 15时17分17秒
 */
public interface TContractInvoiceOrderMapDao extends BaseDao<TContractInvoiceOrderMap,TContractInvoiceOrderMapCondition>{
    void createMap(Integer invoiceId, List<Integer> orderIds);

    List<TContractOrder> getOrderList(Integer invoiceId);
}
