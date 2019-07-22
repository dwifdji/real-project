package com.tzCloud.core.facade.legalEngine.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: CaseHtmlDataVo
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/18 13:39
 */
@Data
public class CaseHtmlDataVo implements Serializable {
    /**
     * 自增ID
     */
    private Integer id;
    /**
     * 文书ID
     */
    private String docId;
    /**
     * 标题
     */
    private String title;
    /**
     * 公示时间
     */
    private String pubDate;
    /**
     * 正文
     */
    private String html;
    /**
     * 去除样式的正文
     */
    private String removeHtml;
    /**
     * 创建时间
     */
    private java.util.Date createdAt;
    /**
     * 修改时间
     */
    private java.util.Date updatedAt;

    private String spcx;

    private String lawyerDocId;

    private String caseDocId;
}
