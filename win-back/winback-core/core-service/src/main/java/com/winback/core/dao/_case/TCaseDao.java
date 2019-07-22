
package com.winback.core.dao._case;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition._case.TCaseCondition;
import com.winback.core.facade._case.req.CaseAssetReq;
import com.winback.core.facade._case.req.CaseCommReq;
import com.winback.core.facade._case.req.CaseLawyerOrderReq;
import com.winback.core.facade._case.vo.Case;
import com.winback.core.facade._case.vo.HomePageCaseVO;
import com.winback.core.model._case.TCase;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model._case.TCaseAsset;
import com.winback.core.model._case.TCaseLawyerOrder;
import com.winback.core.model._case.TCaseStatusUpdateRecord;
import com.winback.core.model.account.TAccount;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;
import java.util.Map;

/**
 * <p>TCase的基础操作Dao</p>
 * @ClassName: TCaseDao
 * @Description: TCase基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
public interface TCaseDao extends BaseDao<TCase,TCaseCondition>{
    PageInfo<TCase> getListByAccountId(int page, int perPage, Integer accountId, String orderBy);
    PageInfo<TCase> searchCase(int page, int perPage,TCaseCondition condition,String orderBy,String personType);
    PageInfo<TCase> searchCaseByName(int page, int perPage,String name,String orderBy);
    PageInfo<TCase> searchSelfCaseByName(int page, int perPage,String name,Integer accountId,String orderBy);
    PageInfo<TCase> getApplyedCase(CaseCommReq req);
    PageInfo<TCaseLawyerOrder> searchLawyerOrder(int page, int perPage,String orderBy, CaseLawyerOrderReq req);
    PageInfo<TCaseAsset> searchCaseAsset(int page, int perPage, String orderBy, CaseAssetReq req);
    List<TCaseAsset> searchCaseList(CaseAssetReq req);
    TCase getCaseByCaseId(String caseId);
    int applyCaseCount(Integer accountId);
    PageInfo<TCase> getPublishedCaseList(int page,int perPage,String orderBy,Integer caseTypeId);
    List<HomePageCaseVO> getRecommendedCaseList(Integer caseSize);

    List<WorkBenchVO> getStatusCase();

    PageInfo<Case> getProjectManagerAllocatedCaseList(int page, int perPage, Integer accountId, String orderBy);


    PageInfo<TCase> getMyManageCaseList(int page, int perPage, Map<String, Object> params, String orderBy);

}
