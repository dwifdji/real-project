
package com._360pai.core.dao.activity;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.activity.AuctionActivityAlbumCondition;
import com._360pai.core.model.activity.AuctionActivityAlbum;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionActivityAlbum的基础操作Dao</p>
 * @ClassName: AuctionActivityAlbumDao
 * @Description: AuctionActivityAlbum基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AuctionActivityAlbumDao extends BaseDao<AuctionActivityAlbum,AuctionActivityAlbumCondition>{

    PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    List<AuctionActivityAlbum> getStickyList();

    PageInfo getRelatedActivityList(int page, int perPage, Integer albumId);

    PageInfo getFrontRelatedActivityList(int page, int perPage, Integer albumId);

}
