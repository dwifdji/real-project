
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.model.account.TCompany;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.model.assistant.Staff;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Staff数据层的基础操作</p>
 * @ClassName: StaffMapper
 * @Description: Staff数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface StaffMapper extends BaseMapper<Staff, StaffCondition>{
    List<Staff> getList(Map<String, Object> params);
    int deleteById(@Param("id") Integer id);
}
