package com._360pai.arch.common;

import com._360pai.arch.common.enums.SideType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author: whisky_vip
 * @Date: 2018/8/15 12:48
 * @Description:
 */
@Getter
@Setter
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

    private SideType.Operate operate = SideType.Operate.ADD;
}
