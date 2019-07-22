
package com._360pai.core.dao.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.account.SysRoleCondition;
import com._360pai.core.model.account.SysRole;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>SysRole数据层的基础操作</p>
 * @ClassName: SysRoleMapper
 * @Description: SysRole数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 16时55分54秒
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole, SysRoleCondition>{
    List<SysRole> getList(Map<String, Object> params);
}
