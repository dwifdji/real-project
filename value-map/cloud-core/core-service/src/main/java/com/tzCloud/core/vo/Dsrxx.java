package com.tzCloud.core.vo;

import java.util.Objects;

public class Dsrxx {
    private String identity;
    private String name;

    public static Dsrxx instance(String identity, String name) {
        Dsrxx dsrxx = new Dsrxx();
        dsrxx.identity = identity;
        dsrxx.name = name;
        return dsrxx;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentity() {
        return identity;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dsrxx)) return false;
        Dsrxx dsrxx = (Dsrxx) o;
        return Objects.equals(identity, dsrxx.identity) &&
                Objects.equals(name, dsrxx.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identity, name);
    }
}
