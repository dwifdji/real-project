package com.tzCloud.core.facade.caseMatching.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * @author zxiao
 * @Title: ViewCourtNumReq
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 10:10
 */
@Data
public class ViewCourtNumReq extends RequestModel {

    /**
     * 主键
     */
    private Integer id;
    /**
     * 法院名称
     */
    private String courtName;
    /**
     * 法院文书统计数量
     */
    private Integer courtNum;
    /**
     * 法院省份
     */
    private String province;
    /**
     * 法院城市
     */
    private String cityName;

}
