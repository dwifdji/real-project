
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分26秒
 */
@Data
public class ArticleReq extends RequestModel {

    public static String ONLINE = "ONLINE";  //上线
    public static String OFFLINE = "OFFLINE"; //下线

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String title;
    /**
     *
     */
    private String content;
    /**
     *
     */
    private java.util.Date createAt;
    /**
     *
     */
    private Integer categoryId;
    /**
     *
     */
    private String status;
    /**
     *
     */
    private String orderNum;

    /**
     *
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     */
    public String getContent() {
        return content;
    }

    /**
     *
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     */
    public java.util.Date getCreateAt() {
        return createAt;
    }

    /**
     *
     */
    public void setCreateAt(java.util.Date createAt) {
        this.createAt = createAt;
    }

    /**
     *
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
