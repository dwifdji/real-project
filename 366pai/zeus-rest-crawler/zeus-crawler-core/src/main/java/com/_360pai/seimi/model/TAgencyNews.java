package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuhaolei
 * @since 2018-11-13
 */
@Data
@Entity
@Table(name = "t_agency_news")
public class TAgencyNews {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "news_url")
    private String newsUrl;
    @Column(name = "release_date")
    private String releaseDate;
    @Column(name = "news_title")
    private String newsTitle;
    @Column(name = "news_net")
    private String newsNet;
    @Column(name = "agency_id")
    private String agencyId;

}
