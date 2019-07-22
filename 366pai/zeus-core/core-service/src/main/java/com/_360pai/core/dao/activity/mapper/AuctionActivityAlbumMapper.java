
package com._360pai.core.dao.activity.mapper;

import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.activity.AuctionActivity;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.AuctionActivityAlbumCondition;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionActivityAlbum数据层的基础操作</p>
 * @ClassName: AuctionActivityAlbumMapper
 * @Description: AuctionActivityAlbum数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface AuctionActivityAlbumMapper extends BaseMapper<AuctionActivityAlbum, AuctionActivityAlbumCondition>{
    List<AuctionActivityAlbum> getList(Map<String, Object> params);

    List<AuctionActivityAlbum> getStickyList();

    List<AuctionActivity> getRelatedActivityList(@Param("albumId") Integer albumId);

    List<Map> getFrontRelatedActivityList(@Param("albumId") Integer albumId);
}
