package com.tzCloud.arch.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author xdrodger
 * @Title: ListResp
 * @ProjectName zeus
 * @Description:
 * @date 21/09/2018 10:56
 */
@Getter
@Setter
public class ListResp<T> extends BaseResp {
    private List<T> list;
}