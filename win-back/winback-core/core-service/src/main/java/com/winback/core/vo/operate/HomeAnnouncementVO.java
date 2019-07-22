package com.winback.core.vo.operate;

import java.io.Serializable;

public class HomeAnnouncementVO implements Serializable {


    private String id;

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
