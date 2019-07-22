
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TAgency的基础操作Dao</p>
 * @ClassName: TAgencyDao
 * @Description: TAgency基础操作的Dao
 * @author Generator
 * @date 2018年08月17日 15时47分34秒
 */
public interface TAgencyDao extends BaseDao<TAgency,TAgencyCondition>{
    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    boolean isExistLicense(String license);

    boolean isExistCode(String code);

    TAgency createFromApply(TAgencyApplyRecord apply);

    TAgency selectById(Integer id);

    int updateById(TAgency agency);

    boolean isExistMobile(String mobile);

    TAgency getByCode(String code);

    TAgency getByLicense(String license);;

    String getName(Integer id);

    List<Map<String, Object>> getOnlineList(Map<String, Object> params);

    PageInfo<Map<String, Object>> searchOnlineList(int page, int perPage, Map<String, Object> params, String orderBy);
}
