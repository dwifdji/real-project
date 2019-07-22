
package com._360pai.core.dao.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.payment.PayDepositActionCondition;
import com._360pai.core.model.payment.PayDepositAction;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>PayDepositAction数据层的基础操作</p>
 * @ClassName: PayDepositActionMapper
 * @Description: PayDepositAction数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分19秒
 */
@Mapper
public interface PayDepositActionMapper extends BaseMapper<PayDepositAction, PayDepositActionCondition>{

}
