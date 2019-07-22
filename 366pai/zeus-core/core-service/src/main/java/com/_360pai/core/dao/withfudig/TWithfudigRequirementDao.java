
package com._360pai.core.dao.withfudig;

import com._360pai.core.condition.withfudig.TWithfudigRequirementCondition;
import com._360pai.core.model.withfudig.TWithfudigRequirement;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>TWithfudigRequirement的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: TWithfudigRequirementDao
 * @Description: TWithfudigRequirement基础操作的Dao
 * @date 2018年09月06日 15时50分14秒
 */
public interface TWithfudigRequirementDao extends BaseDao<TWithfudigRequirement, TWithfudigRequirementCondition> {
    /**
     * 描述 根据id获取未支付的需求单
     *
     * @author : whisky_vip
     * @date : 2018/9/18 13:58
     */
    TWithfudigRequirement selectByIdWithoutPay(Integer id);

    PageInfo selectListForPlatform(int page, int perPage, TWithfudigRequirementCondition condition);

    List<TWithfudigRequirement> selectFollowListForPlatform(TWithfudigRequirementCondition condition);

    List<TWithfudigRequirement> myRequirementList(TWithfudigRequirementCondition condition);
}
