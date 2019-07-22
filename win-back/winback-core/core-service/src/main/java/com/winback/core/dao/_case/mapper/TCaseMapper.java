
package com.winback.core.dao._case.mapper;

import com.winback.core.facade._case.req.CaseCommReq;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.HomePageCaseVO;
import com.winback.core.model.account.TAccount;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseCondition;
import com.winback.core.model._case.TCase;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TCase数据层的基础操作</p>
 * @ClassName: TCaseMapper
 * @Description: TCase数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
@Mapper
public interface TCaseMapper extends BaseMapper<TCase, TCaseCondition>{

    int applyCaseCount(@Param("accountId") Integer accountId);

    List<HomePageCaseVO> getRecommendedCaseList(@Param("caseSize") Integer caseSize);

    List<WorkBenchVO> getStatusCase();

    List<TCase> getApplyCase(CaseCommReq req);
    List<TCase> getPublishedCaseList(@Param("caseTypeId") Integer caseTypeId);
    List<TCase> searchCaseByName(@Param("name") String name);
    List<TCase> searchSelfCaseByName(@Param("name") String name,@Param("accountId") Integer accountId);

    List<TCase> searchCase(TCaseCondition condition);

    List<Case> getProjectManagerAllocatedCaseList(@Param("accountId") Integer accountId);

    List<TCase> getMyManageCaseList(Map<String, Object> params);
}
