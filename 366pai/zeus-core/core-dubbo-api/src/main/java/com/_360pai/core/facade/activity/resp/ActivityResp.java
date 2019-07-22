package com._360pai.core.facade.activity.resp;

import com._360pai.arch.common.BaseResp;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.FileInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: ActivityResp
 * @ProjectName zeus
 * @Description:
 * @date 06/09/2018 10:39
 */
public class ActivityResp implements Serializable {

    private Integer activityId;

    AuctionActivityVo activity;

    private String url;

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public AuctionActivityVo getActivity() {
        return activity;
    }

    public void setActivity(AuctionActivityVo activity) {
        this.activity = activity;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Getter
    @Setter
    public static class DelegationAgreement extends BaseResp {
        private FileInfo agreement;
    }

   @Data
    public static class ShareInfoResp extends BaseResp {
        private String imageUrl;
       private String amount;
       private String amountDesc;
       private String category;
       private String name;
    }
}
