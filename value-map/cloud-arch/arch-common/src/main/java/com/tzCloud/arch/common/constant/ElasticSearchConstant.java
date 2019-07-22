package com.tzCloud.arch.common.constant;

/**
 * @author xdrodger
 * @Title: ElasticSearchConstant
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-25 14:34
 */
public class ElasticSearchConstant {

    /**
     * 默认类型
     */
    public static final String DEFAULT_TYPE = "_doc";

    /**
     * 案件
     */
    public static final String CASE_INDEX = "case";
    /**
     * 律师
     */
    public static final String LAWYER_INDEX = "lawyer";
    /**
     * 法院
     */
    public static final String COURT_INDEX = "court";

    /**
     * 律所
     */
    public static final String LAW_FIRM_INDEX = "law_firm";

    /**
     * 案件当事人信息
     */
    public static final String CASE_DSRXX = "case_dsrxx";
    /**
     * 律师和案件关联
     */
    public static final String LAWYER_CASE_INDEX = "lawyer_case";

    /**
     * 法律快车法院
     */
    public static final String LAWTIME_COURT_INDEX = "lawtime_court";


    public static final  String QUERY_TYPE_MATCH = "match";

    public static final  String QUERY_TYPE_MATCH_PHRASE = "match_phrase";

    public static final  String QUERY_TYPE_TERM = "term";

    public static final  String QUERY_TYPE_QUERY_STRING = "query_string";

    public static final  String SearchType_keyword = "keyword";

    public static final  String SearchType_searchWord = "searchWord";


    //keyword("keyword", "关键字"),
    //searchWord("searchWord", "搜索词"),
    //caseBrief("caseBrief", "案由"),
    //trialRound("trialRound", "审批程序"),
    //judgementType("judgementType", "文书性质"),
    //trialYear("trialYear", "裁判年份"),
    //courtName("court.name", "法院名称"),
    //courtType("court.type", "法院层级"),
    //courtProvince("court.province", "法院省份"),
    //courtCity("court.city", "法院城市")


    /**
     * 聚合size大小
     */
    public static final Integer AGGS_SIZE = 99999;
}
