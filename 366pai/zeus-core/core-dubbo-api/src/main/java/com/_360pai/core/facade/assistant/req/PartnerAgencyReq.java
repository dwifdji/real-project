
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;
import lombok.Data;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
@Data
public class PartnerAgencyReq extends RequestModel {

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
    private String url;
    /**
     *
     */
    private String img;
    /**
     *
     */
    private Integer orderNum;
    private Integer agencyId;
    private String agencyName;
    private String agencyCode;
    private String logoUrl;

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
    public String getUrl() {
        return url;
    }

    /**
     *
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     *
     */
    public String getImg() {
        return img;
    }

    /**
     *
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     *
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     *
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

}
