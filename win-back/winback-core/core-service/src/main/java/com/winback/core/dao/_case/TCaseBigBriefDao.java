
package com.winback.core.dao._case;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition._case.TCaseBigBriefCondition;
import com.winback.core.model._case.TCaseBigBrief;

import java.util.List;

/**
 * <p>TCaseBigBrief的基础操作Dao</p>
 * @ClassName: TCaseBigBriefDao
 * @Description: TCaseBigBrief基础操作的Dao
 * @author Generator
 * @date 2019年04月22日 18时58分49秒
 */
public interface TCaseBigBriefDao extends BaseDao<TCaseBigBrief,TCaseBigBriefCondition>{

    List<TCaseBigBrief> getList();

    List<TCaseBigBrief> getBackgroundList();

    TCaseBigBrief findBy(String name);
}
