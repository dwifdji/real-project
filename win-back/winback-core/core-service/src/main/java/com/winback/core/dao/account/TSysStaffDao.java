
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.account.TSysStaffCondition;
import com.winback.core.model.account.TSysStaff;

import java.util.Map;

/**
 * <p>TSysStaff的基础操作Dao</p>
 * @ClassName: TSysStaffDao
 * @Description: TSysStaff基础操作的Dao
 * @author Generator
 * @date 2019年01月30日 14时34分26秒
 */
public interface TSysStaffDao extends BaseDao<TSysStaff,TSysStaffCondition>{
    TSysStaff findByMobile(String mobile);

    PageInfo<TSysStaff> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    String getName(Integer staffId);

    String getNameAndMobile(Integer staffId);
}
