
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TSysStaff;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysRoleCondition;
import com.winback.core.model.account.TSysRole;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TSysRole数据层的基础操作</p>
 * @ClassName: TSysRoleMapper
 * @Description: TSysRole数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysRoleMapper extends BaseMapper<TSysRole, TSysRoleCondition>{
    List<TSysRole> getList(Map<String, Object> params);
}
