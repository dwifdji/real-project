
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.core.condition.caseMatching.TLawyerInfoCondition;
import com.tzCloud.core.model.caseMatching.TLawyerInfo;
import com.tzCloud.arch.core.abs.BaseDao;

import java.util.List;
import java.util.concurrent.Future;

/**
 * <p>TLawyerInfo的基础操作Dao</p>
 * @ClassName: TLawyerInfoDao
 * @Description: TLawyerInfo基础操作的Dao
 * @author Generator
 * @date 2019年03月12日 11时53分17秒
 */
public interface TLawyerInfoDao extends BaseDao<TLawyerInfo,TLawyerInfoCondition>{

    Future<TLawyerInfo> asyncSelectFirst(TLawyerInfoCondition tLawyerCondition);
    List<TLawyerInfo> getLawyerByName(String name, String firmShort);

    TLawyerInfo findBy(String xm, String lsswsmc);
}
