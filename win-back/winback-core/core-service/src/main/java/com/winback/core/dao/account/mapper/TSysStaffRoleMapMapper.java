
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TSysRole;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysStaffRoleMapCondition;
import com.winback.core.model.account.TSysStaffRoleMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TSysStaffRoleMap数据层的基础操作</p>
 * @ClassName: TSysStaffRoleMapMapper
 * @Description: TSysStaffRoleMap数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysStaffRoleMapMapper extends BaseMapper<TSysStaffRoleMap, TSysStaffRoleMapCondition>{
    List<TSysRole> findRoleBy(@Param("staffId") Integer staffId);
}
