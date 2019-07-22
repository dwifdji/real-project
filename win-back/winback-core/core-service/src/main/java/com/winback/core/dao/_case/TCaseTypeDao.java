
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseTypeCondition;
import com.winback.core.model._case.TCaseType;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.operate.CaseTypeVO;
import com.winback.core.vo.operate.QuickReleaseVO;

import java.util.List;

/**
 * <p>TCaseType的基础操作Dao</p>
 * @ClassName: TCaseTypeDao
 * @Description: TCaseType基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public interface TCaseTypeDao extends BaseDao<TCaseType,TCaseTypeCondition>{
    String getName(Integer id);

    List<CaseTypeVO> getAllCaseType();
}
