package com.tzCloud.core.facade.caseMatching.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zxiao
 * @Title: ViewCourtNumResp
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/4/19 10:20
 */
@Data
public class ViewCourtNumResp implements Serializable {
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
