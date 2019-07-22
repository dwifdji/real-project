
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.account.TLawFirmCondition;
import com.winback.core.model.account.TLawFirm;

import java.util.Map;

/**
 * <p>TLawFirm的基础操作Dao</p>
 * @ClassName: TLawFirmDao
 * @Description: TLawFirm基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
public interface TLawFirmDao extends BaseDao<TLawFirm,TLawFirmCondition>{
    PageInfo<TLawFirm> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isExistName(String name);

    boolean isExistLicenseNumber(String licenseNumber);
}
