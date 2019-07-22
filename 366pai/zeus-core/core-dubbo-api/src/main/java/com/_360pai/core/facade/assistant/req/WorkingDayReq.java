
package com._360pai.core.facade.assistant.req;

import com._360pai.arch.common.RequestModel;

/**
 * <p>封装实体bean</p>
 *
 * @author Generator
 * @date 2018年08月10日 18时47分27秒
 */
public class WorkingDayReq extends RequestModel {

    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private java.util.Date date;
    /**
     *
     */
    private Boolean isWorking;
    /**
     *
     */
    private String name;

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
    public java.util.Date getDate() {
        return date;
    }

    /**
     *
     */
    public void setDate(java.util.Date date) {
        this.date = date;
    }

    /**
     *
     */
    public Boolean getIsWorking() {
        return isWorking;
    }

    /**
     *
     */
    public void setIsWorking(Boolean isWorking) {
        this.isWorking = isWorking;
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

}
