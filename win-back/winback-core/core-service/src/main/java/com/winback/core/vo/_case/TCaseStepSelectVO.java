package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseStepSelectVO implements Serializable {
    private String name;

    private String id;

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
