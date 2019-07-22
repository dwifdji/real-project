package com.tzCloud.arch.common;

import com.tzCloud.arch.common.enums.SideType;
import lombok.Data;

/**
 * 平台请求通用信息
 */
@Data
public class PlatformReq extends RequestModel {
    /**
     * 请求来源
     */
    private SideType sideType = SideType.PLATFORM;
}
