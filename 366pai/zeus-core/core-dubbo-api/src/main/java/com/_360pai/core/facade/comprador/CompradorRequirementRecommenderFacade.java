package com._360pai.core.facade.comprador;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.req.MyRequirementRecommenderListReq;
import com._360pai.core.facade.comprador.req.RequiementRecommenderRemarkReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderAddReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderQueryReq;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/5 09:26
 */
public interface CompradorRequirementRecommenderFacade {

    int requirementRecommenderAdd(RequirementRecommenderAddReq req);

    PageUtils.Page requirementRecommenderList(RequirementRecommenderQueryReq req);

    int requirementRecommenderRemark(RequiementRecommenderRemarkReq req);

    int requirementRecommenderMatchSuccess(RequiementRecommenderRemarkReq req);


    PageUtils.Page myRequirementRecommenderList(MyRequirementRecommenderListReq req);
}
