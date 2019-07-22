package com._360pai.gateway.controller.req.fdd;

import java.io.Serializable;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/28 19:03
 */
public class FddCallBackReq  implements Serializable {

    private String transaction_id;

    private String contract_id;

    private String result_code;

    private String result_desc;

    private String download_url;

    private String viewpdf_url;

    private String timestamp;

    private String msg_digest;

    private String type; //回调类型 1 法大大主动回调 2 job 回调


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getResult_desc() {
        return result_desc;
    }

    public void setResult_desc(String result_desc) {
        this.result_desc = result_desc;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getViewpdf_url() {
        return viewpdf_url;
    }

    public void setViewpdf_url(String viewpdf_url) {
        this.viewpdf_url = viewpdf_url;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMsg_digest() {
        return msg_digest;
    }

    public void setMsg_digest(String msg_digest) {
        this.msg_digest = msg_digest;
    }
}
