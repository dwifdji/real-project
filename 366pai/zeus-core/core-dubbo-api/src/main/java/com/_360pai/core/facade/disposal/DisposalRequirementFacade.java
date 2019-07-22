package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.disposal.resp.PublishInfoResp;
import com._360pai.core.facade.order.resp.ServiceOrderResp;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-14 11:16
 */
public interface DisposalRequirementFacade {

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
    Object addDisposalRequirement(DisposalRequirementReq.AddRequirementInfo req);

    /**
     * 查看处置报名情况
     * @param req
     * @return
     */
    PageInfoResp findBiddingSituation(DisposalRequirementReq.GetBiddingList req);

    /**
     * 查找热门处置需求标的
     * @param req
     * @return
     */
    PageInfoResp findHotBidList(DisposalBiddingReq.GetBiddingInfoReq req);

    /**
     * 获取首页处置服务信息
     * @param req
     * @return
     */
    PageInfoResp findDisposalRequirementListPage(DisposalRequirementReq.GetPublishInfoReq req);

    /**
     * 查看同类服务处置需求列表
     * 按照浏览量排名
     * @param req
     * @return
     */
    PageInfoResp findSimilarBidListPage(DisposalBiddingReq.GetBiddingInfoReq req);



    /**
     * 更新关注数量
     * @param requirementId
     * @return
     */
    int updateFollowCount(String requirementId);

    /**
     * 获取处置需求详情内容
     * @param disposalId
     * @return
     */
    Map<String, Object> findDisposalRequirementDetail(Integer disposalId,Integer partyId, Integer accountId);

    /**
     * 更新关注人数
     * @param disposalIds
     * @return
     */
    int updateFollowCount(Integer[] disposalIds);

    /**
     * 获取已关注的处置列表
     * @param req
     * @return
     */
    PageInfoResp findDisposalFollowListPage(DisposalRequirementReq.GetPublishInfoReq req);

    /**
     * 处置中心获取处置需求详情内容
     * @param disposalId
     * @return
     */
    Map<String, Object> findDisposalRequirementCenterDetail(Integer disposalId, Integer partyId);

    /**
     * 处置中心获取处置需求详情内容
     * @param disposalId
     * @return
     */
    Map<String, Object> findDisposalRequirementCenterDetailEdit(Integer disposalId, Integer partyId);

    /**
     * 查询处置服务商可提供的服务
     * @param accountId
     * @return
     */
    String[] getDisposeProviderType(Integer accountId);

    int updateViewCount(Integer disposalId);
}
