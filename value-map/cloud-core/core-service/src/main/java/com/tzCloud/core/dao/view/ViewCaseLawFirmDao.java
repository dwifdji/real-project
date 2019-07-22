
package com.tzCloud.core.dao.view;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.view.ViewCaseLawFirmCondition;
import com.tzCloud.core.model.view.ViewCaseLawFirm;

import java.util.List;
import java.util.Map;

/**
 * <p>ViewCaseLawFirm的基础操作Dao</p>
 * @ClassName: ViewCaseLawFirmDao
 * @Description: ViewCaseLawFirm基础操作的Dao
 * @author Generator
 * @date 2019年04月28日 14时39分27秒
 */
public interface ViewCaseLawFirmDao extends BaseDao<ViewCaseLawFirm, ViewCaseLawFirmCondition>{

    List<Map<String, Object>> findByCourtName(String name);
}
