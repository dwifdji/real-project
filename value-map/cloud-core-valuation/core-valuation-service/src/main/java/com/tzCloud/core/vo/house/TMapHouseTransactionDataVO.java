package com.tzCloud.core.vo.house;

import java.io.Serializable;

public class TMapHouseTransactionDataVO implements Serializable {
        /**
         * 主键
         */
        private Integer id;
        /**
         * 名称
         */
        private String title;
        /**
         * 成交时间
         */
        private String endTime;
        /**
         * 成交价格
         */
        private String currentPrice;
        /**
         * 单位价格
         */
        private String unitPrice;
        /**
         * 挂牌价格
         */
        private String listingPrice;
        /**
         * 成交周期
         */
        private String transactionCycle;
        /**
         * 调价次数
         */
        private String priceAdjustment;
        /**
         * 带看次数
         */
        private Integer lookTimes;
        /**
         * 关注人数
         */
        private Integer attentionNum;
        /**
         * 浏览人数
         */
        private String viewNum;
        /**
         * 房屋类型
         */
        private String houseType;
        /**
         * 具体层数
         */
        private String houseFloor;
        /**
         * 建筑面积
         */
        private String constructionArea;
        /**
         * 户型结构
         */
        private String houseStructure;
        /**
         * 使用面积
         */
        private String innerArea;
        /**
         * 建筑类型
         */
        private String buildingType;
        /**
         * 房子方位
         */
        private String houseOrientation;
        /**
         * 建成年代
         */
        private String builtEra;
        /**
         * 装修情况
         */
        private String renovationCondition;
        /**
         * 建筑结构
         */
        private String buildingStructure;
        /**
         * 供暖方式
         */
        private String heatingMethod;
        /**
         * 梯户比例
         */
        private String ladderRatio;
        /**
         * 产权年限
         */
        private String yearLimit;
        /**
         * 是否有电梯
         */
        private String elevatorFlag;
        /**
         * 编码
         */
        private String code;
        /**
         * 交易属性
         */
        private String tradingAuthority;
        /**
         * 挂牌时间
         */
        private String listingTime;
        /**
         * 房屋用途
         */
        private String useType;
        /**
         * 房屋年限
         */
        private String houseLimit;
        /**
         * 区域id
         */
        private Integer areaId;
        /**
         * 详细地址
         */
        private String address;
        /**
         * 经度
         */
        private String lng;
        /**
         * 纬度
         */
        private String lat;
        /**
         * 房产归属
         */
        private String houseBelong;
        /**
         * 创建时间
         */
        private java.util.Date createTime;
        /**
         * 删除标识
         */
        private Boolean deleteFlag;
        /**
         *  详情数据
         */
        private String itemUrl;

        public String getItemUrl() {
            return itemUrl;
        }

        public void setItemUrl(String itemUrl) {
            this.itemUrl = itemUrl;
        }

    /**
         * 主键
         */
        public Integer getId(){
            return id;
        }

        /**
         * 主键
         */
        public void setId(Integer id){
            this.id = id;
        }

        /**
         * 名称
         */
        public String getTitle(){
            return title;
        }

        /**
         * 名称
         */
        public void setTitle(String title){
            this.title = title;
        }

        /**
         * 成交时间
         */
        public String getEndTime(){
            return endTime;
        }

        /**
         * 成交时间
         */
        public void setEndTime(String endTime){
            this.endTime = endTime;
        }

        /**
         * 成交价格
         */
        public String getCurrentPrice(){
            return currentPrice;
        }

        /**
         * 成交价格
         */
        public void setCurrentPrice(String currentPrice){
            this.currentPrice = currentPrice;
        }

        /**
         * 单位价格
         */
        public String getUnitPrice(){
            return unitPrice;
        }

        /**
         * 单位价格
         */
        public void setUnitPrice(String unitPrice){
            this.unitPrice = unitPrice;
        }

        /**
         * 挂牌价格
         */
        public String getListingPrice(){
            return listingPrice;
        }

        /**
         * 挂牌价格
         */
        public void setListingPrice(String listingPrice){
            this.listingPrice = listingPrice;
        }

        /**
         * 成交周期
         */
        public String getTransactionCycle(){
            return transactionCycle;
        }

        /**
         * 成交周期
         */
        public void setTransactionCycle(String transactionCycle){
            this.transactionCycle = transactionCycle;
        }

        /**
         * 调价次数
         */
        public String getPriceAdjustment(){
            return priceAdjustment;
        }

        /**
         * 调价次数
         */
        public void setPriceAdjustment(String priceAdjustment){
            this.priceAdjustment = priceAdjustment;
        }

        /**
         * 带看次数
         */
        public Integer getLookTimes(){
            return lookTimes;
        }

        /**
         * 带看次数
         */
        public void setLookTimes(Integer lookTimes){
            this.lookTimes = lookTimes;
        }

        /**
         * 关注人数
         */
        public Integer getAttentionNum(){
            return attentionNum;
        }

        /**
         * 关注人数
         */
        public void setAttentionNum(Integer attentionNum){
            this.attentionNum = attentionNum;
        }

        /**
         * 浏览人数
         */
        public String getViewNum(){
            return viewNum;
        }

        /**
         * 浏览人数
         */
        public void setViewNum(String viewNum){
            this.viewNum = viewNum;
        }

        /**
         * 房屋类型
         */
        public String getHouseType(){
            return houseType;
        }

        /**
         * 房屋类型
         */
        public void setHouseType(String houseType){
            this.houseType = houseType;
        }

        /**
         * 具体层数
         */
        public String getHouseFloor(){
            return houseFloor;
        }

        /**
         * 具体层数
         */
        public void setHouseFloor(String houseFloor){
            this.houseFloor = houseFloor;
        }

        /**
         * 建筑面积
         */
        public String getConstructionArea(){
            return constructionArea;
        }

        /**
         * 建筑面积
         */
        public void setConstructionArea(String constructionArea){
            this.constructionArea = constructionArea;
        }

        /**
         * 户型结构
         */
        public String getHouseStructure(){
            return houseStructure;
        }

        /**
         * 户型结构
         */
        public void setHouseStructure(String houseStructure){
            this.houseStructure = houseStructure;
        }

        /**
         * 使用面积
         */
        public String getInnerArea(){
            return innerArea;
        }

        /**
         * 使用面积
         */
        public void setInnerArea(String innerArea){
            this.innerArea = innerArea;
        }

        /**
         * 建筑类型
         */
        public String getBuildingType(){
            return buildingType;
        }

        /**
         * 建筑类型
         */
        public void setBuildingType(String buildingType){
            this.buildingType = buildingType;
        }

        /**
         * 房子方位
         */
        public String getHouseOrientation(){
            return houseOrientation;
        }

        /**
         * 房子方位
         */
        public void setHouseOrientation(String houseOrientation){
            this.houseOrientation = houseOrientation;
        }

        /**
         * 建成年代
         */
        public String getBuiltEra(){
            return builtEra;
        }

        /**
         * 建成年代
         */
        public void setBuiltEra(String builtEra){
            this.builtEra = builtEra;
        }

        /**
         * 装修情况
         */
        public String getRenovationCondition(){
            return renovationCondition;
        }

        /**
         * 装修情况
         */
        public void setRenovationCondition(String renovationCondition){
            this.renovationCondition = renovationCondition;
        }

        /**
         * 建筑结构
         */
        public String getBuildingStructure(){
            return buildingStructure;
        }

        /**
         * 建筑结构
         */
        public void setBuildingStructure(String buildingStructure){
            this.buildingStructure = buildingStructure;
        }

        /**
         * 供暖方式
         */
        public String getHeatingMethod(){
            return heatingMethod;
        }

        /**
         * 供暖方式
         */
        public void setHeatingMethod(String heatingMethod){
            this.heatingMethod = heatingMethod;
        }

        /**
         * 梯户比例
         */
        public String getLadderRatio(){
            return ladderRatio;
        }

        /**
         * 梯户比例
         */
        public void setLadderRatio(String ladderRatio){
            this.ladderRatio = ladderRatio;
        }

        /**
         * 产权年限
         */
        public String getYearLimit(){
            return yearLimit;
        }

        /**
         * 产权年限
         */
        public void setYearLimit(String yearLimit){
            this.yearLimit = yearLimit;
        }

        /**
         * 是否有电梯
         */
        public String getElevatorFlag(){
            return elevatorFlag;
        }

        /**
         * 是否有电梯
         */
        public void setElevatorFlag(String elevatorFlag){
            this.elevatorFlag = elevatorFlag;
        }

        /**
         * 编码
         */
        public String getCode(){
            return code;
        }

        /**
         * 编码
         */
        public void setCode(String code){
            this.code = code;
        }

        /**
         * 交易属性
         */
        public String getTradingAuthority(){
            return tradingAuthority;
        }

        /**
         * 交易属性
         */
        public void setTradingAuthority(String tradingAuthority){
            this.tradingAuthority = tradingAuthority;
        }

        /**
         * 挂牌时间
         */
        public String getListingTime(){
            return listingTime;
        }

        /**
         * 挂牌时间
         */
        public void setListingTime(String listingTime){
            this.listingTime = listingTime;
        }

        /**
         * 房屋用途
         */
        public String getUseType(){
            return useType;
        }

        /**
         * 房屋用途
         */
        public void setUseType(String useType){
            this.useType = useType;
        }

        /**
         * 房屋年限
         */
        public String getHouseLimit(){
            return houseLimit;
        }

        /**
         * 房屋年限
         */
        public void setHouseLimit(String houseLimit){
            this.houseLimit = houseLimit;
        }

        /**
         * 区域id
         */
        public Integer getAreaId(){
            return areaId;
        }

        /**
         * 区域id
         */
        public void setAreaId(Integer areaId){
            this.areaId = areaId;
        }

        /**
         * 详细地址
         */
        public String getAddress(){
            return address;
        }

        /**
         * 详细地址
         */
        public void setAddress(String address){
            this.address = address;
        }

        /**
         * 经度
         */
        public String getLng(){
            return lng;
        }

        /**
         * 经度
         */
        public void setLng(String lng){
            this.lng = lng;
        }

        /**
         * 纬度
         */
        public String getLat(){
            return lat;
        }

        /**
         * 纬度
         */
        public void setLat(String lat){
            this.lat = lat;
        }

        /**
         * 房产归属
         */
        public String getHouseBelong(){
            return houseBelong;
        }

        /**
         * 房产归属
         */
        public void setHouseBelong(String houseBelong){
            this.houseBelong = houseBelong;
        }

        /**
         * 创建时间
         */
        public java.util.Date getCreateTime(){
            return createTime;
        }

        /**
         * 创建时间
         */
        public void setCreateTime(java.util.Date createTime){
            this.createTime = createTime;
        }

        /**
         * 删除标识
         */
        public Boolean getDeleteFlag(){
            return deleteFlag;
        }

        /**
         * 删除标识
         */
        public void setDeleteFlag(Boolean deleteFlag){
            this.deleteFlag = deleteFlag;
        }


}
