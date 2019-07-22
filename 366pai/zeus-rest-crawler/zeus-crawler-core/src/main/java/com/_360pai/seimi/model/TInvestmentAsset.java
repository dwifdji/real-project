package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "t_investment_agency_asset")
public class TInvestmentAsset {

    @Id
    @GeneratedValue()
    private Integer id;

    @Column(name = "asset_date")
    private String assetDate;
    @Column(name = "asset_state")
    private String assetState;
    @Column(name = "detail_doc")
    private String detailDoc;
    @Column(name = "investment_id")
    private String investmentId;
    @Column(name = "asset_name")
    private String assetName;

}
