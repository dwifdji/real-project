
package com.winback.core.dao.payment;

import com.winback.core.condition.payment.TFinanceInvoiceCondition;
import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.InvoiceDto;
import com.winback.core.facade.finance.req.FinanceReq;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.finance.ExpendAuditVo;
import com.winback.core.vo.finance.InvoiceAuditVo;
import com.winback.core.vo.finance.InvoiceVo;

import java.util.List;

/**
 * <p>TFinanceInvoice的基础操作Dao</p>
 * @ClassName: TFinanceInvoiceDao
 * @Description: TFinanceInvoice基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TFinanceInvoiceDao extends BaseDao<TFinanceInvoice,TFinanceInvoiceCondition>{


    List<InvoiceVo>  getInvoiceList(InvoiceDto req);


    String  getInvoiceSum(InvoiceDto req);


    List<InvoiceAuditVo> getInvoiceAuditList(CommonDto dto);

}
