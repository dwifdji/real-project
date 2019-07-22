
package com._360pai.core.dao.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.payment.PayActionCondition;
import com._360pai.core.model.payment.PayAction;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>PayAction数据层的基础操作</p>
 * @ClassName: PayActionMapper
 * @Description: PayAction数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分19秒
 */
@Mapper
public interface PayActionMapper extends BaseMapper<PayAction, PayActionCondition>{

}
