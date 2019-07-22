
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.TBankCondition;
import com._360pai.core.model.account.TBank;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TBank数据层的基础操作</p>
 * @ClassName: TBankMapper
 * @Description: TBank数据层的基础操作
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
@Mapper
public interface TBankMapper extends BaseMapper<TBank, TBankCondition>{

}
