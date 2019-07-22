package com.winback.core.facade.account.vo;

import com.winback.core.facade._case.vo.CaseBrief;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Lawyer
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 11:10
 */
@Data
public class Lawyer implements Serializable {
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
     * 业务区域市id
     */
    private java.lang.String businessCityCode;
    /**
     * 业务区域区县id
     */
    private java.lang.String businessAreaCode;
    /**
     * 业务区省
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
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 擅长的案由列表
     */
    private List<String> caseBriefIdList;
    /**
     * 擅长的案由列表
     */
    private List<CaseBrief> caseBriefs;

}
