
package com.winback.core.dao.account.mapper;

import com.winback.core.model.account.TFranchiseeApplyRecord;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.account.TSysStaffCondition;
import com.winback.core.model.account.TSysStaff;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TSysStaff数据层的基础操作</p>
 * @ClassName: TSysStaffMapper
 * @Description: TSysStaff数据层的基础操作
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
@Mapper
public interface TSysStaffMapper extends BaseMapper<TSysStaff, TSysStaffCondition>{
    List<TSysStaff> getList(Map<String, Object> params);
}
