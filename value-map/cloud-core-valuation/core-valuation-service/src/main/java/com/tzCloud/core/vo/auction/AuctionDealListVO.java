package com.tzCloud.core.vo.auction;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class AuctionDealListVO implements Serializable {

    private String lat;
    private String lng;
    private String num;
    private String totalAveragePrice;
    private String assetType;
    private List<AuctionDealVO> childrenList;









}
