
package com.winback.core.dao._case.mapper;

import com.winback.core.model._case.TCaseBrief;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TLawyerFirmCaseBriefMapCondition;
import com.winback.core.model._case.TLawyerFirmCaseBriefMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TLawyerFirmCaseBriefMap数据层的基础操作</p>
 * @ClassName: TLawyerFirmCaseBriefMapMapper
 * @Description: TLawyerFirmCaseBriefMap数据层的基础操作
 * @author Generator
 * @date 2019年02月13日 14时47分07秒
 */
@Mapper
public interface TLawyerFirmCaseBriefMapMapper extends BaseMapper<TLawyerFirmCaseBriefMap, TLawyerFirmCaseBriefMapCondition>{
    List<TCaseBrief> getCaseBriefListByLawyerFirmId(@Param("lawFirmId") Integer lawFirmId);
}
