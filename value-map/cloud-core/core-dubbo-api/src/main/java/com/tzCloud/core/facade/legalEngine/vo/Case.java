package com.tzCloud.core.facade.legalEngine.vo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import netscape.javascript.JSObject;

import java.io.Serializable;
import java.util.List;

/**
 * @author xdrodger
 * @Title: Case
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-25 15:16
 */
@Data
public class Case implements Serializable {
    /**
     * 主键
     */
    private Integer id;
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
    ///**
    // * 法院名称，对应cpwsw_item表fymc
    // */
    //private String courtName;
    ///**
    // * 法院名称，对应cpwsw_item表fymc
    // */
    //private String courtLevel;

    /**
     * 法院信息
     */
    private Court court;
    ///**
    // * 案由，对应cpwsw_item表brief,关联查询t_tree_content的id，field包含案由
    // */
    //private Integer briefId;
    /**
     * 案由
     */
    private Brief brief;
    /**
     * 审判人信息
     */
    private JudgePerson judgePerson;
    /**
     * 律师
     */
    private JSONArray lawyers;
}
