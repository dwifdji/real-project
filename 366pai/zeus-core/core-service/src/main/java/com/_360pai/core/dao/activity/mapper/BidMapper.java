
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.BidCondition;
import com._360pai.core.model.activity.Bid;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>Bid数据层的基础操作</p>
 * @ClassName: BidMapper
 * @Description: Bid数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface BidMapper extends BaseMapper<Bid, BidCondition>{

}
