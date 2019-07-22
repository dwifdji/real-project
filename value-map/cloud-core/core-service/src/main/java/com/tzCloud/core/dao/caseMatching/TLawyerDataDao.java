
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.caseMatching.TLawyerDataCondition;
import com.tzCloud.core.model.caseMatching.TLawyerData;

import java.util.List;
import java.util.Map;

/**
 * <p>TLawyerData的基础操作Dao</p>
 * @ClassName: TLawyerDataDao
 * @Description: TLawyerData基础操作的Dao
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
public interface TLawyerDataDao extends BaseDao<TLawyerData, TLawyerDataCondition> {
    int batchUpdateWinFlag(List<Integer> ids, String flag);
    List<TLawyerData> findJoinHtml();
    long count();
    int batchUpdateIdentity(List<Integer> ids, String identity);
    List<TLawyerData> findNoIdentity();
    List<TLawyerData> findNoIdentity2();
    List<TLawyerData> findByParam(Map<String, Object> param);
    List<TLawyerData> findByLawyer(String lawyer);
    List<TLawyerData> findLawyerDirty();
    List<TLawyerData> findLawyerNoLawyerId();
    int batchUpdateLawyerId(List<TLawyerData> list);
    List<TLawyerData> findUnusualData1();
    int deleteById(int id);
    List<TLawyerData> findLawyerByLawyerId(List<Long> id);
    int updateLawyerIdByLawyerId(Long oldId,Long newId);
    Integer getCountDocId(String lawyerId);

    List<TLawyerData> findBy(List<String> docIds);

    List<TLawyerData> findBy(String docId);
    Map<Long,Map<String, String>> findGroupDocIdByLawyerIds(String lawyerIds);
}
