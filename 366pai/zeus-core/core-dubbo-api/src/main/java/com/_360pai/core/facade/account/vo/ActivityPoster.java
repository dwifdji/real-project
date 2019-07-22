package com._360pai.core.facade.account.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: ActivityPoster
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/7/1 14:11
 */
@Data
public class ActivityPoster implements Serializable {
    private Integer id;
    /**
     * 推广位名称
     */
    private String name;
    /**
     * 开始时间
     */
    private java.util.Date beginAt;
    /**
     * 结束时间
     */
    private java.util.Date endAt;
    /**
     * 机构显示标志
     */
    private Boolean agencyDisplayFlag;
    /**
     * 图片链接
     */
    private String imgUrl;
    /**
     * 自动添加资产标志
     */
    private Boolean autoFlag;
    /**
     * 省id
     */
    private Integer provinceId;
    /**
     * 市id
     */
    private Integer cityId;
    /**
     * 区县id
     */
    private Integer areaId;
    /**
     * 资产类型
     */
    private String category;
    /**
     * 商品分类
     */
    private String busType;
    /**
     * 状态
     */
    private String status;
    /**
     * 活动id，逗号分隔
     */
    private String activityIds;
    /**
     * 排序号
     */
    private Integer orderNum;

    private List<Map> activityList = Collections.EMPTY_LIST;

    private Boolean onlineFlag;
}
