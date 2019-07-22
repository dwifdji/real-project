
package com.winback.core.dao.payment.mapper;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ExpendDto;
import com.winback.core.vo.finance.ExpendAuditVo;
import com.winback.core.vo.finance.ExpendVo;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.payment.TFinanceExpendCondition;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TFinanceExpend数据层的基础操作</p>
 * @ClassName: TFinanceExpendMapper
 * @Description: TFinanceExpend数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
@Mapper
public interface TFinanceExpendMapper extends BaseMapper<TFinanceExpend, TFinanceExpendCondition>{

    List<ExpendVo> getExpendList(ExpendDto req);


    List<ExpendAuditVo> getExpendAuditList(CommonDto dto);


    String getExpendSum(ExpendDto req);

    List<WorkBenchVO> getFinanceWorkBench();
}
