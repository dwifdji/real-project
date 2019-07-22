
package com.tzCloud.core.dao.view;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.view.ViewCourtNumCondition;
import com.tzCloud.core.facade.caseMatching.resp.ViewCourtNumResp;
import com.tzCloud.core.model.view.ViewCourtNum;

import java.util.List;

/**
 * <p>ViewCourtNum的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: ViewCourtNumDao
 * @Description: ViewCourtNum基础操作的Dao
 * @date 2019年04月19日 09时44分49秒
 */
public interface ViewCourtNumDao extends BaseDao<ViewCourtNum, ViewCourtNumCondition> {

    List<ViewCourtNumResp> findByCourtName(ViewCourtNum viewCourtNum);

}
