package com._360pai.gateway.controller.req.fdd;


import java.io.Serializable;

/**
 * 描述：法大大会员开户请求参数
 *
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 12:55
 */
public class FQuerySignStatusResp extends FCommResp {


    private String download_url;//合同下载地址

    private String view_pdf_url;//合同查看地址

    private String transaction_id;//签署的交易号

    private String sign_status;//可签署状态

    private String sign_status_desc;//是否可签描述


    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getView_pdf_url() {
        return view_pdf_url;
    }

    public void setView_pdf_url(String view_pdf_url) {
        this.view_pdf_url = view_pdf_url;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getSign_status() {
        return sign_status;
    }

    public void setSign_status(String sign_status) {
        this.sign_status = sign_status;
    }

    public String getSign_status_desc() {
        return sign_status_desc;
    }

    public void setSign_status_desc(String sign_status_desc) {
        this.sign_status_desc = sign_status_desc;
    }
}
