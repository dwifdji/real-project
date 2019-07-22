
package com.tzCloud.core.condition.auction;


import com.tzCloud.arch.core.abs.DaoCondition;

/**
 * <p>用于封装查询条件</p>
 * <p>默认条件下仅生成数据表字段的查询条件，更多条件，请自行添加</p>
 * @author Generator
 * @date 2019年06月14日 16时07分02秒
 */
public class TMapAuctionCondition implements DaoCondition {

	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 数据来源
	 */
	private String source;
	/**
	 * 关联id
	 */
	private Integer relevanceId;
	/**
	 * 商品链接
	 */
	private String itemUrl;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 起拍价
	 */
	private String startingPrice;
	/**
	 * 当前价
	 */
	private String currentPrice;
	/**
	 * 评估价
	 */
	private String consultPrice;
	/**
	 * 成交价
	 */
	private String amount;
	/**
	 * 开拍时间
	 */
	private String startDate;
	/**
	 * 结束时间
	 */
	private String endDate;
	/**
	 * 省份
	 */
	private String proName;
	/**
	 * 城市
	 */
	private String cityName;
	/**
	 * 地区名称
	 */
	private String areaName;
	/**
	 * 标的物类型
	 */
	private String typeName;
	/**
	 * 类型code
	 */
	private String typeCode;
	/**
	 * 加价幅度
	 */
	private String raisePrice;
	/**
	 * 保证金
	 */
	private String deposit;
	/**
	 * 资产状态
	 */
	private String status;
	/**
	 * 竞价人数
	 */
	private String bidNum;
	/**
	 * 市场参考价
	 */
	private String marketPrice;
	/**
	 * 报名人数
	 */
	private String appler;
	/**
	 * 提醒人数
	 */
	private String reminder;
	/**
	 * 详细地址
	 */
	private String addressDetail;
	/**
	 * 纬度值
	 */
	private java.math.BigDecimal lat;
	/**
	 * 经度值
	 */
	private java.math.BigDecimal lng;
	/**
	 * 建筑面积
	 */
	private String area;
	/**
	 * 土地使用面积
	 */
	private String landArea;
	/**
	 * 浏览人数
	 */
	private String looker;
	/**
	 * 出让类型
	 */
	private String sellType;
	/**
	 * 拍卖阶段
	 */
	private String stage;
	/**
	 * 删除标志
	 */
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;
	
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
	 * 数据来源
	 */
	public String getSource(){
		return source;
	}
	
	/**
	 * 数据来源
	 */
	public void setSource(String source){
		this.source = source;
	}
	
	/**
	 * 关联id
	 */
	public Integer getRelevanceId(){
		return relevanceId;
	}
	
	/**
	 * 关联id
	 */
	public void setRelevanceId(Integer relevanceId){
		this.relevanceId = relevanceId;
	}
	
	/**
	 * 商品链接
	 */
	public String getItemUrl(){
		return itemUrl;
	}
	
	/**
	 * 商品链接
	 */
	public void setItemUrl(String itemUrl){
		this.itemUrl = itemUrl;
	}
	
	/**
	 * 标题
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * 标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * 起拍价
	 */
	public String getStartingPrice(){
		return startingPrice;
	}
	
	/**
	 * 起拍价
	 */
	public void setStartingPrice(String startingPrice){
		this.startingPrice = startingPrice;
	}
	
	/**
	 * 当前价
	 */
	public String getCurrentPrice(){
		return currentPrice;
	}
	
	/**
	 * 当前价
	 */
	public void setCurrentPrice(String currentPrice){
		this.currentPrice = currentPrice;
	}
	
	/**
	 * 评估价
	 */
	public String getConsultPrice(){
		return consultPrice;
	}
	
	/**
	 * 评估价
	 */
	public void setConsultPrice(String consultPrice){
		this.consultPrice = consultPrice;
	}
	
	/**
	 * 成交价
	 */
	public String getAmount(){
		return amount;
	}
	
	/**
	 * 成交价
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}
	
	/**
	 * 开拍时间
	 */
	public String getStartDate(){
		return startDate;
	}
	
	/**
	 * 开拍时间
	 */
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	
	/**
	 * 结束时间
	 */
	public String getEndDate(){
		return endDate;
	}
	
	/**
	 * 结束时间
	 */
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	
	/**
	 * 省份
	 */
	public String getProName(){
		return proName;
	}
	
	/**
	 * 省份
	 */
	public void setProName(String proName){
		this.proName = proName;
	}
	
	/**
	 * 城市
	 */
	public String getCityName(){
		return cityName;
	}
	
	/**
	 * 城市
	 */
	public void setCityName(String cityName){
		this.cityName = cityName;
	}
	
	/**
	 * 地区名称
	 */
	public String getAreaName(){
		return areaName;
	}
	
	/**
	 * 地区名称
	 */
	public void setAreaName(String areaName){
		this.areaName = areaName;
	}
	
	/**
	 * 标的物类型
	 */
	public String getTypeName(){
		return typeName;
	}
	
	/**
	 * 标的物类型
	 */
	public void setTypeName(String typeName){
		this.typeName = typeName;
	}
	
	/**
	 * 类型code
	 */
	public String getTypeCode(){
		return typeCode;
	}
	
	/**
	 * 类型code
	 */
	public void setTypeCode(String typeCode){
		this.typeCode = typeCode;
	}
	
	/**
	 * 加价幅度
	 */
	public String getRaisePrice(){
		return raisePrice;
	}
	
	/**
	 * 加价幅度
	 */
	public void setRaisePrice(String raisePrice){
		this.raisePrice = raisePrice;
	}
	
	/**
	 * 保证金
	 */
	public String getDeposit(){
		return deposit;
	}
	
	/**
	 * 保证金
	 */
	public void setDeposit(String deposit){
		this.deposit = deposit;
	}
	
	/**
	 * 资产状态
	 */
	public String getStatus(){
		return status;
	}
	
	/**
	 * 资产状态
	 */
	public void setStatus(String status){
		this.status = status;
	}
	
	/**
	 * 竞价人数
	 */
	public String getBidNum(){
		return bidNum;
	}
	
	/**
	 * 竞价人数
	 */
	public void setBidNum(String bidNum){
		this.bidNum = bidNum;
	}
	
	/**
	 * 市场参考价
	 */
	public String getMarketPrice(){
		return marketPrice;
	}
	
	/**
	 * 市场参考价
	 */
	public void setMarketPrice(String marketPrice){
		this.marketPrice = marketPrice;
	}
	
	/**
	 * 报名人数
	 */
	public String getAppler(){
		return appler;
	}
	
	/**
	 * 报名人数
	 */
	public void setAppler(String appler){
		this.appler = appler;
	}
	
	/**
	 * 提醒人数
	 */
	public String getReminder(){
		return reminder;
	}
	
	/**
	 * 提醒人数
	 */
	public void setReminder(String reminder){
		this.reminder = reminder;
	}
	
	/**
	 * 详细地址
	 */
	public String getAddressDetail(){
		return addressDetail;
	}
	
	/**
	 * 详细地址
	 */
	public void setAddressDetail(String addressDetail){
		this.addressDetail = addressDetail;
	}
	
	/**
	 * 纬度值
	 */
	public java.math.BigDecimal getLat(){
		return lat;
	}
	
	/**
	 * 纬度值
	 */
	public void setLat(java.math.BigDecimal lat){
		this.lat = lat;
	}
	
	/**
	 * 经度值
	 */
	public java.math.BigDecimal getLng(){
		return lng;
	}
	
	/**
	 * 经度值
	 */
	public void setLng(java.math.BigDecimal lng){
		this.lng = lng;
	}
	
	/**
	 * 建筑面积
	 */
	public String getArea(){
		return area;
	}
	
	/**
	 * 建筑面积
	 */
	public void setArea(String area){
		this.area = area;
	}
	
	/**
	 * 土地使用面积
	 */
	public String getLandArea(){
		return landArea;
	}
	
	/**
	 * 土地使用面积
	 */
	public void setLandArea(String landArea){
		this.landArea = landArea;
	}
	
	/**
	 * 浏览人数
	 */
	public String getLooker(){
		return looker;
	}
	
	/**
	 * 浏览人数
	 */
	public void setLooker(String looker){
		this.looker = looker;
	}
	
	/**
	 * 出让类型
	 */
	public String getSellType(){
		return sellType;
	}
	
	/**
	 * 出让类型
	 */
	public void setSellType(String sellType){
		this.sellType = sellType;
	}
	
	/**
	 * 拍卖阶段
	 */
	public String getStage(){
		return stage;
	}
	
	/**
	 * 拍卖阶段
	 */
	public void setStage(String stage){
		this.stage = stage;
	}
	
	/**
	 * 删除标志
	 */
	public Integer getDeleteFlag(){
		return deleteFlag;
	}
	
	/**
	 * 删除标志
	 */
	public void setDeleteFlag(Integer deleteFlag){
		this.deleteFlag = deleteFlag;
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
	 * 更新时间
	 */
	public java.util.Date getUpdateTime(){
		return updateTime;
	}
	
	/**
	 * 更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}

}