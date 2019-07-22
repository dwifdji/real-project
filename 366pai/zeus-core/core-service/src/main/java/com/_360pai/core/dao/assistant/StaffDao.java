
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.model.assistant.Staff;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>Staff的基础操作Dao</p>
 * @ClassName: StaffDao
 * @Description: Staff基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分17秒
 */
public interface StaffDao extends BaseDao<Staff,StaffCondition>{
    Staff getByMobile(String mobile);
    Staff getByQq(String qq);
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
    int deleteById(Integer id);

    Staff selectById(Integer id);

    int updateById(Staff staff);

    String getName(Integer id);
}
