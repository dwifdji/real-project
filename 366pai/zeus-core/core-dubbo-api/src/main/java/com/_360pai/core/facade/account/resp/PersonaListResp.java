package com._360pai.core.facade.account.resp;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: PersonaListResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 01/09/2018 14:11
 */
public class PersonaListResp implements Serializable {

    private Integer personaId;
    /**
     * 客户类型（10 个人 20 公司）
     */
    private String userType = "10";
    /**
     * 用户状态 10 电话空号、20 电话无人接听、30 不愿意合作、40 已建立沟通、50 已展开合作、60 接听电话被拒绝
     */
    private String userStatus = "10";
    /**
     * 联系人姓名
     */
    private String contactName;
    /**
     * 联系人手机号
     */
    private String contactPhone;
    /**
     * 联系人微信
     */
    private String contactWechat;
    /**
     * 职务
     */
    private String title;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司负责人
     */
    private String companyDirector;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 业务区域
     */
    private List<Province> companyBusinessAreas;
    /**
     * 企业类型 10 民营 20 合资 30 国有企业 40 银行 50 个人
     */
    private String companyType = "10";
    /**
     * 录入人员
     */
    private String dataEntryStaff;
    /**
     * 客户类型（10 竞买人 20 资产方 30 处置方 40 中介方 50 联拍机构 60 配资机构）
     */
    private List<String> customerTypes;
    /**
     * 录入时间
     */
    private String createAt;

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactWechat() {
        return contactWechat;
    }

    public void setContactWechat(String contactWechat) {
        this.contactWechat = contactWechat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDirector() {
        return companyDirector;
    }

    public void setCompanyDirector(String companyDirector) {
        this.companyDirector = companyDirector;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public List<Province> getCompanyBusinessAreas() {
        return companyBusinessAreas;
    }

    public void setCompanyBusinessAreas(List<Province> companyBusinessAreas) {
        this.companyBusinessAreas = companyBusinessAreas;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getDataEntryStaff() {
        return dataEntryStaff;
    }

    public void setDataEntryStaff(String dataEntryStaff) {
        this.dataEntryStaff = dataEntryStaff;
    }

    public List<String> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(List<String> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public static class Province implements Serializable {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
