package com._360pai.core.facade.activity.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 *连拍列表请求列表
 */
@Data
public class ActivityJointReq extends RequestModel{

    private String type;// 列表类型
    private String previewAtForm;
    private String previewAtTo;
    private String beginAtFrom;
    private String beginAtTo;
    private String assetName;
    private String auctionType;
    private String categoryId;
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 城市id
     */
    private Integer cityId;
    /**
     * 区县id
     */
    private Integer areaId;
    private String status;
    private String jointStatus;

    private Integer agencyId;

    private Integer isJoint;





}
