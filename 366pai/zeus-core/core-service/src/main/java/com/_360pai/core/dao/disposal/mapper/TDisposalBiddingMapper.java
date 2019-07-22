
package com._360pai.core.dao.disposal.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TDisposalBidding数据层的基础操作</p>
 * @ClassName: TDisposalBiddingMapper
 * @Description: TDisposalBidding数据层的基础操作
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
@Mapper
public interface TDisposalBiddingMapper extends BaseMapper<TDisposalBidding, TDisposalBiddingCondition>{

    List<TDisposalBidding> findBiddingInfoList(Integer partyId);

    List<TDisposalBidding> findInvestmentInfo();

    int updateManuallyFinished(TDisposalBidding condition);

    List<TDisposalBidding> findBiddingInfoListByCondition(TDisposalBiddingCondition condition);
}
