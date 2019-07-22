
package com._360pai.core.dao.numberJump;

import com._360pai.core.condition.numberJump.TBankBenchmarkInterestRateCondition;
import com._360pai.core.model.numberJump.TBankBenchmarkInterestRate;
import com._360pai.arch.core.abs.BaseDao;

import java.util.Date;
import java.util.List;

/**
 * <p>TBankBenchmarkInterestRate的基础操作Dao</p>
 * @ClassName: TBankBenchmarkInterestRateDao
 * @Description: TBankBenchmarkInterestRate基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
public interface TBankBenchmarkInterestRateDao extends BaseDao<TBankBenchmarkInterestRate,TBankBenchmarkInterestRateCondition>{
    List<TBankBenchmarkInterestRate> findAll();
    List<TBankBenchmarkInterestRate> getByDateDuration(Date startDate, Date endDate);
    TBankBenchmarkInterestRate getByDate(Date date);
}
