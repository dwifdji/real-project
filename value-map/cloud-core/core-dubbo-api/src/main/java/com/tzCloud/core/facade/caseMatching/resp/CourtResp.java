package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/26 10:26
 */
@Data
public class CourtResp implements Serializable {

    private Integer caseNum;
    private String courtName;
//    private Integer id;
    /**
     * 法院名称
     */
    private String name;
    /**
     * 法院类型（基层法院，中层法院，高级法院，最高法院）
     */
    private String type;
    /**
     * 法院地址
     */
    private String address;
    /**
     * 法院描述
     */
    private String remark;
    /**
     * 法院地址
     */
    private String imageUrl;
    /**
     * 电话
     */
    private String phone;

    /**
     * 本院案件数量
     */
    private Integer caseload;

    /**
     * 本院法官数量
     */
    private Integer judgeNum;

    /**
     * 案由
     */
    private List<Map<String, Object>> briefs;

    /**
     * 法院案由
     */
    private List<Map<String, Object>> courtBriefs;

    /**
     * 法官集合
     */
    private List<Map<String, Object>> judgePerson;

    /**
     * 裁判年限集合
     */
    private List<Map<String, Object>> judgeDate;

    //案件类型
    private List<Map<String, Object>> typeList;

    //文属性质
    private List<Map<String, Object>> judgeTypeList;

    //参与律所
    private List<Map<String, Object>> lawFirmList;
}
