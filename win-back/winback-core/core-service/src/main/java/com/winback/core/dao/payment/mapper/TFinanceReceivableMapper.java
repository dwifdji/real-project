
package com.winback.core.dao.payment.mapper;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ReceivableDto;
import com.winback.core.vo.finance.ReceivableAuditVo;
import com.winback.core.vo.finance.ReceivableVo;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.payment.TFinanceReceivableCondition;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TFinanceReceivable数据层的基础操作</p>
 * @ClassName: TFinanceReceivableMapper
 * @Description: TFinanceReceivable数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
@Mapper
public interface TFinanceReceivableMapper extends BaseMapper<TFinanceReceivable, TFinanceReceivableCondition>{

    List<ReceivableVo> getReceivableList(ReceivableDto dto);

    List<ReceivableAuditVo> getReceivableAuditList(CommonDto dto);

    String getReceivableSum(ReceivableDto dto);
}
