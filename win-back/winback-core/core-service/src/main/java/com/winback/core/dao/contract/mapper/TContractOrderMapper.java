
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContract;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractOrderCondition;
import com.winback.core.model.contract.TContractOrder;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TContractOrder数据层的基础操作</p>
 * @ClassName: TContractOrderMapper
 * @Description: TContractOrder数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
@Mapper
public interface TContractOrderMapper extends BaseMapper<TContractOrder, TContractOrderCondition>{

    List<TContractOrder> getFrontList(Map<String, Object> params);

    List<TContractOrder> getBackgroundList(Map<String, Object> params);

    boolean hasInvoice(@Param("orderId") Integer orderId);

    Map<String, Object> getSummaryInfo(Map<String, Object> params);

    List<TContract> getContractList(Integer orderId);

    List<TContractOrder> getInvoiceOrderList(Map<String, Object> params);
}
