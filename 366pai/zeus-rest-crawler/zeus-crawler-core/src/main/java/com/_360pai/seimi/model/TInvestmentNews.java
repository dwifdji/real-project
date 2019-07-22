package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investment_agency_news")
public class TInvestmentNews {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "news_title")
    private String newsTitle;
    @Column(name = "news_net")
    private String newsNet;
    @Column(name = "news_date")
    private String newsDate;
    @Column(name = "investment_id")
    private String investmentId;
    @Column(name = "news_url")
    private String newsUrl;


}
