
package com.winback.core.dao.risk;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.risk.TRiskInvestmentCondition;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.core.model.risk.TRiskInvestment;

import java.util.List;

/**
 * <p>TRiskInvestment的基础操作Dao</p>
 * @ClassName: TRiskInvestmentDao
 * @Description: TRiskInvestment基础操作的Dao
 * @author Generator
 * @date 2019年01月23日 16时29分01秒
 */
public interface TRiskInvestmentDao extends BaseDao<TRiskInvestment,TRiskInvestmentCondition> {


    void batchSaveRiskInvestment(List<TRiskInvestment> batchList);
}
