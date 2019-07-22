
package com.tzCloud.core.dao.legalEngine;

import com.github.pagehelper.PageInfo;
import com.tzCloud.core.condition.legalEngine.TCaseCondition;
import com.tzCloud.core.model.legalEngine.TCase;
import com.tzCloud.arch.core.abs.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TCase的基础操作Dao</p>
 * @ClassName: TCaseDao
 * @Description: TCase基础操作的Dao
 * @author Generator
 * @date 2019年04月19日 15时41分11秒
 */
public interface TCaseDao extends BaseDao<TCase,TCaseCondition>{

    int migrationFromCpwswItem(Map<String, Object> params);

    PageInfo<TCase> findNeedToRepair(int page, int perPage, Map<String, Object> params);

    PageInfo<TCase> findBriefIdIsNull(int page, int perPage, Map<String, Object> params);

    int batchUpdateJudgementTypeBriefId(List<TCase> list);

    Integer getMaxId();

    PageInfo<TCase> findByIdGreaterThan(int page, int perPage, Integer id);

    List<Map<String, String>> findBriefByCourtName(String name);

    List<Map<String, Object>> selectJudgeDateByCourtName(String name);

    List<TCase> selectTypeAndJudgementType(String name);

    TCase findBy(String docId);

    int getTotalCaseCount();
}
