
package com.winback.core.dao.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.payment.TInvoiceCondition;
import com.winback.core.model.payment.TInvoice;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TInvoice数据层的基础操作</p>
 * @ClassName: TInvoiceMapper
 * @Description: TInvoice数据层的基础操作
 * @author Generator
 * @date 2019年01月17日 13时31分40秒
 */
@Mapper
public interface TInvoiceMapper extends BaseMapper<TInvoice, TInvoiceCondition>{

}
