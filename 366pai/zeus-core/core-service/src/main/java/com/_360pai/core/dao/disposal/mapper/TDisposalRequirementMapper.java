
package com._360pai.core.dao.disposal.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TDisposalRequirement数据层的基础操作</p>
 * @ClassName: TDisposalRequirementMapper
 * @Description: TDisposalRequirement数据层的基础操作
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
@Mapper
public interface TDisposalRequirementMapper extends BaseMapper<TDisposalRequirement, TDisposalRequirementCondition> {
    List<TDisposalRequirement> findBySimilar(TDisposalRequirement requirement);
    List<TDisposalRequirement> findByHotDisposalList(@Param("disposalType") String disposalType);
    List<TDisposalBidding> findBiddingByDisposalId(TDisposalBiddingCondition req);
    List<TDisposalRequirement> findDisposalRequirementListPage(TDisposalRequirementCondition req);
    List<TDisposalRequirement> findDisposalConditionAdmin(TDisposalRequirementCondition condition);
    int updateRequirementStatusByBiddingId(@Param("status") String status, @Param("biddingId") Integer biddingId,
                                @Param("operatorId") Integer operatorId);
    int updateBiddingStatusByBiddingId(@Param("status") String status, @Param("biddingId") Integer biddingId,
                            @Param("operatorId") Integer operatorId);

    TDisposalRequirement selectByIdWithoutPay(String id);
    List<TDisposalBidding> findBiddingSituation(@Param("disposalId") Integer disposalId);
    int updateManuallyFinished(TDisposalRequirement condition);
    TDisposalRequirement findRequirementDetail(Integer disposalId);
    int updateBiddingNumListByIds(Integer[] disposalIds);
    int updateBiddingNumById(Integer disposalId);
    List<TDisposalRequirement> findDisposalFollowListPage(TDisposalRequirementCondition req);
    int confirmBid(TDisposalRequirement condition);

    List<TDisposalRequirement> mySelectList(TDisposalRequirementCondition condition);

    List<TDisposalRequirement> getDisposalActivityByAccountId( @Param("accountId")Integer accountId, @Param("partyId")Integer partyId);
}
