
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.UserVerifyApplicationCondition;
import com._360pai.core.model.account.UserVerifyApplication;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>UserVerifyApplication数据层的基础操作</p>
 * @ClassName: UserVerifyApplicationMapper
 * @Description: UserVerifyApplication数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface UserVerifyApplicationMapper extends BaseMapper<UserVerifyApplication, UserVerifyApplicationCondition>{

}
