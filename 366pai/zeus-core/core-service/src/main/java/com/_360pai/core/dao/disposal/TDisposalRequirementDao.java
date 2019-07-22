
package com._360pai.core.dao.disposal;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>TDisposalRequirement的基础操作Dao</p>
 * @ClassName: TDisposalRequirementDao
 * @Description: TDisposalRequirement基础操作的Dao
 * @author Generator
 * @date 2018年09月14日 10时33分09秒
 */
public interface TDisposalRequirementDao extends BaseDao<TDisposalRequirement,TDisposalRequirementCondition>{

    List<TDisposalRequirement> findBySimilar(TDisposalRequirement requirement);

    List<TDisposalRequirement> findByHotDisposalList(String disposalType);

    List<TDisposalBidding> findBiddingByDisposalId(TDisposalBiddingCondition req);

    PageInfo findDisposalRequirementListPage(int page, int perPage, TDisposalRequirementCondition req);

    List<TDisposalRequirement> findDisposalConditionAdmin(TDisposalRequirementCondition condition);

    int updateRequirementStatusByBiddingId(String status ,Integer biddingId, Integer operatorId);

    int updateBiddingStatusByBiddingId(String status ,Integer biddingId, Integer operatorId);

    TDisposalRequirement selectByIdWithoutPay(String id);

    List<TDisposalBidding> findBiddingSituation(Integer disposalId);

    int updateManuallyFinished(TDisposalRequirement condition);

    TDisposalRequirement findRequirementDetail(Integer disposalId);

    int updateBiddingNumListByIds(Integer[] disposalIds);

    int updateBiddingNumById(Integer disposalId);

    List<TDisposalRequirement> findDisposalFollowListPage(TDisposalRequirementCondition req);

    int confirmBid(TDisposalRequirement condition);

    List<TDisposalRequirement> mySelectList(TDisposalRequirementCondition condition);

    PageInfo getDisposalActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest);

    PageInfo getListByPage(int page, int perPage);
}
