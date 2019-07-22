package com._360pai.gateway.controller.req.email;

import java.io.File;
import java.io.Serializable;
import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/30 19:27
 */
public class EmailSendReq implements Serializable {

    public static final String CONTENT_TYPE = "2";

    public static final String TEMPLATE_TYPE = "1";

    public static final String EXCEPTION_TYPE = "10";
    private List<String> email;//发送邮箱

    private List<String> copyEmail;//抄送的邮箱号码

    private String sendType;//发送方式 1 模板方式 2 内容方式

    private String templateCode;//邮件模板

    private String partyId;//发送用户id

    private String templateParam;//邮件模板参数

    private String content; //内容发送方式的内容

    private String title; //邮件的标题

    private Integer id; //邮件id

    private List<File> files;


    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getCopyEmail() {
        return copyEmail;
    }

    public void setCopyEmail(List<String> copyEmail) {
        this.copyEmail = copyEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getTemplateParam() {
        return templateParam;
    }

    public void setTemplateParam(String templateParam) {
        this.templateParam = templateParam;
    }
}
