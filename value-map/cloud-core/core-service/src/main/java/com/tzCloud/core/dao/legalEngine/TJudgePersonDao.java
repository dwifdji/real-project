
package com.tzCloud.core.dao.legalEngine;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.legalEngine.TJudgePersonCondition;
import com.tzCloud.core.model.legalEngine.TJudgePerson;

import java.util.List;

/**
 * <p>TJudgePerson的基础操作Dao</p>
 * @ClassName: TJudgePersonDao
 * @Description: TJudgePerson基础操作的Dao
 * @author Generator
 * @date 2019年04月23日 14时53分12秒
 */
public interface TJudgePersonDao extends BaseDao<TJudgePerson, TJudgePersonCondition>{
    TJudgePerson findBy(String docId);

    List<TJudgePerson> findBy(List<String> docIds);
}
