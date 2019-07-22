package com._360pai.seimi.dao;

import com._360pai.seimi.model.Caa123Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-14 17:10
 */
@Repository
public interface Caa123PmDao extends JpaRepository<Caa123Auction, Serializable> {

    @Modifying
    @Transactional
    @Query(value="INSERT INTO caa123_auction(`id`,  `start_time`,  `end_time`,  `name`,  `delay_time`,  `start_price`,  " +
            "`assess_price`,  `now_price`,  `rate_ladder`,  `cash_deposit`,  `court`,  `link_man`,  `link_tel`,  `link_mobile`,  " +
            "`position`,  `pic_large`,  `pic_small`,  `city_id`) " +
            "VALUES (:#{#auction.id}, :#{#auction.startTime}, :#{#auction.endTime}, :#{#auction.name}, :#{#auction.delayTime}, :#{#auction.startPrice}, " +
            ":#{#auction.assessPrice}, :#{#auction.nowPrice}, :#{#auction.rateLadder}, :#{#auction.cashDeposit}, :#{#auction.court}, :#{#auction.linkMan}, " +
            ":#{#auction.linkTel}, :#{#auction.linkMobile}, :#{#auction.position}, :#{#auction.picLarge}, :#{#auction.picSmall}, :#{#auction.cityId}) " +
    "ON DUPLICATE KEY UPDATE start_time = :#{#auction.startTime} ,end_time = :#{#auction.endTime}, name = :#{#auction.name}, delay_time = :#{#auction.delayTime}, start_price = :#{#auction.startPrice} " +
            ", assess_price = :#{#auction.assessPrice}, now_price = :#{#auction.nowPrice}, rate_ladder = :#{#auction.rateLadder}, cash_deposit = :#{#auction.cashDeposit}, court = :#{#auction.court}" +
            " , link_man = :#{#auction.linkMan} , link_tel = :#{#auction.linkTel}, link_mobile = :#{#auction.linkMobile}, position = :#{#auction.position}" +
            ", pic_large = :#{#auction.picLarge}, pic_small = :#{#auction.picSmall}, city_id = :#{#auction.cityId},update_time = now();" , nativeQuery = true )
    int insertOrUpdateAuction(@Param("auction") Caa123Auction caa123Auction);
}
