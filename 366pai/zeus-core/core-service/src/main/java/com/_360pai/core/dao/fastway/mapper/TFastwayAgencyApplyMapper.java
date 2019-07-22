
package com._360pai.core.dao.fastway.mapper;

import com._360pai.core.model.account.TCompany;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.fastway.TFastwayAgencyApplyCondition;
import com._360pai.core.model.fastway.TFastwayAgencyApply;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayAgencyApply数据层的基础操作</p>
 * @ClassName: TFastwayAgencyApplyMapper
 * @Description: TFastwayAgencyApply数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 14时19分01秒
 */
@Mapper
public interface TFastwayAgencyApplyMapper extends BaseMapper<TFastwayAgencyApply, TFastwayAgencyApplyCondition>{
    List<TFastwayAgencyApply> findByParam(Map<String, Object> query);
    List<TCompany> findByAccountId(Integer accountId);
}
