package com._360pai.core.facade.fastway.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaolei
 * @create 2018-11-29 14:24
 */
@Data
public class AuctionVO implements Serializable {
    private static final long serialVersionUID = 3034755705517329513L;

    /**
     * 企业名称
     */
    protected String name;
    /**
     * 统一社会信用码
     */
    protected String license;
    /**
     * 注册地址
     */
    protected String registerAddress;
    /**
     * 办公城市
     */
    protected String cityId;
    /**
     * 办公地址
     */
    protected String address;
    /**
     * 营业执照
     */
    protected String licenseImg;
    /**
     * 开户许可证
     */
    protected String accountLicense;
    /**
     * 授权委托书
     */
    protected String authorizationImg;
    /**
     * 企业logo
     */
    protected String logoUrl;
    /**
     * 拍卖经营批准书
     */
    protected String auctionApproveImg;
    /**
     * 子网站域名
     */
    protected String code;
    /**
     * partyId
     */
    protected Integer partyId;
}
