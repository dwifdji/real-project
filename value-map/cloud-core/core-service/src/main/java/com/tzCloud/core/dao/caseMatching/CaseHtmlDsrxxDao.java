
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.core.condition.caseMatching.CaseHtmlDsrxxCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlDsrxx;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>CaseHtmlDsrxx的基础操作Dao</p>
 * @ClassName: CaseHtmlDsrxxDao
 * @Description: CaseHtmlDsrxx基础操作的Dao
 * @author Generator
 * @date 2019年03月08日 15时17分56秒
 */
public interface CaseHtmlDsrxxDao extends BaseDao<CaseHtmlDsrxx,CaseHtmlDsrxxCondition>{
    void batchInsert(List<CaseHtmlDsrxx> list);
    List<String> findDocId();
    Long findDocId_COUNT();
    List<CaseHtmlDsrxx> findUnusualList();
    void deleteById(Integer id);
    List<CaseHtmlDsrxx> findMoreThanId(Integer id);
}
