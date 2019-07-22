package com._360pai.core.service.account;

import com._360pai.core.condition.account.TSpvCondition;
import com._360pai.core.model.account.TSpv;
import com._360pai.core.model.account.TSpvApply;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author RuQ
 * @Title: SpvService
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/21 20:07
 */
public interface SpvService {

    boolean saveSpv(TSpv spv);

    boolean updateSpv(TSpv spv);

    List<TSpv> getSpvByCompanyId(Integer companyId);

    List<TSpv> getSpvByParam(TSpvCondition spvCondition);

    TSpv getSpvById(Integer spvId);

    TSpv getSpvByMobile(String mobile);

    boolean saveSpvApply(TSpvApply spvApply);

    boolean updateSpvApply(Integer spvApplyId,String status,Integer operatorId);

    List<TSpvApply> getSpvApplyByMobileAndStatus(String mobile,String status);

    List<TSpvApply> getSpvApplyByLicenseAndStatus(String license,String status);

    TSpvApply getSpvApplyById(Integer applyId);

    PageInfo<TSpvApply> getSpvListByPage(Integer companyId,Integer page,Integer perPage);
}
