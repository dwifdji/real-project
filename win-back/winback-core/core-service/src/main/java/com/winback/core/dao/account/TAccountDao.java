
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.account.TAccountCondition;
import com.winback.core.facade.account.vo.Party;
import com.winback.core.model.account.TAccount;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.finance.WorkBenchVO;

import java.util.List;
import java.util.Map;

/**
 * <p>TAccount的基础操作Dao</p>
 * @ClassName: TAccountDao
 * @Description: TAccount基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分01秒
 */
public interface TAccountDao extends BaseDao<TAccount,TAccountCondition>{
    TAccount findByMobile(String mobile);

    PageInfo<TAccount> getList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<Party> getPartyList(int page, int perPage, Map<String, Object> params, String orderBy);

    String getMobile(Integer accountId);

    List<WorkBenchVO> getTodayRole(String searchDay);

}
