package com._360pai.core.facade.account.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Data
public class AccountReq implements Serializable {

    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 密码
     */
    private java.lang.String password;
    /**
     * 注册来源
     */
    private java.lang.String registerSource;
    /**
     * 渠道来源
     */
    private java.lang.String source;
    /**
     * 状态(0:无效,1:有效)
     */
    private java.lang.Integer status;

    private java.lang.Integer currentPartyId;

    /**
     * 默认送拍机构
     */
    private java.lang.Integer defaultAgencyId;
    /**
     * 从属于某个机构的管理员
     */
    private java.lang.Integer agencyId;
    /**
     * 是否是机构的申请者
     */
    private java.lang.Integer isAgencyAdmin;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 更新时间
     */
    private java.util.Date updateTime;

    private boolean defaultPassword;

    public Integer getCurrentPartyId() {
        return currentPartyId;
    }

    public void setCurrentPartyId(Integer currentPartyId) {
        this.currentPartyId = currentPartyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDefaultAgencyId() {
        return defaultAgencyId;
    }

    public void setDefaultAgencyId(Integer defaultAgencyId) {
        this.defaultAgencyId = defaultAgencyId;
    }

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public Integer getIsAgencyAdmin() {
        return isAgencyAdmin;
    }

    public void setIsAgencyAdmin(Integer isAgencyAdmin) {
        this.isAgencyAdmin = isAgencyAdmin;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDefaultPassword() {
        return defaultPassword;
    }

    public void setDefaultPassword(boolean defaultPassword) {
        this.defaultPassword = defaultPassword;
    }

    @Getter
    @Setter
    public static class BaseReq extends RequestModel {
        private Integer id;

        private Integer agencyId;

        private Integer accountId;

        private Integer operatorId;

        private Integer partyId;

        private String mobile;

        private String prefix;
    }

    @Getter
    @Setter
    public static class QueryReq extends RequestModel {
        private String q;
        private String status;
        private String registerSource;
        private Integer companyId;
        private String mobile;
        private Integer agencyId;
        private String applySource;

        private Integer provinceId;
        private Integer cityId;
        private Integer areaId;
    }

    @Getter
    @Setter
    public static class OpenElectronicSignatureReq extends RequestModel {
        private String name;
        private String mobile;
        // 客户类型 1 个人 2 公司
        private String customerType;
        private String idOrLicenceNo;
    }

    @Getter
    @Setter
    public static class LoginReq extends RequestModel {
        private String code;
        private String nickName;
        private String headImgUrl;
    }

    @Getter
    @Setter
    public static class BindAccountReq extends RequestModel {
        private Integer id;
        private Integer accountId;
        private Integer inviteCode;
        private String inviteType;
        private Boolean isNewRegister;
    }

    @Getter
    @Setter
    public static class SelectPartyReq extends RequestModel {
        private Integer id;
        private Integer partyId;
    }
}
