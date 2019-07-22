package com.winback.core.service._case;

import com.github.pagehelper.PageInfo;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade._case.req.AdminCaseReq;
import com.winback.core.facade._case.req.CaseAssetReq;
import com.winback.core.facade._case.resp.CaseAssetVo;
import com.winback.core.facade._case.vo.CaseBigBrief;
import com.winback.core.facade._case.vo.CaseBrief;

/**
 * @author xdrodger
 * @Title: CaseBriefService
 * @ProjectName winback
 * @Description:
 * @date 2019/2/14 18:58
 */
public interface CaseBriefService {

    ListResp<CaseBrief> getCaseBriefList(AdminCaseReq.QueryReq req);

    PageInfoResp<CaseBrief> getCaseBriefList(AppReq req);

    PageInfoResp<CaseBrief> getCaseBriefListByPage(AdminCaseReq.QueryReq req);

    Integer addCaseBrief(AdminCaseReq.AddCaseBriefReq req);

    Integer editCaseBrief(AdminCaseReq.EditCaseBriefReq req);

    Integer deleteCaseBrief(AdminCaseReq.DeleteCaseBriefReq req);

    ListResp<CaseBigBrief> getCaseBigBriefList();

    ListResp<CaseBigBrief> getBackgroundCaseBigBriefList(Boolean all);
}
