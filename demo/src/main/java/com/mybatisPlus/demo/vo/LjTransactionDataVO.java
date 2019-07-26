package com.mybatisPlus.demo.vo;

public class LjTransactionDataVO {
	
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
    
    private String createTime;
    
    
    
    public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String area;
    
    /**
     * 房产归属
     */
    private String houseBelong;
    
    
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(String currentPrice) {
		this.currentPrice = currentPrice;
	}
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getListingPrice() {
		return listingPrice;
	}
	public void setListingPrice(String listingPrice) {
		this.listingPrice = listingPrice;
	}
	public String getTransactionCycle() {
		return transactionCycle;
	}
	public void setTransactionCycle(String transactionCycle) {
		this.transactionCycle = transactionCycle;
	}
	public String getPriceAdjustment() {
		return priceAdjustment;
	}
	public void setPriceAdjustment(String priceAdjustment) {
		this.priceAdjustment = priceAdjustment;
	}
	public Integer getLookTimes() {
		return lookTimes;
	}
	public void setLookTimes(Integer lookTimes) {
		this.lookTimes = lookTimes;
	}
	public Integer getAttentionNum() {
		return attentionNum;
	}
	public void setAttentionNum(Integer attentionNum) {
		this.attentionNum = attentionNum;
	}
	public String getViewNum() {
		return viewNum;
	}
	public void setViewNum(String viewNum) {
		this.viewNum = viewNum;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getHouseFloor() {
		return houseFloor;
	}
	public void setHouseFloor(String houseFloor) {
		this.houseFloor = houseFloor;
	}
	public String getConstructionArea() {
		return constructionArea;
	}
	public void setConstructionArea(String constructionArea) {
		this.constructionArea = constructionArea;
	}
	public String getHouseStructure() {
		return houseStructure;
	}
	public void setHouseStructure(String houseStructure) {
		this.houseStructure = houseStructure;
	}
	public String getInnerArea() {
		return innerArea;
	}
	public void setInnerArea(String innerArea) {
		this.innerArea = innerArea;
	}
	public String getBuildingType() {
		return buildingType;
	}
	public void setBuildingType(String buildingType) {
		this.buildingType = buildingType;
	}
	public String getHouseOrientation() {
		return houseOrientation;
	}
	public void setHouseOrientation(String houseOrientation) {
		this.houseOrientation = houseOrientation;
	}
	public String getBuiltEra() {
		return builtEra;
	}
	public void setBuiltEra(String builtEra) {
		this.builtEra = builtEra;
	}
	public String getRenovationCondition() {
		return renovationCondition;
	}
	public void setRenovationCondition(String renovationCondition) {
		this.renovationCondition = renovationCondition;
	}
	public String getBuildingStructure() {
		return buildingStructure;
	}
	public void setBuildingStructure(String buildingStructure) {
		this.buildingStructure = buildingStructure;
	}
	public String getHeatingMethod() {
		return heatingMethod;
	}
	public void setHeatingMethod(String heatingMethod) {
		this.heatingMethod = heatingMethod;
	}
	public String getLadderRatio() {
		return ladderRatio;
	}
	public void setLadderRatio(String ladderRatio) {
		this.ladderRatio = ladderRatio;
	}
	public String getYearLimit() {
		return yearLimit;
	}
	public void setYearLimit(String yearLimit) {
		this.yearLimit = yearLimit;
	}
	public String getElevatorFlag() {
		return elevatorFlag;
	}
	public void setElevatorFlag(String elevatorFlag) {
		this.elevatorFlag = elevatorFlag;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTradingAuthority() {
		return tradingAuthority;
	}
	public void setTradingAuthority(String tradingAuthority) {
		this.tradingAuthority = tradingAuthority;
	}
	public String getListingTime() {
		return listingTime;
	}
	public void setListingTime(String listingTime) {
		this.listingTime = listingTime;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getHouseLimit() {
		return houseLimit;
	}
	public void setHouseLimit(String houseLimit) {
		this.houseLimit = houseLimit;
	}
	public Integer getAreaId() {
		return areaId;
	}
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getHouseBelong() {
		return houseBelong;
	}
	public void setHouseBelong(String houseBelong) {
		this.houseBelong = houseBelong;
	}
    
    

}
