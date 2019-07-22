package com._360pai.core.facade.disposal.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-09-17 20:46
 */
public class DisposalServiceInvestInfo implements Serializable {
    private static final long serialVersionUID = 8021788992601596714L;

    private Date biddingTime;
    private String companyName;
    private String disposalName;

    public Date getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(Date biddingTime) {
        this.biddingTime = biddingTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDisposalName() {
        return disposalName;
    }

    public void setDisposalName(String disposalName) {
        this.disposalName = disposalName;
    }
}
