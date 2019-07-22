package com._360pai.core.facade.account.resp;

import java.io.Serializable;

/**
 * @author xdrodger
 * @Title: PersonaResp
 * @ProjectName zeus-parent
 * @Description:
 * @date 29/08/2018 13:37
 */
public class PersonaResp implements Serializable {

    private Integer personaId;

    private String uId;

    private Boolean isAdmin;

    private String mobile;

    public Integer getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Integer personaId) {
        this.personaId = personaId;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
