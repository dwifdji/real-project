package com.tzCloud.core.facade.legalEngine.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.tzCloud.arch.common.utils.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: CaseDetailVo
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/5/24 14:04
 */
@Data
public class CaseDetailVo implements Serializable {
    /**
     * 文书ID
     */
    private String docId;
    /**
     * 案件类型（1 刑事案件 2 民事案件 3 行政案件 4 赔偿案件 5 执行案件），对应cpwsw_item表ajlx
     */
    private String type;
    /**
     * 文书性质（1 判决书 2 裁定书 3 调解书 4 决定书 5 通知书 6 批复 7 答复 8 涵 9 令 10 其他）
     */
    private String judgementType;
    /**
     * 裁判日期，对应cpwsw_item表cprq
     */
    @JSONField(format= DateUtil.NORM_DATE_PATTERN)
    private java.util.Date judgementDate;
    /**
     * 裁判年份
     */
    private Integer judgementYear;
    /**
     * 案件名称，对应cpwsw_item表ajmc
     */
    private String title;
    /**
     * 裁判要旨段原文，对应cpwsw_item表cpyzdyw
     */
    private String courtOpinion;
    /**
     * 审判程序，对应cpwsw_item表spcx
     */
    private String trialRound;
    /**
     * 案号，对应cpwsw_item表ah
     */
    private String caseNumber;
    /**
     * 法院信息
     */
    private String courtName;
    /**
     * 案由
     */
    private String briefName;



    /**
     * 合议庭
     */
    private String collegialPanel;
    /**
     * 上诉人
     */
    private String prosecutor;
    /**
     * 被上诉人
     */
    private String defendant;
    /**
     * 上诉人代理律师
     */
    private String prosecutorLawyer;
    /**
     * 被上诉人代理律师
     */
    private String defendantLawyer;
    /**
     * 正文
     */
    private String caseMainBody;

    private List<Map<String,Object>> prosecutorLawyerList;
    private List<Map<String,Object>> defendantLawyerList;
    private Map<String, List<String>> container;

    private List<Map<String, Object>> lawFirmList;
    /**
     * 是否关注标识
     */
    private String attentionFlag = "0";
}
