package com._360pai.core.vo.enrolling;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
public class EnrollingProjectInfoVo implements Serializable {

    private String projectName;//项目经理名称


    private String projectQq;//项目经理QQ


    private String projectPhone;//项目经理电话


    private String disposalService;// 处置服务商

    private Integer disposeProviderId;// 处置服务商id

    private String fundProvider;//资金服务商

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

    public Integer getDisposeProviderId() {
        return disposeProviderId;
    }

    public void setDisposeProviderId(Integer disposeProviderId) {
        this.disposeProviderId = disposeProviderId;
    }
}
