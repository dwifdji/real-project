
package com.winback.core.dao.risk.mapper;

import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.risk.TRiskInvestmentCondition;
import com.winback.core.model.risk.TRiskInvestment;

import java.util.List;

/**
 * <p>TRiskInvestment数据层的基础操作</p>
 * @ClassName: TRiskInvestmentMapper
 * @Description: TRiskInvestment数据层的基础操作
 * @author Generator
 * @date 2019年01月23日 16时29分01秒
 */
@Mapper
public interface TRiskInvestmentMapper extends BaseMapper<TRiskInvestment, TRiskInvestmentCondition> {

    void  batchSaveRiskInvestment(List<TRiskInvestment> batchList);
}
