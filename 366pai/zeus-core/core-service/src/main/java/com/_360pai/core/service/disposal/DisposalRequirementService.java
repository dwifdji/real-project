package com._360pai.core.service.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 *
 * @author xiaolei
 * @create 2018-09-14 11:09
 */
public interface DisposalRequirementService {

    /**
     * 用户查看发布的处置列表
     * @param req
     * @return
     */
    PageInfoResp findPublishedListPage(DisposalRequirementReq.GetPublishInfoReq req);

    /**
     * 用户发布处置需求
     * @param req
     */
    List<Integer> addDisposalRequirement(DisposalRequirementReq.AddRequirementInfo req);

    /**
     * 根据投标单id更新处置单状态
     * @param status
     * @param biddingId
     * @return
     */
    int updateRequirementStatusByBiddingId(String status, Integer biddingId, Integer operatorId);

    /**
     * 根据成功投标单更新其他单信息
     * @param status
     * @param biddingId
     * @return
     */
    int updateBiddingStatusByBiddingId(String status, Integer biddingId, Integer operatorId);

    /**
     * 审核状态修改
     * @param verifyContent
     * @param disposalId
     * @return
     */
    int updateRequirementVerifyStatusByDisposalId(String status, String verifyContent, Integer disposalId);

    /**
     * 获取
     * @param disposalId
     * @return
     */
    List<TDisposalBidding> findBiddingSituation(Integer disposalId);


    /**
     * 获取首页处置服务信息
     * @param req
     * @return
     */
    PageInfoResp findDisposalRequirementListPage(TDisposalRequirementCondition req, int pageNum, int pageSize);
    /**
     * 查看同类服务处置需求列表
     * 按照浏览量排名
     * @param req
     * @return
     */
    PageInfoResp findSimilarBidListPage(DisposalBiddingReq.GetBiddingInfoReq req);


    /**
     * 查找热门处置需求标的
     * @param req
     * @return
     */
    PageInfoResp findHotBidList(DisposalBiddingReq.GetBiddingInfoReq req);


    int updateFollowCount(String requirementId);

    TDisposalRequirement findRequirementDetail(Integer disposalId);

    int updateFollowCountList(Integer[] disposalIds);

    int updateBiddingNumById(Integer disposalId);

    PageInfoResp findDisposalFollowListPage(TDisposalRequirementCondition req, int pageNum, int pageSize);

    /**
     * 定时更新达到截止日期的处置需求状态
     */
    void disposalDeadlineUpdateSchedule();

    void updateDeadlineStatus(TDisposalRequirement tmp);

    int disposalRequirementEdit(TDisposalRequirement tmp);

    void updateRequirementByDeadline(Integer disposalId);

    void sendMessageByDeadline(Integer disposalId);

    PageInfo getDisposalActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest);
}
