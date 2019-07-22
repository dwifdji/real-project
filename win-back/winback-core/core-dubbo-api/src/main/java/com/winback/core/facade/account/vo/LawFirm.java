package com.winback.core.facade.account.vo;

import com.winback.core.facade._case.vo.CaseBrief;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: LawyerFirm
 * @ProjectName winback
 * @Description:
 * @date 2019/1/29 14:53
 */
@Data
public class LawFirm implements Serializable {
    /**
     * 主键
     */
    private java.lang.Integer id;
    /**
     * 律所名称
     */
    private java.lang.String name;
    /**
     * 律所类型
     */
    private java.lang.String type;
    /**
     * 律所类型描述
     */
    private java.lang.String typeDesc;
    /**
     * 营业执照图片
     */
    private java.lang.String licenseImg;
    /**
     * 营业执照号
     */
    private java.lang.String licenseNumber;
    /**
     * 法人
     */
    private java.lang.String legalPerson;
    /**
     * 联系人
     */
    private java.lang.String contactPerson;
    /**
     * 联系人手机号
     */
    private java.lang.String contactPhone;
    /**
     * 律所规模
     */
    private java.lang.String teamSize;
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
     * 业务区域市
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
     * 创建时间
     */
    private java.util.Date createTime;
    /**
     * 擅长的案由列表
     */
    private List<CaseBrief> caseBriefList;
    /**
     * 擅长的案由列表
     */
    private List<String> caseBriefIdList;

}
