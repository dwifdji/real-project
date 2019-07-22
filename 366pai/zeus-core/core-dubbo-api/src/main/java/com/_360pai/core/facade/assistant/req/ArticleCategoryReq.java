
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
public class ArticleCategoryReq extends RequestModel {


    public static String ONLINE = "ONLINE"; //上线
    public static String OFFLINE = "OFFLINE"; //下线

    /**
     *
     */
    private Integer id;
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
    public String getName() {
        return name;
    }

    /**
     *
     */
    public void setName(String name) {
        this.name = name;
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
