
package com._360pai.core.dao.payment.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.payment.LockDepositForSendBackOrderActionCondition;
import com._360pai.core.model.payment.LockDepositForSendBackOrderAction;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>LockDepositForSendBackOrderAction数据层的基础操作</p>
 * @ClassName: LockDepositForSendBackOrderActionMapper
 * @Description: LockDepositForSendBackOrderAction数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
@Mapper
public interface LockDepositForSendBackOrderActionMapper extends BaseMapper<LockDepositForSendBackOrderAction, LockDepositForSendBackOrderActionCondition>{

}
