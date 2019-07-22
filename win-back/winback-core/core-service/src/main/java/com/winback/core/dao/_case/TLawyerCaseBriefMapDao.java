
package com.winback.core.dao._case;

import com.winback.core.condition._case.TLawyerCaseBriefMapCondition;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model._case.TLawyerCaseBriefMap;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TLawyerCaseBriefMap的基础操作Dao</p>
 * @ClassName: TLawyerCaseBriefMapDao
 * @Description: TLawyerCaseBriefMap基础操作的Dao
 * @author Generator
 * @date 2019年01月29日 13时21分44秒
 */
public interface TLawyerCaseBriefMapDao extends BaseDao<TLawyerCaseBriefMap,TLawyerCaseBriefMapCondition>{
    List<TCaseBrief> getCaseBriefListByLawyerId(Integer lawyerId);

    void saveCaseBriefMap(Integer lawyerId, List<Integer> caseBriefIdList);

    void syncCaseBriefMap(Integer lawyerId, List<Integer> caseBriefIdList);
}
