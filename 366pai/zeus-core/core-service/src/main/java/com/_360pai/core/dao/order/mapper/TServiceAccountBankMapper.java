
package com._360pai.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.order.TServiceAccountBankCondition;
import com._360pai.core.model.order.TServiceAccountBank;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>TServiceAccountBank数据层的基础操作</p>
 * @ClassName: TServiceAccountBankMapper
 * @Description: TServiceAccountBank数据层的基础操作
 * @author Generator
 * @date 2018年10月06日 23时43分10秒
 */
@Mapper
public interface TServiceAccountBankMapper extends BaseMapper<TServiceAccountBank, TServiceAccountBankCondition>{

    int updateByPartyId(TServiceAccountBank bank);
}
