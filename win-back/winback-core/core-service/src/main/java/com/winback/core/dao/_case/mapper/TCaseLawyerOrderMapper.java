
package com.winback.core.dao._case.mapper;

import com.winback.core.facade._case.req.CaseLawyerOrderReq;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseLawyerOrderCondition;
import com.winback.core.model._case.TCaseLawyerOrder;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseLawyerOrder数据层的基础操作</p>
 * @ClassName: TCaseLawyerOrderMapper
 * @Description: TCaseLawyerOrder数据层的基础操作
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
@Mapper
public interface TCaseLawyerOrderMapper extends BaseMapper<TCaseLawyerOrder, TCaseLawyerOrderCondition>{

    public boolean updateAcceptOrderStatus(@Param("caseId") String caseId, @Param("lawyerId") Integer lawyerId,@Param("operatorId") Integer operatorId);

    public boolean updateRefusedOrderStatus(@Param("caseId") String caseId, @Param("lawyerId") Integer lawyerId,@Param("operatorId") Integer operatorId);
    public boolean updateSuccessOrderStatus(@Param("caseId") String caseId, @Param("lawyerId") Integer lawyerId,@Param("operatorId") Integer operatorId);

    public List<TCaseLawyerOrder> searchLawyerOrder(CaseLawyerOrderReq req);

}
