
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.UserCondition;
import com._360pai.core.model.account.User;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>User数据层的基础操作</p>
 * @ClassName: UserMapper
 * @Description: User数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface UserMapper extends BaseMapper<User, UserCondition>{

}
