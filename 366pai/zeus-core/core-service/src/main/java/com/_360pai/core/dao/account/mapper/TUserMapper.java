
package com._360pai.core.dao.account.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TUserCondition;
import com._360pai.core.model.account.TUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TUser数据层的基础操作</p>
 * @ClassName: TUserMapper
 * @Description: TUser数据层的基础操作
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
@Mapper
public interface TUserMapper extends BaseMapper<TUser, TUserCondition>{
    List<TUser> getList(Map<String, Object> params);
}
