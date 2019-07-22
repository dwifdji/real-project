package com._360pai.core.facade.activity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: AlbumVo
 * @ProjectName zeus
 * @Description:
 * @date 07/09/2018 09:18
 */
@Data
public class AlbumVo implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private java.util.Date createdAt;
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String img;
    /**
     *
     */
    private java.util.Date beginAt;
    /**
     *
     */
    private java.util.Date endAt;
    /**
     *
     */
    private Boolean isOnline;
    /**
     *
     */
    private String remark;
    /**
     *
     */
    private java.util.Date previewAt;
    /**
     *
     */
    private String description;
    /**
     *
     */
    private String detailImg;

    private Integer activityNumber;

    private List<Map> activityList;

    private String linkUrl;
}
