
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.AuctionActivityAlbumMapCondition;
import com._360pai.core.model.activity.AuctionActivityAlbumMap;
import com._360pai.arch.core.abs.BaseMapper;

/**
 * <p>AuctionActivityAlbumMap数据层的基础操作</p>
 * @ClassName: AuctionActivityAlbumMapMapper
 * @Description: AuctionActivityAlbumMap数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface AuctionActivityAlbumMapMapper extends BaseMapper<AuctionActivityAlbumMap, AuctionActivityAlbumMapCondition>{

    Integer getAuctionActivityCountByAlbumId(Integer id);

    int delete(AuctionActivityAlbumMap model);
}
