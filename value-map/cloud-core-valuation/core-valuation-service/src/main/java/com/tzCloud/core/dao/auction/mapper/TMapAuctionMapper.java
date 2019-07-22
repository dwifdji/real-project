
package com.tzCloud.core.dao.auction.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.auction.TMapAuctionCondition;
import com.tzCloud.core.model.auction.TMapAuction;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;
import java.util.Map;

/**
 * <p>TMapAuction数据层的基础操作</p>
 * @ClassName: TMapAuctionMapper
 * @Description: TMapAuction数据层的基础操作
 * @author Generator
 * @date 2019年06月14日 16时07分02秒
 */
@Mapper
public interface TMapAuctionMapper extends BaseMapper<TMapAuction, TMapAuctionCondition> {

    List<TMapAuction> getMapAuctionListByParam(Map<String, String> param);
}
