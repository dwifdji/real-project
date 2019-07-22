
package com.winback.core.dao._case;

import com.winback.core.condition._case.TLawyerFirmCaseBriefMapCondition;
import com.winback.core.model._case.TCaseBrief;
import com.winback.core.model._case.TLawyerFirmCaseBriefMap;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TLawyerFirmCaseBriefMap的基础操作Dao</p>
 * @ClassName: TLawyerFirmCaseBriefMapDao
 * @Description: TLawyerFirmCaseBriefMap基础操作的Dao
 * @author Generator
 * @date 2019年02月13日 14时47分07秒
 */
public interface TLawyerFirmCaseBriefMapDao extends BaseDao<TLawyerFirmCaseBriefMap,TLawyerFirmCaseBriefMapCondition>{

    List<TCaseBrief> getCaseBriefListByLawyerFirmId(Integer lawFirmId);

    void saveCaseBriefMap(Integer lawFirmId, List<Integer> caseBriefIdList);

    void syncCaseBriefMap(Integer lawFirmId, List<Integer> caseBriefIdList);

    List<String> getCaseBriefIdListByLawyerFirmId(Integer lawFirmId);
}
