package com.tzCloud.core.service;

import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.legalEngine.req.CollectReq;
import com.tzCloud.core.facade.legalEngine.resp.LawyerSearchVO;
import com.tzCloud.core.facade.legalEngine.vo.CaseVo;
import com.tzCloud.core.model.legalEngine.TCollect;

/**
 * @author xiaolei
 * @create 2019-04-24 15:15
 */
public interface CollectService {

    Integer collect(CollectReq.Collect req);

    void cancelCollect(CollectReq.Collect req);

    PageInfoResp getCollectCase(CollectReq.Collect req);

    LawyerSearchVO getCollectLawyer(CollectReq.Collect req);

    PageInfoResp getLawyerByES(CollectReq.Collect req);

    boolean isCollect(Integer accountId, String collectKey, String collectType);

    PageInfo<TCollect> getCollectListByAccountId(CollectReq.Collect req);
}
