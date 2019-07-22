package com._360pai.gateway.controller.req.fdd;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：租赁权委托协议
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
  */
@Data
public class GenerateLeaseDelegationReq implements Serializable {

    private String activityCode;//合同编号
    private String seller;//委托方
    private String lotName;//标的名称
    private String auctionMethod;//拍卖方式
    private String reservePrice;//保留价
    private String beginTime;//开拍时间
    private String endTime;//开拍结束时间
    private String bankAccountName;//账号名
    private String bankName;//银行名称
    private String branchBankName;//支行名称
    private String bankAccount;//银行账号
    private String sellerIdNumber;//委托方证件及号码
    private String sellerAddress;//委托方住址
    private String sellerLegalRep;//法定代表人
    private String sellerPhone;//联系电话

    private Date leaseStartTime;//租赁开始时间
    private Date leaseEndTime;//租赁结束时间
    private String entrustName;//委托代理人

    private String entrustPhone;//委托代理人联系电话

}
