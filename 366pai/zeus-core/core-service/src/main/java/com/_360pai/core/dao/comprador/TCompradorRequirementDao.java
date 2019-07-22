
package com._360pai.core.dao.comprador;

import com._360pai.core.condition.comprador.TCompradorRequirementCondition;
import com._360pai.core.model.comprador.TCompradorRequirement;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TCompradorRequirement的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: TCompradorRequirementDao
 * @Description: TCompradorRequirement基础操作的Dao
 * @date 2018年09月03日 12时40分06秒
 */
public interface TCompradorRequirementDao extends BaseDao<TCompradorRequirement, TCompradorRequirementCondition> {
    /**
     * 描述 未支付的获取
     *
     * @author : whisky_vip
     * @date : 2018/9/18 13:59
     */
    TCompradorRequirement selectByIdWithoutPay(Integer id);

    List<TCompradorRequirement> selectListForPlatform(TCompradorRequirementCondition condition);

    List<TCompradorRequirement> myRequirementList(TCompradorRequirementCondition condition);
}
