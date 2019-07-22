package com._360pai.core.model.FddSignatuer;

import com._360pai.arch.common.ResponseModel;

/**
 * 描述：法大大会员开户请求参数
 *
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/13 12:55
 */
public class SignatuerResp extends ResponseModel {




    private String download_url;//合同下载地址

    private String view_pdf_url;//合同查看地址

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
}
