
package com.winback.core.dao._case.mapper;

import com.winback.core.model._case.TCaseBrief;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TLawyerCaseBriefMapCondition;
import com.winback.core.model._case.TLawyerCaseBriefMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TLawyerCaseBriefMap数据层的基础操作</p>
 * @ClassName: TLawyerCaseBriefMapMapper
 * @Description: TLawyerCaseBriefMap数据层的基础操作
 * @author Generator
 * @date 2019年01月29日 13时21分44秒
 */
@Mapper
public interface TLawyerCaseBriefMapMapper extends BaseMapper<TLawyerCaseBriefMap, TLawyerCaseBriefMapCondition>{
    List<TCaseBrief> getCaseBriefListByLawyerId(@Param("lawyerId") Integer lawyerId);
}
