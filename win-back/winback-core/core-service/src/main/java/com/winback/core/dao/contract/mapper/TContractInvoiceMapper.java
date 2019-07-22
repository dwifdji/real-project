
package com.winback.core.dao.contract.mapper;

import com.winback.core.model.contract.TContract;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.contract.TContractInvoiceCondition;
import com.winback.core.model.contract.TContractInvoice;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TContractInvoice数据层的基础操作</p>
 * @ClassName: TContractInvoiceMapper
 * @Description: TContractInvoice数据层的基础操作
 * @author Generator
 * @date 2019年02月13日 19时26分30秒
 */
@Mapper
public interface TContractInvoiceMapper extends BaseMapper<TContractInvoice, TContractInvoiceCondition>{
    List<TContractInvoice> getFrontList(Map<String, Object> params);

    List<TContractInvoice> getBackgroundList(Map<String, Object> params);

    List<String> getOrderNoList(Integer invoiceId);
}
