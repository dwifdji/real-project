package com.winback.core.vo.systemsite;

import com.winback.core.commons.constants.CaseEnum;

import java.io.Serializable;

public class CaseStatusMsgVO implements Serializable {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 案件进度
     */
    private String caseStatus;

    /**
     * 案件进度描述
     */
    private String caseStatusDesc;
    /**
     * 消息体
     */
    private String msgBody;

    /**
     * 0开通1关闭
     */
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCaseStatusDesc() {
        return caseStatusDesc;
    }

    public void setCaseStatusDesc(String caseStatusDesc) {
        this.caseStatusDesc = caseStatusDesc;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
