
package com.tzCloud.core.dao.legalEngine.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.legalEngine.TCaseCondition;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TCase数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TCaseMapper
 * @Description: TCase数据层的基础操作
 * @date 2019年04月19日 15时41分11秒
 */
@Mapper
public interface TCaseMapper extends BaseMapper<TCase, TCaseCondition> {

    Integer getMaxId();

    int migrationFromCpwswItem(Map<String, Object> params);

    List<TCase> findNeedToRepair(Map<String, Object> params);

    List<TCase> findBriefIdIsNull(Map<String, Object> params);

    int batchUpdateJudgementTypeBriefId(@Param("list") List<TCase> list);

    List<Map<String, String>> findBriefByCourtName(@Param("courtName") String name);

    List<Map<String, Object>> selectJudgeDateByCourtName(@Param("courtName") String name);

    List<TCase> selectTypeAndJudgementType(@Param("courtName") String name);

    List<TCase> findByIdGreaterThan(@Param("id") Integer id);

    int getTotalCaseCount();
}
