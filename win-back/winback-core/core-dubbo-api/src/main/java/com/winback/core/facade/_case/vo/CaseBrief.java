package com.winback.core.facade._case.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CaseBrief
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 13:40
 */
@Data
public class CaseBrief implements Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 案由大类id
     */
    private Integer bigBriefId;
    /**
     * 名称
     */
    private String name;
    /**
     * 图片链接
     */
    private String imgUrl;
    /**
     * 是否展示（0 否 1 是）
     */
    private Boolean display;
    /**
     * 排序号
     */
    private Integer orderNum;
}
