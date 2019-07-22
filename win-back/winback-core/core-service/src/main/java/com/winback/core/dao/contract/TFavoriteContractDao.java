
package com.winback.core.dao.contract;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.contract.TFavoriteContractCondition;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TFavoriteContract;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TFavoriteContract的基础操作Dao</p>
 * @ClassName: TFavoriteContractDao
 * @Description: TFavoriteContract基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TFavoriteContractDao extends BaseDao<TFavoriteContract,TFavoriteContractCondition>{
    TFavoriteContract findBy(Integer accountId, Integer contractId);

    PageInfo<TContract> getFavoriteContractList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<Integer> getContractIdList(Integer accountId);

    void migrateFromAppletToApp(Integer extBindId, Integer accountId);

    Integer favoriteContractCount(Integer accountId);
}
