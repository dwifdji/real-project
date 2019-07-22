
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.core.condition.caseMatching.TTreeContentCondition;
import com.tzCloud.core.facade.legalEngine.vo.Brief;
import com.tzCloud.core.model.caseMatching.TTreeContent;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TTreeContent的基础操作Dao</p>
 * @ClassName: TTreeContentDao
 * @Description: TTreeContent基础操作的Dao
 * @author Generator
 * @date 2019年03月07日 16时22分18秒
 */
public interface TTreeContentDao extends BaseDao<TTreeContent,TTreeContentCondition>{
    List<TTreeContent> findAllBrief();

    Brief findBriefLevelBy(Integer id);

    List<TTreeContent> LikeByKeyWord(String name);
}
