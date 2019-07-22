package com.tzCloud.arch.common.constant;

/**
 * @author xdrodger
 * @Title: RedisKeyConstant
 * @ProjectName zeus
 * @Description:
 * @date 22/09/2018 14:23
 */
public class RedisKeyConstant {
    /**
     * 数据字典相关
     */
    public static final String SYSTEM_DICT_ASSET_PROPERTY = SystemConstant.tzCloud + "_system_dict_asset_property";
    public static final String SYSTEM_DICT_ASSET_CATEGORY = SystemConstant.tzCloud + "_system_dict_asset_category";

    public static final String COURT_NAME = SystemConstant.tzCloud + "_court_name_";

    public static final String BRIEF_LEVEL = SystemConstant.tzCloud + "_brief_level_";

    public static final String BRIEF = SystemConstant.tzCloud + "_brief_";
    public static final String LAW_FIRM_SHORT = SystemConstant.tzCloud + "_law_firm_short_";
    public static final String LAW_FIRM_ANALYSIS = SystemConstant.tzCloud + "_law_firm_analysis_";
    public static final String LAWYER_INTRO = SystemConstant.tzCloud + "_lawyer_intro_";
    public static final String LAWYER_CASE_STATISTICS = SystemConstant.tzCloud + "_lawyer_case_statistics_";
    public static final String LAWYER_CASE_list = SystemConstant.tzCloud + "_lawyer_case_list_";

    /**
     * 表缓存相关
     */
    public static final String TABLE = SystemConstant.tzCloud + "_table_";

    /**
     * 热门案列key
     */
    public static final String HOT_CASE_KEY = SystemConstant.tzCloud + "_hot_case_key";
    /**
     * 热门案列值
     */
    public static final String HOT_CASE_VALUE = SystemConstant.tzCloud + "_hot_case_value";
}
