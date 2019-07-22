
package com.winback.core.dao.payment.mapper;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.InvoiceDto;
import com.winback.core.vo.finance.InvoiceAuditVo;
import com.winback.core.vo.finance.InvoiceVo;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.payment.TFinanceInvoiceCondition;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TFinanceInvoice数据层的基础操作</p>
 * @ClassName: TFinanceInvoiceMapper
 * @Description: TFinanceInvoice数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
@Mapper
public interface TFinanceInvoiceMapper extends BaseMapper<TFinanceInvoice, TFinanceInvoiceCondition>{

    List<InvoiceVo> getInvoiceList(InvoiceDto req);

     List<InvoiceAuditVo> getInvoiceAuditList(CommonDto dto);


    String getInvoiceSum(InvoiceDto req);

}
