
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年09月25日 10时40分37秒
 */
public class TPlatformArticleTypeReq extends RequestModel {

    public static final Integer online = 1;
    public static final Integer down = 0;

    public static final Integer jrtt = 1;
    public static final Integer zcdmb = 2;

    /**
     * 自增
     */
    private Integer id;
    /**
     * 标题
     */
    private String articleTitle;
    /**
     * 图片
     */
    private String image;
    /**
     * 状态  1:上线  0:下线
     */
    private Integer status;
    /**
     * 类别  1:首页今日头条 2:资产大买办 3新闻中心
     */
    private Integer type;
    /**
     * 0 在新闻中心展示 1不在新闻中心
     */
    private Integer showNews;
    /**
     * 排序字段
     */
    private Integer orderNum;

    /**
     * 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 标题
     */
    public String getArticleTitle() {
        return articleTitle;
    }

    /**
     * 标题
     */
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    /**
     * 图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 图片
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 状态  1:上线  0:下线
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态  1:上线  0:下线
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 类别  1:首页今日头条 2:资产大买办
     */
    public Integer getType() {
        return type;
    }

    /**
     * 类别  1:首页今日头条 2:资产大买办
     */
    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getShowNews() {
        return showNews;
    }

    public void setShowNews(Integer showNews) {
        this.showNews = showNews;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
