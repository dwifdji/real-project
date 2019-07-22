
package com._360pai.core.dao.numberJump.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.numberJump.TBankBenchmarkInterestRateCondition;
import com._360pai.core.model.numberJump.TBankBenchmarkInterestRate;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>TBankBenchmarkInterestRate数据层的基础操作</p>
 * @ClassName: TBankBenchmarkInterestRateMapper
 * @Description: TBankBenchmarkInterestRate数据层的基础操作
 * @author Generator
 * @date 2019年04月28日 14时58分37秒
 */
@Mapper
public interface TBankBenchmarkInterestRateMapper extends BaseMapper<TBankBenchmarkInterestRate, TBankBenchmarkInterestRateCondition>{

    List<TBankBenchmarkInterestRate> getByDateDuration(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    TBankBenchmarkInterestRate getByDate(@Param("date") Date date);
}
