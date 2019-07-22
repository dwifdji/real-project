package com._360pai.core.facade.comprador;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.req.MyRecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendAddReq;
import com._360pai.core.facade.comprador.req.RecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendReq;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/5 09:25
 */
public interface CompradorRecommendFacade {

    /**
     * 描述 返回id
     *
     * @author : whisky_vip
     * @date : 2018/9/17 16:53
     */
    int recommendAdd(RecommendAddReq req);

    int recommendMatchSuccess(RecommendReq req);

    int recommendFinished(RecommendReq req);

    PageUtils.Page recommendList(RecommendListReq req);

    PageUtils.Page myRecommendList(MyRecommendListReq req);

    PageUtils.Page recommendListForAdmin(RecommendListReq req);
}
