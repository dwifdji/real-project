
package com.winback.core.dao.contract;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.contract.TContractCondition;
import com.winback.core.model.account.TLawyer;
import com.winback.core.model.contract.TContract;
import com.winback.arch.core.abs.BaseDao;

import java.util.Map;

/**
 * <p>TContract的基础操作Dao</p>
 * @ClassName: TContractDao
 * @Description: TContract基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractDao extends BaseDao<TContract,TContractCondition>{
    PageInfo<TContract> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TContract> getPossessedContractList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo<TContract> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy);

    int addDownloadCount(Integer id);

    int addFavoriteCount(Integer id);

    int subFavoriteCount(Integer id);

    int addViewCount(Integer id);

    int addPurchaseCount(Integer id);

    boolean isExists(String name);

    TContract findBy(String name, Integer contractTypeId);

    PageInfo<TContract> getWrongList(int page, int perPage, Map<String, Object> params, String orderBy);
}
