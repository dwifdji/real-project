
package com.winback.core.dao._case.mapper;

import com.winback.core.vo.operate.CaseBriefVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseBriefCondition;
import com.winback.core.model._case.TCaseBrief;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TCaseBrief数据层的基础操作</p>
 * @ClassName: TCaseBriefMapper
 * @Description: TCaseBrief数据层的基础操作
 * @author Generator
 * @date 2019年01月28日 15时32分09秒
 */
@Mapper
public interface TCaseBriefMapper extends BaseMapper<TCaseBrief, TCaseBriefCondition>{

    List<CaseBriefVO> getCaseBriefList();

    List<TCaseBrief> getList(Map<String, Object> params);
}
