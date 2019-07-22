package com._360pai.core.facade.fastway;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.fastway.req.DisposeApplyReq;
import com._360pai.core.facade.fastway.resp.DisposeLawOfficeVO;
import com._360pai.core.facade.fastway.resp.DisposeLawyerVO;

/**
 * @author xiaolei
 * @create 2018-11-26 14:09
 */
public interface DisposeApplyAdminFacade {

    /**
     * admin获取服务商快速申请列表
     * @param req
     * @return
     */
    PageInfoResp findDisposeApplyByParam(DisposeApplyReq.DisposeFindReq req);

    /**
     * 获取律师详情
     * @param applyId
     * @return
     */
    DisposeLawyerVO findLawyerDetailById(Integer applyId);

    /**
     * 获取律所详情
     * @param applyId
     * @return
     */
    DisposeLawOfficeVO findLawOfficeDetailById(Integer applyId);

    /**
     * 律师审核
     * @param req
     * @return
     */
    int lawyerVerify(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId);

    /**
     * 律所审核
     * @param req
     * @return
     */
    int lawOfficeVerify(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId);

    /**
     * 律师修改
     * @param req
     * @return
     */
    int lawyerUpdate(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId);

    /**
     * 律所修改
     * @param req
     * @return
     */
    int lawOfficeUpdate(DisposeApplyReq.DisposeApplyVerify req, Integer operatorId);

}
