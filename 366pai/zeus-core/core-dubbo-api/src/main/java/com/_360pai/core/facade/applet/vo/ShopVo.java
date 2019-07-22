package com._360pai.core.facade.applet.vo;

import com._360pai.core.facade.account.vo.CompanyVo;
import com._360pai.core.facade.account.vo.UserVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ShopVo
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/26 17:31
 */
@Data
public class ShopVo implements Serializable {
    /**
     * 店铺id
     */
    private String id = "";
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 页面标题名称
     */
    private String pageTitleName;
    /**
     * 店铺名称
     */
    private String name;
    /**
     * 店铺头像
     */
    private String logoUrl;
    /**
     * 关注数
     */
    private Integer favoriteCount = 0;
    /**
     * 访问数
     */
    private Integer viewCount = 0;
    /**
     * 商品数
     */
    private Integer productCount = 0;
    /**
     * 拍卖商品数量
     */
    private Integer auctionProductCount = 0;
    /**
     * 招商商品数量
     */
    private Integer enrollingProductCount = 0;
    /**
     * 店铺推荐分润比例
     */
    private BigDecimal shopCommissionPercent = BigDecimal.ZERO;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 小程序二维码链接
     */
    private java.lang.String appletQrCode;
    /**
     * 店铺邀请码
     */
    private java.lang.String inviteQrCode;
    /**
     * 店铺推荐码
     */
    private String inviteCode;
    /**
     * 邀请人数
     */
    private Integer inviteCount = 0;

    private String type;

    private UserVo user;

    private CompanyVo company;

    private List<AuctionActivityVo> activityList = Collections.EMPTY_LIST;
    /**
     * 是否认证
     */
    private Boolean isAccountAuth = false;
    /**
     * 是否缴纳服务费
     */
    private Boolean isPayServiceCharge = false;
    /**
     * 是否我的店铺
     */
    private Boolean isMyShop = false;
    /**
     * 是否已关注当前店铺
     */
    private Boolean isFavorShop = false;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 联系人电话
     */
    private java.lang.String contactPhone;
    /**
     * 是否隐藏联系电话中间4位
     */
    private java.lang.Boolean isHideContactPhone;
}
