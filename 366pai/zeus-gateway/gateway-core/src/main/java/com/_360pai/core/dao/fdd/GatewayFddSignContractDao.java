
package com._360pai.core.dao.fdd;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.fdd.GatewayFddSignContractCondition;
import com._360pai.core.model.fdd.GatewayFddSignContract;

import java.util.List;

/**
 * <p>GatewayFddSignContract的基础操作Dao</p>
 * @ClassName: GatewayFddSignContractDao
 * @Description: GatewayFddSignContract基础操作的Dao
 * @author Generator
 * @date 2018年09月02日 11时38分38秒
 */
public interface GatewayFddSignContractDao extends BaseDao<GatewayFddSignContract,GatewayFddSignContractCondition>{


    List<GatewayFddSignContract> queryNotSignConTract();

}
