package com._360pai.core.vo.enrolling;

import com._360pai.arch.common.utils.NumberValidationUtils;
import com._360pai.core.common.constants.EnrollingEnum;
import org.apache.commons.lang.StringUtils;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 15:00
 */
public class EnrollingActivityVo implements java.io.Serializable{

    private String agencyName;//
    private String assetPropertyName;
    private String cityName;
    private String code;
    private String createdAt;
    private String deposit;
    private String id;
    private String name;
    private String userName;
    private String refPrice;
    private String status;
    private String comName;


    private String beginAt;//提交时间
    private String contactPhone;//委托方联系方式
    private String endAt;//报名结束时间
    private String commissionPercent;//预招商成交佣金
    private String extra;//预览图片
    private String remark;//委托方备注
    private String descriptionDoc;//资产描述附件
    private String partyName;//委托方
    private String contactName;//委托方联系人
    private String contactQq;//委托方联系QQ
    private String expireAt;//预计处置完成时间
    private String visibilityLevel;//活动可见性
    private String type;//
    private String projectManager;//项目经理

    private String operateAt;//操作人
    private String operateName;//操作时间

    private String userAgencyName;//机构上拍名称
    private Integer thirdPartyStatus;//标的来源

    private String thirdPartyTitle;//第三方渠道标题
    private String thirdPartyUrl;   //第三方渠道路径
    private String thirdPartyName;  //第三方渠道名称
    private String provinceName; // 省名称

    /**
     * 分公司名称
     */
    private java.lang.String branchComName;

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public String getThirdPartyTitle() {
        return thirdPartyTitle;
    }

    public void setThirdPartyTitle(String thirdPartyTitle) {
        this.thirdPartyTitle = thirdPartyTitle;
    }

    public String getThirdPartyUrl() {
        return thirdPartyUrl;
    }

    public void setThirdPartyUrl(String thirdPartyUrl) {
        this.thirdPartyUrl = thirdPartyUrl;
    }

    public Integer getThirdPartyStatus() {
        return thirdPartyStatus;
    }

    public void setThirdPartyStatus(Integer thirdPartyStatus) {
        this.thirdPartyStatus = thirdPartyStatus;
        this.thirdPartyName = EnrollingEnum.THIRTY_PARTY_STATUS.getStatusDesc(thirdPartyStatus);
    }

    public String getUserAgencyName() {
        return userAgencyName;
    }

    public void setUserAgencyName(String userAgencyName) {
        this.userAgencyName = userAgencyName;
    }



    public String getOperateAt() {
        return operateAt;
    }

    public void setOperateAt(String operateAt) {
        this.operateAt = operateAt;
    }

    public String getOperateName() {
        return operateName;
    }

    public void setOperateName(String operateName) {
        this.operateName = operateName;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
    	if(type != null) {
    		this.type = EnrollingEnum.ENROLLING_TYPE.getDesc(type);
    	}else {
    		this.type = type;
    	}
    }

    public String getBeginAt() {
        return beginAt;
    }

    public void setBeginAt(String beginAt) {
        this.beginAt = beginAt;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDescriptionDoc() {
        return descriptionDoc;
    }

    public void setDescriptionDoc(String descriptionDoc) {
        this.descriptionDoc = descriptionDoc;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
            this.partyName = partyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactQq() {
        return contactQq;
    }

    public void setContactQq(String contactQq) {
        this.contactQq = contactQq;
    }

    public String getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(String expireAt) {
        this.expireAt = expireAt;
    }

    public String getVisibilityLevel() {
        return visibilityLevel;
    }

    public void setVisibilityLevel(String visibilityLevel) {
        this.visibilityLevel = visibilityLevel;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAssetPropertyName() {
        return assetPropertyName;
    }

    public void setAssetPropertyName(String assetPropertyName) {
        this.assetPropertyName = assetPropertyName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        if(StringUtils.isNotBlank(deposit)) {
            this.deposit = NumberValidationUtils.formatPrice(deposit);
        }else {
            this.deposit = deposit;
        }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getRefPrice() {
        return refPrice;
    }

    public void setRefPrice(String refPrice) {
        if(StringUtils.isNotBlank(refPrice)) {
            this.refPrice = NumberValidationUtils.formatPrice(refPrice);
        }else {
            this.refPrice = refPrice;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
    	if(StringUtils.isNotBlank(status)) {
            this.status = EnrollingEnum.STATUS.getDesc(status);
        }else {
            this.status = status;
        }
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getBranchComName() {
        return branchComName;
    }

    public void setBranchComName(String branchComName) {
        this.branchComName = branchComName;
    }

}
