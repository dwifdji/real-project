
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TUserCondition;
import com._360pai.core.model.account.TUser;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>TUser的基础操作Dao</p>
 * @ClassName: TUserDao
 * @Description: TUser基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TUserDao extends BaseDao<TUser,TUserCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    TUser selectById(Integer id);

    int updateById(TUser user);

    TUser getByAccountId(Integer accountId);

    TUser getByCertificateNumber(String certificateNumber);

    TUser getByName(String name);

    TUser getByMobile(String mobile);

    PageInfo getListByPage(int page, int perPage);

    boolean isExistCertificateNumber(String certificateNumber);
}
