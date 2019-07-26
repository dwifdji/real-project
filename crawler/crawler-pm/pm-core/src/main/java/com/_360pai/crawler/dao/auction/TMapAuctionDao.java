package com._360pai.crawler.dao.auction;

import com._360pai.crawler.model.auction.TMapAuction;
import com._360pai.crawler.model.jdPm.JdPmTransactionDataLegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Component
public interface TMapAuctionDao extends JpaRepository<TMapAuction, Serializable> {

    @Query(value = "select * from t_map_auction b where b.relevance_id=?1 and b.source = ?2", nativeQuery = true)
    TMapAuction getOneByCode(String code, String type);


}
