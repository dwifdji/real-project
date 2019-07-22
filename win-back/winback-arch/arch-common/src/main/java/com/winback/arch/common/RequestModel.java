package com.winback.arch.common;

import com.winback.arch.common.enums.SideType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求通用信息
 */
@Data
public class RequestModel implements Serializable {
    /**
     * 每页数量.
     */
    private int perPage = 20;

    /**
     * 第几页
     */
    private int page = 1;

    /**
     * 请求来源
     */
    private SideType sideType = SideType.UNKNOWN;
    /**
     * 当前登录id
     */
    private Integer loginId;
}
