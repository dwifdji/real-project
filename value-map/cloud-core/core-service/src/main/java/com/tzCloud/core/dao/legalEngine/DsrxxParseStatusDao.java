
package com.tzCloud.core.dao.legalEngine;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.legalEngine.DsrxxParseStatusCondition;
import com.tzCloud.core.model.legalEngine.DsrxxParseStatus;

import java.util.List;

/**
 * <p>DsrxxParseStatus的基础操作Dao</p>
 * @ClassName: DsrxxParseStatusDao
 * @Description: DsrxxParseStatus基础操作的Dao
 * @author Generator
 * @date 2019年06月25日 09时42分26秒
 */
public interface DsrxxParseStatusDao extends BaseDao<DsrxxParseStatus,DsrxxParseStatusCondition>{

    void batchInsert(List<String> docIds);
    void batchUpdate(List<String> docIds);
    void batchUpdate(List<String> docIds, int status);
    void batchUpdateUnParsed(List<String> docIds);
    void batchUpdateErrorParsed(List<String> docIds);
    List<String> findNoParseDocId();
    Long findNoParseDocId_COUNT();
    List<String> findDocId(List<String> docIds);
}
