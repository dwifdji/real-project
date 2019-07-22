
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.BankAccountCondition;
import com._360pai.core.model.account.BankAccount;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>BankAccount数据层的基础操作</p>
 * @ClassName: BankAccountMapper
 * @Description: BankAccount数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface BankAccountMapper extends BaseMapper<BankAccount, BankAccountCondition>{
    int deleteById(@Param("id") Integer id);
}
