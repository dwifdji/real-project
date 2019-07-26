package com._360pai.crawler.model.jdPm;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "jd_pm_search_url")
public class JDSearchUrl {

        @Id
        @GeneratedValue()
        private Integer id;

        /**
         * 出价时间
         */
        @Column(name = "province")
        private String province;
        /**
         * 是否具有优势
         */
        @Column(name = "city")
        private String city;

        /**
         * 拍品编码
         */
        @Column(name = "category_name")
        private String categoryName;

        /**
         * 出价价格
         */
        @Column(name = "city_code")
        private String cityCode;

        /**
         * 用户名
         */
        @Column(name = "search_url")
        private String searchUrl;


        /**
         * 保证金
         */
        @Column(name = "total_num")
        private Integer totalNum;

        /**
         * 创建时间
         */
        @Column(name = "create_time")
        private Date createTime;


        /**
         * 创建时间
         */
        @Column(name = "status")
        private String status;

        /**
         * 更新时间
         */
        @Column(name = "update_time")
        private Date updateTime;


}
