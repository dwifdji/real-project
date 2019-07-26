package com._360pai.crawler.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author xdrodger
 * @Title: Rmfygg
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/8 19:26
 */
@Data
@Entity
@Table(name = "t_rmfygg")
public class Rmfygg {
    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 公告名称
     */
    @Column(name = "notice_title")
    private String noticeTitle;
    /**
     * 被告
     */
    @Column(name = "defendant")
    private String defendant;
    /**
     * 公告内容
     */
    @Column(name = "notice_content")
    private String noticeContent;
    /**
     * 法院
     */
    @Column(name = "court")
    private String court;
    /**
     * 刊登版面
     */
    @Column(name = "publish_page")
    private String publishPage;
    /**
     * 刊登日期
     */
    @Column(name = "publish_date")
    private String publishDate;
    /**
     * 上传日期
     */
    @Column(name = "upload_date")
    private String uploadDate;
    /**
     * 公告id
     */
    @Column(name = "notice_id")
    private String noticeId;
    /**
     * 公告类型
     */
    @Column(name = "notice_type")
    private String noticeType;
    /**
     * 公告code
     */
    @Column(name = "notice_code")
    private String noticeCode;
    /**
     * 公告code，加密，下载pdf用
     */
    @Column(name = "notice_code_enc")
    private String noticeCodeEnc;
    /**
     * 省
     */
    @Column(name = "province")
    private String province;
    /**
     * 是否删除（0否 1是）
     */
    @Column(name = "delete_flag")
    private Boolean deleteFlag;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
