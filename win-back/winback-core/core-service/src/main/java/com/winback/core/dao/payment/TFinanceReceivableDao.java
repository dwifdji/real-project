
package com.winback.core.dao.payment;

import com.winback.core.condition.payment.TFinanceReceivableCondition;
import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ReceivableDto;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.finance.InvoiceAuditVo;
import com.winback.core.vo.finance.ReceivableAuditVo;
import com.winback.core.vo.finance.ReceivableVo;

import java.util.List;

/**
 * <p>TFinanceReceivable的基础操作Dao</p>
 * @ClassName: TFinanceReceivableDao
 * @Description: TFinanceReceivable基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TFinanceReceivableDao extends BaseDao<TFinanceReceivable,TFinanceReceivableCondition>{

    List<ReceivableVo> getReceivableList(ReceivableDto dto);


    String getReceivableSum(ReceivableDto dto);


    List<ReceivableAuditVo> getReceivableAuditList(CommonDto dto);

}
