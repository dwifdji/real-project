
package com._360pai.core.dao.disposal;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.model.disposal.TDisposalBidding;

import java.util.List;

/**
 * <p>TDisposalBidding的基础操作Dao</p>
 * @ClassName: TDisposalBiddingDao
 * @Description: TDisposalBidding基础操作的Dao
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
public interface TDisposalBiddingDao extends BaseDao<TDisposalBidding,TDisposalBiddingCondition>{

    List<TDisposalBidding> findBiddingInfoList(Integer partyId);

    List<TDisposalBidding> findInvestmentInfo();

    int updateManuallyFinished(TDisposalBidding condition);

    List<TDisposalBidding> findBiddingInfoListByCondition(TDisposalBiddingCondition condition);
}
