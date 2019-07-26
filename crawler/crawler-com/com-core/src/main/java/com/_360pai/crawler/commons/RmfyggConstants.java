package com._360pai.crawler.commons;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: RmfyggConstants
 * @ProjectName crawler-parent
 * @Description:
 * @date 2019/4/9 09:28
 */
public class RmfyggConstants {

    /**
     * 默认公告类型
     */
    public static final String DEFAULT_NOTICE_TYPE_VAL = "全部";

    /**
     * 公告类型
     */
    public static final Set<String> noticeTypeVal = new LinkedHashSet<String>() {{
        add("起诉状、上诉状副本");
        add("开庭传票");
        add("裁判文书");
        add("公示催告");
        add("破产文书");
        add("宣告失踪、死亡");
        add("执行文书");
        add("无主财产认领公告");
        add("起诉状副本及开庭传票");
        add("其他");
        add("更正");
        add("遗失声明");
        add("司法鉴定书");
        add("海事文书");
        add("仲裁文书");
        add("拍卖公告");
        add("清算公告");
        add("行政处罚通知书");
        add("版权公告");
        add("公益诉讼");
        add("送达公告");
    }};


    /**
     * 列表接口
     */
    public static final String LIST_URL = "https://rmfygg.court.gov.cn/web/rmfyportal/noticeinfo";
    /**
     * 详情接口
     */
    public static final String DETAIL_URL = "https://rmfygg.court.gov.cn/web/rmfyportal/noticedetail";

}
