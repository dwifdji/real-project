
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContractOrder;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractInvoiceOrderMapCondition;
import com.winback.core.model.contract.TContractInvoiceOrderMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TContractInvoiceOrderMap数据层的基础操作</p>
 * @ClassName: TContractInvoiceOrderMapMapper
 * @Description: TContractInvoiceOrderMap数据层的基础操作
 * @author Generator
 * @date 2019年02月18日 15时17分17秒
 */
@Mapper
public interface TContractInvoiceOrderMapMapper extends BaseMapper<TContractInvoiceOrderMap, TContractInvoiceOrderMapCondition>{
    List<TContractOrder> getOrderList(@Param("invoiceId") Integer invoiceId);
}
