
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.BankCondition;
import com._360pai.core.model.assistant.Bank;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>Bank数据层的基础操作</p>
 * @ClassName: BankMapper
 * @Description: Bank数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分16秒
 */
@Mapper
public interface BankMapper extends BaseMapper<Bank, BankCondition>{

}
