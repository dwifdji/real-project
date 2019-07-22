
package com._360pai.core.dao.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.payment.LockPayOrderActionCondition;
import com._360pai.core.model.payment.LockPayOrderAction;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>LockPayOrderAction数据层的基础操作</p>
 * @ClassName: LockPayOrderActionMapper
 * @Description: LockPayOrderAction数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
@Mapper
public interface LockPayOrderActionMapper extends BaseMapper<LockPayOrderAction, LockPayOrderActionCondition>{

}
