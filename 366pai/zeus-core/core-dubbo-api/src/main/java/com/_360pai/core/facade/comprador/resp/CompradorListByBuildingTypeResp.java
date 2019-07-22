package com._360pai.core.facade.comprador.resp;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/1 10:40
 */
public class CompradorListByBuildingTypeResp implements Serializable {

    private String                   buildingType;
    private List<ListByBuildingType> list;

    public String getBuildingType() {
        return buildingType;
    }

    public void setBuildingType(String buildingType) {
        this.buildingType = buildingType;
    }

    public List<ListByBuildingType> getList() {
        return list;
    }

    public void setList(List<ListByBuildingType> list) {
        this.list = list;
    }

    @Data
    public static class ListByBuildingType implements Serializable {
        /**
         * 数据id
         */
        private Integer              id;
        /**
         * 价格区间 开始价格
         */
        private String startPrice;
        /**
         * 价格区间 结束价格
         */
        private String endPrice;
        /**
         * 面积区间 开始面积
         */
        private String startArea;
        /**
         * 面积区间 结束面积
         */
        private String endArea;
        /**
         * 区域
         */
        private Integer              cityId;

        /**
         * 佣金 %
         */
        private java.math.BigDecimal commissionPercent;



    }

}

