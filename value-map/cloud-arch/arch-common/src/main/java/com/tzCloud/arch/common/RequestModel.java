package com.tzCloud.arch.common;

import com.tzCloud.arch.common.enums.SideType;
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

    /**
     * 当前登录id
     */
    private Integer loginId;

    //限制分页条件
    public int getPerPage() {
        return perPage > 20 ? 20 : perPage;
    }

    //限制分页条件
    public int getPage() {
        return page > 10 ? 10 : page;
    }

}
