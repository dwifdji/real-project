
package com.tzCloud.core.dao.view;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.view.ViewCourtJudgePersonCondition;
import com.tzCloud.core.model.view.ViewCourtJudgePerson;

import java.util.List;
import java.util.Map;

/**
 * <p>ViewCourtJudgePerson的基础操作Dao</p>
 * @ClassName: ViewCourtJudgePersonDao
 * @Description: ViewCourtJudgePerson基础操作的Dao
 * @author Generator
 * @date 2019年04月24日 10时43分19秒
 */
public interface ViewCourtJudgePersonDao extends BaseDao<ViewCourtJudgePerson, ViewCourtJudgePersonCondition>{

    List<Map<String, Object>> selectJudgePersonByCourtName(String name);
}
