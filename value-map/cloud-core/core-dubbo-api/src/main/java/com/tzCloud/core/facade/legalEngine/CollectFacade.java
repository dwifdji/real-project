package com.tzCloud.core.facade.legalEngine;

import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.facade.legalEngine.req.CollectReq;

/**
 * @author xiaolei
 * @create 2019-04-24 15:10
 */
public interface CollectFacade {

    /**
     * 收藏
     */
    Integer collect(CollectReq.Collect req);

    /**
     * 取消收藏
     */
    void cancelCollect(CollectReq.Collect req);

    /**
     * 获取收藏的案例
     */
    PageInfoResp getCollectCase(CollectReq.Collect req);

    /**
     * 获取收藏的律师
     */
    PageInfoResp getCollectLawyer(CollectReq.Collect req);
}
