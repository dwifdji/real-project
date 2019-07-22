
package com.winback.core.dao._case;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition._case.TCaseBriefCondition;
import com.winback.core.model._case.TCaseBrief;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.operate.CaseBriefVO;

import java.util.List;
import java.util.Map;

/**
 * <p>TCaseBrief的基础操作Dao</p>
 * @ClassName: TCaseBriefDao
 * @Description: TCaseBrief基础操作的Dao
 * @author Generator
 * @date 2019年01月28日 15时32分09秒
 */
public interface TCaseBriefDao extends BaseDao<TCaseBrief,TCaseBriefCondition>{
    String getName(Integer id);

    PageInfo<TCaseBrief> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<TCaseBrief> getList(Map<String, Object> params);

    List<CaseBriefVO> getCaseBriefList();

    List<TCaseBrief> findBy(Integer bigBriefId);

    List<TCaseBrief> findBy(Integer bigBriefId, Boolean display);

    TCaseBrief findBy(String name);

    TCaseBrief findBy(Integer bigBriefId, String name);
}
