package com.winback.core.facade.contract.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: ContractInvoice
 * @ProjectName winback
 * @Description:
 * @date 2019/2/13 19:39
 */
@Data
public class ContractInvoice implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 纸质/电子
     */
    private String type;
    /**
     * 纸质/电子
     */
    private String typeDesc;
    /**
     * 抬头
     */
    private String title;
    /**
     * 抬头类型（user：个人，company：企业）
     */
    private String titleType;
    /**
     * 抬头类型（user：个人，company：企业）
     */
    private String titleTypeDesc;
    /**
     * 发票内容
     */
    private String content;
    /**
     * 纳税人识别号
     */
    private String taxpayerIdentificationNumber;
    /**
     * 金额
     */
    private java.math.BigDecimal amount;
    /**
     * 地址
     */
    private String address;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 收款银行
     */
    private String bankName;
    /**
     * 收款银行账户
     */
    private String bankAccountNumber;
    /**
     * 备注信息
     */
    private String memo;
    /**
     * 地址和电话
     */
    private java.lang.String addressPhone;
    /**
     * 开户行和账号
     */
    private java.lang.String bankAccount;
    /**
     * 	发票编号
     */
    private String invoiceNo;
    /**
     * 发票图片
     */
    private String invoiceImgUrl;
    /**
     * 申请状态(PENDING：审核中,APPROVED：审核通过,REJECT：审核拒绝)
     */
    private String status;
    /**
     * 申请状态描述
     */
    private String statusDesc;
    /**
     * 原因
     */
    private String reason;
    /**
     * 审核人Id
     */
    private Integer operatorId;
    /**
     * 审核人
     */
    private String operator;
    /**
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    private String mobile;
    /**
     * 关联订单编号列表
     */
    private List<String> orderNos;
    /**
     * 关联订单列表
     */
    private List<ContractOrder> orders;
}
