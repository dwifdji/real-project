
package com.winback.core.dao.contract;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.contract.TContractInvoiceCondition;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TContractInvoice;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TContractInvoice的基础操作Dao</p>
 * @ClassName: TContractInvoiceDao
 * @Description: TContractInvoice基础操作的Dao
 * @author Generator
 * @date 2019年02月13日 19时26分30秒
 */
public interface TContractInvoiceDao extends BaseDao<TContractInvoice,TContractInvoiceCondition>{
    PageInfo<TContractInvoice> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TContractInvoice> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<String> getOrderNoList(Integer invoiceId);
}
