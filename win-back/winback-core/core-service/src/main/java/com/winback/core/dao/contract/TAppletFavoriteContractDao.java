
package com.winback.core.dao.contract;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.contract.TAppletFavoriteContractCondition;
import com.winback.core.model.contract.TAppletFavoriteContract;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.model.contract.TContract;
import com.winback.core.model.contract.TFavoriteContract;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletFavoriteContract的基础操作Dao</p>
 * @ClassName: TAppletFavoriteContractDao
 * @Description: TAppletFavoriteContract基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
public interface TAppletFavoriteContractDao extends BaseDao<TAppletFavoriteContract,TAppletFavoriteContractCondition>{
    TAppletFavoriteContract findBy(Integer extBindId, Integer contractId);

    PageInfo<TContract> getFavoriteContractList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<Integer> getContractIdList(Integer extBindId);

    Integer favoriteContractCount(Integer extBindId);
}
