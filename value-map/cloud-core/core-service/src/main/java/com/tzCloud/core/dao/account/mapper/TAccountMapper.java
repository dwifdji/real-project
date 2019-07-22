
package com.tzCloud.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.account.TAccountCondition;
import com.tzCloud.core.model.account.TAccount;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TAccount数据层的基础操作</p>
 * @ClassName: TAccountMapper
 * @Description: TAccount数据层的基础操作
 * @author Generator
 * @date 2019年04月19日 13时33分42秒
 */
@Mapper
public interface TAccountMapper extends BaseMapper<TAccount, TAccountCondition>{

}
