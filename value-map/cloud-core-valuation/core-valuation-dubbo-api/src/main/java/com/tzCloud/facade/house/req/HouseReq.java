package com.tzCloud.facade.house.req;

import com.tzCloud.arch.common.RequestModel;
import com.tzCloud.arch.common.RequestNotFilterModel;
import lombok.Getter;
import lombok.Setter;

public class HouseReq {

    @Setter
    @Getter
    public static class HouseListReq extends RequestNotFilterModel {
        private String id;

        private String assetType;

        private String lat;

        private String lng;

        private String radius;

        private String keyWord;

        private String sixMonFlag;
    }


    @Setter
    @Getter
    public static class HousePriceTrendReq extends RequestNotFilterModel {
        private String province;

        private String city;

        private String area;

        private String lng;

        private String year;
    }
}
