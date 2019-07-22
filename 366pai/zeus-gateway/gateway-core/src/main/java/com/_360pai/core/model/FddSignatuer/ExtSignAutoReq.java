package com._360pai.core.model.FddSignatuer;

/**
 * 描述：自动签署合同
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 13:14
 */
public class ExtSignAutoReq {

    private String transaction_id;//交易号

    private String contract_id;//合同id

    private String customer_id;//客户编号

    private String client_role;//客户角色 1-接入平台 2-担保公司3-投资人 4-借款人

    private String doc_title;//文档id

    private String position_type;//定位类型 0-关键字（默认）1-坐标

    private String sign_keyword;//定位关键字

    private String keyword_strategy;//关键字签章策略 0：所有关键字签章 （默认）；1：第一个关键字签章 ；2：最后一个关键字签章；

    private String signature_positions;//定位坐标

    private String notify_url;//签章异步通知地址

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getContract_id() {
        return contract_id;
    }

    public void setContract_id(String contract_id) {
        this.contract_id = contract_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getClient_role() {
        return client_role;
    }

    public void setClient_role(String client_role) {
        this.client_role = client_role;
    }

    public String getDoc_title() {
        return doc_title;
    }

    public void setDoc_title(String doc_title) {
        this.doc_title = doc_title;
    }

    public String getPosition_type() {
        return position_type;
    }

    public void setPosition_type(String position_type) {
        this.position_type = position_type;
    }

    public String getSign_keyword() {
        return sign_keyword;
    }

    public void setSign_keyword(String sign_keyword) {
        this.sign_keyword = sign_keyword;
    }

    public String getKeyword_strategy() {
        return keyword_strategy;
    }

    public void setKeyword_strategy(String keyword_strategy) {
        this.keyword_strategy = keyword_strategy;
    }

    public String getSignature_positions() {
        return signature_positions;
    }

    public void setSignature_positions(String signature_positions) {
        this.signature_positions = signature_positions;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }
}
