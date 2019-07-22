package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiaolei
 * @create 2019-04-23 13:17
 */
@Data
public class LawyerSearchVO implements Serializable {

    private static final long serialVersionUID = 1505870043234460584L;
    /**
     * 律师信息
     */
    List<LawyerInfoVO> lawyerInfoVOList;
    /**
     * 服务内容
     */
    private Set<String> briefList;
    /**
     * 省份地区
     */
    private Map<String, Long> lawFirmProvinceMap;
    private List<NameValueVO> lawFirmProvinceList;
    /**
     * 执业年限
     */
    private List<Integer> yearsList;

    private long total;

    private boolean hasNextPage;
}


