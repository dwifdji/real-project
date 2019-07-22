package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "soyin_contract")
public class SoYinContract {

    @Id
    @GeneratedValue()
    private Integer id;
    /**
     * 商品ID
     */
    @Column(name = "big_category")
    private String bigCategory;
    /**
     * 商品ID
     */
    @Column(name = "small_category")
    private String smallCategory;
    /**
     * 标题
     */
    private String name;
    /**
     * 起拍价
     */
    @Column(name = "model_html")
    private String modelHtml;
    /**
     * 参考价
     */
    @Column(name = "file_url")
    private String fileUrl;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
