
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.core.condition.caseMatching.CaseHtmlDataCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>CaseHtmlData的基础操作Dao</p>
 * @ClassName: CaseHtmlDataDao
 * @Description: CaseHtmlData基础操作的Dao
 * @author Generator
 * @date 2019年03月05日 11时07分13秒
 */
public interface CaseHtmlDataDao extends BaseDao<CaseHtmlData,CaseHtmlDataCondition>{
    List<CaseHtmlData> findBySPCX(String spcx, int pageNum, int pageSize);
    List<CaseHtmlData> findBySPCX();
    List<CaseHtmlData> findByDocIds(List<String> docId);
    List<CaseHtmlData> findByLimit();
    long findByLimit_Count();
    List<CaseHtmlData> findByNoSave(int pageNum, int pageSize);
    List<CaseHtmlData> findByNewForAnalysis();
    List<CaseHtmlData> findByNewForDsrxx();
    long findByNoUpdateCount();
    long findBySPCXCount();
    long findByLimitCount();

    CaseHtmlData findBy(String docId);
    List<String> findDocId();
    Long findDocId_COUNT();
    List<CaseHtmlData> findHtmlByDocIds(List<String> docId);
}
