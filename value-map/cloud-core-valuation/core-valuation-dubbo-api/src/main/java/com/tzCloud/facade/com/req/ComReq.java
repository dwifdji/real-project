package com.tzCloud.facade.com.req;

import com.tzCloud.arch.common.RequestModel;
import com.tzCloud.arch.common.RequestNotFilterModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author : wcq
 * @date : 2019/6/13 18:34
 */
public class ComReq extends RequestNotFilterModel {

    @Getter
    @Setter
    public static class comReq extends RequestNotFilterModel {



        private String id;//主键id
        private String lat;//纬度值
        private String lng;//经度值
        private String searchKey;//搜索关键字
        private String ip;//ip地址
        private String city;//城市名称
        private String factorName;//分类名称

        private String primaryKey;//详情主键

        private String type;//类型


        private String radius;//距离中心点的距离

    }


}
