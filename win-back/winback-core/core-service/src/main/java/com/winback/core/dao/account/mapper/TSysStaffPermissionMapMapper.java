
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TSysPermission;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysStaffPermissionMapCondition;
import com.winback.core.model.account.TSysStaffPermissionMap;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TSysStaffPermissionMap数据层的基础操作</p>
 * @ClassName: TSysStaffPermissionMapMapper
 * @Description: TSysStaffPermissionMap数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysStaffPermissionMapMapper extends BaseMapper<TSysStaffPermissionMap, TSysStaffPermissionMapCondition>{

    List<TSysPermission> findPermissionBy(@Param("staffId") Integer staffId);
}
