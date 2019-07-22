
package com.winback.core.dao.contract;

import com.github.pagehelper.PageInfo;
import com.winback.core.commons.constants.ContractEnum;
import com.winback.core.condition.contract.TContractOrderCondition;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TContractOrder;
import com.winback.arch.core.abs.BaseDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>TContractOrder的基础操作Dao</p>
 * @ClassName: TContractOrderDao
 * @Description: TContractOrder基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractOrderDao extends BaseDao<TContractOrder,TContractOrderCondition>{
    TContractOrder createOrder(Integer accountId, ContractEnum.OrderSource orderSource, BigDecimal amount);

    PageInfo<TContractOrder> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TContractOrder> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean hasInvoice(Integer orderId);

    TContractOrder findBy(String orderNo);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);

    List<TContract> getContractList(Integer orderId);

    List<TContractOrder> getInvoiceOrderList(Map<String, Object> params);
}
