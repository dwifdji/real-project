package com._360pai.core.vo.enrolling.web;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 15:00
 */
public class EnrollingDetailInfoVo implements java.io.Serializable{


    private String deposit;//
    private String status;//
    private String beginAt;//
    private List<String> images;//
    private String endAt;//
    private String id;//
    private String name;//
    private String refPrice;//
    private String browse;//
    private String remind;//
    private String code;//
    private String apply;//
    private String focus;//
    private String agencyName;//
    private Boolean applyStatus;//报名状态
    private Boolean focusStatus;//关注状态
    private Boolean remindStatus;//提醒状态
    private String extra;//图片信息
    private String cityName;//城市姓名

    private String projectName;//项目经理名称
    private String projectQq;//项目经理QQ
    private String projectPhone;//项目经理电话

    private String publishDate;//发布日期
    private String categoryId;//模板id
    private String isPackage;//是否为包


    private String source;//标的来源

    private String detail;//详情


    private String  type;

    private String cityId;//城市id
    private String provinceId;
    private String areaId;


    private String  disposalService; //处置服务商


    private String  fundProvider;//资金服务商



    private String  disposalPhone; //处置服务商电话


    private String  fundPhone;//资金服务商电话

    public String getDisposalPhone() {
        return disposalPhone;
    }

    public void setDisposalPhone(String disposalPhone) {
        this.disposalPhone = disposalPhone;
    }

    public String getFundPhone() {
        return fundPhone;
    }

    public void setFundPhone(String fundPhone) {
        this.fundPhone = fundPhone;
    }

    public String getDisposalService() {
        return disposalService;
    }

    public void setDisposalService(String disposalService) {
        this.disposalService = disposalService;
    }

    public String getFundProvider() {
        return fundProvider;
    }

    public void setFundProvider(String fundProvider) {
        this.fundProvider = fundProvider;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String userType;//委托人类型

    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getIsPackage() {
        return isPackage;
    }

    public void setIsPackage(String isPackage) {
        this.isPackage = isPackage;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectQq() {
        return projectQq;
    }

    public void setProjectQq(String projectQq) {
        this.projectQq = projectQq;
    }

    public String getProjectPhone() {
        return projectPhone;
    }

    public void setProjectPhone(String projectPhone) {
        this.projectPhone = projectPhone;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
    	if(StringUtils.isNotBlank(cityName)) {
            String[] cityNames = cityName.split(",");
            if (cityNames.length > 1) {
                this.cityName = cityNames[0] + "等多地";
            }else {

                if(cityName.contains(",")){
                    this.cityName = cityName.substring(0, cityName.length() - 1);
                }else{
                    this.cityName = cityName;
                }
            }
    	}else {
    		this.cityName = cityName;
    	}
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    private String statusDesc;//活动状态描述

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public Boolean getApplyStatus() {
		return applyStatus;
	}

	public void setApplyStatus(Boolean applyStatus) {
		this.applyStatus = applyStatus;
	}

	public Boolean getFocusStatus() {
		return focusStatus;
	}

	public void setFocusStatus(Boolean focusStatus) {
		this.focusStatus = focusStatus;
	}

	public Boolean getRemindStatus() {
		return remindStatus;
	}

	public void setRemindStatus(Boolean remindStatus) {
		this.remindStatus = remindStatus;
	}

	public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }


    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getRemind() {
        return remind;
    }

    public void setRemind(String remind) {
        this.remind = remind;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(String refPrice) {

        this.refPrice = refPrice;

    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }
}
