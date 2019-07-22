
package com._360pai.core.dao.fastway.mapper;

import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.fastway.TFastwayFundApplyCondition;
import com._360pai.core.model.fastway.TFastwayFundApply;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayFundApply数据层的基础操作</p>
 * @ClassName: TFastwayFundApplyMapper
 * @Description: TFastwayFundApply数据层的基础操作
 * @author Generator
 * @date 2018年12月07日 09时54分39秒
 */
@Mapper
public interface TFastwayFundApplyMapper extends BaseMapper<TFastwayFundApply, TFastwayFundApplyCondition>{
    List<TFastwayAgencyApply> findByParam(Map<String, Object> query);
    List<TCompany> findApplyStatusByAccountId(Integer accountId);
}
