
package com.winback.core.dao._case;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition._case.TCaseLawyerOrderCondition;
import com.winback.core.model._case.TCaseLawyerOrder;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TCaseLawyerOrder的基础操作Dao</p>
 * @ClassName: TCaseLawyerOrderDao
 * @Description: TCaseLawyerOrder基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
public interface TCaseLawyerOrderDao extends BaseDao<TCaseLawyerOrder,TCaseLawyerOrderCondition>{

    public boolean updateAcceptOrderStatus(String caseId, Integer lawyerId,Integer operatorId);

    public boolean updateRefusedOrderStatus(String caseId, Integer lawyerId,Integer operatorId);
    public boolean updateSuccessOrderStatus(String caseId, Integer lawyerId,Integer operatorId);

    PageInfo<TCaseLawyerOrder> getApplyAcceptLawyers(int page,int perPage,String orderBy,TCaseLawyerOrderCondition condition);
}
