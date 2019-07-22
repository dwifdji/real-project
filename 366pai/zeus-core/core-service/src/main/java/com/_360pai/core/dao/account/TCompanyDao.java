
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TCompanyCondition;
import com._360pai.core.model.account.TCompany;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TCompany的基础操作Dao</p>
 * @ClassName: TCompanyDao
 * @Description: TCompany基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TCompanyDao extends BaseDao<TCompany,TCompanyCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);
    TCompany getById(Integer id);

    boolean isExistLicense(String license);

    TCompany getByLicense(String license);

    TCompany selectById(Integer id);

    int updateById(TCompany staff);

    List<TCompany> getByAccountId(Integer accountId);

    TCompany getLatestByAccountId(Integer accountId);
}
