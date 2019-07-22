package com.tzCloud.core.facade.legalEngine.req;

import com.tzCloud.arch.common.PlatformReq;
import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * @author xdrodger
 * @Title: CaseSearchReq
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/24 15:33
 */
public class CaseSearchReq {

    @Data
    public static class SearchListReq extends PlatformReq {

        /**
         * 排序（default_desc：综合降序，judgementDate_desc：裁判日期降序，courtType_desc：法院层级降序）
         */
        private String orderBy;

        private List<CaseSearchCondition> conditions = Collections.EMPTY_LIST;
        /**
         * 排除侧边栏
         */
        private boolean excludeSidebar = false;
    }

    @Data
    public static class BaseReq extends PlatformReq {

        /**
         * 案件id
         */
        private String docId;
    }
}
