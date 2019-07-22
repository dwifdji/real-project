
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com._360pai.core.model.assistant.StickyAuctionActivityAlbum;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>StickyAuctionActivityAlbum的基础操作Dao</p>
 * @ClassName: StickyAuctionActivityAlbumDao
 * @Description: StickyAuctionActivityAlbum基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
public interface StickyAuctionActivityAlbumDao extends BaseDao<StickyAuctionActivityAlbum,StickyAuctionActivityAlbumCondition>{

    int deleteById(Integer id);
    List<StickyAuctionActivityAlbum> selectStickyAlbumList();
}
