package com.tzCloud.core.dto.com;

import lombok.Data;
import org.apache.commons.collections4.list.TreeList;

import java.io.Serializable;
import java.util.List;

/**
 *城市定位返回
 */
@Data
public class ComCoordinateDto implements Serializable {
    private String lat;//纬度
    private String lng;
    private String city;
    private String district;


}
