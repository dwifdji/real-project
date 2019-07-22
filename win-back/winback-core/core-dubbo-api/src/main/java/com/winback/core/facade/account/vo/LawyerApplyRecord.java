package com.winback.core.facade.account.vo;

import com.winback.core.facade._case.vo.CaseBrief;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: LawyerApplyRecord
 * @ProjectName winback
 * @Description:
 * @date 2019/1/28 19:11
 */
@Data
public class LawyerApplyRecord implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 账户id
     */
    private java.lang.Integer accountId;
    /**
     * 手机号
     */
    private java.lang.String mobile;
    /**
     * 邮箱
     */
    private java.lang.String email;
    /**
     * 姓名
     */
    private java.lang.String name;
    /**
     * 头像
     */
    private java.lang.String headImgUrl;
    /**
     * 身份证号码
     */
    private java.lang.String certificateNumber;
    /**
     * 身份证正面照
     */
    private java.lang.String certificateFrontImg;
    /**
     * 身份证背面照
     */
    private java.lang.String certificateBackImg;
    /**
     * 身份证起始日期
     */
    private java.util.Date certificateBegin;
    /**
     * 身份证结束日期
     */
    private java.util.Date certificateEnd;
    /**
     * 律师证
     */
    private java.lang.String lawyerLicenseImg;
    /**
     * 律师执业证号
     */
    private java.lang.String lawyerLicenseNumber;
    /**
     * 所属律所
     */
    private java.lang.String lawFirm;
    /**
     * 从业年限
     */
    private java.lang.String workYear;
    /**
     * 业务区省code
     */
    private java.lang.String businessProvinceCode;
    /**
     * 业务区域市code
     */
    private java.lang.String businessCityCode;
    /**
     * 业务区区县code
     */
    private java.lang.String businessAreaCode;
    /**
     * 业务区域省
     */
    private java.lang.String businessProvinceName;
    /**
     * 业务区域市
     */
    private java.lang.String businessCityName;
    /**
     * 业务区域区县
     */
    private java.lang.String businessAreaName;
    /**
     * 业务区域
     */
    private String businessArea;
    /**
     * 收款银行
     */
    private java.lang.String bankName;
    /**
     * 收款银行账户
     */
    private java.lang.String bankAccountNumber;
    /**
     * 申请状态(PENDING：审核中,APPROVED：审核通过,REJECT：审核拒绝)
     */
    private java.lang.String status;
    /**
     * 申请状态描述
     */
    private java.lang.String statusDesc;
    /**
     * 原因
     */
    private java.lang.String reason;
    /**
     * 审核人Id
     */
    private java.lang.Integer operatorId;
    /**
     * 审核人
     */
    private java.lang.String operator;
    /**
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 擅长的案由列表
     */
    private List<CaseBrief> caseBriefList = Collections.EMPTY_LIST;
    private List<String> caseBriefIdList = Collections.EMPTY_LIST;
}
