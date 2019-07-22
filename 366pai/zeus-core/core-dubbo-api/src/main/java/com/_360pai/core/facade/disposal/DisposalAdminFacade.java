package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.facade.account.vo.AccountAuthVo;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;

import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-17 08:59
 */
public interface DisposalAdminFacade {

    /**
     * 获取处置需求列表
     * @param
     * @return
     */
    PageInfoResp findDisposalByAdmin(DisposalRequirementReq.GetPublishInfoReq req);


    /**
     * 查看处置需求详情
     * @param disposalId
     * @return
     */
    Map<String, Object> findDisposalById(Integer disposalId);

    /**
     * 用户获取投标情况
     * @param req
     * @return
     */
    PageUtils.Page findBiddingByDisposalId(DisposalRequirementReq.GetBiddingList req);

    /**
     * 撮合成功
     * @param communicateContent
     * @return
     */
    boolean updateDisposalSuccess(String communicateContent, Integer biddingId, Integer operatorId);

    /**
     * 修改审核状态
     * @param
     * @param
     * @return
     */
    boolean updateDisposalVerifyStatus(DisposalRequirementReq.AdminOperateInfo req);

    /**
     * 添加招标须知
     * @param biddingNotice
     * @return
     */
    boolean addBiddingNotice(String biddingNotice, Integer operatorNoticeId, Integer disposalId);

    /**
     * 手动完成
     * @param operatorId
     * @param disposalId
     * @return
     */
    boolean manuallyCompleted(Integer operatorId, Integer disposalId);

    /**
     * 获取投标情况
     * @param req
     * @return
     */
    PageInfoResp findBiddingInfoList(DisposalRequirementReq.GetBiddingList req);

    /**
     * 校验账号认证处置服务商信息
     * @param mobile
     * @return
     */
    Map<String, Object> checkAccountDispose(String mobile);

    /**
     * 管理人员创建处置服务商
     * @param req
     * @return
     */
    int disposeProviderAdminCreate(DisposeProviderApplyReq.CreateReq req);
}
