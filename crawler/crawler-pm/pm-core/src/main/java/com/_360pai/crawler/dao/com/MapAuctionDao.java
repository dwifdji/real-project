package com._360pai.crawler.dao.com;

import com._360pai.crawler.model.com.TMapAuction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public interface MapAuctionDao extends JpaRepository<TMapAuction, Serializable> {


    @Query(value = "select * from t_map_auction b where b.relevance_id =?1  and b.source = ?2 ", nativeQuery = true)
    List<TMapAuction> getByrelevanceId(String relevanceId,String source);




    @Query(value = "select * from t_map_auction  where pro_name   in ( '广东')   ", nativeQuery = true)
    List<TMapAuction> getLatErrorList();


    //@Query(value = "SELECT * FROM t_map_auction WHERE (area not REGEXP '(^[0-9]+.[0-9]+$)|(^[0-9]$)' or area is null) and source in ('aliSf','ali')  ", nativeQuery = true)

    @Query(value = "select * from t_map_auction where   area < 10 and source in ('ali','aliSf')  ", nativeQuery = true)
    List<TMapAuction> getAreaErrorList();

}
