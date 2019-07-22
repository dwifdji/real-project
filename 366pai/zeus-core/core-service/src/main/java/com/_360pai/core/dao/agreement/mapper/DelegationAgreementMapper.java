
package com._360pai.core.dao.agreement.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.agreement.DelegationAgreementCondition;
import com._360pai.core.model.agreement.DelegationAgreement;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>DelegationAgreement数据层的基础操作</p>
 * @ClassName: DelegationAgreementMapper
 * @Description: DelegationAgreement数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface DelegationAgreementMapper extends BaseMapper<DelegationAgreement, DelegationAgreementCondition>{
    List<Integer> getAllSignedTimeout();
}
