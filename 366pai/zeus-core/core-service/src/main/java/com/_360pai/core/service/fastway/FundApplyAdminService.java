package com._360pai.core.service.fastway;

import com._360pai.core.facade.fastway.vo.FundBasisVO;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-12-07 11:09
 */
public interface FundApplyAdminService {
    PageInfo findByParam(Map<String, Object> query, int pageNum, int pageSize);
    TFastwayFundApply findById(Integer applyId);
    int fundUpdate(FundBasisVO detailVO, Integer applyId, Integer operatorId);
    int fundVerifyDeny(String refuseReason, Integer applyId, Integer operatorId);
    int userVerifyAccess(FundBasisVO detailVO, Integer applyId, Integer operatorId);
    int companyVerifyAccess(FundBasisVO detailVO, Integer applyId, Integer operatorId);
}
