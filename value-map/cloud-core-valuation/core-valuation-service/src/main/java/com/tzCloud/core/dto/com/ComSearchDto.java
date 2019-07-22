package com.tzCloud.core.dto.com;

import lombok.Data;

import java.io.Serializable;

/**
 *地点搜索
 */
@Data
public class ComSearchDto implements Serializable {
    private String name;//纬度
    private String lat;//纬度
    private String lng;
    private String city;
    private String district;


}
