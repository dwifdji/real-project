package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_transaction_data")
public class TTransactionData {

    /**
     * 主键
     */
    @Id
    @GeneratedValue()
    private Integer id;


    private String title;

    @Column(name = "total_assets")
    private String totalAssets;

    @Column(name = "claim_number")
    private String claimNumber;

    @Column(name = "update_time")
    private String updateTime;

    @Column(name = "bear_company")
    private String bearCompany;

    @Column(name = "transfer_company")
    private String transferCompany;

    @Column(name = "contact_people")
    private String contactPeople;

    private String phone;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "city_name")
    private String cityName;
}
