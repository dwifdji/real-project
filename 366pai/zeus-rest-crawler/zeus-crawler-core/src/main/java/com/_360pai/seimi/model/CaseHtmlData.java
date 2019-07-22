package com._360pai.seimi.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xiaolei
 * @create 2019-01-29 13:34
 */
@Data
@Entity
@Table(name = "case_html_data")
public class CaseHtmlData {
    @Id
    @Column(name = "id")
    private Integer id;
    @Column(name = "doc_id")
    private String docId;
    @Column(name = "title")
    private String title;
    @Column(name = "pub_date")
    private String pub_date;
    @Column(name = "html")
    private String html;
    @Column(name = "remove_html")
    private String remove_html;
}
