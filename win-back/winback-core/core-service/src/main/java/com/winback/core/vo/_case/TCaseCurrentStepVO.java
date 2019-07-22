package com.winback.core.vo._case;

import java.io.Serializable;

public class TCaseCurrentStepVO implements Serializable {

    private String caseNo;
    private String accuser;
    private String accused;
    private String stepName;
    private String lawyer;


    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getAccuser() {
        return accuser;
    }

    public void setAccuser(String accuser) {
        this.accuser = accuser;
    }

    public String getAccused() {
        return accused;
    }

    public void setAccused(String accused) {
        this.accused = accused;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getLawyer() {
        return lawyer;
    }

    public void setLawyer(String lawyer) {
        this.lawyer = lawyer;
    }
}
