
package com.winback.core.dao.payment;

import com.winback.core.condition.payment.TFinanceExpendCondition;
import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ExpendDto;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.finance.ExpendAuditVo;
import com.winback.core.vo.finance.ExpendVo;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;

/**
 * <p>TFinanceExpend的基础操作Dao</p>
 * @ClassName: TFinanceExpendDao
 * @Description: TFinanceExpend基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public interface TFinanceExpendDao extends BaseDao<TFinanceExpend,TFinanceExpendCondition>{

    List<ExpendVo> getExpendList(ExpendDto req);


    String getExpendSum(ExpendDto req);


    List<ExpendAuditVo> getExpendAuditList(CommonDto dto);

    List<WorkBenchVO> getFinanceWorkBench();
}
