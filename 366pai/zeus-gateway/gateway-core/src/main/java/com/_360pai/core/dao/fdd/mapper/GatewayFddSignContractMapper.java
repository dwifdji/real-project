
package com._360pai.core.dao.fdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.fdd.GatewayFddSignContractCondition;
import com._360pai.core.model.fdd.GatewayFddSignContract;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>GatewayFddSignContract数据层的基础操作</p>
 * @ClassName: GatewayFddSignContractMapper
 * @Description: GatewayFddSignContract数据层的基础操作
 * @author Generator
 * @date 2018年09月02日 11时38分38秒
 */
@Mapper
public interface GatewayFddSignContractMapper extends BaseMapper<GatewayFddSignContract, GatewayFddSignContractCondition>{

    List<GatewayFddSignContract> queryNotSignConTract();

}
