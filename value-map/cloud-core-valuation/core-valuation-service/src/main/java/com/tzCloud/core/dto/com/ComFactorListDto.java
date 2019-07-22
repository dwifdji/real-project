package com.tzCloud.core.dto.com;

import lombok.Data;

import java.io.Serializable;

/**
 *地点搜索
 */
@Data
public class ComFactorListDto implements Serializable {
    private String primaryKey;//
    private String name;
    private String address;
    private String phone;
    private String distance;
    private String lat;
    private String lng;



}
