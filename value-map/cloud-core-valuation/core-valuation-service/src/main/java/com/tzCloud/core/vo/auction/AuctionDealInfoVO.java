package com.tzCloud.core.vo.auction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AuctionDealInfoVO implements Serializable {

    private String primaryKey;
    private String lat;
    private String lng;
    private String name;
    private String address;
    private String buildingArea;
    private String landArea;
    private String staringDate;
    private String endDate;
    private String buildingType;
    private String sellType;
    private String assetName;
    private String amount;
    private String staringPrice;
    private String consultPrice;
    private String averagePrice;
    private String premium;
    private String raisePrice;
    private String deposit;
    private String dealDate;
    private String stage;
    private String webUrl;

    private String status;






}
