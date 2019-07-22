package com._360pai.core.service.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.disposal.TDisposalBiddingCondition;
import com._360pai.core.condition.disposal.TDisposalRequirementCondition;
import com._360pai.core.model.disposal.TDisposalBidding;
import com._360pai.core.model.disposal.TDisposalRequirement;

/**
 * @author xiaolei
 * @create 2018-09-15 18:43
 */
public interface DisposalAdminService {

    /**
     * 获取处置需求列表
     * @param condition
     * @return
     */
    PageInfoResp findDisposalByAdmin(TDisposalRequirementCondition condition, int pageNum, int pageSize);

    /**
     * 查看处置需求投标信息
     * @param disposalId
     * @return
     */
    TDisposalRequirement findDisposalById(Integer disposalId);

    /**
     * 用户获取投标情况
     * @param req
     * @return
     */
    PageUtils.Page findBiddingByDisposalId(TDisposalBiddingCondition req, int pageNum, int pageSize);

    /**
     * 撮合成功
     * @param condition
     * @return
     */
    int dealSuccess(TDisposalBidding condition);

    /**
     * 修改处置单状态
     * @param verify
     * @param disposalId
     * @return
     */
    int verifyRequirementStatus(String verify, Integer disposalId, Integer operatorVerifyId, String disposalStatus);

    /**
     * 添加招标须知
     * @param biddingNotice
     * @return
     */
    int addBiddingNotice(String biddingNotice, Integer operatorNoticeId, Integer disposalId);

    /**
     * 手动完成
     * @param operatorId
     * @param disposalId
     * @return
     */
    boolean manuallyCompleted(Integer operatorId, Integer disposalId);

    PageInfoResp findBiddingInfoList(TDisposalBiddingCondition condition, int page, int perPage);
}
