package com.tzCloud.core.vo.auction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AuctionDealVO implements Serializable {

    private String primaryKey;
    private String lat;
    private String lng;
    private String assetType;
    private String assetName;
    private String amount;
    private String staringPrice;
    private String consultPrice;
    private String averagePrice;
    private String dealDate;
    private List<String> tagList;




}
