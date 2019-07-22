package com._360pai.core.vo.lease;

import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.LeaseEnum;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： 刘好磊
 * 版本： 1.0.0
 * 时间： 2018/8/20 17:00
 */
public class LeaseApplyRecordVo implements Serializable {


    private String imageUrl;

    private String  activityId;

    private String applyBeginTime;//

    private String applyEndTime;//

    private String name;//

    private String applyStatus;

    private String applyStatusDesc;

    private String fileUrl;

    private String deposit;

    private String mode;

    private String modeDesc;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(String applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
        this.applyStatusDesc = LeaseEnum.ApplyStatus.getSecondDescByKey(applyStatus);
    }

    public String getApplyStatusDesc() {
        return applyStatusDesc;
    }


    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDeposit() {
        return deposit;
    }

    public void setDeposit(String deposit) {
        this.deposit = deposit;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.modeDesc = AssetEnum.ExpectedMode.getKeyByValue(mode);
    }

    public String getModeDesc() {
        return modeDesc;
    }

}
