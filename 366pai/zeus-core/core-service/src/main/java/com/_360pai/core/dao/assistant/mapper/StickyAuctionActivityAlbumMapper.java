
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.StickyAuctionActivityAlbumCondition;
import com._360pai.core.model.assistant.StickyAuctionActivityAlbum;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>StickyAuctionActivityAlbum数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: StickyAuctionActivityAlbumMapper
 * @Description: StickyAuctionActivityAlbum数据层的基础操作
 * @date 2018年08月10日 17时37分17秒
 */
@Mapper
public interface StickyAuctionActivityAlbumMapper extends BaseMapper<StickyAuctionActivityAlbum, StickyAuctionActivityAlbumCondition> {

    int deleteById(Integer id);
    List<StickyAuctionActivityAlbum> selectStickyAlbumList();
}
