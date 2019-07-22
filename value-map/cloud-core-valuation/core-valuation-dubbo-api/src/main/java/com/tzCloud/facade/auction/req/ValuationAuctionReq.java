package com.tzCloud.facade.auction.req;


import com.tzCloud.arch.common.RequestModel;
import com.tzCloud.arch.common.RequestNotFilterModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : wcq
 * @date : 2019/6/13 18:34
 */
public class ValuationAuctionReq extends RequestNotFilterModel {

    @Getter
    @Setter
    public static class comReq extends RequestNotFilterModel {

        private String id;//主键id
        private String lat;//纬度值
        private String lng;//经度值
        private String assetType;//1：住宅用房 2：商业用房 3：工业用房 4：其他用房 5：土地
        private String province;//省份
        private String city;//城市
        private String area;//地区
        private String reqType;//地区
        private String radius;//距离中心点的距离
        private String searchKey;//搜索关键字

        private String sixMonFlag;// 获取六个月标志





    }


}
